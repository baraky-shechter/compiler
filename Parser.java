package edu.frostburg.cosc470;

import java.util.LinkedList;

/**
 * Parser for compiler
 *
 * @author Barak Shechter
 * @version 2016.6.29
 */
public class Parser {

    public int[] parseTableIndex = {0,1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116};

    private LinkedList parseStack, valueStack, typeStack, ifStack, whileStack, whileHelperStack;
    private Token currentToken;
    private Token temp;
    private Lexer lexer;
    private int quadCount=0, placeCount =0, action=-1, storageCount = 0;
    public SymbolTable symTable;
    private StringBuilder strBuilder;
    public int[][] parseTable;
    private Generator generator;

    private boolean keepGoing = true;
    private boolean eof = false;


    public Parser(Lexer lexer) {
        parseTable = new ParseTable().parseTable;
        parseStack = new LinkedList();
        valueStack = new LinkedList();
        typeStack = new LinkedList();
        symTable = new SymbolTable();
        ifStack = new LinkedList();
        whileStack = new LinkedList();
        whileHelperStack = new LinkedList();
        strBuilder = new StringBuilder();
        generator = new Generator();

        this.lexer = lexer;

        push(0,true);
        currentToken = lexer.getCurrentToken();
        int state = (Integer)parseStack.peek();
        action = parseTable[state][getIndex(parseTableIndex,currentToken.id)];

        boolean ifExpression = false;
        boolean whileExpression = false;

        while(keepGoing && action!=0 && action !=99) {
            System.out.println("State:          " + (int)parseStack.peekFirst());
            System.out.println("Current Token:  " + currentToken);
            System.out.println("Action:         " + action);
            System.out.println("Stack:          " + parseStack +"\n");
            state = (Integer)parseStack.peek();
            action = parseTable[state][getIndex(parseTableIndex,currentToken.id)];

            // Checking if or while expressions
            if (currentToken.id == 19) {
                ifExpression = true; // IF
            }
            if (currentToken.id == 20) {
                whileExpression = true; // WHILE
                whileHelperStack.push(quadCount);
            }

            // Shifting
            if(action> 0) {
                System.out.println("Shifting to:    " + action);
                shift(action);
            }
            // Reducing
            else if (action<0) {
                action *= -1;
                System.out.println("Reducing with rule: " + action);
                int[] rule = Grammar.rules[action];
                int nonterminal = rule[0];
                int numPops = rule[1] +2;
                Token t = new Token();
                Token s = new Token();
                Token[] tokens;
                tokens = new Token[numPops-2];
                for(int i =2; i < numPops; ++i) {
                    popState();
                    t = popToken();
                    tokens[i - 2] = t;
                }
                s.id = nonterminal;
                s.value = t.value;
                s.type = t.type;

                // Semantic Analysis
                switch(action) {
                    case 43:
                        s.value = "mod";
                        s.type = "mulop";
                        break;
                    case 42:
                        s.value = "div";
                        s.type = "mulop";
                        break;
                    case 41:
                        s.value = "mul";
                        s.type = "mulop";
                        break;
                    case 40:
                        s.value = "sub";
                        s.type = "addop";
                        break;
                    case 39:
                        s.value = "add";
                        s.type = "addop";
                        break;
                    case 38:
                        s.value = "jne";
                        s.type = "relop";
                        break;
                    case 37:
                        s.value = "jlt";
                        s.type = "relop";
                        break;
                    case 36:
                        s.value = "jle";
                        s.type = "relop";
                        break;
                    case 35:
                        s.value = "jeq";
                        s.type = "relop";
                        break;
                    case 34:
                        s.value = "jge";
                        s.type = "relop";
                        break;
                    case 33:
                        s.value = "jgt";
                        s.type = "relop";
                        break;
                    case 32:
                        Token literal = tokens[1];
                        s.value = "#" + (Character.getNumericValue(literal.value.charAt(0))+87);
                        s.type = literal.type;
                        break;
                    case 31:
                        s.value = "#" + 0;
                        s.type = "boolean";
                        break;
                    case 30:
                        s.value = "#" + 1;
                        s.type = "boolean";
                        break;
                    case 29:
                        s.value = "#"+t.value;
                        s.type = t.type;
                        break;
                    case 28:
                        if(symTable.contains(t.value)) {
                            s.value = t.value;
                            s.type = t.type;
                        }
                        else {
                            new Error(32, "Cannot find symbol");
                        }
                        break;
                    case 27: {
                        Token term = tokens[2];
                        Token mulop = tokens[1];
                        Token factor = tokens[0];
                        if(factor.type.equals("num") && term.type.equals("num"))
                            generator.gen(""+nextQuad(), mulop.value,term.value,factor.value,s.value);
                        else if (term.type.equals("id") && factor.type.equals("num")) {
                            if (symTable.contains(term.value)) {
                                Token temp = symTable.retrieve(term.value);
                                generator.gen("" + nextQuad(), mulop.value, term.value, factor.value, s.value);
                            } else new Error(31, "Wrong token discovered");
                        } else if (term.type.equals("id") && factor.type.equals("id")) {
                            if (symTable.contains(term.value)) {
                                Token temp = symTable.retrieve(term.value);
                                Token temp2 = symTable.retrieve(factor.value);
                                generator.gen("" + nextQuad(), mulop.value, temp.value, temp2.value, s.value);
                            } else new Error(31, "Wrong token discovered");
                        } else {
                            new Error(31, "Wrong token discovered");
                        }
                        break;
                    }
                    case 25: {
                        Token simpleexpression = tokens[2];
                        Token addop = tokens[1];
                        Token term = tokens[0];
                        if(term.type.equals("num") && simpleexpression.type.equals("num"))
                            generator.gen(""+nextQuad(), addop.value,simpleexpression.value,term.value,s.value);
                        else if (simpleexpression.type.equals("id") && term.type.equals("num")) {
                            if (symTable.contains(simpleexpression.value)) {
                                Token temp = symTable.retrieve(simpleexpression.value);
                                generator.gen("" + nextQuad(), addop.value, simpleexpression.value, term.value, s.value);
                            } else new Error(31, "Wrong token discovered");
                        } else if (simpleexpression.type.equals("id") && term.type.equals("id")) {
                            if (symTable.contains(simpleexpression.value)) {
                                Token temp = symTable.retrieve(simpleexpression.value);
                                Token temp2 = symTable.retrieve(term.value);
                                generator.gen("" + nextQuad(), addop.value, temp.value, temp2.value, s.value);
                            } else; new Error(31, "Wrong token discovered");
                        } else {
                            new Error(31, "Wrong token discovered");
                        }
                        break;
                    }
                    case 23:
                    {
                        Token simpleexpression = tokens[2];
                        Token relop = tokens[1];
                        Token simpleexpression2 = tokens[0];
                        s.type = "boolean";
                        s.value = nextPlace();
                        if(simpleexpression.type.equals("num") && simpleexpression2.type.equals("num")) {
                            generator.gen("" + nextQuad(), relop.value, simpleexpression.value, simpleexpression2.value, "#"+(quadCount+2));
                            generator.gen("" + nextQuad(), "sto","#0", "", s.value);
                            generator.gen("" + nextQuad(), "jmp","","","#" + (quadCount +1));
                            generator.gen("" + nextQuad(), "sto", "#1", "", s.value);
                        }
                        else if (simpleexpression.type.equals("num") && simpleexpression2.type.equals("id")) {
                            if(symTable.contains(simpleexpression2.value)) {
                                Token token = symTable.retrieve(simpleexpression2.value);
                                generator.gen("" + nextQuad(), relop.value, simpleexpression.value, token.value, "#"+(quadCount+2));
                                generator.gen("" + nextQuad(), "sto","#0", "", s.value);
                                generator.gen("" + nextQuad(), "jmp","","","#" + (quadCount +1));
                                generator.gen("" + nextQuad(), "sto", "#1", "", s.value);
                            } else {
                                new Error(31, "Wrong token discovered");
                            }
                        } else if(simpleexpression.type.equals("id") && simpleexpression2.type.equals("num")) {
                            {
                                if(symTable.contains(simpleexpression.value)) {
                                    Token token = symTable.retrieve(simpleexpression.value);
                                    generator.gen("" + nextQuad(), relop.value, token.value, simpleexpression2.value, "#"+(quadCount+2));
                                    generator.gen("" + nextQuad(), "sto","#0", "", s.value);
                                    generator.gen("" + nextQuad(), "jmp","","","#" + (quadCount +1));
                                    generator.gen("" + nextQuad(), "sto", "#1", "", s.value);
                                } else {
                                    new Error(31, "Wrong token discovered");
                                }
                            }
                        } else if (simpleexpression.type.equals("id") && simpleexpression2.type.equals("id")) {
                            if (symTable.contains(simpleexpression.value) && symTable.contains(simpleexpression2.value)) {
                                Token token = symTable.retrieve(simpleexpression.value);
                                Token token2 = symTable.retrieve(simpleexpression2.value);
                                generator.gen("" + nextQuad(), relop.value, token.value, token2.value, "#" + (quadCount + 2));
                                generator.gen("" + nextQuad(), "sto", "#0", "", s.value);
                                generator.gen("" + nextQuad(), "jmp", "", "", "#" + (quadCount + 1));
                                generator.gen("" + nextQuad(), "sto", "#1", "", s.value);
                            }
                            else {
                                new Error(31, "Wrong token discovered");
                            }
                        }
                        if(ifExpression) {
                            ifStack.push(quadCount);
                            generator.gen("" + nextQuad(), "jne", s.value, "#1", "#");
                            ifExpression = false;
                        }
                        if(whileExpression) {
                            whileStack.push(quadCount);
                            generator.gen(""+nextQuad(), "jne", s.value, "#1", "#");
                            whileExpression = false;
                        }
                        break;
                    }
                    case 20:
                    {
                        Token righthandside = tokens[0];
                        Token id = tokens[2];
                        s.value = righthandside.value;
                        if(symTable.contains(id.value)) {
                            generator.gen(""+nextQuad(), "sto", righthandside.value, "", symTable.retrieve(id.value).value);
                        }
                        else new Error(31, "Wrong token discovered");
                        break;
                    }
                    case 19: {
                        generator.backpatch((Integer) whileStack.pop(), quadCount + 1);
                        whileExpression = false;
                        generator.gen("" + nextQuad(), "jmp", "", "", "#" + whileHelperStack.pop());
                        s.type = t.type;
                        s.value = null;
                        break;
                    }
                    case 18: {
                        generator.backpatch((Integer)ifStack.pop(), quadCount);
                        ifExpression = false;
                        s.type = t.type;
                        s.value = null;
                        break;
                    }
                    case 17: { // PUT
                        Token id = tokens[1];
                        if (id.type.equals("id") && symTable.contains(id.value)) {
                            Token temp = symTable.retrieve(id.value);
                            generator.gen("" + nextQuad(), "sys", "#-2", "#63", "");
                            generator.gen("" + nextQuad(), "sys", "#1", "", temp.value);
                            s.value = temp.value;
                            s.type = temp.type;
                            break;
                        }
                    }
                    case 16: { // GET
                        Token id = tokens[1];
                        if(id.type.equals("id") && symTable.contains(id.value)) {
                            Token temp = symTable.retrieve(id.value);
                            generator.gen(""+nextQuad(), "sys", "#-1", temp.value,"");
                            generator.gen(""+nextQuad(), "sys", "#0", "","");
                            s.value = temp.value;
                            s.type = temp.type;
                            break;
                        }
                    }
                    case 7: {
                        Token type = tokens[1];
                        Token identifierList = tokens[3];
                        Token declarations = tokens[5];
                        s.value = nextPlace();
                        String name = identifierList.value;
                        if (type.type.equals("char")) {
                            s.type = type.type;
                            symTable.insert(name, s);
                            generator.gen("" + nextQuad(), "sto", "#32", "", s.value);
                        } else if (type.type.equals("int")) {
                            s.type = type.type;
                            symTable.insert(name, s);
                            generator.gen("" + nextQuad(), "sto", "#0", "", s.value);
                        }
                        else new Error(31, "Wrong token discovered");
                        break;
                    }
                }

                action = (int)parseStack.peekFirst();
                push(s,false);
                Integer temp2 = parseTable[action][getIndex(parseTableIndex,nonterminal)];
                push(temp2,true);
                System.out.println(parseStack);
                action = (int)parseStack.peekFirst();
            }
            // End of file reached
            if (currentToken == null) {
                keepGoing = false;
                action = 1;
                System.out.println("Reducing with rule: " + action);
                int[] rule = Grammar.rules[action];
                int nonterminal = rule[0];
                int numPops = rule[1] +2;
                Token t = new Token();
                Token s = new Token();
                Token[] tokens;
                tokens = new Token[numPops-2];
                for(int i =2; i < numPops; ++i) {
                    popState();
                    t = popToken();
                    tokens[i - 2] = t;
                }
                s.id = nonterminal;
                s.value = t.value;
                s.type = t.type;
                action = (int)parseStack.peekFirst();
                push(s,false);
                Integer temp2 = parseTable[action][getIndex(parseTableIndex,nonterminal)];
                push(temp2,true);
                System.out.println(parseStack);
                action = (int)parseStack.peekFirst();

                if(action == 3) {
                    System.out.println("\nAccepted");
                    System.out.println("Generating code...");
                    generator.writeOutput();
                }
                else {
                    System.out.println("\nRejected");
                }

            }
        }
    }

    public int getIndex(int[] indexTable, int code) {
        for (int i = 1; i < indexTable.length; ++i) {
            if (code == indexTable[i]) return i;
        }
        return -1;
    }

    public void shift(int action) {

        push(currentToken,false);
        push(action, true);
        currentToken = lexer.getNextToken();
    }

    private void push(Object item, boolean state) {
        if (state) {
            parseStack.push(item);
            valueStack.push(null);
            typeStack.push(null);
        }
        else {
            if(item instanceof Token) {
                parseStack.push(((Token)item).id);
                valueStack.push(((Token)item).value);
                typeStack.push(((Token)item).type);
            }
        }
    }

    public void popState() {
        parseStack.pop();
        valueStack.pop();
        typeStack.pop();
    }

    private Token popToken() {
        Token t = new Token();
        t.id = (Integer)parseStack.pop();
        t.value = (String)valueStack.pop();
        t.type = (String)typeStack.pop();
        return t;
    }

    private void checkTokens(int i, int j) {
        if (i == j) {
            return;
        }
        new Error(31, "Found token: " + i + "\nExpected token: " + j);
    }

    private String nextPlace() {
        return "" + placeCount++;
    }

    private int nextQuad() {
        return quadCount++;
    }

}

