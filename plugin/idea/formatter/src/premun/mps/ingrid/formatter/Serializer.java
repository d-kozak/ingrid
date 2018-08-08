package premun.mps.ingrid.formatter;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.tool.Grammar;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.LiteralRule;
import premun.mps.ingrid.model.RuleReference;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

/**
 * Handles serialization of Ingrid model and antlr model objects, used for debugging purposes
 *
 * @author dkozak
 */
class Serializer {

    private final Grammar grammar;

    public Serializer(Grammar grammar) {
        this.grammar = grammar;
    }

    /**
     * Transforms Alternative into a string representation.
     */
    String serializeAlternative(Alternative alternative) {
        return alternative.elements.stream()
                                   .map(this::serializeElem)
                                   .collect(Collectors.joining(" "));
    }

    /**
     * Transforms RuleReference into a string representation.
     * @return for Literal rule it's value in quotes, otherwise it's name
     */
    String serializeElem(RuleReference elem) {
        if (elem.rule instanceof LiteralRule) {
            return "'" + ((LiteralRule) elem.rule).value + "'";
        } else return elem.rule.name;
    }

    /**
     * Transforms list of ParseTree objects into a string representation.
     */
    String serializeChildren(List<ParseTree> children) {
        return children.stream()
                       .map(this::serializeParseTree)
                       .collect(joining(" "));
    }

    /**
     * Transforms ParseTree object into a string representation.
     */
    String serializeParseTree(ParseTree tree) {
        if (tree instanceof TerminalNode) {
            return "'" + ((TerminalNode) tree).getSymbol()
                                              .getText() + "'";
        } else if (tree instanceof RuleContext) {
            return grammar.getRule(((RuleContext) tree).getRuleIndex()).name;
        } else {
            throw new IllegalArgumentException("Unknown tree type " + tree.getClass()
                                                                          .getName());
        }
    }
}
