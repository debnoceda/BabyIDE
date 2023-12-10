/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packages.baby.compiler.CodeGenerator;

/**
 *
 * @author Dave Noceda
 */

import java.util.BitSet;

public class MIPSAssembly {

    private BitSet registerTable;
    StringBuilder mipsCode = new StringBuilder();

    public MIPSAssembly(){
        registerTable = new BitSet(10);
        registerTable.set(0, 10, true);
    }

    public String allocateRegister(){
        int registerIndex = registerTable.nextSetBit(0);
        if (registerIndex != -1){
            registerTable.clear(registerIndex);
            return "$t" + registerIndex;
        } else {
            // Handle the case when no available registers
            System.err.println("No available registers. Register spilling or other strategy needed.");
            return null;
        }
    }
       
   public String generateMIPS(){
        
        mipsCode.append(".data\n");
        mipsCode.append(".text\n");
        mipsCode.append("main:\n");

        return mipsCode.toString();
    }

    public String exitProgram(){

        // Exit program
        mipsCode.append("    li $v0, 10\n");
        mipsCode.append("    syscall\n");

        return mipsCode.toString();
    }

    public String printStatements(){
        
        return mipsCode.toString();
    }
}
