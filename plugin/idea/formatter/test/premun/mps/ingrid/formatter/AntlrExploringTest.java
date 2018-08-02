package premun.mps.ingrid.formatter;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.Tool;
import org.antlr.v4.tool.Grammar;
import org.junit.Test;

public class AntlrExploringTest {

    @Test(expected = UnsupportedOperationException.class)
    public void emptyGrammar() throws RecognitionException {
        // if we supply antlr with an empty grammar, it throws UnsupportedOperationException
        Grammar grammar = new Grammar("");
    }

    @Test
    public void twoGrammars() throws RecognitionException {
        Grammar grammar  =new Grammar("");
    }

}
