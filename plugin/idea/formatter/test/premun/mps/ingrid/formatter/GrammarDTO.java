package premun.mps.ingrid.formatter;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.tool.Grammar;
import premun.mps.ingrid.model.GrammarInfo;

/**
 * Simple DTO used in tests to group together three most common objects that usually need verification and sometimes are used to verify each other
 *
 * @author dkozak
 */
public class GrammarDTO {

    public final GrammarInfo grammarInfo;
    public final Grammar grammar;
    public final ParserRuleContext ast;

    public GrammarDTO(GrammarInfo grammarInfo, Grammar grammar, ParserRuleContext ast) {
        this.grammarInfo = grammarInfo;
        this.grammar = grammar;
        this.ast = ast;
    }

    /**
     * Parses the inputGrammar, inputText and checks that the start rule exists. Used during the setup phase of most tests.
     * @param inputGrammar
     * @param startRule
     * @param inputText
     * @return
     * @throws RecognitionException if the inputGrammar format in incorrect
     */
    public static GrammarDTO prepareGrammar(String inputGrammar, String startRule, String inputText) throws RecognitionException {
        GrammarInfo grammarInfo = TestGrammars.parseGrammar(inputGrammar);
        Grammar parsedGrammar = new Grammar(inputGrammar);
        if (parsedGrammar.getRule(startRule) == null) {
            throw new IllegalArgumentException("Start rule not found");
        }
        ParserRuleContext parseTree = (ParserRuleContext) InterpretingParser.parse(parsedGrammar, inputText, startRule);
        return new GrammarDTO(grammarInfo, parsedGrammar, parseTree);
    }
}
