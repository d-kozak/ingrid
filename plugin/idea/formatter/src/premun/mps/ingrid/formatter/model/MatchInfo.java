package premun.mps.ingrid.formatter.model;

import org.antlr.v4.runtime.tree.ParseTree;
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

    /**
     * How many times it was matched
     */
    public final int times;

    /**
     * Ast nodes that correspond to the match
     */
    public final List<ParseTree> matched;

    public MatchInfo(Rule rule, int times, List<ParseTree> matched) {
        this.rule = rule;
        this.times = times;
        this.matched = matched;
    }

    @Override
    public String toString() {
        return "MatchInfo{" +
                "ruleName=" + rule.name +
                ", times=" + times +
                ", matched=" + matched +
                '}';
    }
}
