package premun.mps.ingrid.transformer;

import premun.mps.ingrid.model.*;
import premun.mps.ingrid.model.format.SimpleFormatInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Especially after DetectListWithSeparatorsAlgorithm we might end up with rules like literal* or regex*,
 * which cannot be expressed in MPS, because MPS only allows 0..n etc on references to children. Therefore to handle these situations,
 * new rules have to be introduced to wrap there literals or regexes.
 *
 * @author dkozak
 * @see DetectListWithSeparatorsAlgorithm
 */
public class FlatReferencesWithQuantifiesAlgorithm implements MpsSpecificGrammarTransformation {

    private static final Logger log = Logger.getLogger(FlatReferencesWithQuantifiesAlgorithm.class.getName());

    @Override
    public GrammarInfo transform(GrammarInfo grammarInfo) {
        for (Alternative alternative : grammarInfo.getParserRules()
                                                  .stream()
                                                  .flatMap(parserRule -> parserRule.alternatives.stream())
                                                  .collect(toList())) {
            List<RuleReference> newElements = new ArrayList<>();
            for (RuleReference ruleReference : alternative.elements) {
                if (ruleReference.rule instanceof FlatLexerRule && ruleReference.quantity != Quantity.EXACTLY_ONE) {
                    String newRuleName = generateUniqueName(ruleReference.rule.name, grammarInfo.rules.keySet());
                    ParserRule newRule = new ParserRule(newRuleName);
                    newRule.alternatives.add(
                            new Alternative(
                                    Collections.singletonList(
                                            new RuleReference(ruleReference.rule, Quantity.EXACTLY_ONE, SimpleFormatInfo.DEFAULT)
                                    )
                            )
                    );
                    grammarInfo.rules.put(newRuleName, newRule);
                    RuleReference newRuleReference = new RuleReference(newRule, ruleReference.quantity);
                    newRuleReference.formatInfo = ruleReference.formatInfo;
                    log.info("Created new rule named " + newRuleReference.rule.name + " referenced with " + newRuleReference);
                    newElements.add(newRuleReference);
                } else {
                    newElements.add(ruleReference);
                }
            }
            alternative.elements = newElements;
        }
        return grammarInfo;
    }

    private String generateUniqueName(String innerRuleName, Set<String> names) {
        names = names.stream()
                     .map(String::toLowerCase)
                     .collect(Collectors.toSet());
        String lowerCaseName = innerRuleName.toLowerCase();
        if (!names.contains(lowerCaseName)) {
            return lowerCaseName;
        }
        int count = 1;
        lowerCaseName += "_";
        while (true) {
            String newName = lowerCaseName + count;
            if (!names.contains(newName)) {
                return newName;
            }
            count++;
        }
    }
}
