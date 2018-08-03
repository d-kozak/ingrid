package premun.mps.ingrid.formatter.model;

import premun.mps.ingrid.model.LiteralRule;
import premun.mps.ingrid.model.Rule;

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
