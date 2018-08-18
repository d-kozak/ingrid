package premun.mps.ingrid.formatter.utils;

import org.junit.Assert;
import premun.mps.ingrid.model.*;
import premun.mps.ingrid.model.format.FormatInfo;
import premun.mps.ingrid.model.utils.Pair;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;

/**
 * Contains asserts about the formatting informatin saved in grammar info
 *
 * @author dkozak
 */
public class FormatInfoAsserts {

    /**
     * Verifies that the formatting in grammar info is according to expectedRules
     *
     * @param grammarInfo   source of the formatting information
     * @param expectedRules expectations
     */
    public static void verifyFormatInfo(GrammarInfo grammarInfo, List<FormatInfoDSL.AppliedRule> expectedRules) {
        List<Pair<ParserRule, Alternative>> parserRulesWithAlternatives = grammarInfo.getParserRulesWithAlternatives();
        if (expectedRules.size() > parserRulesWithAlternatives.size()) {
            failBecauseNotEnoughInformation(parserRulesWithAlternatives, expectedRules);
        }
        for (FormatInfoDSL.AppliedRule expectedRule : expectedRules) {
            Alternative alternative = grammarInfo.getAlternative(expectedRule.ruleName, expectedRule.alternativeIndex);
            try {
                assertFormattingIsEqual(expectedRule.expectedFormatting, alternative.elements);
            } catch (AssertionError ex) {
                throw new AssertionError("When verifying\n " + expectedRule + "\n against \n" + alternative.elements + "\n" + ex.getMessage());
            }
        }
    }

    private static void failBecauseNotEnoughInformation(List<Pair<ParserRule, Alternative>> parserRulesWithAlternatives, List<FormatInfoDSL.AppliedRule> expectedRules) {
        Set<String> parserRuleNames = parserRulesWithAlternatives.stream()
                                                                 .map(rule -> rule.first.name + ":" + rule.first.alternatives.indexOf(rule.second))
                                                                 .collect(toSet());
        String extraRules = expectedRules.stream()
                                         .map(appliedRule -> appliedRule.ruleName + ":" + appliedRule.alternativeIndex)
                                         .filter(ruleName -> !parserRuleNames.contains(ruleName))
                                         .collect(joining(","));

        Assert.fail("Formatting for rules '" + extraRules + "' was not extracted");
    }

    private static void assertFormattingIsEqual(List<FormatInfoDSL.AppliedRuleReference> expectedFormatting, List<RuleReference> actual) {
        assertEquals(expectedFormatting.size(), actual.size());

        for (int i = 0; i < expectedFormatting.size(); i++) {
            assertFormattingIsEqual(expectedFormatting.get(i), actual.get(i));
        }
    }

    public static void assertFormattingIsEqual(FormatInfoDSL.AppliedRuleReference expected, RuleReference actual) {
        FormatInfo formatInfo = Objects.requireNonNull(actual.formatInfo, () -> "Format info not set in " + actual);
        if (actual.rule instanceof LiteralRule)
            assertEqualsRuleName(expected.ruleName, ((LiteralRule) actual.rule).value);
        else
            assertEqualsRuleName(expected.ruleName, actual.rule.name);
        assertEqualsWithBetterMessage(expected.formatInfo.appendNewLine(), formatInfo.appendNewLine(), expected, "newLine");
        assertEqualsWithBetterMessage(expected.formatInfo.appendSpace(), formatInfo.appendSpace(), expected, "space");
        assertEqualsWithBetterMessage(expected.formatInfo.areChildrenOnNewLine(), formatInfo.areChildrenOnNewLine(), expected, "childrenOnNewLine");
        assertEqualsWithBetterMessage(expected.formatInfo.areChildrenIndented(), formatInfo.areChildrenIndented(), expected, "childrenIndented");
        assertEqualsWithBetterMessage(expected.formatInfo.getChildrenSeparator(), formatInfo.getChildrenSeparator(), expected, "childrenSeparator");
    }

    private static void assertEqualsRuleName(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("Unexpected rule reference found, expected : '" + expected + "', actual: '" + actual + "'");
        }
    }

    private static void assertEqualsWithBetterMessage(Object expected, Object actual, FormatInfoDSL.AppliedRuleReference appliedRuleReference, String fieldName) {
        if (!Objects.equals(expected, actual)) {
            Assert.fail("Expected: '" + expected + "' vs actual: '" + actual + "' in rule '" + appliedRuleReference.ruleName + "' on field " + fieldName);
        }
    }
}
