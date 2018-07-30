package premun.mps.ingrid.formatter.model;

import java.util.List;

public final class RuleFormatInfo {
    public final List<FormatInfo> formatInfoList;

    public RuleFormatInfo(List<FormatInfo> formatInfoList) {
        this.formatInfoList = formatInfoList;
    }

    @Override
    public String toString() {
        return "RuleFormatInfo{" +
                "formatInfoList=" + formatInfoList +
                '}';
    }

}
