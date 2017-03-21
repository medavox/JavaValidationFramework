package com.medavox.validation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adam Howard
 * @date 08/03/2017
 */
/**A key-value store for objects, which also preserves type information.
 * Used for storing the results of validation tests which perform some transformation,
 * and produce multiple variable.
 * For instance, a validation which converts the string "12345:password" into an int and a string.*/
public class ValidationResults<SuperType> {
    private Map<String, SuperType> values = new HashMap<String, SuperType>();
    private Map<String, Class<? extends SuperType>> types = new HashMap<String, Class<? extends SuperType>>();

    /**Add a variable to the results.
     * @param name The name of the variable to add
     * @param value the value (ie contents) of the variable
     * @param type the variable's type.
     * @return true if the variable was added, false if a variable already exists with that name.
     * @throws IllegalStateException if the name key is only present in either the values or
     * the types map.*/
    public boolean put(String name, Class<? extends SuperType> type, SuperType value ) {
        if(!values.containsKey(name) && !types.containsKey(name))  {
            //the maps are empty, good to go
            values.put(name, value);
            types.put(name, type);
            return true;
        }
        else if(values.containsKey(name) && types.containsKey(name)) {
            return false;
        }
        else {//the string key is only present in one of the two maps, which is wrong
            String presentMap = (values.containsKey(name) ? "values" : "types");
            String absentMap = (!values.containsKey(name) ? "values" : "types");
            throw new IllegalStateException("name key \""+name+"\" is present in "+presentMap+
                " but not in "+absentMap+"!");
        }
    }

    public boolean has(String name) {
        if(types.containsKey(name) == values.containsKey(name)) {
            return types.containsKey(name);
        }
        else {//the string key is only present in one of the two maps, which is wrong
            String presentMap = (values.containsKey(name) ? "values" : "types");
            String absentMap = (!values.containsKey(name) ? "values" : "types");
            throw new IllegalStateException("name key \""+name+"\" is present in "+presentMap+
                    " but not in "+absentMap+"!");
        }
    }

    public SuperType getValue(String name) {
        return values.get(name);
    }

    /**Get the type of the variable with the given name.
     * @return null if no variable with that name exists.*/
    public Class<? extends SuperType> getType(String name) {
        return types.get(name);
    }
}
