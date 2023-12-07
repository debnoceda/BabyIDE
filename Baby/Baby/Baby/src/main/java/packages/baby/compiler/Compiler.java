/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packages.baby.compiler;

import java.util.List;
import packages.baby.compiler.LexicalAnalysis.LexicalAnalyzer;
import packages.baby.compiler.LexicalAnalysis.Token;
import packages.baby.components.Terminal;

/**
 *
 * @author liaminakigillamac
 */
public class Compiler {
    
    String code = "";
    Terminal terminal;
    
    public Compiler(String code, Terminal terminal){
        this.code = code;
        this.terminal = terminal;
    }
    
    public void run(){
        String[] lines = code.split("\n");

        // Start Lexical Analysis of code
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
        List<Token> tokens = lexicalAnalyzer.analyze(lines);
        
        // System.out.println(lexicalAnalyzer.getCh());
        terminal.setText(getTokens(tokens));
    }
    
    public String getTokens(List<Token> tokens){
        StringBuilder stringBuilder = new StringBuilder();
        
        for(int i = 0; i < tokens.size(); i++){
            Token token = tokens.get(i);
            stringBuilder.append("Line Number: " + token.getNLine() + "\t" + "Value: " +  token.getValue() + "\t" + "Token Type: " + token.getTokenType() + "\n");
        }
        
        String tokensTable = stringBuilder.toString();
        
        return tokensTable;
    }
    
}
