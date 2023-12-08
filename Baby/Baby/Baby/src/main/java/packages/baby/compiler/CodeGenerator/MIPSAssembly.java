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
    
    public MIPSAssembly(){
        setFileName();
        writeToFile(fileName, mipsCode);
    }
            
    private void setFileName(){
        Ide ide = new Ide();
        fileName = ide.getFileName();
    }
    
    private static String generateMIPS(){
        return "";
    }
    
    private static void writeToFile(String filename, String content) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
