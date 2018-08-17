package premun.mps.ingrid.transformer;

import premun.mps.ingrid.model.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Inlines rules specified in the constructor
 *
 * @author dkozak
 */
public class InlineRulesAlgorithm implements GenericGrammarTransformation {

    private final List<String> rulesToInline;

    /**
     * @param rulesToInline names of rules to inline
     */
    public InlineRulesAlgorithm(List<String> rulesToInline) {
        this.rulesToInline = rulesToInline;
    }

    /**
     * Inlines specified rules
     *
     * @param input ParserResult whose rules should be inlined
     * @return new instance of ParserResult containing specified rules
     */
    @Override
    public void transform(GrammarInfo input) {
        checkForInvalidRuleNames(input, rulesToInline);

        UniqueRuleNameGenerator nameGenerator = new UniqueRuleNameGenerator(input.rules.keySet());

        List<ParserRule> parseRules = input.rules.values()
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
                            Rule ruleToInline = input.rules.get(element.rule.name);
                            List<RuleReference> inlined = inlineRule(ruleToInline);

                            if (element.quantity != Quantity.EXACTLY_ONE) {
                                ParserRule newBlockRule = new ParserRule("_block_" + nameGenerator.newUniqueName());
                                Alternative newAlternative = new Alternative();
                                newAlternative.elements.addAll(inlined);
                                newBlockRule.alternatives.add(newAlternative);
                                input.rules.put(newBlockRule.name, newBlockRule);
                                alternative.elements.add(i, new RuleReference(newBlockRule, element.quantity));
                            } else {
                                alternative.elements.addAll(i, inlined);
                                i += inlined.size();
                            }
                            changed = true;
                        }
                    }
                }
            }

        }

        for (String ruleName : rulesToInline) {
            input.rules.remove(ruleName);
        }
    }

    private void checkForInvalidRuleNames(GrammarInfo input, List<String> rulesToInline) {
        List<String> unresolvedRules = rulesToInline.stream()
                                                    .filter(it -> input.rules.get(it) == null)
                                                    .collect(Collectors.toList());
        if (!unresolvedRules.isEmpty()) {
            throw new IllegalArgumentException("Rules " + unresolvedRules + " were not found in " + input);
        }
    }

    private List<RuleReference> inlineRule(Rule ruleToInline) {
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
}
