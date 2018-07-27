package premun.mps.ingrid.formatter;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import premun.mps.ingrid.model.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class FormatExtractorUtils {
    public static Alternative findAppropriateAlternative(List<Alternative> alternatives, List<ParseTree> parserRuleContext, List<String> ruleNames) {
        alternatives = alternatives.stream()
                                   .map(AlternativeDTO::new)
                                   .collect(toList());
        for (int i = 0; i < parserRuleContext.size(); i++) {
            if (alternatives.size() < 2) break;
            ParseTree parseTree = parserRuleContext.get(i);
            if (parseTree instanceof TerminalNode) {
                Token symbol = ((TerminalNode) parseTree).getSymbol();
                String text = symbol.getText();

                alternativeLoop:
                for (int j = alternatives.size() - 1; j >= 0; j--) {
                    Alternative alternative = alternatives.get(j);
                    for (int k = 0; k < alternative.elements.size(); k++) {
                        RuleReference reference = alternative.elements.get(k);
                        if (reference.rule instanceof LiteralRule) {
                            if (((LiteralRule) reference.rule).value.equals(text)) {
                                alternative.elements.remove(k);
                                continue alternativeLoop;
                            } else {
                                if (reference.quantity == Quantity.EXACTLY_ONE || reference.quantity == Quantity.AT_LEAST_ONE) {
                                    break;
                                }
                            }
                        } else if (reference.rule instanceof RegexRule) {
                            String regex = ((RegexRule) reference.rule).regexp;
                            if (text.matches(regex)) {
                                alternative.elements.remove(k);
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
                int ruleIndex = ((RuleNode) parseTree).getRuleContext()
                                                      .getRuleIndex();
                String ruleName = ruleNames.get(ruleIndex);

                alternativeLoop:
                for (int j = alternatives.size() - 1; j >= 0; j--) {
                    Alternative alternative = alternatives.get(j);
                    for (int k = 0; k < alternative.elements.size(); k++) {
                        RuleReference reference = alternative.elements.get(k);
                        if (reference.rule instanceof ParserRule) {
                            if (reference.rule.name.equals(ruleName)) {
                                alternative.elements.remove(k);
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
