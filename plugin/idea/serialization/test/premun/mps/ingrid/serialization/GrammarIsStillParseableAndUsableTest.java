package premun.mps.ingrid.serialization;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.tool.Grammar;
import org.junit.Test;
import premun.mps.ingrid.formatter.boundary.FormatExtractor;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.model.utils.Pair;
import premun.mps.ingrid.parser.GrammarParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static premun.mps.ingrid.model.utils.Pair.pair;

/**
 * Tests that verify that the grammar is still parseable by antlr even after serialization.
 *
 * @author dkozak
 */
public class GrammarIsStillParseableAndUsableTest {

    @Test
    public void setGrammar() {
        String input = "{\n" +
                "  a,\n" +
                "  b,\n" +
                "  c\n" +
                "}\n" +
                "\n";
        serializeAndParseGrammar(TestGrammars.setGrammar, input);

    }

    @Test
    public void exprGrammar() {
        String input = "(\n1\n+\n1)*2";
        serializeAndParseGrammar(TestGrammars.expressionGrammar, input);
    }

    @Test
    public void bookGrammar() {
        String input = "shortcut povmal ,  povidky Malostranske , Jan Neruda";
        serializeAndParseGrammar(TestGrammars.bookGrammar, input);
    }

    @Test
    public void simpleGrammar() {
        String input = "efg}";
        serializeAndParseGrammar(TestGrammars.simpleGrammar, input);
    }

    @Test
    public void nestedBlockGrammarGrammar() {
        String input = "c";
        serializeAndParseGrammar(TestGrammars.nestedBlockGrammar, input);
    }

    @Test
    public void CimpleGrammar() {
        String input = "fn fib(i){\n" +
                "    if(i < 2){\n" +
                "        return 1;\n" +
                "    } else {\n" +
                "        return fib(i - 1)  + fib(i - 2);\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "i = 10;\n" +
                "\n" +
                "for(j = 0; j < i ; j = j+1){\n" +
                "    res = fib(j);\n" +
                "    print res;\n" +
                "}\n";

        serializeAndParseGrammar(TestGrammars.loadCimple(), input);
    }

    @Test
    public void Java9Grammar() {
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
        serializeAndParseGrammar(TestGrammars.loadJava9(), input, "compilationUnit");
    }

    private void serializeAndParseGrammar(String grammar, String input) {
        serializeAndParseGrammar(grammar, input, null);
    }

    private void serializeAndParseGrammar(String grammar, String input, String rootRule) {
        GrammarParser grammarParser = new GrammarParser(rootRule);
        grammarParser.parseString(grammar);

        String serialized = IngridModelToAntlrSerializer.serializeGrammar(grammarParser.resolveGrammar());

        System.out.println(serialized);

        try {
            Pair<ParseTree, List<String>> resultFromOriginalGrammar = parse(grammar, input, rootRule);
            if (!resultFromOriginalGrammar.second.isEmpty()) {
                throw new IllegalArgumentException("Could not parse the input even with the original grammar, errors: " + resultFromOriginalGrammar.second);
            }

            Pair<ParseTree, List<String>> resultAfterSerialization = parse(serialized, input, rootRule);
            if (!resultAfterSerialization.second.isEmpty()) {
                throw new IllegalArgumentException("Could not parse the input with serialized grammar, errors : " + resultAfterSerialization.second);
            }

            grammarParser = new GrammarParser(rootRule);
            grammarParser.parseString(serialized);

            GrammarInfo grammarInfo = grammarParser.resolveGrammar();

            GrammarInfo result = FormatExtractor.fullyProcessMultipleFiles(grammarInfo, serialized, Collections.singletonList(input));

        } catch (RecognitionException e) {
            e.printStackTrace();
            throw new AssertionError("Grammar was not parsed successfully");
        }
    }

    private Pair<ParseTree, List<String>> parse(String grammar, String input) throws RecognitionException {
        return parse(grammar, input, null);
    }

    private Pair<ParseTree, List<String>> parse(String grammar, String input, String rootRule) throws RecognitionException {
        class CollectionErrorListener extends BaseErrorListener {
            private final List<String> errors = new ArrayList<>();

            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, org.antlr.v4.runtime.RecognitionException e) {
                this.errors.add(msg);
            }
        }

        Grammar antlrGrammar = new Grammar(grammar);
        LexerInterpreter lexerInterpreter = antlrGrammar.createLexerInterpreter(new ANTLRInputStream(input));
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexerInterpreter);
        ParserInterpreter parserInterpreter = antlrGrammar.createParserInterpreter(commonTokenStream);

        int startRuleIndex = 0;
        if (rootRule != null) {
            startRuleIndex = antlrGrammar.getRule(rootRule).index;
        }

        CollectionErrorListener listener = new CollectionErrorListener();
        parserInterpreter.addErrorListener(listener);
        ParserRuleContext ast = parserInterpreter.parse(startRuleIndex);
        return pair(ast, listener.errors);
    }
}
