package premun.mps.ingrid.formatter.utils;

import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.model.ParserRule;
import premun.mps.ingrid.parser.GrammarParser;

import java.util.Map;

import static premun.mps.ingrid.formatter.boundary.FormatExtractor.*;

/**
 * Contains helper method to parse the input, used in format extraction test
 *
 * @author dkozak
 */
public class FormatExtraction {

    /**
     * @param input text written in the target language
     * @param grammar grammar of the target language
     * @return simplified version of the formatInfoMap, where key is a pair of rule name and alternative index
     */
    public static Map<Pair<String, Integer>, RuleFormatInfo> extractFormat(String input, String grammar) {
        return extractFormat(input, grammar, null);
    }

    /**
     * @param input    text written in the target language
     * @param grammar  grammar of the target language
     * @param rootRule rule from which to start parsing
     * @return simplified version of the formatInfoMap, where key is a pair of rule name and alternative index
     */
    public static Map<Pair<String, Integer>, RuleFormatInfo> extractFormat(String input, String grammar, String rootRule) {
        GrammarParser grammarParser = new GrammarParser(rootRule);
        grammarParser.parseString(grammar);
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();
        Map<Pair<ParserRule, Alternative>, RuleFormatInfo> merge = merge(extract(grammarInfo, grammar, input));
        return asRuleNameAlternativeIndexMap(merge);
    }
}
