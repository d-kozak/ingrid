package premun.mps.ingrid.formatter;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.tool.Grammar;
import premun.mps.ingrid.formatter.model.FormatInfo;
import premun.mps.ingrid.formatter.model.FormatInfoMapKey;
import premun.mps.ingrid.model.GrammarInfo;

import java.util.HashMap;
import java.util.Map;

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

    }
}
