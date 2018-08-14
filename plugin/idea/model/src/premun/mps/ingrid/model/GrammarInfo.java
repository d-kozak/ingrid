package premun.mps.ingrid.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class GrammarInfo {
    public String name;
    public Map<String, Rule> rules = new LinkedHashMap<>();
    public Rule rootRule = null;

    public GrammarInfo(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb
            .append("grammar ")
            .append(this.name)
            .append(";")
            .append(System.lineSeparator());

        for (Rule rule : this.rules.values()) {
            sb
                .append(rule.toString())
                .append(System.lineSeparator())
                .append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrammarInfo that = (GrammarInfo) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(rules, that.rules) &&
                Objects.equals(rootRule, that.rootRule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rules, rootRule);
    }
}
