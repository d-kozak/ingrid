package premun.mps.ingrid.formatter.boundary;

import premun.mps.ingrid.formatter.model.CollectionFormatInfo;
import premun.mps.ingrid.model.format.FormatInfo;
import premun.mps.ingrid.model.format.SimpleFormatInfo;

import java.util.Objects;

/**
 * Currently the only implementation of merge operation that takes a CollectionFormatInfo and returns FormatInfo.
 * Used at the end of format info extraction algorithm to merge all found formatting of specified rule and alternative into a single object.
 *
 * @author dkozak
 */
public class MergeFormatInfoOperation {

    /**
     * @param collectionFormatInfo formatting to merge
     * @return FormatInfo containing maximum known formatting from the collectionFormatInfo
     */
    public static FormatInfo merge(CollectionFormatInfo collectionFormatInfo) {
        return collectionFormatInfo.getAllFormatInfo()
                                   .stream()
                                   .reduce(SimpleFormatInfo.UNKNOWN, MergeFormatInfoOperation::mergeTwoFormatInfos);
    }

    private static SimpleFormatInfo mergeTwoFormatInfos(SimpleFormatInfo left, SimpleFormatInfo right) {
        if (!Objects.equals(left.childrenSeparator, right.childrenSeparator)) {
            throw new IllegalStateException("Cannot merge two SimpleFormatInfo Objects with different separators: " + left + " vs" + right);
        }
        return new SimpleFormatInfo(left.appendNewLine() || right.appendNewLine(),
                left.appendSpace() || right.appendSpace(),
                left.areChildrenOnNewLine() || right.areChildrenOnNewLine(),
                left.areChildrenIndented() || right.areChildrenIndented(),
                left.childrenSeparator);
    }

    ;
}
