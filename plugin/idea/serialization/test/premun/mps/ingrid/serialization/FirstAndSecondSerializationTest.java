package premun.mps.ingrid.serialization;

import org.junit.Test;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.parser.GrammarParser;

import static org.junit.Assert.assertEquals;

/**
 * Tests that verify that if the grammar is processed once, it's serialized form stays the same.
 * If we take the output of the serialization and process it again, it should stay the same.
 *
 * @author dkozak
 */
public class FirstAndSecondSerializationTest {

    @Test
    public void setGrammar() {
        compareFirstAndSecondSerialization(TestGrammars.setGrammar);
    }

    @Test
    public void exprGrammar() {
        compareFirstAndSecondSerialization(TestGrammars.expressionGrammar);
    }

    @Test
    public void bookGrammar() {
        compareFirstAndSecondSerialization(TestGrammars.bookGrammar);
    }

    @Test
    public void simpleGrammar() {
        compareFirstAndSecondSerialization(TestGrammars.simpleGrammar);
    }

    @Test
    public void nestedBlockGrammarGrammar() {
        compareFirstAndSecondSerialization(TestGrammars.nestedBlockGrammar);
    }

    @Test
    public void CimpleGrammar() {
        compareFirstAndSecondSerialization(TestGrammars.loadCimple());
    }

    @Test
    public void Java9Grammar() {
        compareFirstAndSecondSerialization(TestGrammars.loadJava9());
    }

    private void compareFirstAndSecondSerialization(String grammar) {
        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(grammar);

        String serialized = GrammarSerializer.serializeGrammar(grammarParser.resolveGrammar());

        grammarParser = new GrammarParser();
        grammarParser.parseString(serialized);

        String doublySerialized = GrammarSerializer.serializeGrammar(grammarParser.resolveGrammar());


        System.out.println("\n\n =====ORIGINAL====\n\n");
        System.out.println(grammar);
        System.out.println("\n\n ====PROCESSED====\n\n");
        System.out.println(serialized);
        System.out.println("\n\n =================\n\n");

        assertEquals(serialized, doublySerialized);
    }
}
