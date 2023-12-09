/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packages.baby.compiler.CodeGenerator;

/**
 *
 * @author Dave Noceda
 */

import packages.baby.frames.Ide;
import java.io.FileWriter;
import java.io.IOException;

public class MIPSAssembly {
     private String fileName;
    //baby language to mips assembly code
    String mipsCode = generateMIPS();
    
    public MIPSAssembly(Ide ide){
        setFileName(ide);
        
        //Write MIPS code to a file
        writeToFile(fileName, mipsCode);
        System.out.println("MIPS code has been written in the file: " + fileName);
    }
            
    private void setFileName(Ide ide){
        fileName = ide.updateFileName();
        fileName = fileName.replace(".bby", ".s");
    }
    
    private static String generateMIPS(){
        StringBuilder mipsCode = new StringBuilder();
        
        mipsCode.append(".data\n");
        
        
        return mipsCode.toString();
    }
    
    private static void writeToFile(String filename, String content) {
        try (FileWriter writer = new FileWriter(filename, false)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
