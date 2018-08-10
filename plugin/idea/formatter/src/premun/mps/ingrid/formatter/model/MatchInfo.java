package premun.mps.ingrid.formatter.model;

import org.antlr.v4.runtime.tree.ParseTree;
import premun.mps.ingrid.model.Quantity;
import premun.mps.ingrid.model.Rule;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains information about the match of a single rule in list of ruleReferences in the handle of given alternative in a rule.
 *
 * @author dkozak
 */
public final class MatchInfo {

    /**
     * Which rule was matched
     */
    public final premun.mps.ingrid.model.Rule rule;

    /**
     * What quantity the rule has
     */
    public final Quantity quantity;

    /**
     * Ast nodes that correspond to the match
     */
    public final List<List<ParseTree>> matched;

    public MatchInfo(Rule rule, Quantity quantity, List<List<ParseTree>> matched) {
        this.rule = rule;
        this.quantity = quantity;
        this.matched = matched;
    }

    @Override
    public String toString() {
        return "MatchInfo{" +
                "rule=" + rule +
                ", quantity=" + quantity +
                ", times=" + times() +
                ", matched=" + matched +
                '}';
    }

    /**
     * How many times it was matched
     */
    public int times() {
        return matched.size();
    }

    public boolean isEmpty() {
        return matched.stream()
                      .allMatch(List::isEmpty);
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public ParseTree getLeftmostParseTree() {
        for (List<ParseTree> matched : this.matched) {
            if (!matched.isEmpty())
                return matched.get(0);
        }
        throw new IllegalArgumentException("There should be at least one ParseTree in MatchInfo");
    }

    public ParseTree getRightMostParseTree() {
        for (int i = this.matched.size() - 1; i >= 0; i--) {
            List<ParseTree> matched = this.matched.get(i);
            if (!matched.isEmpty()) {
                return matched.get(matched.size() - 1);
            }
        }
        throw new IllegalArgumentException("There should be at least one ParseTree in MatchInfo");
    }

    public List<ParseTree> getMatchedRegion() {
        return matched.stream()
                      .flatMap(Collection::stream)
                      .collect(Collectors.toList());
    }
}
