package premun.mps.ingrid.formatter;

public final class TestGrammars {

    public static final String setGrammar = "grammar Set;\n" +
            "\n" +
            "compilationUnit : set EOF ;\n" +
            "\n" +
            "set\n" +
            "    : '{' '}' # emptySet\n" +
            "    | '{' elem (',' elem)* '}' # nonEmptySet\n" +
            "    ;\n" +
            "\n" +
            "elem\n" +
            "    : simpleElement\n" +
            "    | set\n" +
            "    ;\n" +
            "\n" +
            "simpleElement : ELEM;\n" +
            "\n" +
            "ELEM\n" +
            "    : ('A'..'Z' | 'a'..'z' | '0'..'9' |  '_')+\n" +
            "    ;\n" +
            "\n" +
            "WS : [ \\t\\r\\n]+ -> skip ;";


    public static final String expressionGrammar = "grammar expr;\n" +
            "\n" +
            "expr\n" +
            "    : '(' expr ')' # bracket\n" +
            "    | expr '*' expr # mult\n" +
            "    | expr '/' expr # div\n" +
            "    | expr '+' expr # plus\n" +
            "    | expr '-' expr # minus\n" +
            "    | INT # int\n" +
            "    | VAR # var\n" +
            "    ;\n" +
            "\n" +
            "VAR\n" +
            "    : [a-zA-Z_]+\n" +
            "    ;\n" +
            "\n" +
            "INT\n" +
            "    : [0-9]+\n" +
            "    ;\n" +
            "\n" +
            "WS\n" +
            "    : [ \\t\\r\\n]+ -> skip // skip spaces, tabs, newlines\n" +
            "    ;";
}
