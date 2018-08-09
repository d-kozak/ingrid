package premun.mps.ingrid.formatter.utils;

import premun.mps.ingrid.formatter.model.FormatInfo;
import premun.mps.ingrid.formatter.model.RuleFormatInfo;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static premun.mps.ingrid.formatter.utils.Pair.pair;

public class FormatInfoAsserts {

    public static void verifyFormatInfoMap(Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap, List<FormatInfoDSL.AppliedRule> expectedRules) {
        assertEquals(expectedRules.size(), formatInfoMap.size());
        for (FormatInfoDSL.AppliedRule expectedRule : expectedRules) {
            RuleFormatInfo ruleFormatInfo = formatInfoMap.get(pair(expectedRule.ruleName, expectedRule.alternativeIndex));
            if (ruleFormatInfo == null) {
                throw new AssertionError(expectedRule.ruleName + ":" + expectedRule.alternativeIndex + " was not found in " + formatInfoMap);
            }
            assertFormattingIsEqual(expectedRule.expectedFormatting, ruleFormatInfo.formatInfoList);
        }
    }

    private static void assertFormattingIsEqual(List<FormatInfo> expectedFormatting, List<FormatInfo> actual) {
        assertEquals(expectedFormatting.size(), actual.size());

        for (int i = 0; i < expectedFormatting.size(); i++) {
            assertFormattingIsEqual(expectedFormatting.get(0), actual.get(0));
        }
    }

    public static void assertFormattingIsEqual(FormatInfo expected, FormatInfo actual) {
        assertEquals(expected.appendNewLine, actual.appendNewLine);
        assertEquals(expected.appendSpace, actual.appendSpace);
        assertEquals(expected.childrenOnNewLine, actual.childrenOnNewLine);
        assertEquals(expected.childrenIndented, actual.childrenIndented);
    }
}
