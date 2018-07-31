package premun.mps.ingrid.formatter;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.Test;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.ParserRule;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static premun.mps.ingrid.formatter.GrammarDTO.prepareGrammar;

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
        Alternative selectedAlternative = AlternativeResolver.selectAlternative(examinedRule.alternatives, grammarDTO.ast.children, ruleNames).first;

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
        Alternative selectedAlternative = AlternativeResolver.selectAlternative(examinedRule.alternatives, grammarDTO.ast.children, ruleNames).first;

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
        Alternative selectedAlternative = AlternativeResolver.selectAlternative(examinedRule.alternatives, set.children, ruleNames).first;

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
        Alternative selectedAlternative = AlternativeResolver.selectAlternative(examinedRule.alternatives, set.children, ruleNames).first;

        int indexOfAlternative = examinedRule.alternatives.indexOf(selectedAlternative);
        assertEquals(1, indexOfAlternative);
    }

    @Test
    public void bookGrammar__bookRule__alternativeZero() throws RecognitionException {
        String input = "shortcut povmal ,  povidky Malostranske , Jan Neruda";
        String startRuleName = "book";
        GrammarDTO grammarDTO = prepareGrammar(TestGrammars.bookGrammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());

        ParserRule examinedRule = (ParserRule) grammarDTO.grammarInfo.rules.get("book");
        Alternative selectedAlternative = AlternativeResolver.selectAlternative(examinedRule.alternatives, grammarDTO.ast.children, ruleNames).first;

        int indexOfAlternative = examinedRule.alternatives.indexOf(selectedAlternative);
        assertEquals(0, indexOfAlternative);
    }


    @Test
    public void bookGrammar__bookRule__alternativeOne() throws RecognitionException {
        String input = "shortcut povmal ,  povidky Malostranske , Jan Neruda, 1878";
        String startRuleName = "book";
        GrammarDTO grammarDTO = prepareGrammar(TestGrammars.bookGrammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());

        ParserRule examinedRule = (ParserRule) grammarDTO.grammarInfo.rules.get("book");
        Alternative selectedAlternative = AlternativeResolver.selectAlternative(examinedRule.alternatives, grammarDTO.ast.children, ruleNames).first;

        int indexOfAlternative = examinedRule.alternatives.indexOf(selectedAlternative);
        assertEquals(1, indexOfAlternative);
    }

    @Test
    public void expandListTest__nothingToExpand() throws RecognitionException {
        String input = "{a,{},c}";
        String startRuleName = "compilationUnit";
        GrammarDTO grammarDTO = prepareGrammar(TestGrammars.setGrammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());
        ParserRule parserRule = (ParserRule) grammarDTO.grammarInfo.rules.get("compilationUnit");
        List<Alternative> result = AlternativeResolver.expandList(parserRule.alternatives);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).elements
                .size());
    }

    @Test
    public void expandListTest__twoAlternativesOutOfOne() throws RecognitionException {
        String input = "{a,{},c}";
        String startRuleName = "compilationUnit";
        GrammarDTO grammarDTO = prepareGrammar(TestGrammars.setGrammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());
        ParserRule parserRule = (ParserRule) grammarDTO.grammarInfo.rules.get("set");
        List<Alternative> result = AlternativeResolver.expandList(parserRule.alternatives);
        assertEquals(2, result.size());
    }


    @Test
    public void expandListTest__expandInnerBlockRule() throws RecognitionException {
        String input = "shortcut povmal ,  povidky Malostranske , Jan Neruda, 1878";
        String startRuleName = "book";
        GrammarDTO grammarDTO = prepareGrammar(TestGrammars.bookGrammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());

        ParserRule examinedRule = (ParserRule) grammarDTO.grammarInfo.rules.get("book");
        List<Alternative> result = AlternativeResolver.expandList(examinedRule.alternatives);
        assertEquals(4, result.size());
    }

    @Test
    public void expandListTest__nestedBlockGrammar() throws RecognitionException {
        String input = "c";
        String startRuleName = "r";
        GrammarDTO grammarDTO = prepareGrammar(TestGrammars.nestedBlockGrammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());

        ParserRule examinedRule = (ParserRule) grammarDTO.grammarInfo.rules.get("r");
        List<Alternative> result = AlternativeResolver.expandList(examinedRule.alternatives);
        assertEquals(4, result.size());
    }

}
