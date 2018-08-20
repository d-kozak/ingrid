package premun.mps.ingrid.transformer;

import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.model.LiteralRule;
import premun.mps.ingrid.model.ParserRule;
import premun.mps.ingrid.model.Rule;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Returns a grammar info without any unused rules.
 * These rules could either be unused even in the original grammar
 * or could become unused during previous grammar transformations.
 * <p>
 * <p>
 * Used rules are found using breadth first search from the root rule.
 *
 * @author dkozak
 */
public class RemoveUnusedRulesAlgorithm implements GenericGrammarTransformation {

    /**
     * Returns a grammar info without any unused rules.
     * * These rules could either be unused even in the original grammar
     * * or could become unused during previous grammar transformations.
     *
     * @param input grammar to be transformed
     * @return grammar without unused rules
     */
    @Override
    public GrammarInfo transform(GrammarInfo input) {
        GrammarInfo result = new GrammarInfo(input.name);
        result.rootRule = input.rootRule;

        Set<Rule> processedRules = new HashSet<>();

        Deque<Rule> queue = new ArrayDeque<>();
        queue.addFirst(result.rootRule);
        while (!queue.isEmpty()) {
            Rule rule = queue.removeFirst();
            if (!processedRules.contains(rule)) {
                processedRules.add(rule);
                if (!(rule instanceof LiteralRule)) {
                    result.rules.put(rule.name, rule);
                    if (rule instanceof ParserRule) {
                        Set<Rule> nextRules = ((ParserRule) rule).alternatives.stream()
                                                                              .flatMap(alternative -> alternative.elements.stream())
                                                                              .map(ruleReference -> ruleReference.rule)
                                                                              .collect(toSet());
                        queue.addAll(nextRules);
                    }
                }
            }
        }
        return result;
    }
}
