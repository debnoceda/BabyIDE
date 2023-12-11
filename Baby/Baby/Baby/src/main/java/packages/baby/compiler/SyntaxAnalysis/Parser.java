package packages.baby.compiler.SyntaxAnalysis;

import packages.baby.compiler.LexicalAnalysis.Token;
import packages.baby.compiler.LexicalAnalysis.TokenType;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import packages.baby.compiler.CodeGenerator.MIPSAssembly;
import java.io.BufferedWriter;

public class Parser {
    List<Token> tokens;
    Token lookahead;
    StringBuilder message = new StringBuilder();
    boolean success = true;
    private String fileName;
    boolean isShowLine = false;
    String mipsCode;
    String filePath;
    String statement;

    public Parser(List<Token> tokens, String afileName, MIPSAssembly mips) {
        this.tokens = tokens;
        lookahead = getToken();
        setFileName(afileName);
        mipsCode = mips.generateMIPS();
        filePath = writeToFile(fileName, mipsCode);
        System.out.println("MIPS code has been written in the file: " + fileName);
        Program(mips);
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
            System.out.println("Line appended to the file.");
        } catch (IOException e) {
            System.err.println("Error appending line to the file: " + e.getMessage());
        }
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

    public String printParseError() {
        return message.toString();
    }

    private void Program(MIPSAssembly mips) {
        StmtList();
        if (success && match(TokenType.EOF)) { // EOF == $
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
        if (lookahead.getTokenType() == TokenType.LET) { // LET == let
            match(TokenType.LET); // LET == let
            DeclareList();
            if (!match(TokenType.BE)) {Error("'be'");} // BE == be
            Type();
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
        if (lookahead.getTokenType() == TokenType.ID) {
            // identfier = lookahead.getValue()
            Var();
            DeclareType_();
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
            match(TokenType.DATATYPE); 
        }
        else {
            Error("Data type");
        }
    }

    private void Assignment() {
        Var();
        if (!match(TokenType.EQUAL)) {Error("'='");}
        Value();
    }

    private void Value() {
        if (lookahead.getTokenType() == TokenType.ENTER) {
            Get();
        }
        else if (lookahead.getTokenType() == TokenType.LPAREN || lookahead.getTokenType() == TokenType.ID || 
                 lookahead.getTokenType() == TokenType.INT || lookahead.getTokenType() == TokenType.DEC) {
            Expr();
        }
        else if (lookahead.getTokenType() == TokenType.STR || lookahead.getTokenType() == TokenType.CHAR) {
            Word();
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
    }

    private void Expr_() {
        if (lookahead.getTokenType() == TokenType.PLUS) {
            match(TokenType.PLUS);
            Term();
            Expr_();
        }
        else if (lookahead.getTokenType() == TokenType.MINUS) {
            match(TokenType.MINUS);
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
            Factor();
            Term_();
        }
        else if (lookahead.getTokenType() == TokenType.DIVIDE) {
            match(TokenType.DIVIDE);
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
                appendLineToFile(filePath, mips.printStatements(statement));
            appendLineToFile(filePath, mips.printNewLine());
        }
        else{
            if(statement != null)
                mips.printStatements(statement);
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
            Expr();
        }
        else if (lookahead.getTokenType() == TokenType.STR || lookahead.getTokenType() == TokenType.CHAR) {
            statement = lookahead.getValue();
            Prompt();
        }
    }

    private void Num() {
        if (lookahead.getTokenType() == TokenType.INT) {
            match(TokenType.INT); 
        }
        else if (lookahead.getTokenType() == TokenType.DEC) {
            match(TokenType.DEC);
        }
    }

    private void Word() {
        if (lookahead.getTokenType() == TokenType.STR ) {
            match(TokenType.STR); 
        }
        else if (lookahead.getTokenType() == TokenType.CHAR) {
            match(TokenType.CHAR); 
        }
    }

    private void Var() {
        if (lookahead.getTokenType() == TokenType.ID) {
            match(TokenType.ID);
        }
    }
}

