package premun.mps.ingrid.formatter.model;

import premun.mps.ingrid.model.LiteralRule;
import premun.mps.ingrid.model.Rule;

import java.util.Objects;

/**
 * Holds information about formatting of one element in the rule with respect to the next element.
 *
 * @author dkozak
 */
public class FormatInfo {

    /**
     * Rule which is covered by this object.
     */
    public final Rule rule;

    /**
     * Is there a newline after this element?
     */
    public final boolean appendNewLine;

    /**
     * Is there a space after this element?
     */
    public final boolean appendSpace;

    /**
     * Are children of this element on new lines?
     */
    public final boolean childrenOnNewLine;

    /**
     * Are children of this element indented?
     */
    public final boolean childrenIndented;

    /**
     * Are children separated by some char?
     */
    public final String childrenSeparator;

    public FormatInfo(Rule rule, boolean appendNewLine, boolean appendSpace, boolean childrenOnNewLine, boolean childrenIndented) {
        this.rule = rule;
        this.appendNewLine = appendNewLine;
        this.appendSpace = appendSpace;
        this.childrenOnNewLine = childrenOnNewLine;
        this.childrenIndented = childrenIndented;
        this.childrenSeparator = null;
    }

    public FormatInfo(Rule rule, boolean appendNewLine, boolean appendSpace, boolean childrenOnNewLine, boolean childrenIndented, String childrenSeparator) {
        this.rule = rule;
        this.appendNewLine = appendNewLine;
        this.appendSpace = appendSpace;
        this.childrenOnNewLine = childrenOnNewLine;
        this.childrenIndented = childrenIndented;
        this.childrenSeparator = childrenSeparator;
    }

    /**
     * Creates a new merged instance pair FormatInfo, which contains the bigger values for each field
     *
     * @param other another format info, which should be merged with this one
     * @return new merged instance pair FormatInfo, which contains the bigger values for each field
     */
    public FormatInfo merge(FormatInfo other) {
        if (this instanceof UnknownFormatInfo && other instanceof UnknownFormatInfo)
            return this;
        Rule rule = (this instanceof UnknownFormatInfo) ? other.rule : this.rule;
        String separator = this.childrenSeparator;
        if (other.childrenSeparator != null) {
            if (this.childrenSeparator != null && !this.childrenSeparator.equals(other.childrenSeparator)) {
                throw new IllegalArgumentException("Cannot merge formatInfo rules with different children separators: " + this + " vs " + other);
            }
            separator = other.childrenSeparator;
        }
        return new FormatInfo(
                rule,
                this.appendNewLine || other.appendNewLine,
                this.appendSpace || other.appendSpace,
                this.childrenOnNewLine || other.childrenOnNewLine,
                this.childrenIndented || other.childrenIndented,
                separator
        );

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormatInfo)) return false;
        if (this instanceof UnknownFormatInfo || o instanceof UnknownFormatInfo)
            return true;
        FormatInfo that = (FormatInfo) o;
        return Objects.equals(rule, that.rule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rule);
    }

    @Override
    public String toString() {
        if (this instanceof UnknownFormatInfo)
            return "UNKNOWN";

        String ruleName;
        if (rule instanceof LiteralRule) {
            ruleName = ((LiteralRule) rule).value;
        } else {
            ruleName = rule.name;
        }
        return "'" + ruleName + "' => { 'newline': " + appendNewLine + ", 'space': " + appendSpace + ", childrenOnNewLine:" + childrenOnNewLine + ", childrenIndented: " + childrenIndented + ", childrenSeparator: '" + childrenSeparator + "'}";
    }

    public static class UnknownFormatInfo extends FormatInfo {

        /**
         * Null object to be inserted when no information about formatting is known.
         */
        public static final UnknownFormatInfo UNKNOWN_FORMAT_INFO = new UnknownFormatInfo();

        private UnknownFormatInfo() {
            super(new LiteralRule("UNKNOWN"), false, false, false, false);
        }
    }
}
