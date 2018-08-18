package premun.mps.ingrid.model.format;

/**
 * Represents a single piece of information about formatting that was extracted from
 * one place in the source code.
 * <p>
 * This class is immutable.
 *
 * @author dkozak
 * @see FormatInfo
 */
public class SimpleFormatInfo implements FormatInfo {

    /**
     * To be used when no formatting can be extracted
     */
    public static final SimpleFormatInfo UNKNOWN = new SimpleFormatInfo(false, false, false, false);

    /**
     * Are children separated by some char?
     */
    public final String childrenSeparator;
    /**
     * Is there a newline after this element?
     */
    private final boolean appendNewLine;
    /**
     * Is there a space after this element?
     */
    private final boolean appendSpace;
    /**
     * Are children of this element on new lines?
     */
    private final boolean childrenOnNewLine;
    /**
     * Are children of this element indented?
     */
    private final boolean childrenIndented;

    public SimpleFormatInfo(boolean appendNewLine, boolean appendSpace, boolean childrenOnNewLine, boolean childrenIndented) {
        this.appendNewLine = appendNewLine;
        this.appendSpace = appendSpace;
        this.childrenOnNewLine = childrenOnNewLine;
        this.childrenIndented = childrenIndented;
        this.childrenSeparator = null;
    }

    public SimpleFormatInfo(boolean appendNewLine, boolean appendSpace, boolean childrenOnNewLine, boolean childrenIndented, String childrenSeparator) {
        this.appendNewLine = appendNewLine;
        this.appendSpace = appendSpace;
        this.childrenOnNewLine = childrenOnNewLine;
        this.childrenIndented = childrenIndented;
        this.childrenSeparator = childrenSeparator;
    }

    @Override
    public boolean isAppendNewLine() {
        return appendNewLine;
    }

    @Override
    public boolean isAppendSpace() {
        return appendSpace;
    }

    @Override
    public boolean isChildrenOnNewLine() {
        return childrenOnNewLine;
    }

    @Override
    public boolean isChildrenIndented() {
        return childrenIndented;
    }

    @Override
    public String getChildrenSeparator() {
        return childrenSeparator;
    }
}
