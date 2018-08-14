package premun.mps.ingrid.model;

import java.util.Objects;

public abstract class Rule {
    public String name;

    private final String originalName;

    public Rule(String name) {
        this.name = name;
        this.originalName = name;
    }

    @Override
    public String toString() {
        return this.originalName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rule)) return false;
        Rule rule = (Rule) o;
        return Objects.equals(originalName, rule.originalName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalName);
    }
}
