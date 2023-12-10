package packages.baby.compiler.SyntaxAnalysis;

import java.util.*;

public class Parser {
    List<Token> tokens;
	Token lookahead;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        lookahead = getToken();
        Program();   
    }
    
    private Token getToken() {
        return tokens.remove(0);
    }
    
    private boolean match(TokennType type){
        if (lookahead.getTokenType() == type) {
            lookahead = getToken();
            return true;
        }
        else {
            return false;
        }
    }

    private void Error(String lexeme) { // may need other messages if data type is wrong
        System.out.println("Error at line " + lookahead.getNLine() + ". '" + lexeme + "' expected.");
    }
    
    private void Program() {
        StmtList();
        match(EOF); // EOF == $
    }
    
    private void StmtList() {
    	Stmt();
        if (!match(SEMICOLON)) {Error(";");} // SEMICOLON == ;
        StmtList_();
    }
    
    private void StmtList_() {
        if (lookahead.getTokenType == LET || lookahead.getTokenType() == ID ||
            lookahead.getTokenType() == SHOW || lookahead.getTokenType() == SHOWLINE) { // LET == let || ID == Variable names || SHOW == show || SHOWLINE == showline
            StmtList();
        }
        else return; // ϵ // check logic
    }

    private void Stmt() {
        if (lookahead.getTokenType() == LET) { // LET == let
            Declare();
        }
        else if (lookahead.getTokenType() == ID) { // ID == Variable names
            Assignment();
        }
        else if (lookahead.getTokenType() == SHOW || lookahead.getTokenType() == SHOWLINE) { // SHOW == show || SHOWLINE == showline
            Print();
        }
    }

    private void Declare() {
        if (lookahead.getTokenType() == LET) { // LET == let
            if (!match(LET)) {Error("let");} // LET == let
            DeclareList();
            if (!match(BE)) {Error("be");} // BE == be
            Type();
        }
    }

    private void DeclareList() {
        DeclareType();
        DeclareList_();
    }

    private void DeclareList_() {
        if (lookahead.getTokenType() == COM) {
            if (!match(COM)) {Error(",");}
            DeclareList();
        }
        else return; // ϵ // check logic
    }

    private void DeclareType() { 
        if (lookahead.getTokenType() == ID) {
            Var();
            DeclareType_();
        }
    }

    private void DeclareType_() {
        if (lookahead.getTokenType == EQUAL) {
            if (match(EQUAL)) {Error("=");} //////////// ehhhhhhh
            Value(); 
        }
    }

    private void Type() {
        if (lookahead.getTokenType() == NUMTYPE) {
            if (!match(NUMTYPE)) {Error("Numeric type");} ////////////// ehhhhhh
        }
        else if (lookahead.getTokenType() == WORDTYPE) {
            if (!match(WORDTYPE)) {Error("Word Type");}   ////////////// ehhhhhh
        }
    }

    private void Assignment() {
        Var();
        if (!match(EQUAL)) {Error("equal");} /////////// ehhhh
        Value();
    }

    private void Value() {
        if (lookahead.getTokenType() == ENTER) {
            Get();
        }
        else if (lookahead.getTokenType() == LPAREN || lookahead.getTokenType() == ID || 
                 lookahead.getTokenType() == INT || lookahead.getTokenType() == DEC) {
            Expr();
        }
    }

    private void Get() {
        if (lookahead.getTokenType() ==  ENTER) {
            if (!match(ENTER)) {Error("enter");} ////// ehhhhhh
            if (!match(LPAREN)) {Error("(");}
            Prompt();
            if (!march(RPAREN)) {Error("}");}
        }
    }

    private void Prompt() {
        if (lookahead.getTokenType() == STR || lookahead.getTokenType() == CHAR) {
            Word();
        }
        else return; // ϵ // check logic
    }

    private void Expr() {
        Term();
        Expr_();
    }

    private void Expr_() {
        if (lookahead.getTokenType() == PLUS) {
            if (!match(PLUS)) {Error("Operator");} /// eeeehhhhhhhh
            Term();
            Expr();
        }
        else if (lookahead.getTokenType() == MINUS) {
            if (!match(MINUS)) {Error("Operator");} /// eeeehhhhhhhh
            Term();
            Expr();
        }
        else return; // ϵ // check logic 
    }

    private void Term() {
        Factor();
        Term();
    }

    private void Term_() {
        if (lookahead.getTokenType() == TIMES) {
            if (!match(TIMES)) {Error("Operator");} /// eeeehhhhhhhh
            Factor();
            Term_();
        }
        else if (lookahead.getTokenType() == DIVIDE) {
            if (!match(DIVIDE)) {Error("Operator");} /// eeeehhhhhhhh
            Factor();
            Term_();
        }
        else return; // ϵ // check logic 
    }

    private void Factor() {
        if (lookahead.getTokenType() == LPAREN) {
            if (!match(LPAREN)) {Error("(");} 
            Expr();
            if (!match(RPAREN)) {Error(")");}
        }
        else if (lookahead.getTokenType() == ID) {
            Var();
        }
        else if (lookahead.getTokenType() == INT || lookahead.getTokenType() == DEC) {
            Num();
        }
    }

    private void Print() {
        PrintKeyword();
        if (!match(LPAREN)) {Error("(");}
        Output();
        if (!match(LPAREN)) {Error(")");}
    }

    private void PrintKeyword() {
        if (lookahead.getTokenType() == SHOW) {
            if (!match(SHOW)) {Error("show");}
        }
        else if (lookahead.getTokenType() == SHOWLINE) {
            if (!match(SHOWLINE)) {Error("showline");}
        }

    }

    private void Output() {
        if (lookahead.getTokenType() == LPAREN || lookahead.getTokenType() == ID || 
            lookahead.getTokenType() == INT || lookahead.getTokenType() == DEC) {
            Expr();
        }
        else if (lookahead.getTokenType() == STR || lookahead.getTokenType() == CHAR) {
            Prompt();
        }
    }

    private void Num() {
        if (lookahead.getTokenType() == INT) {
            if(!match(INT)) {Error("Numeric");} ////////// ehhhhhhhhhhhhhh
        }
        else if (lookahead.getTokenType() == DEC) {
            if (!match(DEC)) {Error("Numeric");} ///////////// ehhhhhh
        }
    }

    private void Word() {
        if (lookahead.getTokenType() == STR ) {
            if(!match(STR)) {Error("Word");} ////////// ehhhhhhhhhhhhhh
        }
        else if (lookahead.getTokenType() == CHAR) {
            if (!match(CHAR)) {Error("Word");} ///////////// ehhhhhh
        }
    }

    private void Var() {
        if (lookahead.getTokenType() == ID) {
            if (!match(ID)) {Error("Variable");} ////// ehhhhhhh
        }
    }    
}

