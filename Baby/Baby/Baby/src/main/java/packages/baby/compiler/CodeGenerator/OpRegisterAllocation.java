package packages.baby.compiler.CodeGenerator;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class OpRegisterAllocation {
    Map<String, String> registerTable = new HashMap<>();

    public OpRegisterAllocation (){
        this.registerTable = new LinkedHashMap<>();
    }

    public void insertIntoTable (String key, String register){
        System.out.println("key: " + key + " value: " + register);
        registerTable.put (key, register);
    }

    public String getRegister (String key) {
        return registerTable.get(key);
    }
}
