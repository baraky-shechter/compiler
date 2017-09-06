package edu.frostburg.cosc470;

import jdk.nashorn.internal.parser.TokenType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Barak Shechter
 */
public class Lexer {
    public static enum TokenPattern {
        STATIC("static"),
        VOID("void"),
        LEFTPAREN("\\("),
        RIGHTPAREN("\\)"),
        LEFTBRACKET("\\{"),
        RIGHTBRACKET("\\}"),
        DOLLAR("\\$"),
        PUBLIC("public"),
        PRIVATE("private"),
        COMMA(","),
        VAR("var"),
        CHAR("char"),
        INT("int"),
        COLON(":"),
        SEMICOLON(";"),
        GET("get"),
        PUT("put"),
        IF("if"),
        WHILE("while"),
        NUM("-?[0-9]+"),
        TRUE("true"),
        FALSE("false"),
        GREATEREQ(">="),
        GREATER(">{1}"),
        EQEQ("=="),
        EQ("="),
        LESSEQ("<="),
        NOTEQ("<>"),
        LESS("<"),
        PLUS("\\+"),
        MINUS("-"),
        MUL("\\*"),
        COMMENT("//"),
        DIV("/"),
        MOD("%"),
        SINGLEQUOTE("'"),
        ID("[A-Z]+[0-9|A-Z|_]*"),
        LITERAL("\\p{Alpha}{1}");



        public final String pattern;

        private TokenPattern(String pattern) {
            this.pattern = pattern;
        }
    }

    public static File source;
    public static ArrayList<Token> tokens;
    public static int tokenIndex = 0;
    public static Terminals terminals;
    public static Nonterminals nonterminals;

    public Lexer(File s) {
        tokens = new ArrayList<>();
        terminals = new Terminals();
        try {
            Scanner scan = new Scanner(s);
            int lineNumber = 0;
            while(scan.hasNext()) {
                ++lineNumber;
                String input = scan.nextLine();
                System.out.println(input);
                ArrayList<Token> tempTokens = lex(input,lineNumber);
                for (Token token : tempTokens) {
                    tokens.add(token);
                }
            }
            System.out.println(tokens.size());
            for (Token token : tokens) {
                System.out.println(token);
            }
        } catch(FileNotFoundException e) {
            new Error(11,"File not found");
        }
    }

    public static ArrayList<Token> lex(String input, int line) {
        ArrayList<Token> tokens = new ArrayList<>();

        StringBuffer tokensPatternsBuffer = new StringBuffer();
        for(TokenPattern p : TokenPattern.values()) {
            tokensPatternsBuffer.append(String.format("|(?<%s>%s)", p.name(), p.pattern));
        }
        Pattern tokenPatterns = Pattern.compile(new String(tokensPatternsBuffer.substring(1)));

        Matcher matcher = tokenPatterns.matcher(input);
        while(matcher.find()) {
            if(matcher.group(TokenPattern.COMMENT.name()) != null) {
                break;
            }
            if(matcher.group(TokenPattern.STATIC.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.STATIC.name());
                t.id = terminals.terminalsMap.get("static");
                t.type = "static";
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.VOID.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.VOID.name());
                t.id = terminals.terminalsMap.get("void");
                t.type = "void";
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.LEFTPAREN.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.LEFTPAREN.name());
                t.id = terminals.terminalsMap.get("(");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.RIGHTPAREN.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.RIGHTPAREN.name());
                t.id = terminals.terminalsMap.get(")");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.LEFTBRACKET.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.LEFTBRACKET.name());
                t.id = terminals.terminalsMap.get("{");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.RIGHTBRACKET.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.RIGHTBRACKET.name());
                t.id = terminals.terminalsMap.get("}");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.DOLLAR.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.DOLLAR.name());
                t.id = terminals.terminalsMap.get("$");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.PUBLIC.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.PUBLIC.name());
                t.id = terminals.terminalsMap.get("public");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.PRIVATE.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.PRIVATE.name());
                t.id = terminals.terminalsMap.get("private");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.VAR.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.VAR.name());
                t.id = terminals.terminalsMap.get("var");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.CHAR.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.CHAR.name());
                t.id = terminals.terminalsMap.get("char");
                t.type = "char";
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.INT.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.INT.name());
                t.id = terminals.terminalsMap.get("int");
                t.type = "int";
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.COLON.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.COLON.name());
                t.id = terminals.terminalsMap.get(":");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.SEMICOLON.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.SEMICOLON.name());
                t.id = terminals.terminalsMap.get(";");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.GET.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.GET.name());
                t.id = terminals.terminalsMap.get("get");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.PUT.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.PUT.name());
                t.id = terminals.terminalsMap.get("put");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.IF.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.IF.name());
                t.id = terminals.terminalsMap.get("if");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.WHILE.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.WHILE.name());
                t.id = terminals.terminalsMap.get("while");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.TRUE.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.TRUE.name());
                t.id = terminals.terminalsMap.get("true");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.FALSE.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.FALSE.name());
                t.id = terminals.terminalsMap.get("false");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.GREATEREQ.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.GREATEREQ.name());
                t.id = terminals.terminalsMap.get(">=");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.GREATER.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.GREATER.name());
                t.id = terminals.terminalsMap.get(">");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.EQEQ.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.EQEQ.name());
                t.id = terminals.terminalsMap.get("==");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.EQ.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.EQ.name());
                t.id = terminals.terminalsMap.get("=");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.LESSEQ.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.LESSEQ.name());
                t.id = terminals.terminalsMap.get("<=");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.NOTEQ.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.NOTEQ.name());
                t.id = terminals.terminalsMap.get("<>");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.LESS.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.LESS.name());
                t.id = terminals.terminalsMap.get("<");
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.PLUS.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.PLUS.name());
                t.id = terminals.terminalsMap.get("+");
                t.type = "add";
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.MINUS.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.MINUS.name());
                t.id = terminals.terminalsMap.get("-");
                t.type = "sub";
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.MUL.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.MUL.name());
                t.id = terminals.terminalsMap.get("*");
                t.type = "mul";
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.DIV.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.DIV.name());
                t.id = terminals.terminalsMap.get("/");
                t.type = "div";
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.MOD.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.MOD.name());
                t.id = terminals.terminalsMap.get("%");
                t.type = "mod";
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.SINGLEQUOTE.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.SINGLEQUOTE.name());
                t.id = terminals.terminalsMap.get("'");
                tokens.add(t);
            }

            else if(matcher.group(TokenPattern.ID.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.ID.name());
                t.id = terminals.terminalsMap.get("id");
                t.type = "id";
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.LITERAL.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.LITERAL.name());
                t.id = terminals.terminalsMap.get("literal");
                t.type = "literal";
                tokens.add(t);
            }
            else if(matcher.group(TokenPattern.NUM.name()) != null) {
                Token t = new Token();
                t.value = matcher.group(TokenPattern.NUM.name());
                t.id = terminals.terminalsMap.get("num");
                t.type = "num";
                tokens.add(t);
            }
            else {
                new Error(12, "Unrecognized token " + input +  "at line: " + line);
            }
        }
        return tokens;
    }

    public static Token getNextToken() {
        if(tokenIndex+1 >= tokens.size()) {
            return null;
        }
        return tokens.get(++tokenIndex);
    }

    public static boolean hasNext() {
        return (tokenIndex < tokens.size());
    }

    public static Token getCurrentToken() {
        return tokens.get(tokenIndex);
    }
}

