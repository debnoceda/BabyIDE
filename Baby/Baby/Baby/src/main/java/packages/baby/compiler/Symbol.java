package packages.baby.compiler;

import packages.baby.compiler.LexicalAnalysis.TokenType;
import java.util.*;

public class Symbol {
    private String name;
    // private String value;
    private TokenType tokenType;
    private String dataType;


    public Symbol(String name, TokenType tokenType, String dataType) {
        this.name = name;
        //this.value = value;
        this.tokenType = tokenType;
        this.dataType = dataType;
        
    }   

    // Getters and Setters for the Symbol attributes
    // Getters
    public String getName() {
        return name;
    }

    // public String getValue() {
    //     return value;
    // }

    public TokenType getTokenType() { //// review return type
        return tokenType;
    }

    public String getDataType() {
        return dataType != null ? dataType : "";
    }


    // Setters
    public void setName(String name) {
        this.name = name;
    }

    // public void setValue(String value) {
    //     this.value = value;
    // }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }


    @Override
    public String toString() {
        //return "Variable Name: " + name + " Value: " + value + " Token Type: " + tokenType + " Data Type :" + dataType + "\n";
        return "Variable Name: " + name + " Token Type: " + tokenType + " Data Type :" + dataType + "\n";
    }
}
