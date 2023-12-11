package packages.baby.compiler.SyntaxAnalysis;

import packages.baby.compiler.LexicalAnalysis.Token;
import packages.baby.compiler.LexicalAnalysis.TokenType;
import packages.baby.compiler.Symbol;
import packages.baby.compiler.SymbolTable;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import packages.baby.compiler.CodeGenerator.MIPSAssembly;
import java.io.BufferedWriter;

public class Parser {
    List<Token> tokens;
    Token lookahead;
    StringBuilder message;
    boolean success = true;
    private String fileName;
    boolean isShowLine = false, isNum = false, isID = false, isExpr = false;
    boolean hasOperators = false;
    boolean isWord = false, isAdd = false, isSub = false, isMul = false, isDiv = false;
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

    public Parser(StringBuilder message, List<Token> tokens, String afileName, MIPSAssembly mips) {
        this.message = message;
        this.tokens = tokens;
        symbolTable = new SymbolTable(message);
        lookahead = getToken();
        this.mips = mips;
        setFileName(afileName);
        mipsCode = mips.generateMIPS();
        filePath = writeToFile(fileName, mipsCode);
        System.out.println("MIPS code has been written in the file: " + fileName);
        resetSymbolValues();
        Program();
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

    // public static void appendToFile(String filePath, String content) throws IOException {
    //     try (RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rw")) {
    //         // Move the file pointer to the end of the file
    //         randomAccessFile.seek(randomAccessFile.length());

    //         // Write the content at the end of the file
    //         randomAccessFile.writeBytes(content);
    //         System.out.println("Content appended to the file.");
    //     }
    // }
    
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

    public String printParseError() {
        return message.toString();
    }

    private void Program() {
        StmtList();
        if (message.isEmpty() && success && match(TokenType.EOF)) { // EOF == $
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
            Var();
            DeclareType_();
            symbolTable.insertSymbol(identifier,tokenType, dataType);
            varCount++;
            resetSymbolValues();
        }
    }

    private void DeclareType_() {
        if (lookahead.getTokenType() == TokenType.EQUAL) {
            match(TokenType.EQUAL);
            Value(); 
        }
    }

    private void Type() {
        if (lookahead.getTokenType() == TokenType.DATATYPE) {
            dataType = lookahead.getValue();
            match(TokenType.DATATYPE); 
        }
        else {
            Error("Data type");
        }
    }

    private void Assignment() {
        isNum = false;
        String varName, value;
        varName = lookahead.getValue();
        Var();
        if (!match(TokenType.EQUAL)) {Error("'='");}
        value = lookahead.getValue();
        Value();
        if(isWord){
            appendLineToFile(filePath, mips.varDeclarationWord(varName, value, isNum));
        }
        if(isExpr){
            appendLineToFile(filePath, mips.varDeclarationExpr(varName, value, isNum, hasOperators));
            if(hasOperators){
                
            }
        }
    }

    private void Value() {
        if (lookahead.getTokenType() == TokenType.ENTER) {
            Get();
        }
        else if (lookahead.getTokenType() == TokenType.LPAREN || lookahead.getTokenType() == TokenType.ID || 
                 lookahead.getTokenType() == TokenType.INT || lookahead.getTokenType() == TokenType.DEC) {            
            Expr();
            isExpr = true;
        }
        else if (lookahead.getTokenType() == TokenType.STR || lookahead.getTokenType() == TokenType.CHAR) {
            Word();
            isWord = true;
        }
        else {
            Error("Value after '='");
        }
    }

    private void Get() {
        if (lookahead.getTokenType() ==  TokenType.ENTER) {
            match(TokenType.ENTER);
            if (!match(TokenType.LPAREN)) {Error("'('");}
            Prompt();
            if (!match(TokenType.RPAREN)) {Error("')'");}
        }
    }

    private void Prompt() {
        if (lookahead.getTokenType() == TokenType.STR || lookahead.getTokenType() == TokenType.CHAR) {
            Word();
        }
    }

    private void Expr() {
        Term();
        Expr_();
        if (isAdd){
            appendLineToFile(filePath, mips.addOperand());
        }
    }

    private void Expr_() {
        if (lookahead.getTokenType() == TokenType.PLUS) {
            hasOperators = true;
            match(TokenType.PLUS);
            isAdd = true;
            Term();
            Expr_();
        }
        else if (lookahead.getTokenType() == TokenType.MINUS) {
            hasOperators = true;
            match(TokenType.MINUS);
            isSub = true;
            Term();
            Expr_();
        }
    }

    private void Term() {
        Factor();
        Term_();
    }

    private void Term_() {
        if (lookahead.getTokenType() == TokenType.TIMES) {
            match(TokenType.TIMES);
            isMul = true;
            Factor();
            Term_();
        }
        else if (lookahead.getTokenType() == TokenType.DIVIDE) {
            match(TokenType.DIVIDE);
            isDiv = true;
            Factor();
            Term_();
        }
    }

    private void Factor() {
        if (lookahead.getTokenType() == TokenType.LPAREN) {
            match(TokenType.LPAREN); 
            Expr();
            if (!match(TokenType.RPAREN)) {Error("')'");}
        }
        else if (lookahead.getTokenType() == TokenType.ID) {
            Var();
        }
        else if (lookahead.getTokenType() == TokenType.INT || lookahead.getTokenType() == TokenType.DEC) {
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
        }
        else{
            if(statement != null)
                appendLineToFile(filePath, mips.printStatements(statement, isExpr, isID, isNum, isIDNum));
        }
        if (!match(TokenType.RPAREN)) {Error("')'");}
    }

    private void PrintKeyword() {
        if (lookahead.getTokenType() == TokenType.SHOW) {
            match(TokenType.SHOW);
            isShowLine = false;
        }
        else if (lookahead.getTokenType() == TokenType.SHOWLINE) {
            match(TokenType.SHOWLINE);
            isShowLine = true;
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
    }

    private void Var() {
        if (lookahead.getTokenType() == TokenType.ID) {
            String varDataType = symbolTable.getKeyDataType(lookahead.getValue());
            if(varDataType.equals("num")){      
                isIDNum = true;
            }
            match(TokenType.ID);
            isID = true;
        }
    }

}

