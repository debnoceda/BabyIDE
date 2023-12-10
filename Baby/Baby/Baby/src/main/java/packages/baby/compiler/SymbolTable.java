package packages.baby.compiler;

import packages.baby.compiler.LexicalAnalysis.TokenType;
import java.util.*;
        
public class SymbolTable {

    private HashMap<String, Symbol> symbolTable;

    public SymbolTable() {
        this.symbolTable = new HashMap<>();
    }

    // Method to insert a symbol into the table
    public void insertSymbol(String identifier, String value, TokenType type) {
        Symbol symbol = new Symbol(identifier, value, type);
        symbolTable.put(identifier, symbol);
    }


    // Method to lookup a symbol in the table
    public Symbol lookupSymbol(String identifier) {
        return symbolTable.get(identifier);
    }

    // Method to set attributes for an existing symbol
    public void setSymbolAttributes(String identifier, String value, TokenType type) {
        if (symbolTable.containsKey(identifier)) {
            Symbol symbol = symbolTable.get(identifier);
            symbol.setName(identifier);
            symbol.setValue(value);
            symbol.setType(type);
        } else {
            System.out.println("'" + identifier + "' not found in the symbol table.");
        }
    }

    public void setSymbolValue(String identifier, String value) {
        if (symbolTable.containsKey(identifier)) {
            Symbol symbol = symbolTable.get(identifier); {
                symbol.setValue(value);
            }
        }
        else {
            System.out.println("'" + identifier + "' not found in the symbol table.");
        }
    }

    // Method to reset attributes for an existing symbol
    public void resetSymbolAttributes(String identifier) {
        if (symbolTable.containsKey(identifier)) {
            Symbol symbol = symbolTable.get(identifier);
            // Resetting attributes
            symbol.setValue("");
            symbol.setType(null);
        } else {
            System.out.println("'" + identifier + "' not found in the symbol table.");
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
        symbolTable.insertSymbol("x", "1", TokenType.INT);
        symbolTable.insertSymbol("y", "1.8", TokenType.DEC);
        symbolTable.insertSymbol("error", "this is an error", TokenType.STR);
        symbolTable.insertSymbol("ch", "a", TokenType.CHAR);

        // Displaying the symbol table
        symbolTable.displayTable();

        // Looking up and displaying a specific symbol
        Symbol lookedUpSymbol = symbolTable.lookupSymbol("x");
        System.out.println("Looked Up Symbol: " + lookedUpSymbol.toString());

        // Setting attributes for an existing symbol
        symbolTable.setSymbolAttributes("x", "2", TokenType.INT);
        symbolTable.displayTable();

        // Setting attributes for an existing symbol
        symbolTable.setSymbolValue("x", "3");
        symbolTable.displayTable();

        // Resetting attributes for an existing symbol
        symbolTable.resetSymbolAttributes("x");
        symbolTable.displayTable();
    }
}