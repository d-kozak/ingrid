package premun.mps.ingrid.formatter.boundary;

import org.antlr.runtime.RecognitionException;
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
            ParseTree ast = InterpretingParser.parse(grammar, input, grammar.rules.getElement(0).name);
            ParseTreeWalker walker = new ParseTreeWalker();
            RuleEnterParseTreeListener listener = new RuleEnterParseTreeListener(grammar, grammarInfo);
            walker.walk(listener, ast);
            return listener.getFormatInfo();
        } catch (RecognitionException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public static Map<Pair<ParserRule, Alternative>, RuleFormatInfo> merge(Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> input) {
        return input.entrySet()
                    .stream()
                    .map(it -> Pair.of(
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
}
