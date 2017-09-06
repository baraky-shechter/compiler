package edu.frostburg.cosc470;

/**
 * @author Barak Shechter
 * @version 2016.6.29
 */
public class ParseTable {

    public int[][] parseTable;
    public final int numStates = 84;

    public static final int
            // Terminal tokens
            STATIC = 1, VOID = 2, ID = 3, LEFTPAREN = 4, RIGHTPAREN = 5, LEFTBRACKET = 6,
            RIGHTBRACKET = 7, DOLLAR = 8, PUBLIC = 9, PRIVATE = 10, COMMA = 11, VAR = 12,
            COLON = 13, SEMICOLON = 14, CHAR = 15, INT = 16, GET = 17, PUT = 18, IF = 19,
            WHILE = 20, EQ = 21, NUM = 22, TRUE = 23, FALSE = 24, SINGLEQUOTE = 25, LITERAL = 26,
            GREATER = 27, GREATER_EQ = 28, EQEQ = 29, LESS_EQ = 30, LESS = 31, NOT_EQ = 32,
            PLUS = 33,  MINUS = 34, MUL = 35, DIV = 36, MOD = 37,


    // Nonterminal tokens
    START = 38, ACCESS = 39, IDENTIFIER_LIST = 40, DECLARATIONS = 41, TYPE = 42,
            COMPOUND_STATEMENT = 43, STATEMENT_LIST = 44, STATEMENT = 45, LEFTHANDSIDE = 46,
            RIGHTHANDSIDE = 47, EXPRESSION = 48, SIMPLE_EXPRESSION = 49, TERM = 50,
            FACTOR = 51, RELOP = 52, ADDOP = 53, MULOP = 54;


    /**
     * Constructor for the parse table
     */
    public ParseTable() {
        parseTable = new int[numStates][56];
        initTable();
        buildTable();
    }

    /**
     * Initializes the entire table with 99's (rejected state)
     */
    private void initTable() {
        for (int i = 0; i < parseTable.length; ++i) {
            for (int j =0; j<parseTable[0].length; ++j) {
                parseTable[i][j] = 99;
            }
        }
    }

    /**
     * Sets all the action table entries to a default value
     *
     * @param state state to be used
     * @param value value to be used
     */
    private void setDefault(int state, int value) {
        for (int i =0; i< 38; ++i) {
            parseTable[state][i] = value;
        }
    }

    /**
     * Methodically builds the table 1 state at a time
     */
    private void buildTable() {
        int state = 0;
        parseTable[state][PUBLIC] = 1;
        parseTable[state][PRIVATE] = 2;
        parseTable[state][START] = 3;
        parseTable[state][ACCESS] = 4;

        state = 1;
        setDefault(state, -2);

        state = 2;
        setDefault(state, -3);

        state = 3;
        parseTable[state][DOLLAR] = 5;

        state = 4;
        parseTable[state][STATIC] = 6;

        state = 5;
        setDefault(state,0);

        state = 6;
        parseTable[state][VOID] = 7;

        state = 7;
        parseTable[state][ID] = 8;

        state = 8;
        parseTable[state][LEFTPAREN] = 9;

        state = 9;
        setDefault(state,-6);
        parseTable[state][ID] = 10;
        parseTable[state][IDENTIFIER_LIST] = 11;

        state = 10;
        setDefault(state, -4);

        state = 11;
        parseTable[state][RIGHTPAREN] = 12;
        parseTable[state][COMMA] = 13;

        state = 12;
        parseTable[state][LEFTBRACKET] = 14;

        state = 13;
        parseTable[state][ID] = 15;

        state = 14;
        setDefault(state, -8);
        parseTable[state][DECLARATIONS] = 16;

        state = 15;
        setDefault(state, -5);

        state = 16;
        parseTable[state][VAR] = 17;
        parseTable[state][LEFTBRACKET] = 18;
        parseTable[state][COMPOUND_STATEMENT] = 19;

        state = 17;
        setDefault(state, -6);
        parseTable[state][ID] = 10;
        parseTable[state][IDENTIFIER_LIST] = 20;

        state = 18;
        parseTable[state][ID] = 21;
        parseTable[state][GET] = 22;
        parseTable[state][PUT] = 23;
        parseTable[state][IF] = 24;
        parseTable[state][WHILE] = 25;
        parseTable[state][LEFTBRACKET] = 18;
        parseTable[state][COMPOUND_STATEMENT] = 26;
        parseTable[state][STATEMENT_LIST] = 27;
        parseTable[state][STATEMENT] = 28;
        parseTable[state][LEFTHANDSIDE] = 29;

        state = 19;
        parseTable[state][RIGHTBRACKET] = 30;

        state = 20;
        parseTable[state][COMMA] = 13;
        parseTable[state][COLON] = 31;

        state = 21;
        parseTable[state][EQ] = 32;

        state = 22;
        parseTable[state][LEFTPAREN] = 33;

        state = 23;
        parseTable[state][LEFTPAREN] = 34;

        state = 24;
        parseTable[state][LEFTPAREN] = 35;

        state = 25;
        parseTable[state][LEFTPAREN] = 36;

        state = 26;
        setDefault(state, -15);

        state = 27;
        parseTable[state][RIGHTBRACKET] = 37;
        parseTable[state][SEMICOLON] = 38;

        state = 28;
        setDefault(state, -12);

        state = 29;
        setDefault(state, -14);

        state = 30;
        parseTable[state][DOLLAR] = 39;

        state = 31;
        parseTable[state][CHAR] = 40;
        parseTable[state][INT] = 41;
        parseTable[state][TYPE] = 42;

        state = 32;
        parseTable[state][ID] = 43;
        parseTable[state][NUM] = 44;
        parseTable[state][TRUE] = 45;
        parseTable[state][FALSE] = 46;
        parseTable[state][SINGLEQUOTE] = 47;
        parseTable[state][RIGHTHANDSIDE] = 48;
        parseTable[state][EXPRESSION] = 49;
        parseTable[state][SIMPLE_EXPRESSION] = 50;
        parseTable[state][TERM] = 51;
        parseTable[state][FACTOR] = 52;

        state = 33;
        parseTable[state][ID] = 53;

        state = 34;
        parseTable[state][ID] = 54;

        state = 35;
        parseTable[state][ID] = 43;
        parseTable[state][NUM] = 44;
        parseTable[state][TRUE] = 45;
        parseTable[state][FALSE] = 46;
        parseTable[state][SINGLEQUOTE] = 47;
        parseTable[state][EXPRESSION] = 55;
        parseTable[state][SIMPLE_EXPRESSION] = 50;
        parseTable[state][TERM] = 51;
        parseTable[state][FACTOR] = 52;

        state = 36;
        parseTable[state][ID] = 43;
        parseTable[state][NUM] = 44;
        parseTable[state][TRUE] = 45;
        parseTable[state][FALSE] = 46;
        parseTable[state][SINGLEQUOTE] = 47;
        parseTable[state][EXPRESSION] = 56;
        parseTable[state][SIMPLE_EXPRESSION] = 50;
        parseTable[state][TERM] = 51;
        parseTable[state][FACTOR] = 52;

        state = 37;
        setDefault(state, -11);

        state = 38;
        parseTable[state][ID] = 21;
        parseTable[state][GET] = 22;
        parseTable[state][PUT] = 23;
        parseTable[state][IF] = 24;
        parseTable[state][WHILE] = 25;
        parseTable[state][LEFTBRACKET] = 18;
        parseTable[state][COMPOUND_STATEMENT] = 26;
        parseTable[state][STATEMENT] = 57;
        parseTable[state][LEFTHANDSIDE] = 29;

        state = 39;
        setDefault(state, -1);

        state = 40;
        setDefault(state, -9);

        state = 41;
        setDefault(state, -10);

        state = 42;
        parseTable[state][SEMICOLON] = 58;

        state = 43;
        setDefault(state, -28);

        state = 44;
        setDefault(state, -29);

        state = 45;
        setDefault(state, -30);

        state = 46;
        setDefault(state, -31);

        state = 47;
        parseTable[state][LITERAL] = 59;

        state = 48;
        setDefault(state, -20);

        state = 49;
        setDefault(state, -21);

        state = 50;
        setDefault(state, -22);
        parseTable[state][GREATER] = 60;
        parseTable[state][GREATER_EQ] = 61;
        parseTable[state][EQEQ] = 62;
        parseTable[state][LESS_EQ] = 63;
        parseTable[state][LESS] = 64;
        parseTable[state][NOT_EQ] = 65;
        parseTable[state][PLUS] = 66;
        parseTable[state][MINUS] = 67;
        parseTable[state][RELOP] = 68;
        parseTable[state][ADDOP] = 69;

        state = 51;
        setDefault(state, -24);
        parseTable[state][MUL] = 70;
        parseTable[state][DIV] = 71;
        parseTable[state][MOD] = 72;
        parseTable[state][MULOP] = 73;

        state = 52;
        setDefault(state, -26);

        state = 53;
        parseTable[state][RIGHTPAREN] = 74;

        state = 54;
        parseTable[state][RIGHTPAREN] = 75;

        state = 55;
        parseTable[state][RIGHTPAREN] = 76;

        state = 56;
        parseTable[state][RIGHTPAREN] = 77;

        state = 57;
        setDefault(state, -13);

        state = 58;
        setDefault(state,-7);

        state = 59;
        parseTable[state][SINGLEQUOTE] = 78;

        state = 60;
        setDefault(state, -33);

        state = 61;
        setDefault(state, -34);

        state = 62;
        setDefault(state, -35);

        state = 63;
        setDefault(state, -36);

        state = 64;
        setDefault(state, -37);

        state = 65;
        setDefault(state, -38);

        state = 66;
        setDefault(state, -39);

        state = 67;
        setDefault(state, -40);

        state = 68;
        parseTable[state][ID] = 43;
        parseTable[state][NUM] = 44;
        parseTable[state][TRUE] = 45;
        parseTable[state][FALSE] = 46;
        parseTable[state][SINGLEQUOTE] = 47;
        parseTable[state][SIMPLE_EXPRESSION] = 79;
        parseTable[state][TERM] = 51;
        parseTable[state][FACTOR] = 52;

        state = 69;
        parseTable[state][ID] = 43;
        parseTable[state][NUM] = 44;
        parseTable[state][TRUE] = 45;
        parseTable[state][FALSE] = 46;
        parseTable[state][SINGLEQUOTE] = 47;
        parseTable[state][TERM] = 80;
        parseTable[state][FACTOR] = 52;

        state = 70;
        setDefault(state, -41);

        state = 71;
        setDefault(state, -42);

        state = 72;
        setDefault(state, -43);

        state = 73;
        parseTable[state][ID] = 43;
        parseTable[state][NUM] = 44;
        parseTable[state][TRUE] = 45;
        parseTable[state][FALSE] = 46;
        parseTable[state][SINGLEQUOTE] = 47;
        parseTable[state][FACTOR] = 81;

        state = 74;
        setDefault(state, -16);

        state = 75;
        setDefault(state, -17);

        state = 76;
        parseTable[state][ID] = 21;
        parseTable[state][GET] = 22;
        parseTable[state][PUT] = 23;
        parseTable[state][IF] = 24;
        parseTable[state][WHILE] = 25;
        parseTable[state][LEFTBRACKET] = 18;
        parseTable[state][COMPOUND_STATEMENT] = 26;
        parseTable[state][STATEMENT] = 82;
        parseTable[state][LEFTHANDSIDE] = 29;

        state = 77;
        parseTable[state][ID] = 21;
        parseTable[state][GET] = 22;
        parseTable[state][PUT] = 23;
        parseTable[state][IF] = 24;
        parseTable[state][WHILE] = 25;
        parseTable[state][LEFTBRACKET] = 18;
        parseTable[state][COMPOUND_STATEMENT] = 26;
        parseTable[state][STATEMENT] = 83;
        parseTable[state][LEFTHANDSIDE] = 29;

        state = 78;
        setDefault(state, -32);

        state = 79;
        setDefault(state, -23);
        parseTable[state][PLUS] = 66;
        parseTable[state][MINUS] = 67;
        parseTable[state][ADDOP] = 69;

        state = 80;
        setDefault(state, -25);
        parseTable[state][MUL] = 70;
        parseTable[state][DIV] = 71;
        parseTable[state][MOD] = 72;
        parseTable[state][MULOP] = 73;

        state = 81;
        setDefault(state, -27);

        state = 82;
        setDefault(state, -18);

        state = 83;
        setDefault(state, -19);

    }

}
