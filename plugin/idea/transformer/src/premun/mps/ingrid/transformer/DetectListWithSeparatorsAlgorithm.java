package premun.mps.ingrid.transformer;

import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.formatter.utils.Pair;
import premun.mps.ingrid.model.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                for (int i = 0; i < alternative.elements.size() - 1; i++) {
                    RuleReference current = alternative.elements.get(i);
                    RuleReference next = alternative.elements.get(i + 1);
                    boolean mightBeNextElements = next.quantity == Quantity.ANY && next.rule instanceof ParserRule && ((ParserRule) next.rule).alternatives.size() == 1;
                    if (mightBeNextElements) {
                        Alternative nextAlternative = ((ParserRule) next.rule).alternatives.get(0);
                        if (nextAlternative.elements.size() == 2 && nextAlternative.elements.get(0).rule instanceof LiteralRule && current.rule.equals(nextAlternative.elements.get(1).rule)) {
                            System.out.println("found list of " + current.rule.name + " with separator " + ((LiteralRule) nextAlternative.elements.get(0).rule).value);
                            alternative.elements.clear();
                            alternative.elements.add(current);
                        }
                    }
                }
            }
        }
    }
}
