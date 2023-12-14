/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packages.baby.compiler.CodeGenerator;

import java.util.HashMap;

/**
 *
 * @author Dave Noceda
 */

import java.util.Stack;

public class MIPSAssembly {

    private Stack<String> freeRegisters;
    private Stack<String> allocatedRegisters;
    private Stack<String> regRelease;
    private static int stringCounter = 0;
    OpRegisterAllocation regTable;
    

    public MIPSAssembly(){
        regTable = new OpRegisterAllocation();
        freeRegisters = new Stack<>();
        allocatedRegisters = new Stack<>();
        regRelease = new Stack<>();
        for (int i = 9; i >= 0; i--) {
            freeRegisters.push("$t" + i);
        }
    }

    public String getFreeRegister() {
        if (freeRegisters.isEmpty()) {
            return "NoRegistersAvailable";
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
        if (allocatedRegisters.isEmpty()) {
            return "EmptyStack";
        }
        else
            return allocatedRegisters.pop();
    }

    public String getRegRelease(){
        if (regRelease.isEmpty()) {
            return "EmptyStack";
        }
        else
            return regRelease.pop();
    }

    public static boolean isNumeric(String str) {
        try {
            // Try parsing the string as an integer
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e1) {
            try {
                // Try parsing the string as a double
                Double.parseDouble(str);
                return true;
            } catch (NumberFormatException e2) {
                // If both parseInt and parseDouble throw exceptions, the string is not a number
                return false;
            }
        }
    }

    public void regOrder (String var){
        String register = regTable.getRegister(var);
        if (isNumeric(var)){
            regRelease.push(register);
        }
        if (register != null)
            useRegisters(register);
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

        mipsCode.append("\n.data\n");
        mipsCode.append(varName).append(": .asciiz ").append(value).append("\n");
        mipsCode.append(".text\n");
        return mipsCode.toString();
    }

    public String varDeclarationExpr(String varName, String value, boolean isNum, boolean hasOperators){
        StringBuilder mipsCode = new StringBuilder();
        String useReg;

        mipsCode.append("\n.data\n");
        if (hasOperators){
            useReg = getUsedRegister();
            mipsCode.append(varName).append(": .word 0\n");
            mipsCode.append(".text\n\n");
            mipsCode.append("    sw ").append(useReg + ", ").append(varName).append("\n");
            regTable.insertIntoTable(varName, useReg);
        }
        else{
            if (isNum){
                useReg = getFreeRegister();
                mipsCode.append(varName).append(": .word ").append(value).append("\n");
                mipsCode.append(".text\n\n");
                mipsCode.append("    lw ").append(useReg + ", ").append(varName).append("\n");
                regTable.insertIntoTable(varName, useReg);
                useRegisters(useReg);
            }
            else {
                String useReg1 = getFreeRegister();
                useReg = getUsedRegister();
                mipsCode.append(varName).append(": .word 0\n");
                mipsCode.append(".text\n\n");
                mipsCode.append("    move " + useReg1 + ", ");
                regTable.insertIntoTable(varName, useReg1);
                mipsCode.append(useReg + "\n");
                mipsCode.append("    sw ").append(useReg1 + ", ").append(varName).append("\n");
            }
        }
        return mipsCode.toString();
    }

    public String immediateNum (String value){
        StringBuilder mipsCode = new StringBuilder();
        String useReg = getFreeRegister();

        mipsCode.append("    li " + useReg + " " + value);
        regTable.insertIntoTable(value, useReg);

        return mipsCode.toString();
    }

    public String input (String varName, boolean isNum){
        StringBuilder mipsCode = new StringBuilder();

        if(!isNum){
            mipsCode.append("\n.data\n");
            mipsCode.append("  " + varName + ": .space 256\n");
            mipsCode.append(".text\n\n");

            mipsCode.append("    li $v0, 8\n");
            mipsCode.append("    la $a0 " + varName + "\n");
            mipsCode.append("    li $a1, 255\n");
            mipsCode.append("    syscall\n");
        }
        else{
            mipsCode.append("\n.data\n");
            mipsCode.append("  " + varName + ": .word 0\n");
            mipsCode.append(".text\n\n");

            mipsCode.append("    li $v0, 5\n");
            mipsCode.append("    syscall\n");
            mipsCode.append("    sw $v0 " + varName + "\n");
        }
        
        return mipsCode.toString();
    }

    public String addOperand (){
        StringBuilder mipsCode = new StringBuilder();
        String op2 = getUsedRegister();
        String op1 = getUsedRegister();
        if (op1 != "EmptyStack" && op2 != "EmptyStack"){
            String useReg = getFreeRegister();
            mipsCode.append("    add ").append(useReg + " ").append(op1 + " " + op2);
            useRegisters(useReg);
        }
        String release = getRegRelease();
        String release2 = getRegRelease();
        if(!release.equals("EmptyStack"))
            releaseRegister(release);
        else if(!release2.equals("EmptyStack"))
            releaseRegister(release2);
        return mipsCode.toString();
    }

    public String subOperand (){
        StringBuilder mipsCode = new StringBuilder();
        String op2 = getUsedRegister();
        String op1 = getUsedRegister();
        if (op1 != "EmptyStack" && op2 != "EmptyStack"){
            String useReg = getFreeRegister();
            mipsCode.append("    sub ").append(useReg + " ").append(op1 + " " + op2);
            useRegisters(useReg);
        }
        String release = getRegRelease();
        String release2 = getRegRelease();
        if(!release.equals("EmptyStack"))
            releaseRegister(release);
        else if(!release2.equals("EmptyStack"))
            releaseRegister(release2);
        return mipsCode.toString();
    }

    public String mulOperand (){
        StringBuilder mipsCode = new StringBuilder();
        String op2 = getUsedRegister();
        String op1 = getUsedRegister();
        if (op1 != "EmptyStack" && op2 != "EmptyStack"){
            String useReg = getFreeRegister();
            mipsCode.append("    mul ").append(useReg + " ").append(op1 + " " + op2);
            useRegisters(useReg);
        }
        String release = getRegRelease();
        String release2 = getRegRelease();
        if(!release.equals("EmptyStack"))
            releaseRegister(release);
        else if(!release2.equals("EmptyStack"))
            releaseRegister(release2);
        return mipsCode.toString();
    }

    public String divOperand (){
        StringBuilder mipsCode = new StringBuilder();
        String op2 = getUsedRegister();
        String op1 = getUsedRegister();
        if (op1 != "EmptyStack" && op2 != "EmptyStack"){
            String useReg = getFreeRegister();
            mipsCode.append("    div ").append(op1 + " " + op2 + "\n");
            mipsCode.append("    mflo " + useReg);
            useRegisters(useReg);
        }
        String release = getRegRelease();
        String release2 = getRegRelease();
        if(!release.equals("EmptyStack"))
            releaseRegister(release);
        else if(!release2.equals("EmptyStack"))
            releaseRegister(release2);
        return mipsCode.toString();
    }

    public String printNewLine(){
        StringBuilder mipsCode = new StringBuilder();

        mipsCode.append("\n");
        mipsCode.append("    li $v0, 4\n");
        mipsCode.append("    la $a0, newline\n");
        mipsCode.append("    syscall\n");

        return mipsCode.toString();
    }

    public String printStatements(String value, Boolean isExpr, Boolean isID, Boolean isNum, Boolean isIDNum){
        StringBuilder mipsCode = new StringBuilder();
        if (!isExpr){
            String varName = "statement_" + stringCounter++;
            mipsCode.append("\n");
            mipsCode.append(".data\n");
            mipsCode.append(varName).append(": .asciiz ").append(value).append("\n");
            mipsCode.append(".text\n");
            mipsCode.append("    li $v0, 4\n");
            mipsCode.append("    la $a0, ").append(varName).append("\n");
        }
        else{
            if (isID){
                if(!isIDNum){
                    mipsCode.append("\n");
                    mipsCode.append("    li $v0, 4\n");
                    mipsCode.append("    la $a0, ").append(value).append("\n");
                }
                else if (isNum || isIDNum){
                    mipsCode.append("\n");
                    mipsCode.append("    li $v0, 1\n");
                    mipsCode.append("    lw $a0, ").append(value).append("\n");
                }
            }
        }
        mipsCode.append("    syscall\n");

        return mipsCode.toString();
    }

    public String exitProgram(){
        StringBuilder mipsCode = new StringBuilder();

        // Exit program
        mipsCode.append("\n");
        mipsCode.append("    li $v0, 10\n");
        mipsCode.append("    syscall\n");

        return mipsCode.toString();
    }
}
