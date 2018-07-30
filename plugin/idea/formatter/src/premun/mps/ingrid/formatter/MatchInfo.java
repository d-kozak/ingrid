package premun.mps.ingrid.formatter;

import premun.mps.ingrid.model.Rule;

import java.util.Objects;

public final class MatchInfo {
    public final premun.mps.ingrid.model.Rule rule;
    public final int times;

    public MatchInfo(Rule rule, int times) {
        this.rule = rule;
        this.times = times;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchInfo matchInfo = (MatchInfo) o;
        return times == matchInfo.times &&
                Objects.equals(rule, matchInfo.rule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rule, times);
    }

    @Override
    public String toString() {
        return "MatchInfo{" +
                "ruleName=" + rule.name +
                ", times=" + times +
                '}';
    }
}
