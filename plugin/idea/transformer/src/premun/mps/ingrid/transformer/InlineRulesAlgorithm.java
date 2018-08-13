package premun.mps.ingrid.transformer;

import premun.mps.ingrid.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Contains an algorithm that inlines specified rules
 *
 * @author dkozak
 */
public class InlineRulesAlgorithm {

    /**
     * Inlines specified rules
     *
     * @param input         ParserResult whose rules should be inlined
     * @param rulesToInline names of rules to inline
     * @return new instance of ParserResult containing specified rules
     */
    public static GrammarInfo inlineRules(GrammarInfo input, List<String> rulesToInline) {
        checkForUnresolvedRules(input, rulesToInline);


        GrammarInfo result = new GrammarInfo(input.name);
        result.rootRule = input.rootRule;
        if (rulesToInline.contains(input.rootRule.name)) {
            throw new IllegalArgumentException("Cannot inline root rule");
        }
        result.rules = deepCopy(input.rules);

        List<ParserRule> parseRules = result.rules.values()
                                                  .stream()
                                                  .filter(it -> it instanceof ParserRule)
                                                  .map(it -> ((ParserRule) it))
                                                  .collect(Collectors.toList());

        boolean changed = true;
        while (changed) {
            changed = false;
            for (ParserRule rule : parseRules) {
                for (Alternative alternative : rule.alternatives) {
                    for (int i = 0; i < alternative.elements.size(); i++) {
                        RuleReference element = alternative.elements.get(i);
                        if (rulesToInline.contains(element.rule.name)) {
                            alternative.elements.remove(i);
                            Rule ruleToInline = result.rules.get(element.rule.name);
                            List<RuleReference> inlined = inlineRule(ruleToInline);
                            alternative.elements.addAll(i, inlined);
                            i += inlined.size();
                        }
                    }
                }
            }

        }

        for (String ruleName : rulesToInline) {
            result.rules.remove(ruleName);
        }
        return result;
    }

    private static void checkForUnresolvedRules(GrammarInfo input, List<String> rulesToInline) {
        List<String> unresolvedRules = rulesToInline.stream()
                                                    .filter(it -> input.rules.get(it) == null)
                                                    .collect(Collectors.toList());
        if (!unresolvedRules.isEmpty()) {
            throw new IllegalArgumentException("Rules " + unresolvedRules + " were not found in " + input);
        }
    }

    private static List<RuleReference> inlineRule(Rule ruleToInline) {
        if (ruleToInline instanceof ParserRule) {
            if (((ParserRule) ruleToInline).alternatives.size() == 1) {
                return ((ParserRule) ruleToInline).alternatives.get(0).elements;
            } else {
                throw new IllegalArgumentException("Cannot inline rule with more than one alternative. Afterwars Ingrid will create special block rule for it anyway...");
            }
        } else {
            throw new IllegalArgumentException("Only ParserRules can be inlined, " + ruleToInline.getClass()
                                                                                                 .getSimpleName() + " cannot be inlined");
        }
    }

    private static Map<String, Rule> deepCopy(Map<String, Rule> input) {
        HashMap<String, Rule> result = new HashMap<>();
        for (Map.Entry<String, Rule> entry : input.entrySet()) {
            if (entry.getValue() instanceof ParserRule) {
                ParserRule copyOfRule = new ParserRule(entry.getValue().name);
                copyOfRule.alternatives = new ArrayList<>();
                for (Alternative alternative : ((ParserRule) entry.getValue()).alternatives) {
                    Alternative copyOfAlternative = new Alternative();
                    copyOfAlternative.comment = alternative.comment;
                    copyOfAlternative.alias = alternative.alias;
                    copyOfAlternative.elements = new ArrayList<>(alternative.elements);
                    copyOfRule.alternatives.add(copyOfAlternative);
                }
                result.put(entry.getKey(), copyOfRule);
            } else {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }


}
