package premun.mps.ingrid.formatter.boundary;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.tool.Grammar;
import premun.mps.ingrid.formatter.InterpretingParser;
import premun.mps.ingrid.formatter.RuleEnterParseTreeListener;
import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.formatter.utils.Pair;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.model.ParserRule;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static premun.mps.ingrid.formatter.utils.Pair.pair;

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
    public static Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> extract(GrammarInfo grammarInfo, String inputGrammar, String input) {
        try {
            Grammar grammar = new Grammar(inputGrammar);
            Pair<CommonTokenStream, ParseTree> pair = InterpretingParser.tokenizeAndParse(grammar, input, grammar.rules.getElement(0).name);
            CommonTokenStream tokens = pair.first;
            ParseTree ast = pair.second;
            ParseTreeWalker walker = new ParseTreeWalker();
            RuleEnterParseTreeListener listener = new RuleEnterParseTreeListener(grammar, grammarInfo, tokens);
            walker.walk(listener, ast);
            return listener.getFormatInfo();
        } catch (RecognitionException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Merges keys, which are List<RuleFormatInfo> in the formatInfoMap currently into a single RuleFormatInfo, which will be used when creating the editor.
     *
     * @param ruleFormatInfo
     * @return simplified ruleFormatInfo, List<RuleFormatInfo> is merged into a single object
     */
    public static Map<Pair<ParserRule, Alternative>, RuleFormatInfo> merge(Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> ruleFormatInfo) {
        return ruleFormatInfo.entrySet()
                             .stream()
                             .map(it -> pair(
                                     it.getKey(),
                                     it.getValue()
                                       .stream()
                                       .reduce(RuleFormatInfo::merge)
                                       .orElseThrow(() -> new IllegalStateException("Should never happen"))
                                     )
                             )
                             .collect(toMap(
                                     Pair::fst,
                                     Pair::snd
                             ));
    }


    public static Map<Pair<String, Integer>, RuleFormatInfo> asRuleNameAlternativeIndexMap(Map<Pair<ParserRule, Alternative>, RuleFormatInfo> ruleFormatInfo) {
        return ruleFormatInfo.entrySet()
                             .stream()
                             .map(entry -> pair(
                                     pair(
                                             entry.getKey().first.name,
                                             entry.getKey().first.alternatives.indexOf(entry.getKey().second)
                                     ),
                                     entry.getValue()
                                     )
                             )
                             .collect(toMap(
                                     Pair::fst,
                                     Pair::snd
                             ));
    }
}
