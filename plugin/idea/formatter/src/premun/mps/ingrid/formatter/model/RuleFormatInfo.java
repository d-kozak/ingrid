package premun.mps.ingrid.formatter.model;

import java.util.List;

/**
 * Value in the FormatInfo map, each object holds information about one encounter with given rule in given context (part of the key in the hashmap).
 *
 * @author dkozak
 */
public final class RuleFormatInfo {
    public final List<FormatInfo> formatInfoList;

    public RuleFormatInfo(List<FormatInfo> formatInfoList) {
        this.formatInfoList = formatInfoList;
    }

    @Override
    public String toString() {
        return formatInfoList.toString();
    }

}
