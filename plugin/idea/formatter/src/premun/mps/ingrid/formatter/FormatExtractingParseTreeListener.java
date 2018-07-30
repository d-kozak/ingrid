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
import premun.mps.ingrid.model.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class FormatExtractingParseTreeListener extends BaseParseTreeListener {

    private final Map<FormatInfoMapKey, FormatInfo> formatInfo = new HashMap<>();

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
        System.out.println("");
        List<ParseTree> copy = new ArrayList<>(parserRuleContext.children);
        List<MatchInfo> matchInfo = extractFormatInfo(copy, appropriateAlternative.elements);

        System.out.println("matchInfo: " + matchInfo);
    }

    private List<MatchInfo> extractFormatInfo(List<ParseTree> ast, List<RuleReference> elements) {
        List<MatchInfo> matchInfos = new ArrayList<>();
        for (RuleReference ruleReference : elements) {
            matchInfos.add(match(ruleReference, ast));
        }
        return matchInfos;
    }

    private MatchInfo match(RuleReference ruleReference, List<ParseTree> parseTrees) {
        if (parseTrees.size() == 0) {
            if (ruleReference.quantity == Quantity.AT_LEAST_ONE || ruleReference.quantity == Quantity.EXACTLY_ONE) {
                throw new IllegalArgumentException("Matching was not successful");
            } else {
                // match was successful by matching nothing
                return new MatchInfo(ruleReference.rule, 0);
            }
        }
        Quantity quantity = ruleReference.quantity;
        premun.mps.ingrid.model.Rule rule = ruleReference.rule;
        if (rule instanceof ParserRule && rule.name.contains("_block_")) {
            int count = 0;
            List<Alternative> alternatives = ((ParserRule) rule).alternatives;
            Alternative appropriateAlternative = FormatExtractorUtils.findAppropriateAlternative(alternatives, parseTrees, Arrays.asList(grammar.getRuleNames()));
            ArrayList<ParseTree> copy = new ArrayList<>(parseTrees);
            if (quantity == Quantity.MAX_ONE || quantity == Quantity.EXACTLY_ONE || quantity == Quantity.AT_LEAST_ONE) {
                try {
                    extractFormatInfo(copy, appropriateAlternative.elements);
                } catch (IllegalArgumentException ex) {
                    if (quantity == Quantity.EXACTLY_ONE || quantity == Quantity.AT_LEAST_ONE) {
                        throw new IllegalArgumentException("Matching was not successful");
                    }
                }
                if (quantity == Quantity.EXACTLY_ONE) {
                    extractFormatInfo(parseTrees, appropriateAlternative.elements);
                    return new MatchInfo(rule, 1);
                }
                if (quantity == Quantity.AT_LEAST_ONE) {
                    extractFormatInfo(parseTrees, appropriateAlternative.elements);
                    count++;
                }
            }
            while (true) {
                copy = new ArrayList<>(parseTrees);
                try {
                    // first try on copy, if it works, try on original as well
                    extractFormatInfo(copy, appropriateAlternative.elements);
                    extractFormatInfo(parseTrees, appropriateAlternative.elements);
                    count++;
                } catch (IllegalArgumentException ex) {
                    // FIXME using exception for control flow, bleh...needs refactoring
                    break;
                }
            }
            return new MatchInfo(rule, count);
        }
        switch (quantity) {
            case MAX_ONE: {
                int count = 0;
                if (matches(rule, parseTrees.get(0))) {
                    parseTrees.remove(0);
                    count = 1;
                }
                return new MatchInfo(rule, count);
            }
            case EXACTLY_ONE: {
                if (matches(rule, parseTrees.get(0))) {
                    parseTrees.remove(0);
                    return new MatchInfo(rule, 1);
                } else {
                    throw new IllegalArgumentException("Matching was not successful");
                }
            }
            case AT_LEAST_ONE: {
                if (!matches(rule, parseTrees.get(0))) {
                    throw new IllegalArgumentException("Matching was not successful");
                }
                int count = 1;
                parseTrees.remove(0);
                while (matches(rule, parseTrees.get(0))) {
                    parseTrees.remove(0);
                    count++;
                }
                return new MatchInfo(rule, count);
            }
            case ANY: {
                int count = 0;
                while (matches(rule, parseTrees.get(0))) {
                    parseTrees.remove(0);
                    count++;
                }
                return new MatchInfo(rule, count);
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
                throw new IllegalArgumentException("Cannot find leftmost token in this tree");
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

    public Map<FormatInfoMapKey, FormatInfo> getFormatInfo() {
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
