package premun.mps.ingrid.serialization;

import premun.mps.ingrid.model.*;
import premun.mps.ingrid.parser.ParserResult;

import java.util.HashMap;

import static java.util.stream.Collectors.joining;

public class GrammarSerializer {

    public static String serialize(ParserResult parserResult) {
        String name = parserResult.grammarName;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("grammar ")
                     .append(name)
                     .append(";\n\n");

        Rule rootRule = parserResult.rules.get(parserResult.rootRule);
        HashMap<String, Rule> copy = new HashMap<>(parserResult.rules);
        copy.remove(parserResult.rootRule);

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

        if (rule instanceof LexerRule) {
            stringBuilder.append(((LexerRule) rule).alternatives.stream()
                                                                .map(
                                                                        list -> list.stream()
                                                                                    .map(it -> {
                                                                                        if (it instanceof LiteralRule)
                                                                                            return "'" + ((LiteralRule) it).value + "'";
                                                                                        else if (it instanceof RegexRule)
                                                                                            return ((RegexRule) it).regexp;
                                                                                        else return it.name;
                                                                                    })
                                                                                    .collect(joining(" ")))
                                                                .collect(joining("|")));
        } else if (rule instanceof ParserRule) {
            stringBuilder.append(
                    ((ParserRule) rule).alternatives.stream()
                                                    .map(GrammarSerializer::serializeAlternative)
                                                    .collect(joining("\n\t | "))
            );
        } else if (rule instanceof RegexRule) {
            stringBuilder.append(
                    ((RegexRule) rule).regexp
            );
        } else if (rule instanceof LiteralRule) {
            stringBuilder.append("'")
                         .append(((LiteralRule) rule).value)
                         .append("'");
        } else {
            throw new IllegalArgumentException("Invalid type of rule, don't know how to handle: " + rule.getClass()
                                                                                                        .getName());
        }

        return stringBuilder
                .append("\n\t ;\n\n")
                .toString();
    }

    private static String serializeAlternative(Alternative alternative) {
        return alternative.elements.stream()
                                   .map(GrammarSerializer::serializeRuleReference)
                                   .collect(joining(" "));
    }

    private static String serializeRuleReference(RuleReference ruleReference) {
        if (ruleReference.rule instanceof LiteralRule)
            return "'" + ((LiteralRule) ruleReference.rule).value + "'" + ruleReference.quantity;
        return ruleReference.rule.name + ruleReference.quantity;
    }
}
