package premun.mps.ingrid.serialization;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.tool.Grammar;
import org.junit.Test;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.parser.GrammarParser;
import premun.mps.ingrid.parser.ParserResult;

/**
 * Tests that verify that the grammar is still parseable by antlr even after serialization.
 *
 * @author dkozak
 */
public class GrammarIsStillParseableTest {

    @Test
    public void setGrammar() {
        serializeAndParseGrammar(TestGrammars.setGrammar);

    }

    @Test
    public void exprGrammar() {
        serializeAndParseGrammar(TestGrammars.expressionGrammar);
    }

    @Test
    public void bookGrammar() {
        serializeAndParseGrammar(TestGrammars.bookGrammar);
    }

    @Test
    public void simpleGrammar() {
        serializeAndParseGrammar(TestGrammars.simpleGrammar);
    }

    @Test
    public void nestedBlockGrammarGrammar() {
        serializeAndParseGrammar(TestGrammars.nestedBlockGrammar);
    }

    @Test
    public void CimpleGrammar() {
        serializeAndParseGrammar(TestGrammars.loadCimple());
    }

    @Test
    public void Java9Grammar() {
        serializeAndParseGrammar(TestGrammars.loadJava9());
    }

    private void serializeAndParseGrammar(String grammar) {
        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(grammar);
        ParserResult rawParserResult = grammarParser.getRawParserResult();

        String serialized = GrammarSerializer.serialize(rawParserResult);

        try {
            Grammar antlrGrammar = new Grammar(serialized);
        } catch (RecognitionException e) {
            e.printStackTrace();
            throw new AssertionError("Grammar was not parsed successfully");
        }
    }
}
