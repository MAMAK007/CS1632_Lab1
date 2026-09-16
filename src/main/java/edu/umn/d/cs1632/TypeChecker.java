package edu.umn.d.cs1632;

import java.util.List;

public class TypeChecker {

    public String getType(String value) {
        if (value == null) return "Null";

        try {
            Integer.parseInt(value);
            return "Integer";
        } catch (NumberFormatException ignored) {}

        try {
            Float.parseFloat(value);
            return "Float";
        } catch (NumberFormatException ignored) {}
        
        try {
        	Double.parseDouble(value);
        	return "Double";
        } catch (NumberFormatException ignored) {}
        
        return "String";
    }
    
    public String compareType(List<String> index) {
    	for (int i = 1; i < index.size(); i ++) {
			if (getType(index.get(0)).equals(getType(index.get(i)))) {
				continue;
			} else {
				return "\n---------\nData Types: (Multiple Data Types)";
			}
		}
    	
    	return "\n---------\nData Types: (All " + getType(index.get(0))+ "s)";
	}
    
}