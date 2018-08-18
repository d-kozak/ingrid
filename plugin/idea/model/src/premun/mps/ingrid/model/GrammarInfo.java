package premun.mps.ingrid.model;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class GrammarInfo {
    public String name;
    public Map<String, Rule> rules = new LinkedHashMap<>();
    public Rule rootRule = null;

    public GrammarInfo(String name) {
        this.name = name;
    }

    /**
     * Copy constructor, creates new deep copy of the grammar info object
     */
    public GrammarInfo(GrammarInfo oldGrammarInfo) {
        this.name = oldGrammarInfo.name;
        this.rules = oldGrammarInfo.rules.values()
                                         .stream()
                                         .map(rule -> {
                                             if (rule instanceof ParserRule) {
                                                 ParserRule parserRule = new ParserRule(rule.name);
                                                 parserRule.alternatives = new ArrayList<>(((ParserRule) rule).alternatives);
                                                 return parserRule;
                                             } else if (rule instanceof LiteralRule) {
                                                 return new LiteralRule(rule.name, ((LiteralRule) rule).value);
                                             } else if (rule instanceof RegexRule) {
                                                 return new RegexRule(rule.name, ((RegexRule) rule).regexp);
                                             } else
                                                 throw new IllegalArgumentException("Unknown rule type " + rule.getClass()
                                                                                                               .getName());
                                         })
                                         .collect(toMap(
                                                 rule -> rule.name,
                                                 rule -> rule
                                         ));


        for (Rule rule : this.rules.values()) {
            if (rule instanceof ParserRule) {
                ParserRule parserRule = ((ParserRule) rule);
                for (Alternative alternative : parserRule.alternatives) {
                    alternative.elements = alternative.elements.stream()
                                                               .map(oldReference -> {
                                                                   Rule referencedRule = this.rules.get(oldReference.rule.name);
                                                                   if (referencedRule == null) {
                                                                       throw new IllegalStateException("Rule " + oldReference.rule.name + " has to be in" + this.rules + " at this point");
                                                                   }
                                                                   return new RuleReference(referencedRule, oldReference.quantity);
                                                               })
                                                               .collect(toList());
                }
            }
        }
        this.rootRule = this.rules.get(oldGrammarInfo.rootRule.name);
        if (this.rootRule == null) {
            throw new IllegalStateException("Could not lookup the copied root rule " + oldGrammarInfo.rootRule.name);
        }

    }

    public Alternative getAlternative(String ruleName, int alternativeIndex) {
        Rule rule = Objects.requireNonNull(this.rules.get(ruleName), () -> "Rule " + ruleName + " not found");
        if (!(rule instanceof ParserRule)) {
            throw new IllegalArgumentException("Specified rule " + rule.name + " is not a parser rule: " + rule);
        }
        ParserRule parserRule = (ParserRule) rule;
        if (alternativeIndex >= parserRule.alternatives.size()) {
            // custom bounds check for better error message
            throw new IllegalArgumentException("Index " + alternativeIndex + " is outside of bounds of the list of alternatives in rule " + parserRule);
        }
        return parserRule.alternatives.get(alternativeIndex);
    }

    public List<ParserRule> getParserRules() {
        return rules.values()
                    .stream()
                    .filter(rule -> rule instanceof ParserRule)
                    .map(rule -> (ParserRule) rule)
                    .collect(toList());
    }

    public List<RuleReference> getRuleReferences() {
        return rules.values()
                    .stream()
                    .filter(rule -> rule instanceof ParserRule)
                    .map(rule -> (ParserRule) rule)
                    .flatMap(rule -> rule.alternatives.stream())
                    .flatMap(alternative -> alternative.elements.stream()o)
                    .collect(toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb
                .append("grammar ")
                .append(this.name)
                .append(";")
                .append(System.lineSeparator());

        for (Rule rule : this.rules.values()) {
            sb
                    .append(rule.toString())
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrammarInfo that = (GrammarInfo) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(rules, that.rules) &&
                Objects.equals(rootRule, that.rootRule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rules, rootRule);
    }
}
