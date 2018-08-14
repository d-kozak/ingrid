package premun.mps.ingrid.serialization;

import premun.mps.ingrid.model.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

/**
 * Serializes GrammarInfo aka Ingrid Model back into Antlr4 grammar. This step
 * is useful for rewrites on the grammar that should be done before the parser of the input
 * language is generated from it, so that the parse tree returned from the parser
 * corresponds with the Ingrid MOdel. If we first generated the parser and then tweaked the grammar,
 * the parse tree would be different.
 *
 * @author dkozak
 */
public class GrammarSerializer {

    public static String serializeGrammar(GrammarInfo grammarInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("grammar ")
                     .append(grammarInfo.name)
                     .append(";")
                     .append("\n\n");

        Rule rootRule = grammarInfo.rules.get(grammarInfo.rootRule.name);
        HashMap<String, Rule> copy = new LinkedHashMap<>(grammarInfo.rules);
        copy.remove(rootRule.name);

        stringBuilder.append(serializeRule(rootRule));

        stringBuilder.append(copy.values()
                                 .stream()
                                 .map(GrammarSerializer::serializeRule)
                                 .collect(joining("\n")));

        return stringBuilder.toString();
    }

    private static String serializeRule(Rule rule) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(rule.name)
                     .append("\n\t : ");

        if (rule instanceof FlatLexerRule) {
            stringBuilder.append(serializeLexerRule((FlatLexerRule) rule));
        } else if (rule instanceof ParserRule) {
            stringBuilder.append(serializeParserRule(((ParserRule) rule)));
        } else {
            throw new IllegalArgumentException("Unsupported type of rule: " + rule.getClass()
                                                                                  .getName());
        }

        // small hack to handle whitespace properly
        if ("WS".equals(rule.name)) {
            stringBuilder.append(" -> skip ");
        }

        return stringBuilder.append("\n")
                            .append("\t")
                            .append(" ;\n\n")
                            .toString();
    }

    private static String serializeParserRule(ParserRule rule) {
        return rule.alternatives.stream()
                                .map(GrammarSerializer::serializeAlternative)
                                .collect(Collectors.joining("\n\t | "));
    }

    private static String serializeAlternative(Alternative alternative) {
        return alternative.elements.stream()
                                   .map(GrammarSerializer::serializeRuleReference)
                                   .collect(joining(" "));
    }

    private static String serializeRuleReference(RuleReference ruleReference) {
        if (ruleReference.rule instanceof LiteralRule) {
            return "'" + ((LiteralRule) ruleReference.rule).value + "'" + ruleReference.quantity;
        }
        return ruleReference.rule.name + ruleReference.quantity;
    }

    private static String serializeLexerRule(FlatLexerRule rule) {
        if (rule instanceof LiteralRule) {
            return "'" + ((LiteralRule) rule).value + "'";
        } else if (rule instanceof RegexRule) {
            return RegexSerializer.serializeRegex(((RegexRule) rule).regexp);
        } else {
            throw new IllegalArgumentException("Unsupported type of rule: " + rule.getClass()
                                                                                  .getName());
        }
    }
}
