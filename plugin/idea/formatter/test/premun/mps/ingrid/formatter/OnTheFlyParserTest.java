package premun.mps.ingrid.formatter;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.tool.Grammar;
import org.junit.Test;

public class OnTheFlyParserTest {

    @Test
    public void setParsing() throws RecognitionException {
        String grammarText = "grammar Set;\n" +
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
        Grammar grammar = new Grammar(grammarText);
        String input = "{a,b,{}}";
        ParseTree parseTree = OnTheFlyParser.parse(grammar, input, "compilationUnit");
        System.out.println(parseTree);
    }
}
