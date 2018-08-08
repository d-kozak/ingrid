package premun.mps.ingrid.formatter.model;

import premun.mps.ingrid.model.LiteralRule;
import premun.mps.ingrid.model.Rule;

import static java.lang.Integer.max;

/**
 * Holds information about formatting of one element in the rule with respect to the next element.
 *
 * @author dkozak
 */
public final class FormatInfo {

    /**
     * Null object to be inserted when no information about formatting is known.
     */
    public static final FormatInfo NULL_INFO = new FormatInfo(new LiteralRule("NULL"), 0, 0, false, false);

    /**
     * Rule which is covered by this object.
     */
    public final Rule rule;

    /**
     * Is there a newline after this element?
     */
    public final int followingNewLinesCount;

    /**
     * Is there a space after this element?
     */
    public final int followingSpacesCount;

    /**
     * Are children pair this element on new lines?
     */
    public final boolean childrenOnNewLine;

    /**
     * Are children pair this element indented?
     */
    public final boolean childrenIndented;

    public FormatInfo(Rule rule, int followingNewLinesCount, int followingSpacesCount, boolean childrenOnNewLine, boolean childrenIndented) {
        this.rule = rule;
        this.followingNewLinesCount = followingNewLinesCount;
        this.followingSpacesCount = followingSpacesCount;
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
                max(this.followingNewLinesCount, other.followingNewLinesCount),
                max(this.followingSpacesCount, other.followingSpacesCount),
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
        return "'" + ruleName + "' => { 'newline': " + followingNewLinesCount + ", 'space': " + followingSpacesCount + ", appendSpace:" + childrenOnNewLine + ", childrenIndented: " + childrenIndented + " }";
    }
}
