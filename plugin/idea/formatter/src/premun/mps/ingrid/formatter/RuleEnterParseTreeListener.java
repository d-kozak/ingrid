package premun.mps.ingrid.formatter;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.tool.Grammar;
import org.antlr.v4.tool.Rule;
import premun.mps.ingrid.formatter.model.CollectionFormatInfo;
import premun.mps.ingrid.formatter.model.MatchInfo;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.model.ParserRule;
import premun.mps.ingrid.model.RuleReference;
import premun.mps.ingrid.model.format.FormatInfo;
import premun.mps.ingrid.model.utils.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Handles extracting information about formatting. Uses BaseParseTreeListener method enterEveryRule,
 * so that it is agnostic to the underlying grammar and can be used to extract information from any antlr4 grammar.
 * <p>
 * The information about formatting is stored directly to the GrammarInfo passed in.
 *
 * @author dkozak
 */
public class RuleEnterParseTreeListener extends BaseParseTreeListener {

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

    private final ParseTreeToIngridRuleMapper parseTreeToIngridRuleMapper;

    public RuleEnterParseTreeListener(Grammar grammar, GrammarInfo grammarInfo, CommonTokenStream tokens) {
        this.grammar = grammar;
        this.grammarInfo = grammarInfo;
        this.tokens = tokens;
        this.parseTreeToIngridRuleMapper = new ParseTreeToIngridRuleMapper(tokens, Arrays.asList(grammar.getRuleNames()));
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

        Pair<Pair<Alternative, List<MatchInfo>>, Map<Pair<ParserRule, Alternative>, List<List<FormatInfo>>>> resolved = parseTreeToIngridRuleMapper.resolve(parserRule.alternatives, parserRuleContext.children);
        Pair<Alternative, List<MatchInfo>> pair = resolved.first;
        Map<Pair<ParserRule, Alternative>, List<List<FormatInfo>>> blockRules = resolved.second;

        addBlockRules(blockRules);

        Alternative appropriateAlternative = pair.first;
        List<FormatInfo> formatInfos = FormatInfoExtractor.extractFormatInfo(pair.second, tokens);

        addFormatInfoToAlternative(appropriateAlternative, formatInfos);
    }

    /**
     * Adds block rules that were returned from ParseTreeToIngridRuleMapper to the formatInfo map
     *
     * @param blockRules to be saved in the formatInfoMap
     */
    private void addBlockRules(Map<Pair<ParserRule, Alternative>, List<List<FormatInfo>>> blockRules) {
        for (Map.Entry<Pair<ParserRule, Alternative>, List<List<FormatInfo>>> entry : blockRules.entrySet()) {
            // We could theoretically insert the information directly, but we perform the lookup just to be sure nothing is broken
            String ruleName = entry.getKey().first.name;
            List<List<FormatInfo>> collectedFormatInfo = entry.getValue();

            ParserRule parserRule = (ParserRule) Objects.requireNonNull(grammarInfo.rules.get(ruleName), () -> "Rule with name " + ruleName + "not found in " + grammarInfo);
            int i = parserRule.alternatives.indexOf(entry.getKey().second);
            if (i == -1) {
                throw new IllegalStateException("Could not found alternative " + entry.getKey().second + " in rule " + parserRule);
            }
            Alternative alternative = parserRule.alternatives.get(i);

            for (List<FormatInfo> simpleFormatInfos : collectedFormatInfo) {
                addFormatInfoToAlternative(alternative, simpleFormatInfos);
            }
        }
    }

    private void addFormatInfoToAlternative(Alternative appropriateAlternative, List<FormatInfo> formatInfos) {
        if (formatInfos.size() != appropriateAlternative.elements.size()) {
            throw new IllegalStateException("The size of formatInfos should be the same as rule references, otherwise we cannot merge them, alternative:  " + appropriateAlternative + ", formatInfos: " + formatInfos);
        }

        for (int i = 0; i < formatInfos.size(); i++) {
            RuleReference ruleReference = appropriateAlternative.elements.get(i);
            FormatInfo formatInfo = formatInfos.get(i);
            ((CollectionFormatInfo) ruleReference.formatInfo).addFormatInfo(formatInfo);
        }
    }
}
