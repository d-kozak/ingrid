package premun.mps.ingrid.formatter;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import premun.mps.ingrid.model.*;

import java.util.ArrayList;
import java.util.List;

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

    private static class AlternativeDTO extends Alternative {
        private final Alternative original;

        public AlternativeDTO(Alternative original) {
            this.original = original;
            this.elements = new ArrayList<>(original.elements);
        }
    }
}
