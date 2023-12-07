package packages.baby.compiler.LexicalAnalysis;

public enum TokenType {

    // Infinite possible lexemes
    ID, STR, CHAR, INT, DEC,

    // Special characters
    PLUS, MINUS, TIMES, DIVIDE, SEMICOLON, LPAREN, RPAREN, COM, EQUAL,

    // Keywords
    DATATYPE, LET, BE, ENTER, SHOW, SHOWLINE, EOF,
    
    // Invalid
    INVALID

}