package premun.mps.ingrid.formatter;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import premun.mps.ingrid.formatter.model.MatchInfo;
import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.formatter.utils.Pair;
import premun.mps.ingrid.model.*;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static premun.mps.ingrid.formatter.utils.Pair.pair;

/**
 * Computes how the parse tree passed in corresponds to the Ingrid rule that matched it.
 * <p>
 * The algorithm works as follows.
 * <p>
 * It first have to expand the alternatives into separate rules.
 * If it encounters a block rule, it also separates each of it's alternatives into rules and
 * it adds a special SerializedParserRule  as the wrapper of the content of the block rule
 * to clearly separate it from other kind of rules. If there are any inner blocks such as
 * (a | (b | c)), this algorithm generates two SerializedParserRules on the route from root to b or c.
 * As this is not necessary, a flattening happens afterwards that removes these unnecessary layers.
 * <p>
 * When the rule in expanded, it uses the following algorithm to figure out which of the
 * alternatives matches the ast.
 * Foreach ruleReference in rule.handle:
 * consume as much of the input ast as possible
 * if you did not manage to consume token/rule that was obligatory:
 * return error
 * save information about what you matched
 * <p>
 * if whole ast was matched:
 * return information about matching
 * else:
 * return error
 *
 * @author dkozak
 */
public class ParseTreeToIngridRuleMapper {

    private static final Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> blockRules = new HashMap<>();

    private static CommonTokenStream tokens;

    /**
     * Figures out which alternative can parse the ast and returns how the ast can be parsed
     *
     * @param alternatives list of alternatives, one of them has to match
     * @param ast          input which should be parsed by one of the alternative
     * @param ruleNames    names of rules from grammar, in the same ordering as in the grammar file
     * @return Which Alternative matched the ast and how
     */
    public static Pair<Alternative, List<MatchInfo>> resolve(List<Alternative> alternatives, List<ParseTree> ast, List<String> ruleNames, CommonTokenStream tokens) {
        blockRules.clear();
        ParseTreeToIngridRuleMapper.tokens = tokens;

        alternatives = expandRules(alternatives);
        for (Alternative alternative : alternatives) {
            List<MatchInfo> matchInfoList = match(alternative.elements, new ArrayList<>(ast), ruleNames, true);
            if (matchInfoList != null) {
                return pair(((AlternativeDTO) alternative).original, matchInfoList);
            }
            blockRules.clear();
        }
        throw new IllegalArgumentException("Did not match");
    }

    /**
     * Tries to match given ast to the rule handle specified by elements.
     *
     * @param elements       rule handle which could match the ast
     * @param ast            ast to match
     * @param ruleNames      names of rules from grammar, in the same ordering as in the grammar file
     * @param isTopLevelCall topLevel calls have to match the whole input, while calls for inner block rules don't have to do that
     * @return list of matchInfo objects, one for each ruleReference in the elements list, or null if input was not matched
     */
    private static List<MatchInfo> match(List<RuleReference> elements, List<ParseTree> ast, List<String> ruleNames, boolean isTopLevelCall) {
        List<MatchInfo> result = new ArrayList<>();
        for (RuleReference ruleReference : elements) {
            List<ParseTree> tmp = new ArrayList<>();
            int times = 0;
            switch (ruleReference.quantity) {
                case MAX_ONE: {
                    // it is ok not to match optional element
                    List<ParseTree> match = match(ruleReference.rule, ast, ruleNames);
                    result.add(new MatchInfo(ruleReference.rule, Quantity.MAX_ONE, match.size(), match));
                    break;
                }
                case EXACTLY_ONE: {
                    List<ParseTree> match = match(ruleReference.rule, ast, ruleNames);
                    if (match.isEmpty()) {
                        return null;
                    }
                    result.add(new MatchInfo(ruleReference.rule, Quantity.EXACTLY_ONE, 1, match));
                    break;
                }
                case AT_LEAST_ONE:
                    List<ParseTree> match = match(ruleReference.rule, ast, ruleNames);
                    if (match.isEmpty()) {
                        return null;
                    }
                    tmp.addAll(match);
                    times++;
                    // note that there is no break here, we continue to ANY to match any remaining iterations
                case ANY:
                    List<ParseTree> m = match(ruleReference.rule, ast, ruleNames);
                    while (!m.isEmpty()) {
                        tmp.addAll(m);
                        times++;
                        m = match(ruleReference.rule, ast, ruleNames);
                    }
                    result.add(new MatchInfo(ruleReference.rule, ruleReference.quantity, times, tmp));
                    break;
            }
        }
        boolean matchedWholeInput = ast.size() == 0;
        boolean justEofTokenLeft = ast.size() == 1 && ast.get(0) instanceof TerminalNode && (((TerminalNode) ast.get(0)).getSymbol()
                                                                                                                        .getText()).contains("<EOF>");
        if (matchedWholeInput || !isTopLevelCall || justEofTokenLeft) {
            return result;
        } else return null;
    }

    /**
     * Tries to match first element(s) of ParseTree to the given rule.
     *
     * @param rule      rule which should match the parse tree
     * @param parseTree parse tree to match
     * @param ruleNames names of rules from grammar, in the same ordering as in the grammar file
     * @return List of elements that matched the rule
     */
    private static List<ParseTree> match(Rule rule, List<ParseTree> parseTree, List<String> ruleNames) {
        if (parseTree.size() == 0) {
            return Collections.emptyList();
        }

        ParseTree current = parseTree.get(0);
        if (rule instanceof LiteralRule && current instanceof TerminalNode) {
            boolean matches = ((LiteralRule) rule).value.equals(((TerminalNode) current).getSymbol()
                                                                                        .getText());
            if (matches) {
                return Collections.singletonList(parseTree.remove(0));
            } else
                return Collections.emptyList();
        } else if (rule instanceof RegexRule && current instanceof TerminalNode) {
            boolean matches = ((TerminalNode) current).getSymbol()
                                                      .getText()
                                                      .matches(((RegexRule) rule).regexp);
            if (matches) {
                return Collections.singletonList(parseTree.remove(0));
            }
            return Collections.emptyList();
        } else if (rule instanceof ParserRule && current instanceof ParserRuleContext) {
            boolean matches = rule.name.equals(ruleNames.get(((ParserRuleContext) current).getRuleIndex()));
            if (matches) {
                return Collections.singletonList(parseTree.remove(0));
            }
            return Collections.emptyList();
        } else if (rule instanceof SerializedParserRule) {
            SerializedParserRule parserRule = (SerializedParserRule) rule;
            List<MatchInfo> result = match((parserRule).alternative.elements, parseTree, ruleNames, false);
            if (result != null) {
                List<RuleFormatInfo> formatInfoList = blockRules.computeIfAbsent(pair(parserRule.rule, ((AlternativeDTO) parserRule.alternative).original), __ -> new ArrayList<>());
                formatInfoList.add(new RuleFormatInfo(FormatInfoExtractor.extractFormatInfo(result, ParseTreeToIngridRuleMapper.tokens)));
                return result.stream()
                             .flatMap(matchInfo -> matchInfo.matched.stream())
                             .collect(Collectors.toList());
            } else return Collections.emptyList();
        } else return Collections.emptyList();
    }

    /**
     * Expands each alternative from the input into multiple alternatives to get rid of inner blocks with alternatives.
     * This step enables a linear algorithm for ast to input matching.
     *
     * @param input
     * @return expanded list of alternatives that do not contain any inner blocks with alternatives
     */
    static List<Alternative> expandRules(List<Alternative> input) {
        List<Alternative> result = new ArrayList<>();

        for (Alternative alternative : input) {
            List<List<RuleReference>> expandedAlternative = new ArrayList<>();
            for (RuleReference element : alternative.elements) {
                if (element.rule instanceof ParserRule && element.rule.name.contains("_block_")) {
                    List<Alternative> inner = expandRules(((ParserRule) element.rule).alternatives);
                    if (expandedAlternative.isEmpty()) {
                        for (int i = 0; i < inner.size(); i++) {
                            String name = element.rule.name + "_alt_" + i;
                            SerializedParserRule serializedParserRule = new SerializedParserRule(name, ((ParserRule) element.rule), inner.get(i));
                            List<RuleReference> tmp = new ArrayList<>();
                            tmp.add(new RuleReference(serializedParserRule, element.quantity));
                            expandedAlternative.add(tmp);
                        }
                    } else {
                        for (int i = 0; i < inner.size(); i++) {
                            String name = element.rule.name + "_alt_" + i;
                            SerializedParserRule serializedParserRule = new SerializedParserRule(name, ((ParserRule) element.rule), inner.get(i));
                            for (List<RuleReference> ruleReferences : expandedAlternative) {
                                ruleReferences.add(new RuleReference(serializedParserRule, element.quantity));
                            }
                        }
                    }
                } else {
                    if (expandedAlternative.isEmpty()) {
                        ArrayList<RuleReference> tmp = new ArrayList<>();
                        tmp.add(element);
                        expandedAlternative.add(tmp);
                    } else {
                        for (List<RuleReference> ruleReferences : expandedAlternative) {
                            ruleReferences.add(element);
                        }
                    }
                }
            }
            result.addAll(expandedAlternative.stream()
                                             .map(references -> new AlternativeDTO(alternative, references))
                                             .collect(Collectors.toList()));
        }

        return flatten(result);
    }

    /**
     * Flattens the references, removing unnecessary SerializedParserRules that are created with nested blocks rules
     * e.g. r : ('a' | ('b' | ( 'c' | 'd'))) ;
     */
    private static List<Alternative> flatten(List<Alternative> result) {
        return result.stream()
                     .map(it -> ((AlternativeDTO) it))
                     .map(alternative -> {
                         List<RuleReference> handle = alternative.elements.stream()
                                                                          .map(ParseTreeToIngridRuleMapper::flattenReference)
                                                                          .collect(toList());
                         return new AlternativeDTO(alternative.original, handle);
                     })
                     .collect(Collectors.toList());
    }

    private static RuleReference flattenReference(RuleReference input) {
        if (input.rule instanceof SerializedParserRule) {
            SerializedParserRule rule = (SerializedParserRule) input.rule;
            while (rule.alternative.elements.size() == 1 && rule.alternative.elements.get(0).rule instanceof SerializedParserRule) {
                rule = (SerializedParserRule) rule.alternative.elements.get(0).rule;
            }
            input.rule = rule;
            return input;
        } else {
            return input;
        }
    }

    public static Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> getBlockRules() {
        return blockRules;
    }

    /**
     * A temporal rule inserted into the Ingrid model during alternative expanding
     */
    static class SerializedParserRule extends Rule {
        public final ParserRule rule;
        public final Alternative alternative;

        public SerializedParserRule(String name, ParserRule rule, Alternative alternative) {
            super(name);
            this.rule = rule;
            this.alternative = alternative;
        }


    }


    /**
     * Internally used DTO that represents expanded alternative created in expandRules method.
     * It keeps a reference to the original alternative so that at the end of the algorithm we
     * can return the original alternative.
     */
    private static class AlternativeDTO extends Alternative {
        private final Alternative original;

        public AlternativeDTO(Alternative original) {
            this.original = original;
            this.elements = new ArrayList<>(original.elements);
        }

        public AlternativeDTO(Alternative original, List<RuleReference> elements) {
            this.original = original;
            this.elements = elements;
        }
    }
}
