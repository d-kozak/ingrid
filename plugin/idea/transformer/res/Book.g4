grammar Book;

book : people NEWLINE shops;

people : person+ ;

shops : shop+ ;

person : firstName ',' lastName ;

firstName : NAME ;

lastName : NAME ;

shop : NAME ;

NAME : [a-zA-Z_]+ ;


NEWLINE : '\n';

WS : [\r ] -> skip;