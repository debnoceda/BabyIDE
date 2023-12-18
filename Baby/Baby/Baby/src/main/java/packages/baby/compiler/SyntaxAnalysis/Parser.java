package packages.baby.compiler.SyntaxAnalysis;

import packages.baby.compiler.LexicalAnalysis.Token;
import packages.baby.compiler.LexicalAnalysis.TokenType;
import packages.baby.compiler.Symbol;
import packages.baby.compiler.SymbolTable;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

import packages.baby.compiler.CodeGenerator.MIPSAssembly;
import java.io.BufferedWriter;

public class Parser {
    List<Token> tokens;
    Token lookahead;
    StringBuilder message;
    boolean success = true;

    // Attributes used in Code Generation
    String prompt, varName;
    private String fileName;
    boolean isShowLine = false, isNum = false, isID = false, isExpr = false;
    boolean hasOperators = false, isGet = false, isPrompt = false;
    boolean isWord = false; 
    boolean isIDNum = false;
    String mipsCode, filePath, statement;
    MIPSAssembly mips;

    List<Symbol> symbolsToAdd;
    SymbolTable symbolTable;

    // Attributes that contains values to add in symbol
    String identifier;
    TokenType tokenType;  
    String dataType; 

    // Track number of variables declared in one let statement
    int varCount = 0;

    // Determine if var parsed is a previously declared variable reassigned
    boolean isAssignVar = false;
    String assignVar = "";

    boolean isPrinting = false;
    boolean isDeclaredVar = false;

    public Parser(StringBuilder message, List<Token> tokens, String afileName, MIPSAssembly mips) {
        this.message = message;
        this.tokens = tokens;
        symbolTable = new SymbolTable(message);
        lookahead = getToken();
        this.mips = mips;
        setFileName(afileName);
        mipsCode = mips.generateMIPS();
        filePath = writeToFile(fileName, mipsCode);
        resetSymbolValues();
        Program();
        System.out.println("MIPS code has been written in the file: " + fileName);
        symbolTable.printSymbolTable();
    }

    private void resetVarCount(){
        varCount = 0;
    }
    
    private void resetSymbolValues(){
        identifier = null;
        tokenType = null;  
        dataType = null; 
    }

    private void resetAssignVar(){
        String assignVar = "";
    }
    
    private void setFileName(String afileName){
        fileName = afileName.replace(".bby", ".s");
    }
    
    private String writeToFile(String filename, String content) {
        try (FileWriter writer = new FileWriter(filename, false)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename;
    } 

    public static void appendLineToFile(String filePath, String line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(line);
            writer.newLine(); // Append a new line
        } catch (IOException e) {
            System.err.println("Error appending line to the file: " + e.getMessage());
        }
    }

    public String getFilePath (){
        return filePath;
    }
    
    private Token getToken() {
        
        if (tokens.size() > 1)
            return tokens.remove(0);
        else
            return tokens.get(0);
    }
    
    private boolean match(TokenType type){
        if (lookahead.getTokenType() == type) {
            lookahead = getToken();
            return true;
        }
        else {
            return false;
        }
    }

    private void setSuccess(boolean SuccessState) {
        this.success = SuccessState;
    }

    public boolean getSuccess() {
        return this.success;
    }

    private void Error(String lexeme) { // may need other messages if data type is wrong
        message.append("Error at line " +  lookahead.getNLine()+ ": " + lexeme + " expected.\n\n") ;
        setSuccess(false);
    }

    private void VarNotDeclaredError(String var){
        success = false;
        message.append("Error at line " + lookahead.getNLine() + ": variable '" + var + "' not declared.\n\n");
    }


    public String printParseError() {
        return message.toString();
    }

    private void MultipleEnterError(){
        success = false;
        message.append("Error at line " + lookahead.getNLine() + ": multiple enter function in one declaration statement is not allowed.\n\n");
    }

    private void Program() {
        StmtList();
        System.out.println("message: " + message);
        System.out.println("success: " + success);
        System.out.println("TokenType: " + TokenType.EOF);
        if (message.isEmpty() && match(TokenType.EOF)) { // EOF == $
            appendLineToFile(filePath, mips.exitProgram());
            message.append("Parsing successful!");
        }
        else {
            message.append("Parsing Unsuccessful!"); // ehhhhhhhhhhhhh
        }                                             
    }
    
    private void StmtList() {
    	Stmt();
        if (!match(TokenType.SEMICOLON)) {Error("';'");} // SEMICOLON == ;
        StmtList_();
    }
    
    private void StmtList_() {
        if (lookahead.getTokenType() == TokenType.LET || lookahead.getTokenType() == TokenType.ID ||
            lookahead.getTokenType() == TokenType.SHOW || lookahead.getTokenType() == TokenType.SHOWLINE) { // LET == let || ID == Variable names || SHOW == show || SHOWLINE == showline
            StmtList();
        }
    }

    private void Stmt() {
        if (lookahead.getTokenType() == TokenType.LET) { // LET == let
            Declare();
        }
        else if (lookahead.getTokenType() == TokenType.ID) { // ID == Variable names
            Assignment();
        }
        else if (lookahead.getTokenType() == TokenType.SHOW || lookahead.getTokenType() == TokenType.SHOWLINE) { // SHOW == show || SHOWLINE == showline
            Print();
        }
    }

    private void Declare() {
        resetVarCount();
        if (lookahead.getTokenType() == TokenType.LET) { // LET == let
            match(TokenType.LET); // LET == let
            DeclareList();
            if (!match(TokenType.BE)) {Error("'be'");} // BE == be
            Type();
            if (isGet){
                if (isPrompt){
                    appendLineToFile(filePath, mips.printStatements(prompt, isExpr, isID, isNum, isIDNum));
                    isPrompt = false;
                }
                appendLineToFile(filePath, mips.input(varName, isNum));

                if(isNum)
                    symbolTable.setTokenType(varName, TokenType.INT);
                else
                    symbolTable.setTokenType(varName, TokenType.STR);

                isGet = false;
            }
            symbolTable.setDataType(varCount, dataType);
        }
    }

    private void DeclareList() {
        DeclareType();
        DeclareList_();
    }

    private void DeclareList_() {
        if (lookahead.getTokenType() == TokenType.COM) {
            match(TokenType.COM);
            DeclareList();
        }
    }

    private void DeclareType() { 
        resetSymbolValues();
        
        if (lookahead.getTokenType() == TokenType.ID) {
            identifier = lookahead.getValue();
            varName = lookahead.getValue();
            isDeclaredVar = true;
            Var();
            isDeclaredVar = false;
            DeclareType_();
            symbolTable.updateSymbol(identifier, tokenType, dataType);
            varCount++;
            resetSymbolValues();
        }

        else {
            Error("variable");
        }
    }

    private void DeclareType_() {
        if (lookahead.getTokenType() == TokenType.EQUAL) {
            match(TokenType.EQUAL);
            String value = lookahead.getValue();
            Value();
            if(isWord){
                appendLineToFile(filePath, mips.varDeclarationWord(identifier, value, isNum));
                isWord = false;
            }
            else if(isExpr){
                appendLineToFile(filePath, mips.varDeclarationExpr(identifier, value, isNum, hasOperators));
                isExpr = false;
            }

        }
    }

    private void Type() {
        if (lookahead.getTokenType() == TokenType.DATATYPE) {
            dataType = lookahead.getValue();
            if(dataType.equals("num")){
                isNum = true;
            }
            match(TokenType.DATATYPE); 
        }
        else {
            Error("Data type");
        }
    }

    private boolean isDeclared(String varName){
        boolean declared = symbolTable.isDeclared(varName);
        if(!declared)
            VarNotDeclaredError(varName);
        return declared;
    }

    private void Assignment() {
        isNum = false;
        String varName, value;
        varName = lookahead.getValue();

        isDeclared(varName);
        isAssignVar = true;
        assignVar = varName;

        Var();
        isAssignVar = false;
        if (!match(TokenType.EQUAL)) {Error("'='");}
        value = lookahead.getValue();
        Value();
        if(isWord){
            appendLineToFile(filePath, mips.varDeclarationWord(varName, value, isNum));
            isWord = false;
        }
        else if(isExpr){ 
            appendLineToFile(filePath, mips.varDeclarationExpr(varName, value, isNum, hasOperators));
            isExpr = false;
        }
        else if (isGet){
            if (isPrompt){
                appendLineToFile(filePath, mips.printStatements(prompt, isExpr, isID, isNum, isIDNum));
                isPrompt = false;
            }
            if (symbolTable.getKeyDataType(varName).equals("num"))
                isNum = true;
            appendLineToFile(filePath, mips.input(varName, isNum));

            if(isNum)
                symbolTable.setTokenType(varName, TokenType.INT);
            else
                symbolTable.setTokenType(varName, TokenType.STR);

            isGet = false;
            isNum = false;
        }
        resetAssignVar();
    }

    private void Value() {
        if (lookahead.getTokenType() == TokenType.ENTER) {
            Get();
        }
        else if (lookahead.getTokenType() == TokenType.LPAREN || lookahead.getTokenType() == TokenType.ID || 
                 lookahead.getTokenType() == TokenType.INT || lookahead.getTokenType() == TokenType.DEC) {            
            Expr();
            isExpr = true;
            isWord = false;
        }
        else if (lookahead.getTokenType() == TokenType.STR || lookahead.getTokenType() == TokenType.CHAR) {
            Word();
            isWord = true;
            isExpr = false;
        }
        else {
            Error("Value after '='");
        }
    }

    private void Get() {
        if (lookahead.getTokenType() ==  TokenType.ENTER) {
            if(isGet)
                MultipleEnterError();
            isGet = true;
            match(TokenType.ENTER);
            if (!match(TokenType.LPAREN)) {Error("'('");}
            Prompt();
            if (!match(TokenType.RPAREN)) {Error("')'");}
        }
    }

    private void Prompt() {
        if (lookahead.getTokenType() == TokenType.STR || lookahead.getTokenType() == TokenType.CHAR) {
            isPrompt = true;
            prompt = lookahead.getValue();
            isExpr = false;
            Word();
        }
    }

    private void Expr() {
        isExpr = true;
        Term();
        Expr_();
    }

    private void Expr_() {
        if (lookahead.getTokenType() == TokenType.PLUS) {
            hasOperators = true;
            match(TokenType.PLUS);
            Term();
            appendLineToFile(filePath, mips.addOperand());
            Expr_();
        }
        else if (lookahead.getTokenType() == TokenType.MINUS) {
            hasOperators = true;
            match(TokenType.MINUS);
            Term();
            appendLineToFile(filePath, mips.subOperand());
            Expr_();
        }
    }

    private void Term() {
        Factor();
        Term_();
    }

    private void Term_() {
        if (lookahead.getTokenType() == TokenType.TIMES) {
            hasOperators = true;
            match(TokenType.TIMES);
            Factor();
            appendLineToFile(filePath, mips.mulOperand());
            Term_();
        }
        else if (lookahead.getTokenType() == TokenType.DIVIDE) {
            hasOperators = true;
            match(TokenType.DIVIDE);
            Factor();
            appendLineToFile(filePath, mips.divOperand());
            Term_();
        }
    }

    private void Factor() {
        String value = lookahead.getValue();
        if (lookahead.getTokenType() == TokenType.LPAREN) {
            match(TokenType.LPAREN);  
            Expr();
            if (!match(TokenType.RPAREN)) {Error("')'");}
        }
        else if (lookahead.getTokenType() == TokenType.ID) {
            mips.regOrder(value);
            isIDNum = false;
            Var();
        }
        else if (lookahead.getTokenType() == TokenType.INT || lookahead.getTokenType() == TokenType.DEC) {
            appendLineToFile(filePath, mips.immediateNum(value));
            mips.regOrder(value);
            Num();
        }
    }

    private void Print() {
        PrintKeyword();
        if (!match(TokenType.LPAREN)) {Error("'('");}
        Output();
        if(isShowLine){
            if(statement != null)
                appendLineToFile(filePath, mips.printStatements(statement, isExpr, isID, isNum, isIDNum));
            appendLineToFile(filePath, mips.printNewLine());
            isExpr = false;
            isID = false;
            isNum = false;
            isIDNum = false;
        }
        else{
            if(statement != null){
                appendLineToFile(filePath, mips.printStatements(statement, isExpr, isID, isNum, isIDNum));
            }
            isExpr = false;
            isID = false;
            isNum = false;
            isIDNum = false;
        }
        if (!match(TokenType.RPAREN)) {Error("')'");}
        isPrinting = false;
        isExpr = false;
        isID = false;
        isNum = false;
        isIDNum = false;
    }

    private void PrintKeyword() {
        if (lookahead.getTokenType() == TokenType.SHOW) {
            match(TokenType.SHOW);
            isShowLine = false;
            isPrinting = true;
        }
        else if (lookahead.getTokenType() == TokenType.SHOWLINE) {
            match(TokenType.SHOWLINE);
            isShowLine = true;
            isPrinting = true;
        }

    }

    private void Output() {
        if (lookahead.getTokenType() == TokenType.LPAREN || lookahead.getTokenType() == TokenType.ID || 
            lookahead.getTokenType() == TokenType.INT || lookahead.getTokenType() == TokenType.DEC) {
            statement = lookahead.getValue();
            Expr();
            isExpr = true;
        }
        else if (lookahead.getTokenType() == TokenType.STR || lookahead.getTokenType() == TokenType.CHAR) {
            statement = lookahead.getValue();
            Prompt();
            isExpr = false;
        }
        else{
            statement = null;
            isExpr = false;
        }
    }
    
    private void setTokenTypeOfAssignVar(){
        if(!assignVar.isBlank()){
            String assignVarDataType = symbolTable.getKeyDataType(assignVar);
            if (!symbolTable.isDataTypeConsistent(assignVarDataType, tokenType))
                symbolTable.InconsistentDataTypeError(assignVar, assignVarDataType);

            else {
                TokenType assignVarTokenType = symbolTable.getKeyTokenType(assignVar);
                if(assignVarTokenType == null){
                    symbolTable.setTokenType(assignVar, tokenType);
                }
            }
        }    
    }

    private void Num() {
        tokenType = lookahead.getTokenType();
        if (lookahead.getTokenType() == TokenType.INT) {
            match(TokenType.INT);
            isNum = true;
        }
        else if (lookahead.getTokenType() == TokenType.DEC) {
            match(TokenType.DEC);
            isNum = true;
        }
        setTokenTypeOfAssignVar();
    }

    private void Word() {
        tokenType = lookahead.getTokenType();
        if (lookahead.getTokenType() == TokenType.STR ) {
            match(TokenType.STR);
            isNum = false;
        }
        else if (lookahead.getTokenType() == TokenType.CHAR) {
            match(TokenType.CHAR); 
            isNum = false;
        }

        if(!isPrompt)
            setTokenTypeOfAssignVar();
    }

    private void Var() {
        if (lookahead.getTokenType() == TokenType.ID) {
            String var = lookahead.getValue();
            
            // Case 1: let x 
            // Case 2: let x = y
            // Case 3: y = x

            /*  The codes in the next condition should only execute 
                if var is a variable assigned at a variable X, say X = var.
                This should not run for any variable X, 
                that is every variable before '=' or 
                for a being declared variable, say X in let X be num.
            */

            /*  For a being declared variable, say X in let X be num,
                we set the value for the identifier as X.
                So we can say that the var is not being declared
                when var != identifier.
                We have to place it before isDeclared(var), so it will
                short circuit since, it is always not declared initially.
            */

            /*  For a variable in assignment statement, say X in X = a,
                we still need to check if X is already declared. But,
                it is being handled in Assignment.
                We could make flag to determine if isAssignVar.
            */

            if (isDeclaredVar){
                symbolTable.insertID(var);
            }
           

            if ( var != null && !isPrinting && !isAssignVar && !var.equals(identifier) && isDeclared(var)){
                
                /*  There is a possibility that there are multiple var in an expr.*/

                // Handle if a var B is assigned at var A : A = B
                // var A can be assignVar or a being declared var
                String varToUpdate = identifier != null ? identifier : assignVar; 

                // Get token type of var B if declared
                tokenType = symbolTable.getKeyTokenType(var);
                if(tokenType != null){
                    // Set  A tokenType = B tokenType

                    /*  Ensure that 
                        if TokenType of var A is NOT null,
                        we do not need set token type again.
                    */ 
                    
                    // Check if 
                    TokenType tokenTypeA = symbolTable.getKeyTokenType(varToUpdate);
                    String dataTypeA = symbolTable.getKeyDataType(varToUpdate);

                    if (varToUpdate != null && varToUpdate.equals(assignVar) && !symbolTable.isDataTypeConsistent(dataTypeA, tokenType))
                        symbolTable.InconsistentDataTypeError(assignVar, dataTypeA);

                    if(tokenTypeA == null){
                        // Check inconsistencies first of B token type and A datatype
                        // Inconsistencies in being declared var is already handled

                        symbolTable.setTokenType(varToUpdate, tokenType);
                    }
                    
                    else{
                        // Check inconsistencies
                    }
                }

                else{
                    // If token type is null
                    VarNotInitializedError(var);
                }
                    
                // !!Check inconsistensies
            
            }

            String varDataType = symbolTable.getKeyDataType(var);
            if(varDataType != null && varDataType.equals("num")){      
                isIDNum = true;
            }
            match(TokenType.ID);
            isID = true;
        }
    }

    private void VarNotInitializedError(String var){
        message.append("Error: variable '" + var + "' might not have been initialized.\n\n");
    }

}

