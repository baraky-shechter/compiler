package edu.frostburg.cosc470;

import java.util.HashMap;

/**
 * @author Barak Shechter
 * @version 2016.5.16
 */
public class Nonterminals {

    private final String[] nonterminals = {
            "start","access","identifier_list", "declarations", "type", "compound_statement",
            "statement_list", "statement", "lefthandside", "righthandside", "expression", "simple_expression",
            "term", "factor", "relop", "addop", "mulop"
    };

    public HashMap<String,Integer> map;

    public Nonterminals() {
        map = new HashMap<>();
        int i = 100;
        for(String s: nonterminals) {
            map.put(s,i++);
        }
    }
}

