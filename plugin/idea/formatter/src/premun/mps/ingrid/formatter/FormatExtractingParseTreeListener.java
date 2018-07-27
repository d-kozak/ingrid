package premun.mps.ingrid.formatter;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.tool.Grammar;
import org.antlr.v4.tool.Rule;
import premun.mps.ingrid.formatter.model.FormatInfo;
import premun.mps.ingrid.formatter.model.FormatInfoMapKey;
import premun.mps.ingrid.model.*;

import java.util.*;
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
