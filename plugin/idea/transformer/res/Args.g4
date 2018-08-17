grammar Args;

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

WS : [ \r\n\t] -> skip;