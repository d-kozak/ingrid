package premun.mps.ingrid.transformer;

import premun.mps.ingrid.model.*;
import premun.mps.ingrid.model.format.SimpleFormatInfo;

import java.util.*;
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

    /**
     * Sometimes we can guess the for name of the rule by looking at its value.
     * In order to prevent ugly concept names, we first try to check whether it is not a
     * literal, whose name can be easily extrapolated.
     */
    private static Map<String, String> usefulNames = new HashMap<>();

    static {
        usefulNames.put("{", "LeftBracket");
        usefulNames.put("}", "RightBracket");
        usefulNames.put("(", "LeftParenthesis");
        usefulNames.put(")", "RightParenthesis");
        usefulNames.put(",", "Comma");
        usefulNames.put(";", "Semicolon");
        usefulNames.put(":", "Colon");
        usefulNames.put("::", "DoubleColon");
        usefulNames.put("+", "Plus");
        usefulNames.put("-", "Minus");
        usefulNames.put("*", "Multiply");
        usefulNames.put("/", "Divide");
        usefulNames.put("?", "QuestionMark");
    }


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
                    String newRuleName = generateUniqueName(ruleReference.rule, grammarInfo.rules.keySet());
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

    private String generateUniqueName(Rule rule, Set<String> names) {
        String innerRuleName = rule.name;

        if (rule instanceof LiteralRule && !innerRuleName.matches("[a-zA-Z[_]][a-zA-Z0-9$[_]]*")) {
            String ruleValue = ((LiteralRule) rule).value;
            boolean isValidMpsConceptName = ruleValue.matches("[a-zA-Z[_]][a-zA-Z0-9$[_]]*");
            if (isValidMpsConceptName) {
                innerRuleName = ruleValue;
            } else {
                // the rule has no name specified in the grammar, but we have to create a new concept for it to express the cardinality
                // therefore we will give it a generated name
                innerRuleName = usefulNames.get(ruleValue);
                if (innerRuleName == null) {
                    innerRuleName = "LITERAL_WRAPPER";
                }
            }
        }

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
