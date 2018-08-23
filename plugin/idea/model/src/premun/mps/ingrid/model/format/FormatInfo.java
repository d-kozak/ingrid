package premun.mps.ingrid.model.format;

/**
 * An information about formatting of given rule reference
 *
 * @author dkozak
 * @see premun.mps.ingrid.model.RuleReference
 */
public interface FormatInfo {

    /**
     * To be used when no formatting can be extracted
     */
    FormatInfo UNKNOWN = new SimpleFormatInfo();

    /**
     * Default formatting, to be used when no customization is necessary
     */
    FormatInfo DEFAULT = new SimpleFormatInfo(false, true, false, false);


    /**
     * Is there a newline after this element?
     */
    boolean appendNewLine();

    /**
     * Is there a space after this element?
     */
    boolean appendSpace();

    /**
     * Are children of this element on new lines?
     */
    boolean areChildrenOnNewLine();

    /**
     * Are children of this element indented?
     */
    boolean areChildrenIndented();

    /**
     * Are children separated by some char?
     */
    String getChildrenSeparator();


    /**
     * Do we know anything about the format of this?
     */
    boolean isUnknown();
}
