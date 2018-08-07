package premun.mps.ingrid.formatter.utils;

import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.ParserRule;

import java.util.List;
import java.util.Map;

public class FormatInfoMapDump {

    public static void dump(Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> formatInfoMap) {
        formatInfoMap.entrySet()
                     .stream()
                     .map(
                             entry -> entry.getKey().first.name + ":" + entry.getKey().first.alternatives.indexOf(entry.getKey().second) + " => " + entry.getValue()
                     )
                     .forEach(System.out::println);
    }
}
