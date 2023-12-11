/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packages.baby.compiler.CodeGenerator;

import java.util.BitSet;

/**
 *
 * @author Dave Noceda
 */

import java.util.Stack;

public class MIPSAssembly {

    private Stack<String> freeRegisters;
    private Stack<String> allocatedRegisters;
    private static int stringCounter = 0;

    public MIPSAssembly(){
        freeRegisters = new Stack<>();
        allocatedRegisters = new Stack<>();
        for (int i = 9; i >= 0; i--) {
            freeRegisters.push("$t" + i);
        }
    }

    public String getFreeRegister() {
        if (freeRegisters.isEmpty()) {
            throw new RuntimeException("No free registers available");
        }
        return freeRegisters.pop();
    }

    public void releaseRegister(String reg) {
        if (reg != null && reg.startsWith("$t")) {
            freeRegisters.push(reg);
        }
    }

    public void useRegisters(String reg) {
        if (reg != null && reg.startsWith("$t")) {
            allocatedRegisters.push(reg);
        }
    }

    public String getUsedRegister() {
        if (freeRegisters.isEmpty()) {
            throw new RuntimeException("No free registers available");
        }
        String useReg = allocatedRegisters.pop();
        releaseRegister(useReg);
        return useReg;
    }
       
   public String generateMIPS(){
        StringBuilder mipsCode = new StringBuilder();
        
        mipsCode.append(".data\n");
        mipsCode.append("newline: .asciiz \"\\n\"\n");
        mipsCode.append(".text\n");
        mipsCode.append("main:\n");

        return mipsCode.toString();
    }

    public String varDeclarationWord(String varName, String value, boolean isNum){
        StringBuilder mipsCode = new StringBuilder();

        mipsCode.append(".data\n");
        mipsCode.append(varName).append(": .asciiz ").append(value).append("\n");
        mipsCode.append(".text\n");
        return mipsCode.toString();
    }

    public String varDeclarationExpr(String varName, String value, boolean isNum){
        StringBuilder mipsCode = new StringBuilder();

        mipsCode.append("\n.data\n");
        if(isNum){
            String useReg = getFreeRegister();
            mipsCode.append(varName).append(": .word ").append(value).append("\n");
            mipsCode.append(".text\n\n");
            mipsCode.append("lw ").append(useReg).append(varName);
            useRegisters(useReg);
        }
        else{
            mipsCode.append(varName).append(": .word 0");
            mipsCode.append(".text\n\n");
        }
        return mipsCode.toString();
    }

    public String addOperand (){
        StringBuilder mipsCode = new StringBuilder();
        String useReg = getFreeRegister();
        mipsCode.append("add ").append(useReg).append(getUsedRegister() + " " + getUsedRegister());
        useRegisters(useReg);

        return mipsCode.toString();
    }

    public String printNewLine(){
        StringBuilder mipsCode = new StringBuilder();

        mipsCode.append("\n");
        mipsCode.append("li $v0, 4\n");
        mipsCode.append("la $a0, newline\n");
        mipsCode.append("syscall\n");

        return mipsCode.toString();
    }

    public String printStatements(String value, Boolean isExpr, Boolean isID){
        StringBuilder mipsCode = new StringBuilder();
        if (!isExpr){
            String varName = "statement_" + stringCounter++;
            mipsCode.append("\n");
            mipsCode.append(".data\n");
            mipsCode.append(varName).append(": .asciiz ").append(value).append("\n");
            mipsCode.append(".text\n");
            mipsCode.append("li $v0, 4\n");
            mipsCode.append("la $a0, ").append(varName).append("\n");
        }
        else{
            if (isID){
                mipsCode.append("\n");
                mipsCode.append("li $v0, 4\n");
                mipsCode.append("la $a0, ").append(value).append("\n");
            }
        }
        mipsCode.append("syscall\n");

        return mipsCode.toString();
    }

    public String exitProgram(){
        StringBuilder mipsCode = new StringBuilder();

        // Exit program
        mipsCode.append("\n");
        mipsCode.append("li $v0, 10\n");
        mipsCode.append("syscall\n");

        return mipsCode.toString();
    }
}
