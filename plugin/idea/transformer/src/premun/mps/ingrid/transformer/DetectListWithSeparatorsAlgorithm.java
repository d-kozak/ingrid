package premun.mps.ingrid.transformer;

import premun.mps.ingrid.formatter.model.FormatInfo;
import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.formatter.utils.Pair;
import premun.mps.ingrid.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static premun.mps.ingrid.formatter.utils.Pair.pair;

/**
 * Detects statements like arg (',' arg)* and turns them into arg* with separator ','
 *
 * @author dkozak
 */
public class DetectListWithSeparatorsAlgorithm implements MpsSpecificGrammarTransformation {

    @Override
    public void transform(GrammarInfo grammarInfo, Map<Pair<ParserRule, Alternative>, RuleFormatInfo> formatInfoMap) {
        List<ParserRule> parserRules = grammarInfo.rules.values()
                                                        .stream()
                                                        .filter(it -> it instanceof ParserRule)
                                                        .map(it -> (ParserRule) it)
                                                        .collect(Collectors.toList());

        for (ParserRule parserRule : parserRules) {
            for (Alternative alternative : parserRule.alternatives) {
                RuleFormatInfo ruleFormatInfo = formatInfoMap.computeIfAbsent(pair(parserRule, alternative), __ -> new RuleFormatInfo(new ArrayList<>()));
                for (int i = 0; i < alternative.elements.size() - 1; i++) {
                    RuleReference current = alternative.elements.get(i);
                    RuleReference next = alternative.elements.get(i + 1);
                    boolean mightBeNextElements = next.quantity == Quantity.ANY && next.rule instanceof ParserRule && ((ParserRule) next.rule).alternatives.size() == 1;
                    if (mightBeNextElements) {
                        Alternative nextAlternative = ((ParserRule) next.rule).alternatives.get(0);
                        if (nextAlternative.elements.size() == 2 && nextAlternative.elements.get(0).rule instanceof LiteralRule && current.rule.equals(nextAlternative.elements.get(1).rule)) {
                            String separator = ((LiteralRule) nextAlternative.elements.get(0).rule).value;
                            System.out.println("found list of " + current.rule.name + " with separator " + separator);
                            alternative.elements.clear();
                            current.quantity = Quantity.ANY;
                            alternative.elements.add(current);

                            FormatInfo currentFormatInfo = i < ruleFormatInfo.formatInfoList.size() ? ruleFormatInfo.formatInfoList.get(i) : new FormatInfo(current.rule, false, false, false, false);
                            ruleFormatInfo.formatInfoList.clear();
                            ruleFormatInfo.formatInfoList.add(
                                    new FormatInfo(currentFormatInfo.rule,
                                            currentFormatInfo.appendNewLine,
                                            currentFormatInfo.appendSpace,
                                            currentFormatInfo.childrenOnNewLine,
                                            currentFormatInfo.childrenIndented,
                                            separator)
                            );
                        }
                    }
                }
            }
        }
    }
}
