package premun.mps.ingrid.model.format;

/**
 * An information about formatting of given rule reference
 *
 * @author dkozak
 * @see premun.mps.ingrid.model.RuleReference
 */
public interface FormatInfo {

    /**
     * Is there a newline after this element?
     */
    boolean isAppendNewLine();

    /**
     * Is there a space after this element?
     */
    boolean isAppendSpace();

    /**
     * Are children of this element on new lines?
     */
    boolean isChildrenOnNewLine();

    /**
     * Are children of this element indented?
     */
    boolean isChildrenIndented();

    /**
     * Are children separated by some char?
     */
    String getChildrenSeparator();
}
