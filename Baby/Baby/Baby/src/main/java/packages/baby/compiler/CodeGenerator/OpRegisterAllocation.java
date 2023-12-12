package packages.baby.compiler.CodeGenerator;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class OpRegisterAllocation {
    Map<String, String> registerTable = new HashMap<>();

    public OpRegisterAllocation (){
        registerTable = new LinkedHashMap<>();
    }

    public void insertIntoTable (String key, String register){
        registerTable.put (key, register);
    }

    public String getRegister (String key) {
        return registerTable.get(key);
    }
}
