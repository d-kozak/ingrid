package premun.mps.ingrid.formatter.model;

import org.antlr.v4.runtime.tree.ParseTree;
import premun.mps.ingrid.model.Quantity;
import premun.mps.ingrid.model.Rule;

import java.util.List;

/**
 * Contains information about the match of a single rule in list of ruleReferences on the handle of given alternative
 *
 * @author dkozak
 */
public final class MatchInfo {
    /**
     * Which rule was matched
     */
    public final premun.mps.ingrid.model.Rule rule;

    public final Quantity quantity;

    /**
     * How many times it was matched
     */
    public final int times;

    /**
     * Ast nodes that correspond to the match
     */
    public final List<ParseTree> matched;

    public MatchInfo(Rule rule, Quantity quantity, int times, List<ParseTree> matched) {
        this.rule = rule;
        this.quantity = quantity;
        this.times = times;
        this.matched = matched;
    }

    @Override
    public String toString() {
        return "MatchInfo{" +
                "rule=" + rule +
                ", quantity=" + quantity +
                ", times=" + times +
                ", matched=" + matched +
                '}';
    }
}
