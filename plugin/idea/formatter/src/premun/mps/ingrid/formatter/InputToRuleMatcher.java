package premun.mps.ingrid.formatter;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.tool.Grammar;
import premun.mps.ingrid.formatter.model.MatchInfo;
import premun.mps.ingrid.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class InputToRuleMatcher {

    private final Grammar grammar;

    public InputToRuleMatcher(Grammar grammar) {
        this.grammar = grammar;
    }

    public List<MatchInfo> matchRule(List<ParseTree> input, List<RuleReference> ruleReferences) {
        List<MatchInfo> matchInfos = new ArrayList<>();
        for (RuleReference ruleReference : ruleReferences) {
            matchInfos.add(match(input, ruleReference));
        }
        return matchInfos;
    }

    public MatchInfo match(List<ParseTree> input, RuleReference ruleReference) {
        if (input.size() == 0) {
            if (ruleReference.quantity == Quantity.AT_LEAST_ONE || ruleReference.quantity == Quantity.EXACTLY_ONE) {
                throw new IllegalArgumentException("Matching was not successful");
            } else {
                // matchRule was successful by matching nothing
                return new MatchInfo(ruleReference.rule, 0, Collections.emptyList());
            }
        }
        Quantity quantity = ruleReference.quantity;
        premun.mps.ingrid.model.Rule rule = ruleReference.rule;
        if (rule instanceof ParserRule && rule.name.contains("_block_")) {
            int count = 0;
            List<ParseTree> nodes = new ArrayList<>();
            List<Alternative> alternatives = ((ParserRule) rule).alternatives;
            Alternative appropriateAlternative = AlternativeResolver.selectAlternative(alternatives, input, Arrays.asList(grammar.getRuleNames()));
            ArrayList<ParseTree> copy = new ArrayList<>(input);
            if (quantity == Quantity.MAX_ONE || quantity == Quantity.EXACTLY_ONE || quantity == Quantity.AT_LEAST_ONE) {
                try {
                    List<MatchInfo> matchInfos = matchRule(copy, appropriateAlternative.elements);
                    nodes.addAll(matchInfos.stream()
                                           .flatMap(info -> info.matched.stream())
                                           .collect(toList()));
                } catch (IllegalArgumentException ex) {
                    if (quantity == Quantity.EXACTLY_ONE || quantity == Quantity.AT_LEAST_ONE) {
                        throw new IllegalArgumentException("Matching was not successful");
                    }
                }
                if (quantity == Quantity.EXACTLY_ONE) {
                    List<MatchInfo> matchInfos = matchRule(input, appropriateAlternative.elements);
                    nodes.addAll(matchInfos.stream()
                                           .flatMap(info -> info.matched.stream())
                                           .collect(toList()));
                    return new MatchInfo(rule, 1, nodes);
                }
                if (quantity == Quantity.AT_LEAST_ONE) {
                    matchRule(input, appropriateAlternative.elements);
                    count++;
                }
            }
            while (true) {
                copy = new ArrayList<>(input);
                try {
                    // first try on copy, if it works, try on original as well
                    matchRule(copy, appropriateAlternative.elements);
                    List<MatchInfo> matchInfos = matchRule(input, appropriateAlternative.elements);
                    nodes.addAll(matchInfos.stream()
                                           .flatMap(info -> info.matched.stream())
                                           .collect(toList()));
                    count++;
                } catch (IllegalArgumentException ex) {
                    // FIXME using exception for control flow, bleh...needs refactoring
                    break;
                }
            }
            return new MatchInfo(rule, count, nodes);
        }
        ParseTree currentNode = input.get(0);
        List<ParseTree> nodes = new ArrayList<>();
        switch (quantity) {
            case MAX_ONE: {
                int count = 0;
                if (matches(rule, currentNode)) {
                    input.remove(0);
                    count = 1;
                    nodes.add(currentNode);
                }
                return new MatchInfo(rule, count, nodes);
            }
            case EXACTLY_ONE: {
                if (matches(rule, currentNode)) {
                    input.remove(0);
                    nodes.add(currentNode);
                    return new MatchInfo(rule, 1, nodes);
                } else {
                    throw new IllegalArgumentException("Matching was not successful");
                }
            }
            case AT_LEAST_ONE: {
                if (!matches(rule, currentNode)) {
                    throw new IllegalArgumentException("Matching was not successful");
                }
                int count = 1;
                input.remove(0);
                nodes.add(currentNode);
                currentNode = input.get(0);
                while (matches(rule, currentNode)) {
                    input.remove(0);
                    count++;
                    nodes.add(currentNode);
                    currentNode = input.get(0);
                }
                return new MatchInfo(rule, count, nodes);
            }
            case ANY: {
                int count = 0;
                while (matches(rule, currentNode)) {
                    input.remove(0);
                    nodes.add(currentNode);
                    count++;
                    currentNode = input.get(0);
                }
                return new MatchInfo(rule, count, nodes);
            }
        }


        throw new RuntimeException("Should never get here");
    }

    boolean matches(premun.mps.ingrid.model.Rule rule, ParseTree parseTree) {
        if (rule instanceof LiteralRule) {
            if (!(parseTree instanceof TerminalNode)) {
                return false;
            } else {
                return ((TerminalNode) parseTree).getSymbol()
                                                 .getText()
                                                 .equals(((LiteralRule) rule).value);
            }
        } else if (rule instanceof RegexRule) {
            if (!(parseTree instanceof TerminalNode)) {
                return false;
            } else {
                return ((TerminalNode) parseTree).getSymbol()
                                                 .getText()
                                                 .matches(((RegexRule) rule).regexp);
            }
        } else if (rule instanceof ParserRule) {
            if (parseTree instanceof TerminalNode) {
                return false;
            } else if (!(parseTree instanceof RuleContext)) {
                throw new IllegalArgumentException("Unknown type of ParseTree node");
            } else {
                return rule.name.equals(grammar.getRule(((RuleContext) parseTree).getRuleIndex()).name);
            }
        } else {
            throw new IllegalArgumentException("Unknown type of rule");
        }
    }

}
