package premun.mps.ingrid.formatter.utils;

import premun.mps.ingrid.model.*;
import premun.mps.ingrid.model.format.FormatInfo;
import premun.mps.ingrid.model.format.SimpleFormatInfo;
import premun.mps.ingrid.model.utils.Pair;

import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * Helper class to convert grammar info into FormatInfoDSL used in testing so that it is not necessary to do this rewrite manually
 *
 * @author dkozak
 */
public class FormatInfoMapToDSLConvertor {

    /**
     * Converts the formatting saved in grammar info into FormatInfoDSL used in testing so that it is not necessary to do this rewrite manually
     * and prints it to stdout
     *
     * @param grammarInfo source of the formatting info
     */
    public static void print(GrammarInfo grammarInfo) {
        System.out.println(convert(grammarInfo));
    }

    /**
     * Convert the formatting saved in grammar info into FormatInfoDSL used in testing so that it is not necessary to do this rewrite manually
     *
     * @param grammarInfo source of the formatting info
     * @return string with code in the FormatInfoDSL
     */
    public static String convert(GrammarInfo grammarInfo) {
        return "verifyFormatInfo(\n" +
                "\tgrammarInfo,\n" +
                "\trules(\n" +
                convertGrammar(grammarInfo) +
                "\n\t)\n" +
                ");";
    }

    private static String convertGrammar(GrammarInfo grammarInfo) {
        return grammarInfo.getParserRulesWithAlternatives()
                          .stream()
                          .filter(pair -> pair.second.elements.stream()
                                                              .noneMatch(ruleReference -> ((SimpleFormatInfo) ruleReference.formatInfo).isUnknown()))
                          .map(FormatInfoMapToDSLConvertor::convertParserRules)
                          .collect(joining(",\n"));
    }

    private static String convertParserRules(Pair<ParserRule, Alternative> pair) {
        return "\t\trule(\"" + pair.first.name + "\", " + pair.first.alternatives.indexOf(pair.second) + ",\n\t\t\t handle(\n" + convertElems(pair.second.elements) + "\n\t\t\t)\n\t\t)";
    }

    private static String convertElems(List<RuleReference> ruleReferences) {
        return ruleReferences.stream()
                             .map(FormatInfoMapToDSLConvertor::converFormatInfo)
                             .collect(joining(",\n"));
    }

    private static String converFormatInfo(RuleReference ruleReference) {
        String ruleName;
        if (ruleReference.rule instanceof LiteralRule) {
            ruleName = ((LiteralRule) ruleReference.rule).value;
        } else {
            ruleName = ruleReference.rule.name;
        }
        FormatInfo formatInfo = ruleReference.formatInfo;
        if (ruleReference.quantity == Quantity.ANY || ruleReference.quantity == Quantity.AT_LEAST_ONE)
            return "\t\t\t\tcollection(\"" + ruleName + "\", newLine(" + formatInfo.appendNewLine() + "),space(" + formatInfo.appendSpace() + "),childrenOnNewLine(" + formatInfo.areChildrenOnNewLine() + "),childrenIndented(" + formatInfo.areChildrenIndented() + "),childrenSeparator(" + (formatInfo.getChildrenSeparator() != null ? "\"" + formatInfo.getChildrenSeparator() + "\"" : null) + "))";
        else
            return "\t\t\t\telement(\"" + ruleName + "\", newLine(" + formatInfo.appendNewLine() + "),space(" + formatInfo.appendSpace() + "))";
    }
}
