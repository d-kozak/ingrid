package premun.mps.ingrid.formatter;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.tool.Grammar;
import org.antlr.v4.tool.Rule;
import premun.mps.ingrid.formatter.model.FormatInfo;
import premun.mps.ingrid.formatter.model.FormatInfoMapKey;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.model.ParserRule;

import java.util.*;

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
        //Alternative alternative = FormatExtractorUtils.findAppropriateAlternative(parserRule.alternatives, parserRuleContext.children, Arrays.asList(grammar.getRuleNames()));


        List<String> context = getContext(parserRuleContext);

        System.out.println(context);
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
}
