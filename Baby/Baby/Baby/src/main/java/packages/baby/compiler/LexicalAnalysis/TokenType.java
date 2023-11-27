package packages.baby.compiler.LexicalAnalysis;

public enum TokenType {

    // Infinite possible lexemes
    ID, STR, CHAR, INT, DEC,

    // Special characters
    PLUS, MINUS, TIMES, DIVIDE, SEMICOLON, LPAREN, RPAREN, COM,

    // Keywords
    DATATYPE, FUNC, LET, BE,
    
    // Invalid
    INVALID

}