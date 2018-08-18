package premun.mps.ingrid.transformer;

import premun.mps.ingrid.model.*;
import premun.mps.ingrid.model.format.FormatInfo;
import premun.mps.ingrid.model.format.SimpleFormatInfo;

/**
 * Detects statements like arg (',' arg)* and turns them into arg* with separator ','
 *
 * @author dkozak
 */
public class DetectListWithSeparatorsAlgorithm implements MpsSpecificGrammarTransformation {

    @Override
    public GrammarInfo transform(GrammarInfo grammarInfo) {
        for (ParserRule parserRule : grammarInfo.getParserRules()) {
            for (Alternative alternative : parserRule.alternatives) {
                for (int ruleReferenceIndex = 0; ruleReferenceIndex < alternative.elements.size() - 1; ruleReferenceIndex++) {
                    RuleReference current = alternative.elements.get(ruleReferenceIndex);
                    RuleReference next = alternative.elements.get(ruleReferenceIndex + 1);
                    boolean mightBeNextElements = next.quantity == Quantity.ANY && next.rule instanceof ParserRule && ((ParserRule) next.rule).alternatives.size() == 1;
                    if (mightBeNextElements) {
                        Alternative nextAlternative = ((ParserRule) next.rule).alternatives.get(0);
                        RuleReference afterSeparatorRuleReference = nextAlternative.elements.get(1);
                        RuleReference separatorRuleReference = nextAlternative.elements.get(0);
                        if (nextAlternative.elements.size() == 2 && separatorRuleReference.rule instanceof LiteralRule && current.rule.equals(afterSeparatorRuleReference.rule)) {
                            String separator = ((LiteralRule) separatorRuleReference.rule).value;
                            System.out.println("found list of " + current.rule.name + " with separator " + separator);

                            FormatInfo beforeSeparatorFormatInfo = current.formatInfo;
                            FormatInfo separatorFormatInfo = separatorRuleReference.formatInfo;
                            FormatInfo afterSeparatorFormatInfo = afterSeparatorRuleReference.formatInfo;


                            alternative.elements.clear();
                            RuleReference ruleReference = new RuleReference(current.rule, Quantity.ANY);

                            ruleReference.formatInfo = new SimpleFormatInfo(
                                    false, // the only element - no need for new line
                                    true,  // the only element - space is necessary for MPS editor to work
                                    separatorFormatInfo.appendNewLine(),  // if there is space after separator, then element should be on new lines
                                    afterSeparatorFormatInfo.areChildrenIndented(), // children identation is just passed from afterSeparatorFormatInfo
                                    separator
                            );

                            alternative.elements.add(ruleReference);
                        }
                    }
                }
            }
        }
        return grammarInfo;
    }
}
