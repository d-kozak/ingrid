package premun.mps.ingrid.model;

import java.util.Objects;

public class LiteralRule extends FlatLexerRule {
    public String value;

    public LiteralRule(String name, String value) {
        super(name);
        // Strip apostrophes 'foo' -> foo
        if (value.startsWith("'")) {
            this.value = value.substring(1, value.length() - 1);
        } else {
            this.value = value;
        }
    }

    public LiteralRule(String value) {
        this(java.util.UUID.randomUUID().toString(), value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiteralRule that = (LiteralRule) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }

    @Override
    public String getContent() {
        return value;
    }

    @Override
    public String toString() {
        return this.name
            + ":\t"
            + this.value
            + System.lineSeparator()
            + "\t\t;"
            + System.lineSeparator();
    }
}
