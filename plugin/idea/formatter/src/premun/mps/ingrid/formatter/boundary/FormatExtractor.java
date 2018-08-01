package premun.mps.ingrid.formatter.boundary;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.tool.Grammar;
import premun.mps.ingrid.formatter.InterpretingParser;
import premun.mps.ingrid.formatter.RuleEnterParseTreeListener;
import premun.mps.ingrid.formatter.model.FormatInfoMapKey;
import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.model.GrammarInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Interface of the module
 *
 * @author dkozak
 */
public class FormatExtractor {

    /**
     * Extracts formatting information from given input using the inputGrammar
     *
     * @param grammarInfo  information about the grammar as parsed by the Ingrid Parser
     * @param inputGrammar antl4 grammar of the input
     * @param input        input file from which the formatting should be extracted
     * @return all information about formatting that could be extracted from the input file
     * @throws IllegalArgumentException if the inputGrammar is not a valid antlr4 grammar
     */
    public static Map<FormatInfoMapKey, List<RuleFormatInfo>> extract(GrammarInfo grammarInfo, String inputGrammar, String input) {
        try {
            Grammar grammar = new Grammar(inputGrammar);
            ParseTree ast = InterpretingParser.parse(grammar, input, grammar.rules.getElement(0).name);
            ParseTreeWalker walker = new ParseTreeWalker();
            RuleEnterParseTreeListener listener = new RuleEnterParseTreeListener(grammar, grammarInfo);
            walker.walk(listener, ast);
            return listener.getFormatInfo();
        } catch (RecognitionException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Simplifies the formatInfo map. The internal version keeps track of the context that lead to a specific formatting, but
     * MPS but in MPS we will build only one editor for given concept anyway so we can allows ourselves to forget and concept
     * and create mapping like rule -> alternativeIndex -> ruleFormatInfo, because that is precisely what the importer module needs.
     * <p>
     * TODO Because we have multiple RuleFormatInfo objects for each for each alternative,
     * we have to create a function to merge multiple alternatives together. Our current version simply
     * takes the highest value for each field.
     *
     * @param formatInfoMap map to be simplified
     * @return mapping rule -> alternativeIndex -> ruleFormatInfo
     */
    public static Map<String, Map<Integer, RuleFormatInfo>> simplify(Map<FormatInfoMapKey, List<RuleFormatInfo>> formatInfoMap) {
        Map<String, Map<Integer, RuleFormatInfo>> formatInfo = new HashMap<>();
        for (Map.Entry<FormatInfoMapKey, List<RuleFormatInfo>> entry : formatInfoMap.entrySet()) {
            FormatInfoMapKey key = entry.getKey();
            RuleFormatInfo merged = entry.getValue()
                                         .stream()
                                         .reduce(
                                                 RuleFormatInfo::merge
                                         )
                                         .orElseThrow(() -> new IllegalStateException("Should never happen"));
            String ruleName = key.context.get(key.context.size() - 1);
            int alternativeIndex = key.alternative;

            Map<Integer, RuleFormatInfo> innerMap = formatInfo.computeIfAbsent(ruleName, __ -> new HashMap<>());

            RuleFormatInfo ruleFormatInfos = innerMap.get(alternativeIndex);
            if (ruleFormatInfos == null) {
                innerMap.put(alternativeIndex, merged);
            } else {
                innerMap.put(alternativeIndex, ruleFormatInfos.merge(merged));
            }

        }
        return formatInfo;
    }
}
