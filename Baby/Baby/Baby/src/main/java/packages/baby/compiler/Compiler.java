/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packages.baby.compiler;

import java.io.IOException;
import java.util.List;
import packages.baby.compiler.LexicalAnalysis.LexicalAnalyzer;
import packages.baby.compiler.LexicalAnalysis.Token;
import packages.baby.compiler.SyntaxAnalysis.Parser;
import packages.baby.components.Terminal;
import packages.baby.compiler.CodeGenerator.CompilerTerminalIntegration;
import packages.baby.compiler.CodeGenerator.MIPSAssembly;

/**
 *
 * @author liaminakigillamac
 */
public class Compiler {
    
    String code = "";
    Terminal terminal;
    StringBuilder message = new StringBuilder();
    String output;
    
    List<Token> tokens;
    
    public Compiler(String code, Terminal terminal){
        this.code = code;
        this.terminal = terminal;
    }
    
    public void run(String afileName){
        // Append $
        code = code + "$";
        
        /*
        * Lexemes must be separated by at least one space to be recognized as separate things.
        * Hence, to handle cases where there are no spaces before and after symbols, we need to
        * add white space before and after particular symbols , ; ( ) + - * /
        */

        // Add white space before and after particular symbols
        code = code.replaceAll("([,;()\\+\\-*/])", " $1 "); // !! Issue: will also replace symbols inside quote marks

        // Remove comments
        code = code.replaceAll("#.*\n", ""); // !! Issue: will also remove those in quote marks

        // Handle numbers with signs
        code.replaceAll("([+-/*])\\s*([+-])\\s*([0-9]+)", "$1 $2$3"); 
        
        String[] lines = code.split("\n");           

        // Start Lexical Analysis of code
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
        tokens = lexicalAnalyzer.analyze(lines);

        MIPSAssembly mips = new MIPSAssembly();
        
        // Start Syntax Analysis of Code
        Parser parser = new Parser(message, tokens, afileName, mips);
        //String successState = "Parse Success State: " + parser.getSuccess();
        
        // System.out.println(lexicalAnalyzer.getCh());
        //terminal.setText(printTokens(tokens) + "\n" + successState);
//        terminal.setText(printTokens(tokens));
        output = getTerminalMessage();
        terminal.setText(output);
        if(output.equals("Parsing successful!")){
            try{
                CompilerTerminalIntegration mipsCompiler = new CompilerTerminalIntegration();
                mipsCompiler.runMIPSFile(parser.getFilePath());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();  // Handle the exception appropriately
            }
        }   
    }
    
    public String printTokens(List<Token> tokens){
        StringBuilder stringBuilder = new StringBuilder();
        
        for(int i = 0; i < tokens.size(); i++){
            Token token = tokens.get(i);
            stringBuilder.append("Line Number: " + token.getNLine() + "\t" + "Value: " +  token.getValue() + "\t" + "Token Type: " + token.getTokenType() + "\n");
        }
        
        String tokensTable = stringBuilder.toString();
        
        return tokensTable;
    }
    
    public List<Token> getTokens(){
        return tokens;
    }

    public String getTerminalMessage() {
        return message.toString();
    }
    
}
