package premun.mps.ingrid.formatter.utils;

import premun.mps.ingrid.formatter.boundary.FormatExtractor;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.parser.GrammarParser;

import java.util.Collections;

/**
 * Helper methods used in formatextraction test
 *
 * @author dkozak
 */
public class Parser {

    public static GrammarInfo extractFormat(String inputText, String inputGrammar) {
        return extractFormat(inputText, inputGrammar, null);
    }

    public static GrammarInfo extractFormat(String inputText, String inputGrammar, String rootRule) {
        GrammarParser parser = new GrammarParser(rootRule);
        parser.parseString(inputGrammar);
        GrammarInfo grammarInfo = parser.resolveGrammar();
        return FormatExtractor.fullyProcessMultipleFiles(grammarInfo, inputGrammar, Collections.singletonList(inputText));
    }
}
