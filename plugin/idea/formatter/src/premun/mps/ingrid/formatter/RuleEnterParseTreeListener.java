package premun.mps.ingrid.formatter;

import org.antlr.v4.runtime.CommonTokenStream;
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

import static premun.mps.ingrid.formatter.utils.Pair.pair;

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
     * Stream of all tokens, used during formatting extraction
     */
    private final CommonTokenStream tokens;

    /**
     * Contains information about the grammar from Antlr4 perspective
     */
    private final Grammar grammar;

    /**
     * Contains information about the grammar from Ingrid perspective
     */
    private final GrammarInfo grammarInfo;

    public RuleEnterParseTreeListener(Grammar grammar, GrammarInfo grammarInfo, CommonTokenStream tokens) {
        this.grammar = grammar;
        this.grammarInfo = grammarInfo;
        this.tokens = tokens;
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

        Pair<Alternative, List<MatchInfo>> pair = ParseTreeToIngridRuleMapper.resolve(parserRule.alternatives, parserRuleContext.children, Arrays.asList(grammar.getRuleNames()), tokens);
        Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> blockRules = ParseTreeToIngridRuleMapper.getBlockRules();
        addBlockRules(blockRules);
        Alternative appropriateAlternative = pair.first;
        List<FormatInfo> formatInfos = FormatInfoExtractor.extractFormatInfo(pair.second, tokens);
        List<RuleFormatInfo> ruleFormatInfos = this.formatInfo.computeIfAbsent(pair(parserRule, appropriateAlternative), __ -> new ArrayList<>());
        ruleFormatInfos.add(new RuleFormatInfo(formatInfos));
    }

    /**
     * Adds block rules that were returned from ParseTreeToIngridRuleMapper to the formatInfo map
     *
     * @param blockRules to be saved in the formatInfoMap
     */
    private void addBlockRules(Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> blockRules) {
        for (Map.Entry<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> entry : blockRules.entrySet()) {
            List<RuleFormatInfo> ruleFormatInfos = formatInfo.computeIfAbsent(entry.getKey(), __ -> new ArrayList<>());
            ruleFormatInfos.addAll(entry.getValue());
        }
    }

    public Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> getFormatInfo() {
        return formatInfo;
    }
}
