package edu.frostburg.cosc470;

/**
 * Created by barakshechter on 6/25/16.
 */
public class Grammar {

    public static final int
            // Terminal tokens
            STATIC = 1, VOID = 2, ID = 3, LEFTPAREN = 4, RIGHTPAREN = 5, LEFTBRACKET = 6,
            RIGHTBRACKET = 7, DOLLAR = 8, PUBLIC = 9, PRIVATE = 10, COMMA = 11, VAR = 12,
            COLON = 13, SEMICOLON = 14, CHAR = 15, INT = 16, GET = 17, PUT = 18, IF = 19,
            WHILE = 20, EQ = 21, NUM = 22, TRUE = 23, FALSE = 24, SINGLEQUOTE = 25, LITERAL = 26,
            GREATER = 27, GREATER_EQ = 28, EQEQ = 29, LESS_EQ = 30, LESS = 31, NOT_EQ = 32,
            PLUS = 33,  MINUS = 34, MUL = 35, DIV = 36, MOD = 37,


            // Nonterminal tokens
            START = 100, ACCESS = 101, IDENTIFIER_LIST = 102, DECLARATIONS = 103, TYPE = 104,
            COMPOUND_STATEMENT = 105, STATEMENT_LIST = 106, STATEMENT = 107, LEFTHANDSIDE = 108,
            RIGHTHANDSIDE = 109, EXPRESSION = 110, SIMPLE_EXPRESSION = 111, TERM = 112,
            FACTOR = 113, RELOP = 114, ADDOP = 115, MULOP = 116;


    public static final int rules[][] =
            {
                    {}, // RULE 0
                    {START, 12, ACCESS, STATIC, VOID, ID, LEFTPAREN, IDENTIFIER_LIST, RIGHTPAREN,
                            LEFTBRACKET, DECLARATIONS, COMPOUND_STATEMENT, RIGHTBRACKET, DOLLAR},
                    {ACCESS, 1, PUBLIC},
                    {ACCESS, 1, PRIVATE},
                    {IDENTIFIER_LIST, 1, ID},
                    {IDENTIFIER_LIST, 3, IDENTIFIER_LIST, COMMA, ID}, // RULE 5
                    {IDENTIFIER_LIST, 0, 0},
                    {DECLARATIONS, 6, DECLARATIONS, VAR, IDENTIFIER_LIST, COLON, TYPE, SEMICOLON},
                    {DECLARATIONS, 0, 0},
                    {TYPE, 1, CHAR},
                    {TYPE, 1, INT}, // RULE 10
                    {COMPOUND_STATEMENT, 3, LEFTBRACKET, STATEMENT_LIST, RIGHTBRACKET},
                    {STATEMENT_LIST, 1, STATEMENT},
                    {STATEMENT_LIST, 3, STATEMENT_LIST, SEMICOLON, STATEMENT},
                    {STATEMENT, 1, LEFTHANDSIDE},
                    {STATEMENT, 1, COMPOUND_STATEMENT}, // RULE 15
                    {STATEMENT, 4, GET, LEFTPAREN, ID, RIGHTPAREN},
                    {STATEMENT, 4, PUT, LEFTPAREN, ID, RIGHTPAREN},
                    {STATEMENT, 5, IF, LEFTPAREN, EXPRESSION, RIGHTPAREN, STATEMENT},
                    {STATEMENT, 5, WHILE, LEFTPAREN, EXPRESSION, RIGHTPAREN, STATEMENT},
                    {LEFTHANDSIDE, 3, ID, EQ, RIGHTHANDSIDE}, // RULE 20
                    {RIGHTHANDSIDE, 1, EXPRESSION},
                    {EXPRESSION, 1, SIMPLE_EXPRESSION},
                    {EXPRESSION, 3, SIMPLE_EXPRESSION, RELOP, SIMPLE_EXPRESSION},
                    {SIMPLE_EXPRESSION, 1, TERM},
                    {SIMPLE_EXPRESSION, 3, SIMPLE_EXPRESSION, ADDOP, TERM}, // RULE 25
                    {TERM, 1, FACTOR},
                    {TERM, 3, TERM, MULOP, FACTOR},
                    {FACTOR, 1, ID},
                    {FACTOR, 1, NUM},
                    {FACTOR, 1, TRUE}, // RULE 30
                    {FACTOR, 1, FALSE},
                    {FACTOR, 3, SINGLEQUOTE, LITERAL, SINGLEQUOTE},
                    {RELOP, 1, GREATER},
                    {RELOP, 1, GREATER_EQ},
                    {RELOP, 1, EQEQ}, // RULE 35
                    {RELOP, 1, LESS_EQ},
                    {RELOP, 1, LESS},
                    {RELOP, 1, NOT_EQ},
                    {ADDOP, 1, PLUS},
                    {ADDOP, 1, MINUS}, // RULE 40
                    {MULOP, 1, MUL},
                    {MULOP, 1, DIV},
                    {MULOP, 1, MOD}
            };
}
