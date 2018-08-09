package premun.mps.ingrid.formatter.utils;

import premun.mps.ingrid.formatter.model.FormatInfo;

import java.util.Arrays;
import java.util.List;

public final class FormatInfoDSL {

    public static FormatInfo elem(boolean appendNewLine, boolean appendSpace, boolean childrenOnNewLine, boolean childrenIndented) {
        return new FormatInfo(appendNewLine, appendSpace, childrenOnNewLine, childrenIndented);
    }

    public static List<FormatInfo> handle(FormatInfo... input) {
        return Arrays.asList(input);
    }

    public static List<AppliedRule> rules(AppliedRule... input) {
        return Arrays.asList(input);
    }

    public static class AppliedRule {
        public final String ruleName;
        public final int alternativeIndex;
        public final List<FormatInfo> expectedFormatting;

        private AppliedRule(String ruleName, int alternativeIndex, List<FormatInfo> expectedFormatting) {
            this.ruleName = ruleName;
            this.alternativeIndex = alternativeIndex;
            this.expectedFormatting = expectedFormatting;
        }

        public static AppliedRule rule(String ruleName, int alternativeIndex, List<FormatInfo> expectedFormatting) {
            return new AppliedRule(ruleName, alternativeIndex, expectedFormatting);
        }
    }
}
