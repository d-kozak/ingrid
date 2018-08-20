grammar ArgsNoUnused;

// args grammar without the whitespace rule, that is unused(at least from the point of view of MPS, antlr actually uses in)

statements : statement+ ;

statement
    : functionCall
    | idList
    ;

functionCall : ID '(' args? ')' ;

args : arg (',' arg)* ;

arg : ID;

idList : ID (',' ID)* ;

ID : [a-zA-Z_]+;

