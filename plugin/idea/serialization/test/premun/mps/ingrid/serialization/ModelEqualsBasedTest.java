package premun.mps.ingrid.serialization;


import org.junit.Test;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.parser.GrammarParser;

import static org.junit.Assert.assertEquals;

/**
 * Tests whether models before and after serialization are equal.
 * First it loads the model using IngridParser, then it serializes it and loads it again, and afterwards it checks
 * those models for equality.
 *
 * @author dkozak
 */
public class ModelEqualsBasedTest {

    @Test
    public void setGrammar() {
        compareModels(TestGrammars.setGrammar);
    }

    @Test
    public void exprGrammar() {
        compareModels(TestGrammars.expressionGrammar);
    }

    @Test
    public void bookGrammar() {
        compareModels(TestGrammars.bookGrammar);
    }

    @Test
    public void simpleGrammar() {
        compareModels(TestGrammars.simpleGrammar);
    }

    @Test
    public void nestedBlockGrammarGrammar() {
        compareModels(TestGrammars.nestedBlockGrammar);
    }

    @Test
    public void CimpleGrammar() {
        compareModels(TestGrammars.loadCimple());
    }

    @Test
    public void Java9Grammar() {
        compareModels(TestGrammars.loadJava9());
    }

    private void compareModels(String grammar) {
        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(grammar);
        GrammarInfo infoOne = grammarParser.resolveGrammar();

        String serialized = GrammarSerializer.serializeGrammar(infoOne);

        grammarParser = new GrammarParser();
        grammarParser.parseString(serialized);

        GrammarInfo infoTwo = grammarParser.resolveGrammar();

        assertEquals(infoOne, infoTwo);
    }
}
