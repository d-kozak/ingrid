package premun.mps.ingrid.formatter.utils;

import premun.mps.ingrid.formatter.model.FormatInfo;
import premun.mps.ingrid.model.Rule;

import java.util.Arrays;
import java.util.List;

public final class FormatInfoDSL {

    public static AppliedRuleReference element(String ruleName, AppendNewLine appendNewLine, AppendSpace appendSpace) {
        Rule dummy = new Rule(ruleName) {
        };
        return new AppliedRuleReference(ruleName, new FormatInfo(dummy, appendNewLine.value, appendSpace.value, false, false, null));
    }

    public static AppliedRuleReference collection(String ruleName, AppendNewLine appendNewLine, AppendSpace appendSpace, ChildrenOnNewLine childrenOnNewLine, ChildrenIndented childrenIndented, ChildrenSeparator childrenSeparator) {
        Rule dummy = new Rule(ruleName) {
        };
        return new AppliedRuleReference(ruleName, new FormatInfo(dummy, appendNewLine.value, appendSpace.value, childrenOnNewLine.value, childrenIndented.value, childrenSeparator.value));
    }

    public static AppliedRuleReference collection(String ruleName, AppendNewLine appendNewLine, AppendSpace appendSpace, ChildrenOnNewLine childrenOnNewLine, ChildrenIndented childrenIndented) {
        Rule dummy = new Rule(ruleName) {
        };
        return new AppliedRuleReference(ruleName, new FormatInfo(dummy, appendNewLine.value, appendSpace.value, childrenOnNewLine.value, childrenIndented.value));
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

    public static ChildrenSeparator childrenSeparator(String value) {
        return new ChildrenSeparator(value);
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

        @Override
        public String toString() {
            return ruleName + ":" + alternativeIndex + " => " + expectedFormatting;
        }
    }

    public static class AppliedRuleReference {
        public final String ruleName;
        public final FormatInfo formatInfo;

        public AppliedRuleReference(String ruleName, FormatInfo formatInfo) {
            this.ruleName = ruleName;
            this.formatInfo = formatInfo;
        }

        @Override
        public String toString() {
            return formatInfo.toString();
        }
    }

    private static class AppendNewLine {
        private final boolean value;

        private AppendNewLine(boolean value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "newLine:" + value;
        }
    }

    private static class AppendSpace {
        private final boolean value;

        private AppendSpace(boolean value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "space:" + value;
        }
    }

    private static class ChildrenOnNewLine {
        private final boolean value;

        private ChildrenOnNewLine(boolean value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "childrenOnNewLine:" + value;
        }
    }

    private static class ChildrenIndented {
        private final boolean value;

        private ChildrenIndented(boolean value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "childrenIndented:" + value;
        }
    }

    private static class ChildrenSeparator {
        private final String value;

        private ChildrenSeparator(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "childrenSeparator:" + value;
        }
    }

}
