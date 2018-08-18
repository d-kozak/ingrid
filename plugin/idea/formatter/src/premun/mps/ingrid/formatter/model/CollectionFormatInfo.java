package premun.mps.ingrid.formatter.model;


import premun.mps.ingrid.model.format.SimpleFormatInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a collection of SimpleFormatInfo objects as they are being collected
 * in the FormatExtraction algorithm.
 * <p>
 * This class is never meant to be queried for formatting.
 * In fact it's instances should never escape the formatter module.
 * escape the formatter module.
 *
 * @author dkozak
 * @see FormatInfo
 * @see SimpleFormatInfo
 */
public class CollectionFormatInfo implements premun.mps.ingrid.model.format.FormatInfo {

    /**
     * List into which format information is collected during format extraction.
     */
    private final List<SimpleFormatInfo> collectedInformation;


    public CollectionFormatInfo() {
        collectedInformation = new ArrayList<>();
    }


    /**
     * Adds new format info to the underlying list
     *
     * @param formatInfo new format info to be added
     */
    public void addFormatInfo(SimpleFormatInfo formatInfo) {
        collectedInformation.add(formatInfo);
    }

    /**
     * @return all collected information about formatting in an immutable list
     */
    public List<SimpleFormatInfo> getAllFormatInfo() {
        return Collections.unmodifiableList(collectedInformation);
    }


    @Override
    public boolean appendNewLine() {
        throw new UnsupportedOperationException("This class is only used during the format extraction phase. See docs.");
    }

    @Override
    public boolean appendSpace() {
        throw new UnsupportedOperationException("This class is only used during the format extraction phase. See docs.");
    }

    @Override
    public boolean areChildrenOnNewLine() {
        throw new UnsupportedOperationException("This class is only used during the format extraction phase. See docs.");
    }

    @Override
    public boolean areChildrenIndented() {
        throw new UnsupportedOperationException("This class is only used during the format extraction phase. See docs.");
    }

    @Override
    public String getChildrenSeparator() {
        throw new UnsupportedOperationException("This class is only used during the format extraction phase. See docs.");
    }
}
