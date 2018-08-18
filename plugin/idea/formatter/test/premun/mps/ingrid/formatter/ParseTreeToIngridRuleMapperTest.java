package premun.mps.ingrid.formatter;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.Test;
import premun.mps.ingrid.formatter.model.GrammarDTO;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.ParserRule;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static premun.mps.ingrid.formatter.model.GrammarDTO.prepareGrammar;

/**
 * Tests the alternative resolving process in ParseTreeToIngridRuleMapper.
 * To simplify the test setup, we specify the grammar as string and let Ingrid Parser Module provide us with model to use
 *
 * @author dkozak
 * @see ParseTreeToIngridRuleMapper
 */
public class ParseTreeToIngridRuleMapperTest {

    @Test
    public void simpleGrammar__alternativeZero() throws RecognitionException {
        String input = "efg}";
        String startRuleName = "rule";
        GrammarDTO grammarDTO = prepareGrammar(TestGrammars.simpleGrammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());

        ParserRule examinedRule = (ParserRule) grammarDTO.grammarInfo.rules.get("rule");
        ParseTreeToIngridRuleMapper parseTreeToIngridRuleMapper = new ParseTreeToIngridRuleMapper(grammarDTO.tokens, Arrays.asList(grammarDTO.grammar.getRuleNames()));
        Alternative selectedAlternative = parseTreeToIngridRuleMapper.resolve(examinedRule.alternatives, grammarDTO.ast.children).first.first;

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
        ParseTreeToIngridRuleMapper parseTreeToIngridRuleMapper = new ParseTreeToIngridRuleMapper(grammarDTO.tokens, Arrays.asList(grammarDTO.grammar.getRuleNames()));
        Alternative selectedAlternative = parseTreeToIngridRuleMapper.resolve(examinedRule.alternatives, grammarDTO.ast.children).first.first;

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
        ParseTreeToIngridRuleMapper parseTreeToIngridRuleMapper = new ParseTreeToIngridRuleMapper(grammarDTO.tokens, Arrays.asList(grammarDTO.grammar.getRuleNames()));
        Alternative selectedAlternative = parseTreeToIngridRuleMapper.resolve(examinedRule.alternatives, set.children).first.first;

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
        ParseTreeToIngridRuleMapper parseTreeToIngridRuleMapper = new ParseTreeToIngridRuleMapper(grammarDTO.tokens, Arrays.asList(grammarDTO.grammar.getRuleNames()));
        Alternative selectedAlternative = parseTreeToIngridRuleMapper.resolve(examinedRule.alternatives, set.children).first.first;

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
        ParseTreeToIngridRuleMapper parseTreeToIngridRuleMapper = new ParseTreeToIngridRuleMapper(grammarDTO.tokens, Arrays.asList(grammarDTO.grammar.getRuleNames()));
        Alternative selectedAlternative = parseTreeToIngridRuleMapper.resolve(examinedRule.alternatives, grammarDTO.ast.children).first.first;

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
        ParseTreeToIngridRuleMapper parseTreeToIngridRuleMapper = new ParseTreeToIngridRuleMapper(grammarDTO.tokens, Arrays.asList(grammarDTO.grammar.getRuleNames()));
        Alternative selectedAlternative = parseTreeToIngridRuleMapper.resolve(examinedRule.alternatives, grammarDTO.ast.children).first.first;

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
        List<Alternative> result = ParseTreeToIngridRuleMapper.expandRules(parserRule.alternatives);
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
        List<Alternative> result = ParseTreeToIngridRuleMapper.expandRules(parserRule.alternatives);
        assertEquals(2, result.size());
    }


    @Test
    public void expandListTest__expandInnerBlockRule() throws RecognitionException {
        String input = "shortcut povmal ,  povidky Malostranske , Jan Neruda, 1878";
        String startRuleName = "book";
        GrammarDTO grammarDTO = prepareGrammar(TestGrammars.bookGrammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());

        ParserRule examinedRule = (ParserRule) grammarDTO.grammarInfo.rules.get("book");
        List<Alternative> result = ParseTreeToIngridRuleMapper.expandRules(examinedRule.alternatives);
        assertEquals(4, result.size());
    }

    @Test
    public void expandListTest__nestedBlockGrammar() throws RecognitionException {
        String input = "c";
        String startRuleName = "r";
        GrammarDTO grammarDTO = prepareGrammar(TestGrammars.nestedBlockGrammar, startRuleName, input);
        List<String> ruleNames = Arrays.asList(grammarDTO.grammar.getRuleNames());

        ParserRule examinedRule = (ParserRule) grammarDTO.grammarInfo.rules.get("r");
        List<Alternative> result = ParseTreeToIngridRuleMapper.expandRules(examinedRule.alternatives);
        assertEquals(4, result.size());
    }

    @Test
    public void expandRulesTest__java__unannClassOrInterfaceType() throws RecognitionException {
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

        ParserRule examinedRule = (ParserRule) grammarDTO.grammarInfo.rules.get("unannClassOrInterfaceType");
        List<Alternative> result = ParseTreeToIngridRuleMapper.expandRules(examinedRule.alternatives);

        assertEquals(4, result.size());
        for (Alternative alternative : result) {
            assertEquals(2, alternative.elements.size());
        }
    }

    @Test
    public void expandRules__shouldCreateFourRules() throws RecognitionException {
        String grammar = "grammar Elem ; elem : (foo | bar) (foo|bar)* ; foo : 'foo' ; bar : 'bar'; WS: [ \\t\\n\\r] -> skip ;";
        String input = "foo foo bar bar";
        GrammarDTO grammarDTO = prepareGrammar(grammar, "elem", input);

        ParserRule elem = (ParserRule) grammarDTO.grammarInfo.rules.get("elem");

        List<Alternative> result = ParseTreeToIngridRuleMapper.expandRules(elem.alternatives);
        assertEquals(4, result.size());
        for (Alternative alternative : result) {
            assertEquals(2, alternative.elements.size());
        }
    }

}
