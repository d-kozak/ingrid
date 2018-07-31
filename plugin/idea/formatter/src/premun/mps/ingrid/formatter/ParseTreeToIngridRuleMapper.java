package premun.mps.ingrid.formatter;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import premun.mps.ingrid.formatter.model.MatchInfo;
import premun.mps.ingrid.formatter.model.SerializedParserRule;
import premun.mps.ingrid.formatter.utils.Pair;
import premun.mps.ingrid.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ParseTreeToIngridRuleMapper {
    public static Pair<Alternative, List<MatchInfo>> resolve(List<Alternative> alternatives, List<ParseTree> ast, List<String> ruleNames) {
        alternatives = expandList(alternatives);
        for (Alternative alternative : alternatives) {
            List<MatchInfo> matchInfoList = match(alternative.elements, new ArrayList<>(ast), ruleNames, true);
            if (matchInfoList != null) {
                return Pair.of(((AlternativeDTO) alternative).original, matchInfoList);
            }
        }
        throw new IllegalArgumentException("Did not match");
    }

    private static List<MatchInfo> match(List<RuleReference> elements, List<ParseTree> ast, List<String> ruleNames, boolean isTopLevelCall) {
        List<MatchInfo> result = new ArrayList<>();
        for (RuleReference ruleReference : elements) {
            List<ParseTree> tmp = new ArrayList<>();
            int times = 0;
            switch (ruleReference.quantity) {
                case MAX_ONE: {
                    // it is ok not to match optional element
                    List<ParseTree> match = match(ruleReference.rule, ast, ruleNames);
                    result.add(new MatchInfo(ruleReference.rule, match.size(), match));
                    break;
                }
                case EXACTLY_ONE: {
                    List<ParseTree> match = match(ruleReference.rule, ast, ruleNames);
                    if (match.isEmpty()) {
                        return null;
                    }
                    result.add(new MatchInfo(ruleReference.rule, 1, match));
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
                    result.add(new MatchInfo(ruleReference.rule, times, tmp));
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

    private static List<ParseTree> match(Rule rule, List<ParseTree> parseTree, List<String> ruleNames) {
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
            List<MatchInfo> result = match(((SerializedParserRule) rule).elements, parseTree, ruleNames, false);
            if (result != null)
                return result.stream()
                             .flatMap(matchInfo -> matchInfo.matched.stream())
                             .collect(Collectors.toList());
            else return Collections.emptyList();
        } else return Collections.emptyList();
    }

    static List<Alternative> expandList(List<Alternative> input) {
        List<Alternative> result = new ArrayList<>();

        for (Alternative alternative : input) {
            List<List<RuleReference>> expandedAlternative = new ArrayList<>();
            for (RuleReference element : alternative.elements) {
                if (element.rule instanceof ParserRule && element.rule.name.contains("_block_")) {
                    List<Alternative> inner = expandList(((ParserRule) element.rule).alternatives);
                    if (expandedAlternative.isEmpty()) {
                        for (int i = 0; i < inner.size(); i++) {
                            String name = element.rule.name + "_alt_" + i;
                            SerializedParserRule serializedParserRule = new SerializedParserRule(name, inner.get(i).elements);
                            List<RuleReference> tmp = new ArrayList<>();
                            tmp.add(new RuleReference(serializedParserRule, element.quantity));
                            expandedAlternative.add(tmp);
                        }
                    } else {
                        for (int i = 0; i < inner.size(); i++) {
                            String name = element.rule.name + "_alt_" + i;
                            SerializedParserRule serializedParserRule = new SerializedParserRule(name, inner.get(i).elements);
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
            while (rule.elements.size() == 1 && rule.elements.get(0).rule instanceof SerializedParserRule) {
                rule = (SerializedParserRule) rule.elements.get(0).rule;
            }
            input.rule = rule;
            return input;
        } else {
            return input;
        }
    }

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
