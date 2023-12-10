package packages.baby.compiler;

import packages.baby.compiler.LexicalAnalysis.TokenType;
import java.util.*;

public class Symbol {
    private String name;
    private String value;
    private TokenType type;


    public Symbol(String name, String value, TokenType type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }   

    // Getters and Setters for the Symbol attributes
    // Getters
    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public TokenType getType() { //// review return type
        return type;
    }


    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(TokenType type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "Variable Name: " + name + " Value: " + value + " Type: " + type + "\n";
    }
}
