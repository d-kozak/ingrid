package premun.mps.ingrid.tranformer;

import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.parser.GrammarParser;

import static org.junit.Assert.assertEquals;

/**
 * Asserts for easier verification of grammars
 *
 * @author dkozak
 */
public class GrammarAsserts {

    /**
     * Parses the grammars and then checks the models for equality
     *
     * @param expected
     * @param actual
     */
    public static void assertGrammarEquals(String expected, String actual) {
        GrammarInfo expectedGrammar = parseGrammar(expected);
        GrammarInfo actualGrammar = parseGrammar(actual);
        assertEquals(expectedGrammar, actualGrammar);
    }

    private static GrammarInfo parseGrammar(String grammar) {
        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(grammar);
        return grammarParser.resolveGrammar();
    }

}
