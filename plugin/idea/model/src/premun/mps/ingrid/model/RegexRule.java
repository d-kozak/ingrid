package premun.mps.ingrid.model;

import org.jetbrains.mps.openapi.model.SNode;

/**
 * Warning: Regex equals method compares the NAME of the rule!
 */
public class RegexRule extends FlatLexerRule {
    public String regexp;

    public SNode node = null;

    public RegexRule(String name, String regexp) {
        super(name);
        this.regexp = regexp;
    }

    public RegexRule(String regexp) {
        this(java.util.UUID.randomUUID().toString(), regexp);
    }

    @Override
    public String getContent() {
        return regexp;
    }

    @Override
    public String toString() {
        return this.name
                + ":\t"
                + this.regexp
                + System.lineSeparator()
                + "\t\t;"
                + System.lineSeparator();
    }
}
