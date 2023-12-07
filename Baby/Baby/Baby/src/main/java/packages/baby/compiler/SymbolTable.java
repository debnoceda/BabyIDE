package packages.baby.compiler;

import java.util.*;
        
public class SymbolTable {

    private HashMap<String, Symbol> symbolTable;

    public SymbolTable() {
        this.symbolTable = new HashMap<>();
    }

    // Method to insert a symbol into the table
    public void insertSymbol(String identifier, String type, int declarationLine, int usageLine) {
        Symbol symbol = new Symbol(identifier, type, declarationLine, usageLine);
        symbolTable.put(identifier, symbol);
    }


    // Method to lookup a symbol in the table
    public Symbol lookupSymbol(String identifier) {
        return symbolTable.get(identifier);
    }

    // Method to set attributes for an existing symbol
    public void setSymbolAttributes(String identifier, String type, int size, int declarationLine, int usageLine, String address) {
        if (symbolTable.containsKey(identifier)) {
            Symbol symbol = symbolTable.get(identifier);
            symbol.setType(type);
            symbol.setLineOfDeclaration(declarationLine);
            symbol.setLineOfUsage(usageLine);
        } else {
            System.out.println("Symbol '" + identifier + "' not found in the symbol table.");
        }
    }

    // Method to reset attributes for an existing symbol
    public void resetSymbolAttributes(String identifier) {
        if (symbolTable.containsKey(identifier)) {
            Symbol symbol = symbolTable.get(identifier);
            // Resetting attributes, you can customize this based on your requirements
            symbol.setType("");
            symbol.setLineOfDeclaration(0);
            symbol.setLineOfUsage(0);
        } else {
            System.out.println("Symbol '" + identifier + "' not found in the symbol table.");
        }
    }

    // Method to remove a symbol from the table
    public void removeSymbol(String identifier) {
        symbolTable.remove(identifier);
    }

    // Method to display the contents of the symbol table
    public void displayTable() {
        System.out.println("Symbol Table:");
        for (String identifier : symbolTable.keySet()) {
            Symbol symbol = symbolTable.get(identifier);
            System.out.println(identifier + ": " + symbol.toString());
        }
    }

    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable();

        // Inserting symbols into the table
        symbolTable.insertSymbol("int", "type", 5, 10);
        symbolTable.insertSymbol("float", "type", 8, 15);
        symbolTable.insertSymbol("x", "variable", 12, 20);
        symbolTable.insertSymbol("print", "function", 25, 30);

        // Displaying the symbol table
        symbolTable.displayTable();

        // Looking up and displaying a specific symbol
        Symbol lookedUpSymbol = symbolTable.lookupSymbol("x");
        System.out.println("Looked Up Symbol: " + lookedUpSymbol.toString());

        // Setting attributes for an existing symbol
        symbolTable.setSymbolAttributes("x", "newType", 8, 15, 25, "0x5000");
        symbolTable.displayTable();

        // Resetting attributes for an existing symbol
        symbolTable.resetSymbolAttributes("x");
        symbolTable.displayTable();
    }
}