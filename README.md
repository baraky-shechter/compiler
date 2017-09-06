B++
User Manual

Barak Shechter
Version 1
7/24/2016

Introduction	3
How to Start	3
Requirements	3
Running the Compiler	3
Data and Data Types	4
Declaring a Variable	4
Operators	4
Condition Statements	4
Looping Statements	5
Comments	5
Input/Output (I/O)	5
get(ID)	5
put(ID)	5
Ending the Program	6
Appendices	6
Syntax Diagram	6
Error Messages	8

Introduction
B++ is a programming language written for a course project at Frostburg State University. The name is the first letter in the creator’s name, Barak Shechter, and is a play on C++. The compiler is a one pass compiler and was designed using GNU bison and a LALR parser.

How to Start

Requirements
•	Java 1.8 Runtime Environment
•	Text editor

Running the Compiler
1.	Write code in the specified syntax at the bottom of this document in a text editor.
2.	Run the Main.Class file in the command line to compile the B++ code:

java main filename

You should be inside the directory where cornucopia.jar is stored.
Filename should be replaced with the name of your source code.

3.	An output file will be created after compilation
4.	Open a terminal window
5.	Run mini and pass output as the input code file, this will create an output.out file

java mini
Intermediate Code mini-Assembler
Copyright (c) Truman Parks Boyer 02 Apr 2002

Input Code File ?: output

6.	Run mICE and pass output.out as the input intermediate code file

java mice
mini-Intermediate Code Engine (mICE)
Copyright (c) Truman Parks Boyer 22 Apr 2002
Intermediate Code File ?: output.out

7.	The code should now run in the terminal window



Data and Data Types
B++ can handle Boolean expressions, however does not have a Boolean datatype.

These are the two data types that B++ currently supports:
•	char
•	int

Declaring a Variable
•	User identifiers are in uppercase only
•	Identifiers are strings of letters, digits, underscores starting with a letter

var IDENTIFIER_NAME : type ;

•	When declaring a variable, the compiler automatically instantiates the variable based on its type
o	Int types are instantiated to 0
o	Char types are instantiated to a space character ‘ ‘
Operators
•	Addition operations
o	+, addition
o	-, subtraction
•	Multiplication operations
o	*, multiplication
o	/, division
o	%, modulus
•	Relational Operations
o	<, less than
o	>, greater than
o	<=, less than equal to
o	>=, greater than equal to
o	<>, not equal to

Condition Statements
Creating a conditional statement is very straightforward:

if (expression) statement

OR

if (expression)
{
	statement;
statement;
.
.
.
statement
}
Looping Statements
Creating a loop is also very straightforward:

while (expression) statement

OR

while (expression)
{
	statement;
statement;
.
.
.
statement
}

Comments
B++ supports single line comments that may appear anywhere, however the scanner will ignore the rest of the line.

Comments can be defined by double forward-slashes //.

Input/Output (I/O)
•	get(ID) and put(ID) are the only I/O in this programming language

get(ID)
get(ID) will display the value associated with the identifier in the terminal window

put(ID)
put(ID) will display a ‘?’ in the terminal window and will wait for input to assign the identifier with the inputted value

Ending the Program
To let the compiler know that it has reached the end of the program, all programs must end with a ‘$’.

Appendices
Syntax Diagram
start:                  access STATIC VOID ID '(' identifier_list ')'
                        '{'     declarations
                                compound_statement
                        '}'     '$'
                        ;
access:                 PUBLIC
                        | PRIVATE
                        ;

identifier_list:        ID
                        | identifier_list ',' ID
                        |
                        ;

declarations:           declarations VAR identifier_list ':' type ';'
                        |
                        ;

type:                   CHAR
                        | INT
                        ;

compound_statement:     '{' statement_list
                        '}'
                        ;

statement_list:         statement
                        | statement_list ';' statement
                        ;

statement:              lefthandside
                        | compound_statement
                        | GET '(' ID ')'
                        | PUT '(' ID ')'
                        | IF '(' expression ')' statement
                        | WHILE '(' expression ')' statement
                        ;

lefthandside:           ID '=' righthandside
                        ;

righthandside:          expression
                        ;

expression:             simple_expression
                        | simple_expression relop simple_expression
                        ;

simple_expression:      term
                        | simple_expression addop term
                        ;

term:                   factor
                        | term mulop factor
                        ;

factor:                 ID
                        | NUM
                        | TRUE
                        | FALSE
                        | '\'' LITERAL '\''
                        ;

relop:                  '>'
                        | ">="
                        | "=="
                        | "<="
                        | '<'
                        | "<>"
                        ;

addop:                  '+'
                        | '-'
                        ;

mulop:                  '*'
                        | '/'
                        | '%'
                        ;

Error Messages

Error Number
Description
0
Wrong usage. Format: java –jar b++.jar filename.txt
11
Source file not found
12
Unrecognized token
21
Failed accessing generator line
22
Output was already created
31
Wrong token discovered
32
Cannot find symbol in symbol table
41
Symbol already exists in symbol table





