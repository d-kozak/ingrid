package premun.mps.ingrid.formatter;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import premun.mps.ingrid.formatter.model.GrammarDTO;
import premun.mps.ingrid.formatter.model.MatchInfo;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.ParserRule;
import premun.mps.ingrid.model.utils.Pair;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

        ParseTreeToIngridRuleMapper parseTreeToIngridRuleMapper = new ParseTreeToIngridRuleMapper(grammarDTO.tokens, Arrays.asList(grammarDTO.grammar.getRuleNames()));
        Pair<Alternative, List<MatchInfo>> selectedAlternative = parseTreeToIngridRuleMapper.resolve(examinedRule.alternatives, grammarDTO.ast.children).first;
        List<MatchInfo> matchInfoList = selectedAlternative.second;

        // one element
        assertEquals(1, matchInfoList.size());
        // matches only once
        assertEquals(1, matchInfoList.get(0)
                                     .times());

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

        ParseTreeToIngridRuleMapper parseTreeToIngridRuleMapper = new ParseTreeToIngridRuleMapper(grammarDTO.tokens, Arrays.asList(grammarDTO.grammar.getRuleNames()));
        Pair<Alternative, List<MatchInfo>> selectedAlternative = parseTreeToIngridRuleMapper.resolve(examinedRule.alternatives, setSubtree.children).first;
        List<MatchInfo> matchInfoList = selectedAlternative.second;

        // four elements - lbracket collection blk rbracket
        assertEquals(4, matchInfoList.size());

        // lbracket matched only once
        assertEquals(1, matchInfoList.get(0)
                                     .times());

        // collection matched only once
        assertEquals(1, matchInfoList.get(1)
                                     .times());

        // blk matched twice
        assertEquals(2, matchInfoList.get(2)
                                     .times());

        // rbracket matched once
        assertEquals(1, matchInfoList.get(3)
                                     .times());

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

        ParseTreeToIngridRuleMapper parseTreeToIngridRuleMapper = new ParseTreeToIngridRuleMapper(grammarDTO.tokens, Arrays.asList(grammarDTO.grammar.getRuleNames()));
        Pair<Alternative, List<MatchInfo>> selectedAlternative = parseTreeToIngridRuleMapper.resolve(examinedRule.alternatives, setSubtree.children).first;
        List<MatchInfo> matchInfoList = selectedAlternative.second;

        // two elements - lbracket rbracket
        assertEquals(2, matchInfoList.size());

        // lbracket matched only once
        assertEquals(1, matchInfoList.get(0)
                                     .times());

        // rbracket matched once
        assertEquals(1, matchInfoList.get(1)
                                     .times());

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

        ParseTreeToIngridRuleMapper parseTreeToIngridRuleMapper = new ParseTreeToIngridRuleMapper(grammarDTO.tokens, Arrays.asList(grammarDTO.grammar.getRuleNames()));
        Pair<Alternative, List<MatchInfo>> selectedAlternative = parseTreeToIngridRuleMapper.resolve(examinedRule.alternatives, setSubtree.children).first;
        List<MatchInfo> matchInfoList = selectedAlternative.second;

        assertEquals(4, matchInfoList.size());

        MatchInfo innerSetMatchInfo = matchInfoList.get(2);
        assertTrue(innerSetMatchInfo.rule instanceof ParseTreeToIngridRuleMapper.SerializedParserRule); // our own abstraction for handling block rules

        // (',' collection) should be there twice
        assertEquals(2, innerSetMatchInfo.matched.size());

        List<ParseTree> blk = innerSetMatchInfo.matched.stream()
                                                       .flatMap(Collection::stream)
                                                       .collect(Collectors.toList());

        // blk should match: ',' collection ',' collection
        assertEquals(4, blk.size());
        assertEquals(",", blk.get(0)
                             .getText());
        assertEquals("collection", grammarDTO.grammar.getRuleNames()[((ParserRuleContext) blk.get(1)).getRuleIndex()]);
        assertEquals(",", blk.get(2)
                             .getText());
        assertEquals("collection", grammarDTO.grammar.getRuleNames()[((ParserRuleContext) blk.get(3)).getRuleIndex()]);

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

        ParseTreeToIngridRuleMapper parseTreeToIngridRuleMapper = new ParseTreeToIngridRuleMapper(grammarDTO.tokens, Arrays.asList(grammarDTO.grammar.getRuleNames()));
        Pair<Alternative, List<MatchInfo>> selectedAlternative = parseTreeToIngridRuleMapper.resolve(examinedRule.alternatives, setSubtree.children).first;
        List<MatchInfo> matchInfoList = selectedAlternative.second;

        assertEquals(4, matchInfoList.size());

        MatchInfo innerSetMatchInfo = matchInfoList.get(2);
        assertTrue(innerSetMatchInfo.rule instanceof ParseTreeToIngridRuleMapper.SerializedParserRule); // our own abstraction for handling block rules

        // (',' collection) should be there twice
        assertEquals(2, innerSetMatchInfo.matched.size());

        List<ParseTree> blk = innerSetMatchInfo.matched.stream()
                                                       .flatMap(Collection::stream)
                                                       .collect(Collectors.toList());

        // blk should match: ',' collection ',' collection
        assertEquals(4, blk.size());
        assertEquals(",", blk.get(0)
                             .getText());
        assertEquals("collection", grammarDTO.grammar.getRuleNames()[((ParserRuleContext) blk.get(1)).getRuleIndex()]);
        assertEquals(",", blk.get(2)
                             .getText());
        ParserRuleContext mostNestedSetRule = ((ParserRuleContext) blk.get(1));
        // now collection -> set
        assertEquals(1, mostNestedSetRule.children.size());
        assertEquals("set", grammarDTO.grammar.getRuleNames()[((ParserRuleContext) mostNestedSetRule.getChild(0)).getRuleIndex()]);


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

        ParseTreeToIngridRuleMapper ruleMapper = new ParseTreeToIngridRuleMapper(grammarDTO.tokens, Arrays.asList(grammarDTO.grammar.getRuleNames()));
        Pair<Alternative, List<MatchInfo>> pair = ruleMapper.resolve(compilationUnit.alternatives, grammarDTO.ast.children).first;

        assertEquals(0, compilationUnit.alternatives.indexOf(pair.first));
    }

    @Test
    public void java9Grammar__simpleClassWithComments() throws RecognitionException {
        String java9grammar = TestGrammars.loadJava9();
        String startRuleName = "compilationUnit";
        String input = "import java.util.*;\n" +
                "\n" +
                "\n" +
                "import static java.util.stream.Collectors.toList;\n" +
                "\n" +
                "\n" +
                "/**\n" +
                " * Computes how the parse tree passed in corresponds to the Ingrid rule that matched it.\n" +
                " * <p>\n" +
                " * The algorithm works as follows.\n" +
                " * <p>\n" +
                " * It first have to expand the alternatives into separate rules.\n" +
                " * If it encounters a block rule, it also separates each of it's alternatives into rules and\n" +
                " * it adds a special SerializedParserRule  as the wrapper of the content of the block rule\n" +
                " * to clearly separate it from other kind of rules. If there are any inner blocks such as\n" +
                " * (a | (b | c)), this algorithm generates two SerializedParserRules on the route from root to b or c.\n" +
                " * As this is not necessary, a flattening happens afterwards that removes these unnecessary layers.\n" +
                " * <p>\n" +
                " * When the rule in expanded, it uses the following algorithm to figure out which of the\n" +
                " * alternatives matches the ast.\n" +
                " * Foreach ruleReference in rule.handle:\n" +
                " * consume as much of the input ast as possible\n" +
                " * if you did not manage to consume token/rule that was obligatory:\n" +
                " * return error\n" +
                " * save information about what you matched\n" +
                " * <p>\n" +
                " * if whole ast was matched:\n" +
                " * return information about matching\n" +
                " * else:\n" +
                " * return error\n" +
                " *\n" +
                " * @author dkozak\n" +
                " */\n" +
                "public class Animal {\n" +
                "\n" +
                "    private final String name;\n" +
                "    \n" +
                "    public static int age;\n" +
                "    \n" +
                "\n" +
                "    public Animal(String name){\n" +
                "        this.name = name;\n" +
                "    }\n" +
                "    \n" +
                "    public int count(){\n" +
                "        if(age == 0){\n" +
                "            return -1;\n" +
                "        }\n" +
                "        for(int i = 1 ; i <= age; i++){\n" +
                "            System.out.println(i);\n" +
                "        }\n" +
                "        return age + 1;\n" +
                "    }\n" +
                "    \n" +
                "    public static void main(String[] args){\n" +
                "        Animal animal = new Animal(\"Bobik\");\n" +
                "        Animal.age = 42;\n" +
                "        animal.count();\n" +
                "    }\n" +
                "}\n";

        GrammarDTO grammarDTO = prepareGrammar(java9grammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());
        ParserRule compilationUnit = (ParserRule) grammarDTO.grammarInfo.rules.get("compilationUnit");

        ParseTreeToIngridRuleMapper ruleMapper = new ParseTreeToIngridRuleMapper(grammarDTO.tokens, Arrays.asList(grammarDTO.grammar.getRuleNames()));
        Pair<Alternative, List<MatchInfo>> pair = ruleMapper.resolve(compilationUnit.alternatives, grammarDTO.ast.children).first;

        assertEquals(0, compilationUnit.alternatives.indexOf(pair.first));
    }

}
