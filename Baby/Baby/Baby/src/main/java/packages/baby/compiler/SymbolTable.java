package packages.baby.compiler;

import packages.baby.compiler.LexicalAnalysis.TokenType;
import java.util.*;
        
public class SymbolTable {

    private Map<String, Symbol> symbolTable;

    public SymbolTable() {
        this.symbolTable = new LinkedHashMap<>();
    }

    // Method to insert a symbol into the table
    public void insertSymbol(String identifier,TokenType tokenType, String dataType) {
        Symbol symbol = new Symbol(identifier, tokenType, dataType);
        symbolTable.put(identifier, symbol);
    }


    // Method to lookup a symbol in the table
    public Symbol lookupSymbol(String identifier) {
        return symbolTable.get(identifier);
    }

    // Method to set attributes for an existing symbol
    public void setSymbolAttributes(String identifier, TokenType tokenType, String dataType) { // D
        if (symbolTable.containsKey(identifier)) {
            Symbol symbol = symbolTable.get(identifier);
            symbol.setName(identifier);
            //symbol.setValue(value);
            symbol.setTokenType(tokenType);
            symbol.setDataType(dataType);
        } else {
            System.out.println("'" + identifier + "' not found in the symbol table.");
        }
    }

    // public void setSymbolValue(String identifier, String value) {
    //     if (symbolTable.containsKey(identifier)) {
    //         Symbol symbol = symbolTable.get(identifier); {
    //             symbol.setValue(value);
    //         }
    //     }
    //     else {
    //         System.out.println("'" + identifier + "' not found in the symbol table.");
    //     }
    // }

    public void setSymbolTokenType(String identifier, TokenType tokenType) {
        if (symbolTable.containsKey(identifier)) {
            Symbol symbol = symbolTable.get(identifier); {
                symbol.setTokenType(tokenType);
            }
        }
        else {
            System.out.println("'" + identifier + "' not found in the symbol table.");
        }
    }

    public void setSymbolType(String identifier, String dataType) {
        if (symbolTable.containsKey(identifier)) {
            Symbol symbol = symbolTable.get(identifier); {
                symbol.setDataType(dataType);
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
            //symbol.setValue(null);
            symbol.setTokenType(null);
            symbol.setDataType(null);
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

    public void printSymbolTable(){
        
        System.out.println("Traversing using Iterator:");
        Iterator<Map.Entry<String, Symbol>> iterator = symbolTable.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Symbol> entry = iterator.next();
            String key = entry.getKey();
            String name = entry.getValue().getName();
            TokenType tokType = entry.getValue().getTokenType();
            String datType = entry.getValue().getDataType();
            
            System.out.println("Key: " + key + "\tIdentifier: " + name + "\tTokenType: " + tokType + "\tDataType: " + datType);
        }
    }
    
    public void setDataType(int varCount, String dataType){
        // Get the keys and reverse them
        List<String> keys = new ArrayList<>(symbolTable.keySet());
        Collections.reverse(keys);

        int count = 0;

        // Iterate over the reversed keys and set dataType
        for (String key : keys) {
            Symbol symbol = symbolTable.get(key);

            // Set the dataType for the current symbol
            symbol.setDataType(dataType);
                    
            count++;
            if (count == varCount) {
                break;  // Stop after setting dataType for varCount elements
            }
        }
    }

    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable();

        // Inserting symbols into the table
        symbolTable.insertSymbol("x", TokenType.INT, "num");
        symbolTable.insertSymbol("y", TokenType.DEC, "num");
        symbolTable.insertSymbol("error", TokenType.STR, "word");
        symbolTable.insertSymbol("ch", TokenType.CHAR, "word");

        // Displaying the symbol table
        symbolTable.displayTable();

        // Looking up and displaying a specific symbol
        Symbol lookedUpSymbol = symbolTable.lookupSymbol("x");
        System.out.println("Looked Up Symbol: " + lookedUpSymbol.toString());

        // Setting attributes for an existing symbol
        symbolTable.setSymbolAttributes("x", TokenType.INT, "num");
        symbolTable.displayTable();

        // // Setting attributes for an existing symbol
        // symbolTable.setSymbolValue("x", "3");
        // symbolTable.displayTable();

        // Resetting attributes for an existing symbol
        symbolTable.resetSymbolAttributes("x");
        symbolTable.displayTable();
    }
}