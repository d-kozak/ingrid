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
}
