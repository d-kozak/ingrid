package premun.mps.ingrid.formatter.utils;

import premun.mps.ingrid.formatter.model.FormatInfo;
import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.model.LiteralRule;

import java.util.Map;

import static java.util.stream.Collectors.joining;

/**
 * Helper class to convert the formatInfo map into FormatInfoDSL used in testing so that it is not necessary to do this rewrite manually
 *
 * @author dkozak
 */
public class FormatInfoMapToDSLConvertor {

    /**
     * Convert the formatInfo map into FormatInfoDSL used in testing so that it is not necessary to do this rewrite manually
     *
     * @param formatInfoMap map to convert
     * @return string with code in the FormatInfoDSL
     */
    public static String covert(Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap) {
        return "verifyFormatInfoMap(\n" +
                "\tformatInfoMap,\n" +
                "\trules(\n" +
                convertRule(formatInfoMap) +
                "\n\t)\n" +
                ");";
    }

    private static String convertRule(Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap) {
        return formatInfoMap.entrySet()
                            .stream()
                            .map(FormatInfoMapToDSLConvertor::convertRule)
                            .collect(joining(",\n"));
    }

    private static String convertRule(Map.Entry<Pair<String, Integer>, RuleFormatInfo> entry) {
        return "\t\trule(\"" + entry.getKey().first + "\", " + entry.getKey().second + ",\n\t\t\t handle(\n" + convertElems(entry.getValue()) + "\n\t\t\t)\n\t\t)";
    }

    private static String convertElems(RuleFormatInfo ruleFormatInfo) {
        return ruleFormatInfo.formatInfoList.stream()
                                            .map(FormatInfoMapToDSLConvertor::converFormatInfo)
                                            .collect(joining(",\n"));
    }

    private static String converFormatInfo(FormatInfo it) {
        String ruleName;
        if (it.rule instanceof LiteralRule) {
            ruleName = ((LiteralRule) it.rule).value;
        } else {
            ruleName = it.rule.name;
        }
        return "\t\t\t\telem(\"" + ruleName + "\", newLine(" + it.appendNewLine + "),space(" + it.appendSpace + "),childrenOnNewLine(" + it.childrenOnNewLine + "),childrenIndented(" + it.childrenIndented + "))";
    }

}
