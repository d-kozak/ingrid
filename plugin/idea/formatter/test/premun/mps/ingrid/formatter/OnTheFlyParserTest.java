package premun.mps.ingrid.formatter;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.tool.Grammar;
import org.junit.Test;

public class OnTheFlyParserTest {

    @Test
    public void setParsing() throws RecognitionException {
        Grammar grammar = new Grammar(TestGrammars.setGrammar);
        String input = "{a,b,{}}";
        ParseTree parseTree = OnTheFlyParser.parse(grammar, input, "compilationUnit");
        System.out.println(parseTree);
    }
}
