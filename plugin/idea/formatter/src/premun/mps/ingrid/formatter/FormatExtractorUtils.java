package premun.mps.ingrid.formatter;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.LiteralRule;
import premun.mps.ingrid.model.Quantity;
import premun.mps.ingrid.model.RuleReference;

import java.util.ArrayList;
import java.util.List;

public class FormatExtractorUtils {

    public static Alternative findAppropriateAlternative(List<Alternative> alternatives, List<ParseTree> parserRuleContext, List<String> ruleNames) {
        alternatives = new ArrayList<>(alternatives);
        for (int i = 0; i < parserRuleContext.size(); i++) {
            if (alternatives.size() < 2) break;
            ParseTree parseTree = parserRuleContext.get(i);
            if (parseTree instanceof TerminalNode) {
                Token symbol = ((TerminalNode) parseTree).getSymbol();
                String text = symbol.getText();

                alternativeLoop:
                for (int j = alternatives.size() - 1; j >= 0; j--) {
                    Alternative alternative = alternatives.get(j);
                    for (int k = i; k < alternative.elements.size(); k++) {
                        RuleReference reference = alternative.elements.get(k);
                        if (reference.rule instanceof LiteralRule) {
                            if (((LiteralRule) reference.rule).value.equals(text)) {
                                break alternativeLoop;
                            } else {
                                if (reference.quantity == Quantity.EXACTLY_ONE || reference.quantity == Quantity.AT_LEAST_ONE) {
                                    alternatives.remove(alternative);
                                    break alternativeLoop;
                                }
                            }
                        } else {
                            if (reference.quantity == Quantity.EXACTLY_ONE || reference.quantity == Quantity.AT_LEAST_ONE) {
                                alternatives.remove(alternative);
                                break alternativeLoop;
                            }
                        }
                    }
                    // didn't match
                    alternatives.remove(alternative);
                }
            } else if (parseTree instanceof RuleNode) {

            } else {
                throw new UnsupportedOperationException("Cannot process parse tree of type " + parseTree.getClass()
                                                                                                        .getName());
            }
        }
        if (alternatives.size() == 0)
            throw new IllegalArgumentException("No appropriate alternative found");
        else if (alternatives.size() > 1)
            throw new IllegalArgumentException("More than one alternative found");
        else return alternatives.get(0);
    }
}
