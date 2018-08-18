package premun.mps.ingrid.formatter.utils;

import premun.mps.ingrid.model.GrammarInfo;

import java.util.stream.Collectors;

/**
 * Contains debug method for printing the formatting information
 *
 * @author dkozak
 */
public class FormatInfoDump {

    /**
     * Debug method to print the formatting in GrammarInfo
     *
     * @param grammarInfo source of the formatting information
     */
    public static void dumpFormatting(GrammarInfo grammarInfo) {
        grammarInfo.getParserRulesWithAlternatives()
                   .stream()
                   .map(
                           pair -> pair.first.name + ":" + pair.first.alternatives.indexOf(pair.second) + " => " +
                                   pair.second.elements.stream()
                                                       .map(ruleReference -> ruleReference.formatInfo.toString())
                                                       .collect(Collectors.joining(",")))
                   .forEach(System.out::println);
    }
}
