package premun.mps.ingrid.formatter;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.tool.Grammar;
import org.junit.Test;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.model.ParserRule;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests the alternative resolving process.
 * To simplify the test setup, we specify the grammar as string and let Ingrid Parser Module provide us with model to use
 *
 * @author dkozak
 * @see AlternativeResolver
 */
public class AlternativeResolverTest {


    @Test
    public void simpleGrammar__alternativeZero() throws RecognitionException {
        GrammarInfo simple = TestGrammars.prepareGrammar("simple");
        Grammar grammar = new Grammar(TestGrammars.simpleGrammar);
        List<String> ruleNames = Arrays.asList(grammar.getRuleNames());
        String startRuleName = "rule";
        String input = "efg}";

        ParserRule parserRule = (ParserRule) simple.rules.get(startRuleName);
        ParserRuleContext ast = ((ParserRuleContext) OnTheFlyParser.parse(grammar, input, startRuleName));
        Alternative selectAlternative = AlternativeResolver.selectAlternative(parserRule.alternatives, ast.children, ruleNames);

        int indexOfAlternative = parserRule.alternatives.indexOf(selectAlternative);
        assertEquals(0, indexOfAlternative);
    }

    @Test
    public void simpleGrammar__alternativeOne() throws RecognitionException {
        GrammarInfo simple = TestGrammars.prepareGrammar("simple");
        Grammar grammar = new Grammar(TestGrammars.simpleGrammar);
        List<String> ruleNames = Arrays.asList(grammar.getRuleNames());
        String startRuleName = "rule";
        String input = "{";

        ParserRule parserRule = (ParserRule) simple.rules.get(startRuleName);
        ParserRuleContext ast = ((ParserRuleContext) OnTheFlyParser.parse(grammar, input, startRuleName));
        Alternative selectAlternative = AlternativeResolver.selectAlternative(parserRule.alternatives, ast.children, ruleNames);

        int indexOfAlternative = parserRule.alternatives.indexOf(selectAlternative);
        assertEquals(1, indexOfAlternative);
    }


}
