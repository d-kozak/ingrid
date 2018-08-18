package premun.mps.ingrid.formatter.utils;

import premun.mps.ingrid.model.*;
import premun.mps.ingrid.model.format.FormatInfo;

import java.util.List;

import static java.util.stream.Collectors.joining;
import static premun.mps.ingrid.formatter.utils.Pair.pair;

/**
 * Helper class to convert grammar info into FormatInfoDSL used in testing so that it is not necessary to do this rewrite manually
 *
 * @author dkozak
 */
public class FormatInfoMapToDSLConvertor {

    /**
     * Convert the formatting saved in grammar info into FormatInfoDSL used in testing so that it is not necessary to do this rewrite manually
     *
     * @param grammarInfo source of the formatting info
     * @return string with code in the FormatInfoDSL
     */
    public static String covert(GrammarInfo grammarInfo) {
        return "verifyFormatInfo(\n" +
                "\tgrammarInfo,\n" +
                "\trules(\n" +
                convertGrammar(grammarInfo) +
                "\n\t)\n" +
                ");";
    }

    private static String convertGrammar(GrammarInfo grammarInfo) {
        return grammarInfo.getParserRules()
                          .stream()
                          .flatMap(parserRule -> parserRule.alternatives.stream()
                                                                        .map(alternative -> pair(parserRule, alternative)))
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
            return "\t\t\t\tcollection(\"" + ruleName + "\", newLine(" + formatInfo.appendNewLine() + "),space(" + formatInfo.appendSpace() + "),childrenOnNewLine(" + formatInfo.areChildrenOnNewLine() + "),childrenIndented(" + formatInfo.areChildrenIndented() + "),childrenSeparator(" + formatInfo.getChildrenSeparator() + "))";
        else
            return "\t\t\t\telement(\"" + ruleName + "\", newLine(" + formatInfo.appendNewLine() + "),space(" + formatInfo.appendSpace() + "))";
    }
}
