grammar CimpleFunc;

// a simplified version of cimple that has just function definitions and function calls, created because to provide a test case for error in extracting newline for
// statement* with only one child

program
    : statement+ EOF
    ;

statement
    : functionDefinition
    | functionCall SEMICOLON ;

functionCall
    : ID LPAREN RPAREN
    ;

functionDefinition
    : FN ID LPAREN parameters? RPAREN LBRAC statement* RBRAC
    ;

parameters
    : ID (COMMA ID)*
    ;


EQUALS : '==';
NOT_EQUALS : '!=';
LT : '<' ;
LE : '<=' ;
GT : '>';
GE : '>=';

MULT : '*';
DIV : '/' ;
PLUS : '+';
MINUS: '-';
MOD : '%' ;

AND : 'and';
OR : 'or';
NOT : 'not';

ASSIGN : '=';
SEMICOLON : ';';
PRINT : 'print';
INPUT : 'input';

FN : 'fn';

FOR : 'for';
IF : 'if';
ELSE : 'else';

RETURN : 'return';

COMMA : ',';

LPAREN : '(';
RPAREN : ')';
LBRAC : '{';
RBRAC : '}';

INT : [0-9]+;
ID : [a-zA-Z_][a-zA-Z0-9_]*;

WS: [ \t\n] -> skip;
