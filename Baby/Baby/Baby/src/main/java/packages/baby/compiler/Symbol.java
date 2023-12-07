package packages.baby.compiler;

import java.util.*;

public class Symbol {
    private String name;
    private String type; // make token
    private int lineOfDeclaration;
    private int lineOfUsage;

    public Symbol(String name, String type, int lineOfDeclaration, int lineOfUsage) {
        this.name = name;
        this.type = type;
        this.lineOfDeclaration = lineOfDeclaration;
        this.lineOfUsage = lineOfUsage;
    }

    // Getters and Setters for the Symbol attributes
    // Getters
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getLineOfDeclaration() {
        return lineOfDeclaration;
    }

    public int getLineOfUsage() {
        return lineOfUsage;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLineOfDeclaration(int lineOfDeclaration) {
        this.lineOfDeclaration = lineOfDeclaration;
    }

    public void setLineOfUsage(int lineOfUsage) {
        this.lineOfUsage = lineOfUsage;
    }

    @Override
    public String toString() {
        return "Type: " + type + ", Declaration Line: " + lineOfDeclaration + 
                ", Usage Line: " + lineOfUsage;
    }
}
