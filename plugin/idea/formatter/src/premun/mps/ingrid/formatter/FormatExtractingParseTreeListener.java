package premun.mps.ingrid.formatter;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.tool.Grammar;
import org.antlr.v4.tool.Rule;
import premun.mps.ingrid.formatter.model.FormatInfo;
import premun.mps.ingrid.formatter.model.FormatInfoMapKey;
import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.model.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class FormatExtractingParseTreeListener extends BaseParseTreeListener {

    private final Map<FormatInfoMapKey, List<RuleFormatInfo>> formatInfo = new HashMap<>();

    private final Grammar grammar;

    private final GrammarInfo grammarInfo;

    public FormatExtractingParseTreeListener(Grammar grammar, GrammarInfo grammarInfo) {
        this.grammar = grammar;
        this.grammarInfo = grammarInfo;
    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {
        Rule antlrRule = grammar.getRule(parserRuleContext.getRuleIndex());
        ParserRule parserRule = (ParserRule) grammarInfo.rules.get(antlrRule.name);
        List<String> context = getContext(parserRuleContext);
        String serialized = serializeChildren(parserRuleContext.children);
        System.out.print(context + " => ");
        System.out.println(serialized);
        System.out.println("Choosing from alternatives: ");
        List<String> alternatives = parserRule.alternatives.stream()
                                                           .map(this::serializeAlternative)
                                                           .collect(Collectors.toList());
        for (String alternative : alternatives) {
            System.out.println(alternative);
        }

        Alternative appropriateAlternative = FormatExtractorUtils.findAppropriateAlternative(parserRule.alternatives, parserRuleContext.children, Arrays.asList(grammar.getRuleNames()));
        System.out.println("appropriate alternative => " + serializeAlternative(appropriateAlternative));
        int alternativeIndex = parserRule.alternatives.indexOf(appropriateAlternative);
        System.out.println("at index: ");
        System.out.println("");
        List<ParseTree> copy = new ArrayList<>(parserRuleContext.children);
        List<MatchInfo> matchInfo = matchRules(copy, appropriateAlternative.elements);

        System.out.println("matchInfo: " + matchInfo);

        List<FormatInfo> formatInfos = extractFormatInfo(parserRuleContext.children, appropriateAlternative.elements, matchInfo);

        System.out.println("formatInfo: " + formatInfos);

        List<RuleFormatInfo> ruleFormatInfos = this.formatInfo.computeIfAbsent(new FormatInfoMapKey(context, alternativeIndex), key -> new ArrayList<>());
        ruleFormatInfos.add(new RuleFormatInfo(formatInfos));
    }

    private List<FormatInfo> extractFormatInfo(List<ParseTree> input, List<RuleReference> ruleReferences, List<MatchInfo> matchInfos) {
        if (ruleReferences.size() != matchInfos.size()) {
            throw new IllegalArgumentException("RuleReferences and their matchInfos have different length, rule references: " + ruleReferences.size() + " , matchRules infos: " + matchInfos.size());
        }

        List<FormatInfo> formatInfos = new ArrayList<>();

        for (int i = 0; i < matchInfos.size() - 1; i++) {
            MatchInfo left = matchInfos.get(i);
            MatchInfo right = matchInfos.get(i + 1);
            if (left.matched.size() > 0 && right.matched.size() > 0) {
                ParseTree rightmostNode = left.matched.get(left.matched.size() - 1);
                ParseTree leftmostNode = right.matched.get(0);
                Token current = extractToken(rightmostNode, node -> node.getChild(node.getChildCount() - 1));
                Token next = extractToken(leftmostNode, node -> node.getChild(0));

                int appendedNewLines = next.getLine() - current.getLine();
                int indentation = next.getCharPositionInLine() - (current.getCharPositionInLine() + current.getText()
                                                                                                           .length());

                formatInfos.add(new FormatInfo(appendedNewLines, indentation));
            } else {
                formatInfos.add(FormatInfo.NULL_INFO);
            }
        }
        return formatInfos;
    }

    private List<MatchInfo> matchRules(List<ParseTree> input, List<RuleReference> ruleReferences) {
        List<MatchInfo> matchInfos = new ArrayList<>();
        for (RuleReference ruleReference : ruleReferences) {
            matchInfos.add(match(input, ruleReference));
        }
        return matchInfos;
    }

    private MatchInfo match(List<ParseTree> input, RuleReference ruleReference) {
        if (input.size() == 0) {
            if (ruleReference.quantity == Quantity.AT_LEAST_ONE || ruleReference.quantity == Quantity.EXACTLY_ONE) {
                throw new IllegalArgumentException("Matching was not successful");
            } else {
                // matchRules was successful by matching nothing
                return new MatchInfo(ruleReference.rule, 0, Collections.emptyList());
            }
        }
        Quantity quantity = ruleReference.quantity;
        premun.mps.ingrid.model.Rule rule = ruleReference.rule;
        if (rule instanceof ParserRule && rule.name.contains("_block_")) {
            int count = 0;
            List<ParseTree> nodes = new ArrayList<>();
            List<Alternative> alternatives = ((ParserRule) rule).alternatives;
            Alternative appropriateAlternative = FormatExtractorUtils.findAppropriateAlternative(alternatives, input, Arrays.asList(grammar.getRuleNames()));
            ArrayList<ParseTree> copy = new ArrayList<>(input);
            if (quantity == Quantity.MAX_ONE || quantity == Quantity.EXACTLY_ONE || quantity == Quantity.AT_LEAST_ONE) {
                try {
                    List<MatchInfo> matchInfos = matchRules(copy, appropriateAlternative.elements);
                    nodes.addAll(matchInfos.stream()
                                           .flatMap(info -> info.matched.stream())
                                           .collect(toList()));
                } catch (IllegalArgumentException ex) {
                    if (quantity == Quantity.EXACTLY_ONE || quantity == Quantity.AT_LEAST_ONE) {
                        throw new IllegalArgumentException("Matching was not successful");
                    }
                }
                if (quantity == Quantity.EXACTLY_ONE) {
                    List<MatchInfo> matchInfos = matchRules(input, appropriateAlternative.elements);
                    nodes.addAll(matchInfos.stream()
                                           .flatMap(info -> info.matched.stream())
                                           .collect(toList()));
                    return new MatchInfo(rule, 1, nodes);
                }
                if (quantity == Quantity.AT_LEAST_ONE) {
                    matchRules(input, appropriateAlternative.elements);
                    count++;
                }
            }
            while (true) {
                copy = new ArrayList<>(input);
                try {
                    // first try on copy, if it works, try on original as well
                    matchRules(copy, appropriateAlternative.elements);
                    List<MatchInfo> matchInfos = matchRules(input, appropriateAlternative.elements);
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

    private boolean matches(premun.mps.ingrid.model.Rule rule, ParseTree parseTree) {
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

    private List<RuleReference> expandBlockRules(List<RuleReference> elements) {
        // we need to expand block rules created by ingrid, because they do not exist in the original grammar
        // and therefore enter every rule will not be called for them
        // as a simple fix, we can find them by looking for substring _block_ in their name
        // if we find one of these, we expand them manually
        List<RuleReference> result = new ArrayList<>();
        for (RuleReference elem : elements) {
            if ((elem.rule instanceof ParserRule) && elem.rule.name.contains("_block_")) {
                if (((ParserRule) elem.rule).alternatives.size() != 1) {
                    throw new IllegalArgumentException("Block rule should only have one alternative");
                }
                result.addAll(expandBlockRules(((ParserRule) elem.rule).alternatives.get(0).elements));
            } else {
                result.add(elem);
            }
        }
        return result;
    }

    private Token extractToken(ParseTree parseTree, Function<ParseTree, ParseTree> nextNodeExtractor) {
        while (!(parseTree instanceof TerminalNode)) {
            if (parseTree.getChildCount() == 0) {
                throw new IllegalArgumentException("Cannot find wanted token in this tree");
            }
            parseTree = nextNodeExtractor.apply(parseTree);
        }
        return ((TerminalNode) parseTree).getSymbol();
    }

    private String serializeAlternative(Alternative alternative) {
        return alternative.elements.stream()
                                   .map(this::serializeElem)
                                   .collect(Collectors.joining(" "));
    }

    private String serializeElem(RuleReference elem) {
        if (elem.rule instanceof LiteralRule) {
            return "'" + ((LiteralRule) elem.rule).value + "'";
        } else return elem.rule.name;
    }

    private String serializeChildren(List<ParseTree> children) {
        return children.stream()
                       .map(this::serializeParseTree)
                       .collect(joining(" "));
    }

    private List<String> getContext(ParserRuleContext parserRuleContext) {
        String context = parserRuleContext.toString(Arrays.asList(grammar.getRuleNames()));
        context = context.substring(1, context.length() - 1);
        List<String> contextList = Arrays.asList(context.split(" "));
        Collections.reverse(contextList);
        return contextList;
    }

    public Map<FormatInfoMapKey, List<RuleFormatInfo>> getFormatInfo() {
        return formatInfo;
    }

    private String serializeParseTree(ParseTree tree) {
        if (tree instanceof TerminalNode) {
            return "'" + ((TerminalNode) tree).getSymbol()
                                              .getText() + "'";
        } else if (tree instanceof RuleContext) {
            return grammar.getRule(((RuleContext) tree).getRuleIndex()).name;
        } else {
            throw new IllegalArgumentException("Unknown tree type " + tree.getClass()
                                                                          .getName());
        }
    }
}
