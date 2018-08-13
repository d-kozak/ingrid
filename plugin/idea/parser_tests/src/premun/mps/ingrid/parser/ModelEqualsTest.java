package premun.mps.ingrid.parser;

import org.junit.Test;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.GrammarInfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests whether equals method on the Ingrid model works as expected
 *
 * @author dkozak
 */
public class ModelEqualsTest {


    @Test
    public void setGrammar() {
        testEquals(TestGrammars.setGrammar);

    }

    @Test
    public void exprGrammar() {
        testEquals(TestGrammars.expressionGrammar);
    }

    @Test
    public void bookGrammar() {
        testEquals(TestGrammars.bookGrammar);
    }

    @Test
    public void simpleGrammar() {
        testEquals(TestGrammars.simpleGrammar);
    }

    @Test
    public void nestedBlockGrammarGrammar() {
        testEquals(TestGrammars.nestedBlockGrammar);
    }

    @Test
    public void CimpleGrammar() {
        testEquals(TestGrammars.loadCimple());
    }

    @Test
    public void Java9Grammar() {
        testEquals(TestGrammars.loadJava9());
    }

    @Test
    public void setAndExprNotEquals() {
        testNotEquals(TestGrammars.setGrammar, TestGrammars.expressionGrammar);
    }

    @Test
    public void exprAndBookNotEquals() {
        testNotEquals(TestGrammars.expressionGrammar, TestGrammars.bookGrammar);
    }

    @Test
    public void cimpleAndJava9NotEquals() {
        testNotEquals(TestGrammars.loadCimple(), TestGrammars.loadJava9());
    }

    private void testNotEquals(String grammarOne, String grammarTwo) {
        GrammarInfo infoOne = getGrammarInfo(grammarOne);
        GrammarInfo infoTwo = getGrammarInfo(grammarTwo);
        assertNotEquals(infoOne, infoTwo);
    }


    private void testEquals(String grammar) {
        GrammarInfo infoOne = getGrammarInfo(grammar);
        GrammarInfo infoTwo = getGrammarInfo(grammar);
        assertEquals(infoOne, infoTwo);
    }

    private GrammarInfo getGrammarInfo(String grammar) {
        GrammarParser parserOne = new GrammarParser();
        parserOne.parseString(grammar);
        return parserOne.resolveGrammar();
    }

}
