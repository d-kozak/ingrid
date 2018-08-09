package premun.mps.ingrid.formatter.model;

import premun.mps.ingrid.model.LiteralRule;
import premun.mps.ingrid.model.Rule;

/**
 * Holds information about formatting of one element in the rule with respect to the next element.
 *
 * @author dkozak
 */
public final class FormatInfo {

    /**
     * Null object to be inserted when no information about formatting is known.
     */
    public static final FormatInfo NULL_INFO = new FormatInfo(new LiteralRule("NULL"), false, false, false, false);

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

    public FormatInfo(Rule rule, boolean appendNewLine, boolean appendSpace, boolean childrenOnNewLine, boolean childrenIndented) {
        this.rule = rule;
        this.appendNewLine = appendNewLine;
        this.appendSpace = appendSpace;
        this.childrenOnNewLine = childrenOnNewLine;
        this.childrenIndented = childrenIndented;
    }


    /**
     * Uses for testing
     */
    public FormatInfo(boolean appendNewLine, boolean appendSpace, boolean childrenOnNewLine, boolean childrenIndented) {
        this.rule = new LiteralRule("NULL");
        this.appendNewLine = appendNewLine;
        this.appendSpace = appendSpace;
        this.childrenOnNewLine = childrenOnNewLine;
        this.childrenIndented = childrenIndented;
    }

    /**
     * Creates a new merged instance pair FormatInfo, which contains the bigger values for each field
     *
     * @param other another format info, which should be merged with this one
     * @return new merged instance pair FormatInfo, which contains the bigger values for each field
     */
    public FormatInfo merge(FormatInfo other) {
        return new FormatInfo(
                this.rule,
                this.appendNewLine || other.appendNewLine,
                this.appendSpace || other.appendSpace,
                this.childrenOnNewLine || other.childrenOnNewLine,
                this.childrenIndented || other.childrenIndented
        );
    }

    @Override
    public String toString() {
        String ruleName;
        if (rule instanceof LiteralRule) {
            ruleName = ((LiteralRule) rule).value;
        } else {
            ruleName = rule.name;
        }
        return "'" + ruleName + "' => { 'newline': " + appendNewLine + ", 'space': " + appendSpace + ", appendSpace:" + childrenOnNewLine + ", childrenIndented: " + childrenIndented + " }";
    }
}
