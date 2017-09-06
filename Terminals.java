package edu.frostburg.cosc470;

import java.util.HashMap;

/**
 * @author Barak Shechter
 * @version 2016.6.23
 */
public class Terminals {

    public HashMap<String, Integer> terminalsMap;

    private String[] terminals = {
            "static", "void", "id", "(", ")", "{", "}", "$", "public",
            "private", ",", "var", ":", ";", "char", "int", "get", "put",
            "if", "while", "=", "num", "true", "false","'", "literal", ">",
            ">=", "==", "<=", "<", "<>", "+", "-", "*", "/", "%"
    };

    /**
     * Constructor for terminals
     */
    public Terminals() {
        terminalsMap = new HashMap<>();
        int i = 1;
        for (String s: terminals) {
            terminalsMap.put(s,i++);
        }
    }

}
