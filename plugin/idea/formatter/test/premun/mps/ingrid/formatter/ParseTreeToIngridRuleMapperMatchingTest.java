package premun.mps.ingrid.formatter;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.Ignore;
import org.junit.Test;
import premun.mps.ingrid.formatter.model.GrammarDTO;
import premun.mps.ingrid.formatter.model.MatchInfo;
import premun.mps.ingrid.formatter.utils.Pair;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.ParserRule;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static premun.mps.ingrid.formatter.model.GrammarDTO.prepareGrammar;

/**
 * Verifies that resolving algotihm used in ParseTreeToIngridRuleMapper
 *
 * @author dkozak
 * @see ParseTreeToIngridRuleMapper
 */
public class ParseTreeToIngridRuleMapperMatchingTest {

    @Test
    public void setGrammar__ruleCompilationUnit() throws RecognitionException {
        String input = "{}";
        String startRuleName = "compilationUnit";
        GrammarDTO grammarDTO = prepareGrammar(TestGrammars.setGrammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());
        ParserRule examinedRule = (ParserRule) grammarDTO.grammarInfo.rules.get("compilationUnit");

        Pair<Alternative, List<MatchInfo>> selectedAlternative = ParseTreeToIngridRuleMapper.resolve(examinedRule.alternatives, grammarDTO.ast.children, ruleNames, grammarDTO.tokens);
        List<MatchInfo> matchInfoList = selectedAlternative.second;

        // one element
        assertEquals(1, matchInfoList.size());
        // matches only once
        assertEquals(1, matchInfoList.get(0).times);

        assertEquals("set", matchInfoList.get(0).rule.name);

        // make sure correct alternative was chosen (in this case there is only one though)
        Alternative emptySetAlternative = ((ParserRule) grammarDTO.grammarInfo.rules.get("compilationUnit")).alternatives.get(0);
        assertEquals(
                emptySetAlternative,
                selectedAlternative.first
        );

    }


    @Test
    public void setGrammar__ruleSet__flatSet() throws RecognitionException {
        String input = "{a,b,c}";
        String startRuleName = "compilationUnit";
        GrammarDTO grammarDTO = prepareGrammar(TestGrammars.setGrammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());
        ParserRule examinedRule = (ParserRule) grammarDTO.grammarInfo.rules.get("set");

        // get wanted subtree - set
        ParserRuleContext setSubtree = ((ParserRuleContext) grammarDTO.ast.getChild(0));

        Pair<Alternative, List<MatchInfo>> selectedAlternative = ParseTreeToIngridRuleMapper.resolve(examinedRule.alternatives, setSubtree.children, ruleNames, grammarDTO.tokens);
        List<MatchInfo> matchInfoList = selectedAlternative.second;

        // four elements - lbracket elem blk rbracket
        assertEquals(4, matchInfoList.size());

        // lbracket matched only once
        assertEquals(1, matchInfoList.get(0).times);

        // elem matched only once
        assertEquals(1, matchInfoList.get(1).times);

        // blk matched twice
        assertEquals(2, matchInfoList.get(2).times);

        // rbracket matched once
        assertEquals(1, matchInfoList.get(3).times);

        // check that correct alternative was selected
        assertEquals(
                examinedRule.alternatives.get(1),
                selectedAlternative.first
        );

    }

    @Test
    public void setGrammar__ruleSet__emptySet() throws RecognitionException {
        String input = "{}";
        String startRuleName = "compilationUnit";
        GrammarDTO grammarDTO = prepareGrammar(TestGrammars.setGrammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());
        ParserRule examinedRule = (ParserRule) grammarDTO.grammarInfo.rules.get("set");

        // get wanted subtree - set
        ParserRuleContext setSubtree = ((ParserRuleContext) grammarDTO.ast.getChild(0));

        Pair<Alternative, List<MatchInfo>> selectedAlternative = ParseTreeToIngridRuleMapper.resolve(examinedRule.alternatives, setSubtree.children, ruleNames, grammarDTO.tokens);
        List<MatchInfo> matchInfoList = selectedAlternative.second;

        // two elements - lbracket rbracket
        assertEquals(2, matchInfoList.size());

        // lbracket matched only once
        assertEquals(1, matchInfoList.get(0).times);

        // rbracket matched once
        assertEquals(1, matchInfoList.get(1).times);

        // check that correct alternative was selected
        assertEquals(
                examinedRule.alternatives.get(0),
                selectedAlternative.first
        );

    }

    @Test
    public void setGrammar__ruleSet__nestedSet() throws RecognitionException {
        String input = "{a,{1,2},c}";
        String startRuleName = "compilationUnit";
        GrammarDTO grammarDTO = prepareGrammar(TestGrammars.setGrammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());
        ParserRule examinedRule = (ParserRule) grammarDTO.grammarInfo.rules.get("set");

        // get wanted subtree - set
        ParserRuleContext setSubtree = ((ParserRuleContext) grammarDTO.ast.getChild(0));

        Pair<Alternative, List<MatchInfo>> selectedAlternative = ParseTreeToIngridRuleMapper.resolve(examinedRule.alternatives, setSubtree.children, ruleNames, grammarDTO.tokens);
        List<MatchInfo> matchInfoList = selectedAlternative.second;

        assertEquals(4, matchInfoList.size());

        MatchInfo innerSetMatchInfo = matchInfoList.get(2);
        assertTrue(innerSetMatchInfo.rule instanceof ParseTreeToIngridRuleMapper.SerializedParserRule); // our own abstraction for handling block rules

        // blk should match: ',' elem ',' elem
        assertEquals(4, innerSetMatchInfo.matched.size());
        assertEquals(",", innerSetMatchInfo.matched.get(0)
                                                   .getText());
        assertEquals("elem", grammarDTO.grammar.getRuleNames()[((ParserRuleContext) innerSetMatchInfo.matched.get(1)).getRuleIndex()]);
        assertEquals(",", innerSetMatchInfo.matched.get(2)
                                                   .getText());
        assertEquals("elem", grammarDTO.grammar.getRuleNames()[((ParserRuleContext) innerSetMatchInfo.matched.get(3)).getRuleIndex()]);

    }


    @Test
    public void setGrammar__ruleSet__nestedSet__depthThree() throws RecognitionException {
        String input = "{a,{1,{x}},c}";
        String startRuleName = "compilationUnit";
        GrammarDTO grammarDTO = prepareGrammar(TestGrammars.setGrammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());
        ParserRule examinedRule = (ParserRule) grammarDTO.grammarInfo.rules.get("set");

        // get wanted subtree - set
        ParserRuleContext setSubtree = ((ParserRuleContext) grammarDTO.ast.getChild(0));

        Pair<Alternative, List<MatchInfo>> selectedAlternative = ParseTreeToIngridRuleMapper.resolve(examinedRule.alternatives, setSubtree.children, ruleNames, grammarDTO.tokens);
        List<MatchInfo> matchInfoList = selectedAlternative.second;

        assertEquals(4, matchInfoList.size());

        MatchInfo innerSetMatchInfo = matchInfoList.get(2);
        assertTrue(innerSetMatchInfo.rule instanceof ParseTreeToIngridRuleMapper.SerializedParserRule); // our own abstraction for handling block rules

        // blk should match: ',' elem ',' elem
        assertEquals(4, innerSetMatchInfo.matched.size());
        assertEquals(",", innerSetMatchInfo.matched.get(0)
                                                   .getText());
        assertEquals("elem", grammarDTO.grammar.getRuleNames()[((ParserRuleContext) innerSetMatchInfo.matched.get(1)).getRuleIndex()]);
        assertEquals(",", innerSetMatchInfo.matched.get(2)
                                                   .getText());
        ParserRuleContext mostNestedSetRule = ((ParserRuleContext) innerSetMatchInfo.matched.get(1));
        // now elem -> set
        assertEquals(1, mostNestedSetRule.children.size());
        assertEquals("set", grammarDTO.grammar.getRuleNames()[((ParserRuleContext) mostNestedSetRule.children.get(0)).getRuleIndex()]);


    }

    @Test
    public void java9Grammar__simpleClass() throws RecognitionException {
        String java9grammar = TestGrammars.loadJava9();
        String startRuleName = "compilationUnit";
        String input = "  public abstract class Animal<T> {\n" +
                "            private String name;\n" +
                "            private int age;\n" +
                "            private T secret;\n" +
                "\n" +
                "            public abstract void makeSound();\n" +
                "        }";


        GrammarDTO grammarDTO = prepareGrammar(java9grammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());
        ParserRule compilationUnit = (ParserRule) grammarDTO.grammarInfo.rules.get("compilationUnit");


        Pair<Alternative, List<MatchInfo>> pair = ParseTreeToIngridRuleMapper.resolve(compilationUnit.alternatives, grammarDTO.ast.children, ruleNames, grammarDTO.tokens);

        assertEquals(0, compilationUnit.alternatives.indexOf(pair.first));
    }


    @Ignore // TODO sadly this test does not work, the grammar cannot be parsed
    @Test
    public void cpp14Grammar__crazyTemplateExample() throws RecognitionException {
        String cpp14grammar = TestGrammars.loadCpp14();
        String startRuleName = "translationUnit";
        String input = TestGrammars.loadResource("/brigand.hpp");

        GrammarDTO grammarDTO = prepareGrammar(cpp14grammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());
        ParserRule translationUnit = (ParserRule) grammarDTO.grammarInfo.rules.get("translationUnit");


        Pair<Alternative, List<MatchInfo>> pair = ParseTreeToIngridRuleMapper.resolve(translationUnit.alternatives, grammarDTO.ast.children, ruleNames, grammarDTO.tokens);

        System.out.println("Made it!");
    }
}
