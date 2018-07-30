package premun.mps.ingrid.formatter;

import org.antlr.v4.runtime.tree.ParseTree;
import premun.mps.ingrid.model.Rule;

import java.util.List;

public final class MatchInfo {
    public final premun.mps.ingrid.model.Rule rule;
    public final int times;
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
