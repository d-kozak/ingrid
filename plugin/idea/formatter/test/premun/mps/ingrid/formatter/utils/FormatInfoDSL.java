package premun.mps.ingrid.formatter.utils;

import premun.mps.ingrid.formatter.model.FormatInfo;

import java.util.Arrays;
import java.util.List;

public final class FormatInfoDSL {

    public static AppliedRuleReference elem(String ruleName, AppendNewLine appendNewLine, AppendSpace appendSpace, ChildrenOnNewLine childrenOnNewLine, ChildrenIndented childrenIndented) {
        return new AppliedRuleReference(ruleName, new FormatInfo(appendNewLine.value, appendSpace.value, childrenOnNewLine.value, childrenIndented.value));
    }

    public static List<AppliedRuleReference> handle(AppliedRuleReference... input) {
        return Arrays.asList(input);
    }

    public static List<AppliedRule> rules(AppliedRule... input) {
        return Arrays.asList(input);
    }

    public static AppendNewLine newLine(boolean value) {
        return new AppendNewLine(value);
    }

    public static AppendSpace space(boolean value) {
        return new AppendSpace(value);
    }

    public static ChildrenOnNewLine childrenOnNewLine(boolean value) {
        return new ChildrenOnNewLine(value);
    }

    public static ChildrenIndented childrenIndented(boolean value) {
        return new ChildrenIndented(value);
    }

    public static class AppliedRule {
        public final String ruleName;
        public final int alternativeIndex;
        public final List<AppliedRuleReference> expectedFormatting;

        private AppliedRule(String ruleName, int alternativeIndex, List<AppliedRuleReference> expectedFormatting) {
            this.ruleName = ruleName;
            this.alternativeIndex = alternativeIndex;
            this.expectedFormatting = expectedFormatting;
        }

        public static AppliedRule rule(String ruleName, int alternativeIndex, List<AppliedRuleReference> expectedFormatting) {
            return new AppliedRule(ruleName, alternativeIndex, expectedFormatting);
        }
    }

    public static class AppliedRuleReference {
        public final String ruleName;
        public final FormatInfo formatInfo;

        public AppliedRuleReference(String ruleName, FormatInfo formatInfo) {
            this.ruleName = ruleName;
            this.formatInfo = formatInfo;
        }
    }

    private static class AppendNewLine {
        private final boolean value;

        private AppendNewLine(boolean value) {
            this.value = value;
        }
    }

    private static class AppendSpace {
        private final boolean value;

        private AppendSpace(boolean value) {
            this.value = value;
        }
    }

    private static class ChildrenOnNewLine {
        private final boolean value;

        private ChildrenOnNewLine(boolean value) {
            this.value = value;
        }
    }

    private static class ChildrenIndented {
        private final boolean value;

        private ChildrenIndented(boolean value) {
            this.value = value;
        }
    }

}
