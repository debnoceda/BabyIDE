package packages.baby.compiler.LexicalAnalysis;

import java.util.*;

public class LexicalAnalyzer {
    // private char aCh;

    /*
     * For token types characterized by fixed lexemes, 
     * such as special characters and predefined keywords,
     * an automatic determination of their token type can be achieved
     * without reliance on a FA.
     * We handle it by creating a Map for these lexemes
     * and their corresponding token type.
     * This way we just need to check if a string is in the map
     * and get its token type directly if it exists there.
     */

    private Map<String, TokenType> typeWithFixedLex;

    public LexicalAnalyzer(){
        initTypeWithFixedLexMap();
    }

    private void initTypeWithFixedLexMap(){
        this.typeWithFixedLex = new HashMap<>();
        typeWithFixedLex.put("+",TokenType.PLUS);
        typeWithFixedLex.put("-",TokenType.MINUS);
        typeWithFixedLex.put("*",TokenType.TIMES);
        typeWithFixedLex.put("/",TokenType.DIVIDE);
        typeWithFixedLex.put(";",TokenType.SEMICOLON);
        typeWithFixedLex.put("(",TokenType.LPAREN);
        typeWithFixedLex.put(")",TokenType.RPAREN);
        typeWithFixedLex.put(",",TokenType.COM);
        typeWithFixedLex.put("let",TokenType.LET);
        typeWithFixedLex.put("be",TokenType.BE);
        typeWithFixedLex.put("word",TokenType.DATATYPE);
        typeWithFixedLex.put("num",TokenType.DATATYPE);
        typeWithFixedLex.put("enter",TokenType.ENTER);
        typeWithFixedLex.put("show",TokenType.SHOW);
        typeWithFixedLex.put("showline",TokenType.SHOWLINE);
        typeWithFixedLex.put("$",TokenType.EOF);
        typeWithFixedLex.put("=",TokenType.EQUAL);
        // Might add for printing with new line

    }

    public List<Token> analyze(String[] lines){
        List<Token> tokens = new ArrayList<>();
        Automaton automaton = new Automaton();

        for(int i = 0; i < lines.length; i++){
            
            /*  
             *  Lexemes must be separated by at least one space to be recognized as separate things.
             *  Here, we split the line by the space to identify the token type of each string.
             *  However, spaces inside double quote should not be included since it is part of the string.
             *  We handle it using the regex.
             */

            // Handle space and exclude those inside double and single quotes
            String regex = "\\s+(?=(?:[^'\"]*['\"][^'\"]*['\"])*(?![^'\"]*['\"]))";

            for(String str : lines[i].split(regex)){
                
                TokenType aTokenType;

                if(typeWithFixedLex.containsKey(str))
                    aTokenType = typeWithFixedLex.get(str);
                else{
                    aTokenType = automaton.getTokenType(str);
                    // this.aCh = automaton.getCh();
                }
                    

                Token token = new Token(i+1, aTokenType, str);
                tokens.add(token);
            }
        }

        return tokens;

    }

    // public char getCh(){
    //     return this.aCh;
    // }



}
