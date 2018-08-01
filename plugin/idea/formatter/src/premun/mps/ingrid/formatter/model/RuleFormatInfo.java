package premun.mps.ingrid.formatter.model;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.max;

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


    public RuleFormatInfo merge(RuleFormatInfo other) {
        if (other.formatInfoList.size() != this.formatInfoList.size()) {
            throw new IllegalArgumentException("Different formatInfoList size, cannot merge, this:" + this.formatInfoList + ", other: " + other.formatInfoList);
        }
        List<FormatInfo> formatInfos = new ArrayList<>();
        for (int i = 0; i < formatInfoList.size(); i++) {
            FormatInfo thisFormatInfo = formatInfoList.get(i);
            FormatInfo otherFormatInfo = other.formatInfoList.get(i);
            if (!thisFormatInfo.rule.equals(otherFormatInfo.rule)) {
                throw new IllegalArgumentException("Comparing different formatInfo rules");
            }
            FormatInfo mergedInfo = new FormatInfo(
                    thisFormatInfo.rule,
                    max(thisFormatInfo.followingNewLinesCount, otherFormatInfo.followingNewLinesCount),
                    max(thisFormatInfo.followingSpacesCount, otherFormatInfo.followingSpacesCount));
            formatInfos.add(mergedInfo);
        }
        return new RuleFormatInfo(formatInfos);
    }
}
