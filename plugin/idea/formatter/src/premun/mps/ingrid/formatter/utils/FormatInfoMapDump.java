package premun.mps.ingrid.formatter.utils;

import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.ParserRule;

import java.util.List;
import java.util.Map;

/**
 * Contains debug method for printing formatInfoMap
 *
 * @author dkozak
 */
public class FormatInfoMapDump {

    /**
     * Debug method to print the content of formatInfoMap
     *
     * @param formatInfoMap to be printed
     */
    public static void dump(Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> formatInfoMap) {
        formatInfoMap.entrySet()
                     .stream()
                     .map(
                             entry -> entry.getKey().first.name + ":" + entry.getKey().first.alternatives.indexOf(entry.getKey().second) + " => " + entry.getValue()
                     )
                     .forEach(System.out::println);
    }


    /**
     * Debug method to print the content of formatInfoMap
     *
     * @param formatInfoMap to be printed
     */
    public static void dumpSimplifiedMap(Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap) {
        formatInfoMap.entrySet()
                     .stream()
                     .map(
                             entry -> entry.getKey().first + ":" + entry.getKey().second + " => " + entry.getValue()
                     )
                     .forEach(System.out::println);
    }
}
