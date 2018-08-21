package premun.mps.ingrid.formatter;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.tool.Grammar;
import org.antlr.v4.tool.LexerGrammar;
import org.junit.Test;
import premun.mps.ingrid.formatter.utils.TestGrammars;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author dkozak
 * @see InterpretingParser
 */
public class InterpretingParserTest {

    @Test
    public void antlrOfficialExample() throws RecognitionException {
        String startRule = "s";
        String input = "abbaaabbac";

        LexerGrammar lg = new LexerGrammar(
                "lexer grammar L;\n" +
                        "A : 'a' ;\n" +
                        "B : 'b' ;\n" +
                        "C : 'c' ;\n");
        Grammar g = new Grammar(
                "parser grammar T;\n" +
                        "s : (A|B)* C ;\n",
                lg);
        LexerInterpreter lexEngine =
                lg.createLexerInterpreter(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexEngine);
        ParserInterpreter parser = g.createParserInterpreter(tokens);
        ParseTree t = parser.parse(g.rules.get(startRule).index);
    }

    /**
     * Checks whether sentences in set language were parsed correctly
     */
    @Test
    public void parsingSetLanguage() throws RecognitionException {
        Grammar grammar = new Grammar(TestGrammars.setGrammar);
        String input = "{a,b,{}}";
        ParseTree parseTree = InterpretingParser.parse(grammar, input, "compilationUnit");
        assertEquals(2, parseTree.getChildCount()); // set EOF
        ParseTree set = parseTree.getChild(0);

        assertEquals(7, set.getChildCount()); // { a , b , {} } <- 7 children (empty set counts as one)

        assertTrue(set.getChild(0) instanceof TerminalNode);
        assertEquals("{", ((TerminalNode) set.getChild(0)).getSymbol()
                                                          .getText());

        assertTrue(set.getChild(1) instanceof ParserRuleContext);
        assertEquals(2, ((ParserRuleContext) set.getChild(1)).getRuleIndex()); // 2 == index of collection rule

        assertTrue(set.getChild(2) instanceof TerminalNode);
        assertEquals(",", (((TerminalNode) set.getChild(2)).getSymbol()
                                                           .getText()));

        assertTrue(set.getChild(3) instanceof ParserRuleContext);
        assertEquals(2, ((ParserRuleContext) set.getChild(3)).getRuleIndex());// 2 == index of collection rule

        assertTrue(set.getChild(4) instanceof TerminalNode);
        assertEquals(",", ((TerminalNode) set.getChild(4)).getSymbol()
                                                          .getText());

        assertTrue(set.getChild(5) instanceof ParserRuleContext);
        assertEquals(2, ((ParserRuleContext) set.getChild(5)).getRuleIndex());// 2 == index of set rule

        ParseTree elemAsSetWrapper = set.getChild(5); // collection
        assertEquals(1, elemAsSetWrapper.getChildCount()); // collection should have one child, the inner set
        ParseTree innerSet = elemAsSetWrapper.getChild(0);
        assertTrue(innerSet instanceof ParserRuleContext);
        assertEquals(2, innerSet.getChildCount()); // inner set has just two children, the curly brackets { }

        assertTrue(set.getChild(6) instanceof TerminalNode && "}".equals(((TerminalNode) set.getChild(6)).getSymbol()
                                                                                                         .getText()));
    }

    @Test
    public void parseTwoFiles() throws RecognitionException {
        List<String> grammars = TestGrammars.loadJava();
        String animalClass = "import java.util.*;\n" +
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
        InterpretingParser.InterpretingParserResult pair = InterpretingParser.tokenizeAndParse(grammars.get(0), grammars.get(1), animalClass, "compilationUnit");

    }
}
