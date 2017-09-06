package edu.frostburg.cosc470;

import java.io.File;

/**
 * Driver class for the compiler
 */
public class Main {

    public static void main(String[] args) {
        if (args.length == 1) {
            File f = new File(args[0] + ".txt");
            Lexer lex = new Lexer(f);
            Parser parse = new Parser(lex);
        }
        else if (args.length<1) {
            new Error(0,"No input file\nPass source file as argument");
        }
        else {
            new Error(0,"Incorrect usage. Use: \"java –jar b++.jar filename\"");
        }
    }
}
