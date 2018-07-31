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
        String input = "efg}";
        String startRuleName = "rule";
        GrammarDTO grammarDTO = prepareGrammar(TestGrammars.simpleGrammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());

        ParserRule examinedRule = (ParserRule) grammarDTO.grammarInfo.rules.get("rule");
        Alternative selectedAlternative = AlternativeResolver.selectAlternative(examinedRule.alternatives, grammarDTO.ast.children, ruleNames);

        int indexOfAlternative = examinedRule.alternatives.indexOf(selectedAlternative);
        assertEquals(0, indexOfAlternative);
    }

    @Test
    public void simpleGrammar__alternativeOne() throws RecognitionException {
        String input = "{";
        String startRuleName = "rule";
        GrammarDTO grammarDTO = prepareGrammar(TestGrammars.simpleGrammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());

        ParserRule examinedRule = (ParserRule) grammarDTO.grammarInfo.rules.get("rule");
        Alternative selectedAlternative = AlternativeResolver.selectAlternative(examinedRule.alternatives, grammarDTO.ast.children, ruleNames);

        int indexOfAlternative = examinedRule.alternatives.indexOf(selectedAlternative);
        assertEquals(1, indexOfAlternative);
    }

    @Test
    public void setGrammar__setRule__alternativeZero() throws RecognitionException {
        String input = "{}";
        String startRuleName = "compilationUnit";
        GrammarDTO grammarDTO = prepareGrammar(TestGrammars.setGrammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());

        // get the appropriate subtree in ast
        ParserRuleContext set = (ParserRuleContext) grammarDTO.ast.getChild(0);

        ParserRule examinedRule = (ParserRule) grammarDTO.grammarInfo.rules.get("set");
        Alternative selectedAlternative = AlternativeResolver.selectAlternative(examinedRule.alternatives, set.children, ruleNames);

        int indexOfAlternative = examinedRule.alternatives.indexOf(selectedAlternative);
        assertEquals(0, indexOfAlternative);
    }

    @Test
    public void setGrammar__setRule__alternativeOne() throws RecognitionException {
        String input = "{a,{},c}";
        String startRuleName = "compilationUnit";
        GrammarDTO grammarDTO = prepareGrammar(TestGrammars.setGrammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());

        // get the appropriate subtree in ast
        ParserRuleContext set = (ParserRuleContext) grammarDTO.ast.getChild(0);

        ParserRule examinedRule = (ParserRule) grammarDTO.grammarInfo.rules.get("set");
        Alternative selectedAlternative = AlternativeResolver.selectAlternative(examinedRule.alternatives, set.children, ruleNames);

        int indexOfAlternative = examinedRule.alternatives.indexOf(selectedAlternative);
        assertEquals(1, indexOfAlternative);
    }

    @Test
    public void bookGrammar__bookRule__alternativeZero() throws RecognitionException {
        String input = "shortcut povmal ,  povidky Malostranske , Jan Neruda";
        String startRuleName = "book";
        GrammarDTO grammarDTO = prepareGrammar(TestGrammars.bookGrammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());

        // get the appropriate subtree in ast
        ParserRuleContext set = (ParserRuleContext) grammarDTO.ast.getChild(0);

        ParserRule examinedRule = (ParserRule) grammarDTO.grammarInfo.rules.get("book");
        Alternative selectedAlternative = AlternativeResolver.selectAlternative(examinedRule.alternatives, set.children, ruleNames);

        int indexOfAlternative = examinedRule.alternatives.indexOf(selectedAlternative);
        assertEquals(0, indexOfAlternative);
    }


    @Test
    public void bookGrammar__bookRule__alternativeOne() throws RecognitionException {
        String input = "shortcut povmal ,  povidky Malostranske , Jan Neruda, 1878";
        String startRuleName = "book";
        GrammarDTO grammarDTO = prepareGrammar(TestGrammars.bookGrammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());

        // get the appropriate subtree in ast
        ParserRuleContext set = (ParserRuleContext) grammarDTO.ast.getChild(0);

        ParserRule examinedRule = (ParserRule) grammarDTO.grammarInfo.rules.get("book");
        Alternative selectedAlternative = AlternativeResolver.selectAlternative(examinedRule.alternatives, set.children, ruleNames);

        int indexOfAlternative = examinedRule.alternatives.indexOf(selectedAlternative);
        assertEquals(1, indexOfAlternative);
    }


    private GrammarDTO prepareGrammar(String inputGrammar, String startRule, String inputText) throws RecognitionException {
        GrammarInfo grammarInfo = TestGrammars.parseGrammar(inputGrammar);
        Grammar parsedGrammar = new Grammar(inputGrammar);
        if (parsedGrammar.getRule(startRule) == null) {
            throw new IllegalArgumentException("Start rule not found");
        }
        ParserRuleContext parseTree = (ParserRuleContext) OnTheFlyParser.parse(parsedGrammar, inputText, startRule);
        return new GrammarDTO(grammarInfo, parsedGrammar, parseTree);
    }

    private static class GrammarDTO {
        public final GrammarInfo grammarInfo;
        public final Grammar grammar;
        public final ParserRuleContext ast;

        public GrammarDTO(GrammarInfo grammarInfo, Grammar grammar, ParserRuleContext ast) {
            this.grammarInfo = grammarInfo;
            this.grammar = grammar;
            this.ast = ast;
        }
    }


}
