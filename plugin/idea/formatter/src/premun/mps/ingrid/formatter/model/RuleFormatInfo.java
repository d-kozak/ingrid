package premun.mps.ingrid.formatter.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Value in the FormatInfo map. It contains a list of FormatInfo objects,
 * each of those corresponds to one ruleReference in the handle of rule's alternative,
 * which are the key in the formatInfoMap
 *
 * @author dkozak
 */
public final class RuleFormatInfo {

    /**
     * Each of these contains format information for one ruleReference in the handle of rule's alternatives (Keys in the map)
     */
    public final List<FormatInfo> formatInfoList;

    public RuleFormatInfo(List<FormatInfo> formatInfoList) {
        this.formatInfoList = formatInfoList;
    }

    @Override
    public String toString() {
        return formatInfoList.toString();
    }

    /***
     * @param other another RuleFormatInfo to be merged with this one
     * @return new RuleFormatInfo, which was crates by merging this and other FormatInfos
     */
    public RuleFormatInfo merge(RuleFormatInfo other) {
        if (other.formatInfoList.size() != this.formatInfoList.size()) {
            throw new IllegalArgumentException("Different formatInfoList size, cannot merge, this:" + this.formatInfoList + ", other: " + other.formatInfoList);
        }
        List<FormatInfo> formatInfos = new ArrayList<>();
        for (int i = 0; i < formatInfoList.size(); i++) {
            FormatInfo thisFormatInfo = formatInfoList.get(i);
            FormatInfo otherFormatInfo = other.formatInfoList.get(i);

            if (!thisFormatInfo.equals(otherFormatInfo)) {
                throw new IllegalArgumentException("Comparing different formatInfos: " + thisFormatInfo.rule + "\n\n is not equal to \n\n" + otherFormatInfo.rule + "\n\n");
            }
            formatInfos.add(thisFormatInfo.merge(otherFormatInfo));
        }
        return new RuleFormatInfo(formatInfos);
    }
}
