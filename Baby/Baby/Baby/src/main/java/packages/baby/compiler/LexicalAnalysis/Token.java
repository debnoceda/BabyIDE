package packages.baby.compiler.LexicalAnalysis;

public class Token {
    private int nLine;
    private TokenType type;
    private String value;

    public Token(int lineNumber, TokenType aType, String aValue){
        this.nLine = lineNumber;
        this.type = aType;
        this.value = aValue;
    }

    public int getNLine(){
        return this.nLine;
    }

    public TokenType getTokenType(){
        return this.type;
    }

    public String getValue(){
        return this.value;
    }

}