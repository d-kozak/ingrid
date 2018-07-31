package premun.mps.ingrid.formatter;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.tool.Grammar;
import premun.mps.ingrid.model.GrammarInfo;

public class GrammarDTO {

    public final GrammarInfo grammarInfo;
    public final Grammar grammar;
    public final ParserRuleContext ast;

    public GrammarDTO(GrammarInfo grammarInfo, Grammar grammar, ParserRuleContext ast) {
        this.grammarInfo = grammarInfo;
        this.grammar = grammar;
        this.ast = ast;
    }

    public static GrammarDTO prepareGrammar(String inputGrammar, String startRule, String inputText) throws RecognitionException {
        GrammarInfo grammarInfo = TestGrammars.parseGrammar(inputGrammar);
        Grammar parsedGrammar = new Grammar(inputGrammar);
        if (parsedGrammar.getRule(startRule) == null) {
            throw new IllegalArgumentException("Start rule not found");
        }
        ParserRuleContext parseTree = (ParserRuleContext) OnTheFlyParser.parse(parsedGrammar, inputText, startRule);
        return new GrammarDTO(grammarInfo, parsedGrammar, parseTree);
    }
}
