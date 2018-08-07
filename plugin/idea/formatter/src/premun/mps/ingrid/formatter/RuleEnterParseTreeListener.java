package premun.mps.ingrid.formatter;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.tool.Grammar;
import org.antlr.v4.tool.Rule;
import premun.mps.ingrid.formatter.model.FormatInfo;
import premun.mps.ingrid.formatter.model.MatchInfo;
import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.formatter.utils.Pair;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.model.ParserRule;

import java.util.*;

/**
 * Handles extracting information about formatting. Uses BaseParseTreeListener method enterEveryRule,
 * so that it is agnostic to the underlying grammar and can be used to extract information from any antlr4 grammar.
 *
 * @author dkozak
 */
public class RuleEnterParseTreeListener extends BaseParseTreeListener {

    /**
     * Contains the extracted formatting information
     */
    private final Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> formatInfo = new HashMap<>();

    /**
     * Used to serialize various objects into Strigns, for debugging
     */
    private final Serializer serializer;

    /**
     * Contains information about the grammar from Antlr4 perspective
     */
    private final Grammar grammar;

    /**
     * Contains information about the grammar from Ingrid perspective
     */
    private final GrammarInfo grammarInfo;

    public RuleEnterParseTreeListener(Grammar grammar, GrammarInfo grammarInfo) {
        this.grammar = grammar;
        this.grammarInfo = grammarInfo;
        this.serializer = new Serializer(grammar);
    }

    /**
     * Main method of this listener, it figures out the mapping between IngridRule model for this rule and the ast passed in
     * as an argument and then it uses this information to extract formatting and saves it into formatInfo hashmap
     *
     * @param parserRuleContext ast that matched this rule
     */
    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {
        Rule antlrRule = grammar.getRule(parserRuleContext.getRuleIndex());
        ParserRule parserRule = (ParserRule) grammarInfo.rules.get(antlrRule.name);
        List<String> context = getContext(parserRuleContext);

        Pair<Alternative, List<MatchInfo>> pair = ParseTreeToIngridRuleMapper.resolve(parserRule.alternatives, parserRuleContext.children, Arrays.asList(grammar.getRuleNames()));
        Alternative appropriateAlternative = pair.first;
        List<MatchInfo> matchInfo = pair.second;
        int alternativeIndex = parserRule.alternatives.indexOf(appropriateAlternative);
        List<FormatInfo> formatInfos = FormatInfoExtractor.extractFormatInfo(matchInfo);
        List<RuleFormatInfo> ruleFormatInfos = this.formatInfo.computeIfAbsent(Pair.of(parserRule, appropriateAlternative), __ -> new ArrayList<>());
        ruleFormatInfos.add(new RuleFormatInfo(formatInfos));
    }

    /**
     * @param parserRuleContext ast that matched specified rule
     * @return List of rule names that lead to the match, used a part of the key to the formatInfo hashmap
     */
    private List<String> getContext(ParserRuleContext parserRuleContext) {
        String context = parserRuleContext.toString(Arrays.asList(grammar.getRuleNames()));
        context = context.substring(1, context.length() - 1);
        List<String> contextList = Arrays.asList(context.split(" "));
        Collections.reverse(contextList);
        return contextList;
    }

    public Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> getFormatInfo() {
        return formatInfo;
    }
}
