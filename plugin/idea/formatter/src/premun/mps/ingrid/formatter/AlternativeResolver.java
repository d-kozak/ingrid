package premun.mps.ingrid.formatter;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import premun.mps.ingrid.formatter.model.SerializedParserRule;
import premun.mps.ingrid.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class AlternativeResolver {
    public static Alternative selectAlternative(List<Alternative> alternatives, List<ParseTree> parserRuleContext, List<String> ruleNames) {
        alternatives = alternatives.stream()
                                   .map(AlternativeDTO::new)
                                   .collect(toList());
        for (ParseTree parseTree : parserRuleContext) {
            if (alternatives.size() < 2) break;
            if (parseTree instanceof TerminalNode) {
                String tokenContent = ((TerminalNode) parseTree).getSymbol()
                                                                .getText();

                alternativeLoop:
                for (int i = alternatives.size() - 1; i >= 0; i--) {
                    Alternative alternative = alternatives.get(i);
                    for (int j = 0; j < alternative.elements.size(); j++) {
                        RuleReference reference = alternative.elements.get(j);
                        if (reference.rule instanceof LiteralRule) {
                            if (((LiteralRule) reference.rule).value.equals(tokenContent)) {
                                alternative.elements.remove(j);
                                continue alternativeLoop;
                            } else {
                                if (reference.quantity == Quantity.EXACTLY_ONE || reference.quantity == Quantity.AT_LEAST_ONE) {
                                    break;
                                }
                            }
                        } else if (reference.rule instanceof RegexRule) {
                            String regex = ((RegexRule) reference.rule).regexp;
                            if (tokenContent.matches(regex)) {
                                alternative.elements.remove(j);
                                continue alternativeLoop;
                            } else {
                                if (reference.quantity == Quantity.EXACTLY_ONE || reference.quantity == Quantity.AT_LEAST_ONE) {
                                    break;
                                }
                            }
                        } else {
                            if (reference.quantity == Quantity.EXACTLY_ONE || reference.quantity == Quantity.AT_LEAST_ONE) {
                                break;
                            }
                        }
                    }
                    // didn't match
                    alternatives.remove(alternative);
                }
            } else if (parseTree instanceof RuleNode) {
                String ruleName = ruleNames.get(((RuleNode) parseTree).getRuleContext()
                                                                      .getRuleIndex());

                alternativeLoop:
                for (int i = alternatives.size() - 1; i >= 0; i--) {
                    Alternative alternative = alternatives.get(i);
                    for (int j = 0; j < alternative.elements.size(); j++) {
                        RuleReference reference = alternative.elements.get(j);
                        if (reference.rule instanceof ParserRule) {
                            if (reference.rule.name.equals(ruleName)) {
                                alternative.elements.remove(j);
                                continue alternativeLoop;
                            } else if (reference.quantity == Quantity.EXACTLY_ONE || reference.quantity == Quantity.AT_LEAST_ONE) {
                                break;
                            }
                        } else {
                            if (reference.quantity == Quantity.EXACTLY_ONE || reference.quantity == Quantity.AT_LEAST_ONE) {
                                break;
                            }
                        }
                    }
                    // didn't match
                    alternatives.remove(alternative);
                }

            } else {
                throw new UnsupportedOperationException("Cannot process parse tree of type " + parseTree.getClass()
                                                                                                        .getName());
            }
        }
        if (alternatives.size() == 0)
            throw new IllegalArgumentException("No appropriate alternative found");
        else if (alternatives.size() > 1)
            throw new IllegalArgumentException("More than one alternative found");
        else {
            return ((AlternativeDTO) alternatives.get(0)).original;
        }
    }

    static List<Alternative> expandList(List<Alternative> input) {
        List<Alternative> result = new ArrayList<>();

        for (Alternative alternative : input) {
            List<List<RuleReference>> expandedAlternative = new ArrayList<>();
            for (RuleReference element : alternative.elements) {
                if (element.rule instanceof ParserRule && element.rule.name.contains("_block_")) {
                    List<Alternative> inner = expandList(((ParserRule) element.rule).alternatives);
                    if (expandedAlternative.isEmpty()) {
                        for (int i = 0; i < inner.size(); i++) {
                            String name = element.rule.name + "_alt_" + i;
                            SerializedParserRule serializedParserRule = new SerializedParserRule(name, inner.get(i).elements);
                            List<RuleReference> tmp = new ArrayList<>();
                            tmp.add(new RuleReference(serializedParserRule, element.quantity));
                            expandedAlternative.add(tmp);
                        }
                    } else {
                        for (int i = 0; i < inner.size(); i++) {
                            String name = element.rule.name + "_alt_" + i;
                            SerializedParserRule serializedParserRule = new SerializedParserRule(name, inner.get(i).elements);
                            for (List<RuleReference> ruleReferences : expandedAlternative) {
                                ruleReferences.add(new RuleReference(serializedParserRule, element.quantity));
                            }
                        }
                    }
                } else {
                    if (expandedAlternative.isEmpty()) {
                        ArrayList<RuleReference> tmp = new ArrayList<>();
                        tmp.add(element);
                        expandedAlternative.add(tmp);
                    } else {
                        for (List<RuleReference> ruleReferences : expandedAlternative) {
                            ruleReferences.add(element);
                        }
                    }
                }
            }
            result.addAll(expandedAlternative.stream()
                                             .map(references -> new AlternativeDTO(alternative, references))
                                             .collect(Collectors.toList()));
        }

        return flatten(result);
    }

    /**
     * Flattens the references, removing unnecessary SerializedParserRules that are created with nested blocks rules
     * e.g. r : ('a' | ('b' | ( 'c' | 'd'))) ;
     */
    private static List<Alternative> flatten(List<Alternative> result) {
        return result.stream()
                     .map(it -> ((AlternativeDTO) it))
                     .map(alternative -> {
                         List<RuleReference> handle = alternative.elements.stream()
                                                                          .map(AlternativeResolver::flattenReference)
                                                                          .collect(toList());
                         return new AlternativeDTO(alternative.original, handle);
                     })
                     .collect(Collectors.toList());
    }

    private static RuleReference flattenReference(RuleReference input) {
        if (input.rule instanceof SerializedParserRule) {
            SerializedParserRule rule = (SerializedParserRule) input.rule;
            while (rule.elements.size() == 1 && rule.elements.get(0).rule instanceof SerializedParserRule) {
                rule = (SerializedParserRule) rule.elements.get(0).rule;
            }
            input.rule = rule;
            return input;
        } else {
            return input;
        }
    }

    private static class AlternativeDTO extends Alternative {
        private final Alternative original;

        public AlternativeDTO(Alternative original) {
            this.original = original;
            this.elements = new ArrayList<>(original.elements);
        }

        public AlternativeDTO(Alternative original, List<RuleReference> elements) {
            this.original = original;
            this.elements = elements;
        }
    }
}
