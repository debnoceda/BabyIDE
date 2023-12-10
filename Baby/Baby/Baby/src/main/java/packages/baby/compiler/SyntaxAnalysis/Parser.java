package packages.baby.compiler.SyntaxAnalysis;

import packages.baby.compiler.LexicalAnalysis.Token;
import packages.baby.compiler.LexicalAnalysis.TokenType;
import java.util.*;

public class Parser {
    List<Token> tokens;
    Token lookahead;
    StringBuilder message = new StringBuilder();
    boolean success = true;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        lookahead = getToken();
        Program();
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
        message.append("Error at line " +  lookahead.getNLine()+ ": '" + lexeme + "' expected.\n\n") ;
        setSuccess(false);
    }

    public String printParseError() {
        return message.toString();
    }
    
    private void Program() {
        StmtList();
        if (success && match(TokenType.EOF)) { // EOF == $
            message.append("Parsing successful!");
        }
    }
    
    private void StmtList() {
    	Stmt();
        if (!match(TokenType.SEMICOLON)) {Error(";");} // SEMICOLON == ;
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
            if (!match(TokenType.LET)) {Error("let");} // LET == let
            DeclareList();
            if (!match(TokenType.BE)) {Error("be");} // BE == be
            Type();
        }
    }

    private void DeclareList() {
        DeclareType();
        DeclareList_();
    }

    private void DeclareList_() {
        if (lookahead.getTokenType() == TokenType.COM) {
            if (!match(TokenType.COM)) {Error(",");}
            DeclareList();
        }
    }

    private void DeclareType() { 
        if (lookahead.getTokenType() == TokenType.ID) {
            Var();
            DeclareType_();
        }
    }

    private void DeclareType_() {
        if (lookahead.getTokenType() == TokenType.EQUAL) {
            if (!match(TokenType.EQUAL)) {Error("=");} //////////// ehhhhhhh
            Value(); 
        }
    }

    private void Type() {
        if (lookahead.getTokenType() == TokenType.DATATYPE) {
            match(TokenType.DATATYPE); ////////////// ehhhhhh
        }
        else {
            Error("Data type");   ////////////// ehhhhhh
        }
    }

    private void Assignment() {
        Var();
        if (!match(TokenType.EQUAL)) {Error("equal");} /////////// ehhhh
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
    }

    private void Get() {
        if (lookahead.getTokenType() ==  TokenType.ENTER) {
            if (!match(TokenType.ENTER)) {Error("enter");} ////// ehhhhhh
            if (!match(TokenType.LPAREN)) {Error("(");}
            Prompt();
            if (!match(TokenType.RPAREN)) {Error("}");}
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
            if (!match(TokenType.PLUS)) {Error("Operator");} /// eeeehhhhhhhh
            Term();
            Expr_();
        }
        else if (lookahead.getTokenType() == TokenType.MINUS) {
            if (!match(TokenType.MINUS)) {Error("Operator");} /// eeeehhhhhhhh
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
            if (!match(TokenType.TIMES)) {Error("Operator");} /// eeeehhhhhhhh
            Factor();
            Term_();
        }
        else if (lookahead.getTokenType() == TokenType.DIVIDE) {
            if (!match(TokenType.DIVIDE)) {Error("Operator");} /// eeeehhhhhhhh
            Factor();
            Term_();
        }
    }

    private void Factor() {
        if (lookahead.getTokenType() == TokenType.LPAREN) {
            if (!match(TokenType.LPAREN)) {Error("(");} 
            Expr();
            if (!match(TokenType.RPAREN)) {Error(")");}
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
        if (!match(TokenType.LPAREN)) {Error("(");}
        Output();
        if (!match(TokenType.LPAREN)) {Error(")");}
    }

    private void PrintKeyword() {
        if (lookahead.getTokenType() == TokenType.SHOW) {
            if (!match(TokenType.SHOW)) {Error("show");}
        }
        else if (lookahead.getTokenType() == TokenType.SHOWLINE) {
            if (!match(TokenType.SHOWLINE)) {Error("showline");}
        }

    }

    private void Output() {
        if (lookahead.getTokenType() == TokenType.LPAREN || lookahead.getTokenType() == TokenType.ID || 
            lookahead.getTokenType() == TokenType.INT || lookahead.getTokenType() == TokenType.DEC) {
            Expr();
        }
        else if (lookahead.getTokenType() == TokenType.STR || lookahead.getTokenType() == TokenType.CHAR) {
            Prompt();
        }
    }

    private void Num() {
        if (lookahead.getTokenType() == TokenType.INT) {
            if(!match(TokenType.INT)) {Error("Numeric");} ////////// ehhhhhhhhhhhhhh
        }
        else if (lookahead.getTokenType() == TokenType.DEC) {
            if (!match(TokenType.DEC)) {Error("Numeric");} ///////////// ehhhhhh
        }
    }

    private void Word() {
        if (lookahead.getTokenType() == TokenType.STR ) {
            if(!match(TokenType.STR)) {Error("Word");} ////////// ehhhhhhhhhhhhhh
        }
        else if (lookahead.getTokenType() == TokenType.CHAR) {
            if (!match(TokenType.CHAR)) {Error("Word");} ///////////// ehhhhhh
        }
    }

    private void Var() {
        if (lookahead.getTokenType() == TokenType.ID) {
            if (!match(TokenType.ID)) {Error("Variable");} ////// ehhhhhhh
        }
    }
}

