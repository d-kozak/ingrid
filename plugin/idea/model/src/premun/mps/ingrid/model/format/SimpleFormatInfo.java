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
    public static final SimpleFormatInfo UNKNOWN = new SimpleFormatInfo();

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

    /**
     * True just for the UNKNOWN
     */
    private final boolean isUnknown;

    public SimpleFormatInfo(boolean appendNewLine, boolean appendSpace, boolean childrenOnNewLine, boolean childrenIndented) {
        this.appendNewLine = appendNewLine;
        this.appendSpace = appendSpace;
        this.childrenOnNewLine = childrenOnNewLine;
        this.childrenIndented = childrenIndented;
        this.childrenSeparator = null;
        this.isUnknown = false;
    }

    public SimpleFormatInfo(boolean appendNewLine, boolean appendSpace, boolean childrenOnNewLine, boolean childrenIndented, String childrenSeparator) {
        this.appendNewLine = appendNewLine;
        this.appendSpace = appendSpace;
        this.childrenOnNewLine = childrenOnNewLine;
        this.childrenIndented = childrenIndented;
        this.childrenSeparator = childrenSeparator;
        this.isUnknown = false;
    }

    /**
     * Used just for creating the UNKNOWN null-like object
     */
    private SimpleFormatInfo() {
        this.appendNewLine = false;
        this.appendSpace = false;
        this.childrenOnNewLine = false;
        this.childrenIndented = false;
        this.childrenSeparator = null;
        this.isUnknown = true;
    }

    @Override
    public boolean appendNewLine() {
        return appendNewLine;
    }

    @Override
    public boolean appendSpace() {
        return appendSpace;
    }

    @Override
    public boolean areChildrenOnNewLine() {
        return childrenOnNewLine;
    }

    @Override
    public boolean areChildrenIndented() {
        return childrenIndented;
    }

    @Override
    public String getChildrenSeparator() {
        return childrenSeparator;
    }

    public boolean isUnknown() {
        return isUnknown;
    }

    @Override
    public String toString() {
        return "{" +
                "appendNewLine=" + appendNewLine +
                ", appendSpace=" + appendSpace +
                ", childrenOnNewLine=" + childrenOnNewLine +
                ", childrenIndented=" + childrenIndented +
                ",childrenSeparator='" + childrenSeparator + '\'' +
                '}';
    }
}
