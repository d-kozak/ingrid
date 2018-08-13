package premun.mps.ingrid.serialization;

import org.junit.Test;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.parser.GrammarParser;

import static org.junit.Assert.assertEquals;

/**
 * Tests that verify that once the grammar is processed once, it's serialized form stays the same.
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

    private void compareFirstAndSecondSerialization(String setGrammar) {
        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(setGrammar);

        String serialized = GrammarSerializer.serialize(grammarParser.getRawParserResult());

        grammarParser = new GrammarParser();
        grammarParser.parseString(serialized);

        String doublySerialized = GrammarSerializer.serialize(grammarParser.getRawParserResult());

        assertEquals(serialized, doublySerialized);

        System.out.println("\n\n =====ORIGINAL====\n\n");
        System.out.println(setGrammar);
        System.out.println("\n\n ====PROCESSED====\n\n");
        System.out.println(serialized);
        System.out.println("\n\n =================\n\n");
    }
}
