package premun.mps.ingrid.formatter.utils;

import premun.mps.ingrid.formatter.model.FormatInfo;
import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.model.LiteralRule;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static premun.mps.ingrid.formatter.utils.Pair.pair;

public class FormatInfoAsserts {

    public static void verifyFormatInfoMap(Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap, List<FormatInfoDSL.AppliedRule> expectedRules) {
        if (expectedRules.size() != formatInfoMap.size()) {
            failBecauseOfInvalidNumberOfRules(formatInfoMap, expectedRules);
        }
        for (FormatInfoDSL.AppliedRule expectedRule : expectedRules) {
            RuleFormatInfo ruleFormatInfo = formatInfoMap.get(pair(expectedRule.ruleName, expectedRule.alternativeIndex));
            if (ruleFormatInfo == null) {
                throw new AssertionError(expectedRule.ruleName + ":" + expectedRule.alternativeIndex + " was not found in " + formatInfoMap);
            }
            try {
                assertFormattingIsEqual(expectedRule.expectedFormatting, ruleFormatInfo.formatInfoList);
            } catch (AssertionError ex) {
                throw new AssertionError("When verifying\n " + expectedRule + "\n against \n" + ruleFormatInfo + "\n" + ex.getMessage());
            }
        }
    }

    private static void failBecauseOfInvalidNumberOfRules(Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap, List<FormatInfoDSL.AppliedRule> expectedRules) {
        if (formatInfoMap.size() > expectedRules.size()) {
            HashMap<Pair<String, Integer>, RuleFormatInfo> copy = new HashMap<>(formatInfoMap);
            for (FormatInfoDSL.AppliedRule expectedRule : expectedRules) {
                copy.remove(pair(expectedRule.ruleName, expectedRule.alternativeIndex));
            }
            throw new AssertionError("Some unexpected rules found in the format info map: " + copy.entrySet()
                                                                                                  .stream()
                                                                                                  .map(
                                                                                                          entry -> entry.getKey().first + ":" + entry.getKey().second + " => " + entry.getValue()
                                                                                                  )
                                                                                                  .collect(Collectors.joining(",")));
        } else if (formatInfoMap.size() < expectedRules.size()) {
            ArrayList<FormatInfoDSL.AppliedRule> copy = new ArrayList<>(expectedRules);
            for (Pair<String, Integer> pair : formatInfoMap.keySet()) {
                copy.removeIf(
                        appliedRule -> appliedRule.ruleName.equals(pair.first) && appliedRule.alternativeIndex == pair.second
                );
            }
            throw new AssertionError("Some of expected rules were not found in the format info map: " + copy);
        } else {
            throw new IllegalArgumentException("The size of formatInfoMap and expectedRules should be different");
        }
    }

    private static void assertFormattingIsEqual(List<FormatInfoDSL.AppliedRuleReference> expectedFormatting, List<FormatInfo> actual) {
        assertEquals(expectedFormatting.size(), actual.size());

        for (int i = 0; i < expectedFormatting.size(); i++) {
            assertFormattingIsEqual(expectedFormatting.get(i), actual.get(i));
        }
    }

    public static void assertFormattingIsEqual(FormatInfoDSL.AppliedRuleReference expected, FormatInfo actual) {
        if (actual.rule instanceof LiteralRule)
            assertEqualsRuleName(expected.ruleName, ((LiteralRule) actual.rule).value);
        else
            assertEqualsRuleName(expected.ruleName, actual.rule.name);
        assertEqualsWithBetterMessage(expected.formatInfo.appendNewLine, actual.appendNewLine, expected, "newLine");
        assertEqualsWithBetterMessage(expected.formatInfo.appendSpace, actual.appendSpace, expected, "space");
        assertEqualsWithBetterMessage(expected.formatInfo.childrenOnNewLine, actual.childrenOnNewLine, expected, "childrenOnNewLine");
        assertEqualsWithBetterMessage(expected.formatInfo.childrenIndented, actual.childrenIndented, expected, "childrenIndented");
        assertEqualsWithBetterMessage(expected.formatInfo.childrenSeparator, actual.childrenSeparator, expected, "childrenSeparator");
    }

    private static void assertEqualsRuleName(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("Unexpected rule reference found, expected : '" + expected + "', actual: '" + actual + "'");
        }
    }

    private static void assertEqualsWithBetterMessage(Object expected, Object actual, FormatInfoDSL.AppliedRuleReference appliedRuleReference, String fieldName) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError("Expected: '" + expected + "' vs actual: '" + actual + "' in rule '" + appliedRuleReference.ruleName + "' on field " + fieldName);
        }
    }
}
