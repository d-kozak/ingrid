package premun.mps.ingrid.formatter.model;

import premun.mps.ingrid.model.LiteralRule;
import premun.mps.ingrid.model.Rule;

import static java.lang.Integer.max;

/**
 * Holds information about formatting of one rule with respect to the following rules in the handle.
 *
 * @author dkozak
 */
public final class FormatInfo {
    public static final FormatInfo NULL_INFO = new FormatInfo(new LiteralRule("NULL"), 0, 0, false, false);

    public final Rule rule;
    public final int followingNewLinesCount;
    public final int followingSpacesCount;
    public final boolean childrenOnNewLine;
    public final boolean childrenIndented;

    public FormatInfo(Rule rule, int followingNewLinesCount, int followingSpacesCount, boolean childrenOnNewLine, boolean childrenIndented) {
        this.rule = rule;
        this.followingNewLinesCount = followingNewLinesCount;
        this.followingSpacesCount = followingSpacesCount;
        this.childrenOnNewLine = childrenOnNewLine;
        this.childrenIndented = childrenIndented;
    }

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
