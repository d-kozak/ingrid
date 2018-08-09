package premun.mps.ingrid.formatter;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import premun.mps.ingrid.formatter.boundary.FormatExtractor;
import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.formatter.utils.Pair;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.model.ParserRule;
import premun.mps.ingrid.parser.GrammarParser;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static premun.mps.ingrid.formatter.utils.FormatInfoMapDump.dump;

/**
 * A set of simple tests that should pass where all we care about is that no exception is thrown,
 * the ouput of the formatting algorithm is not checked yet, because it is not yet known, what it will look like.
 * TODO add more asserts
 *
 * @author dkozak
 * @see FormatExtractor
 */
public class FormatExtractorBasicTest {

    private static void printFormatInfo(Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> formatInfoMap) {
        System.out.println(
                formatInfoMap.entrySet()
                             .stream()
                             .map(entry -> entry.getKey() + " = " + entry.getValue())
                             .collect(Collectors.joining(",\n"))
        );
    }

    @Test
    public void setGrammarEmptySet() throws RecognitionException {
        Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> formatInfoMap = extractFormat("{}", TestGrammars.setGrammar);
        printFormatInfo(formatInfoMap);

        assertEquals(2, formatInfoMap.size());
    }

    @Test
    public void setGrammarSimpleInput() throws RecognitionException {
        Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> formatInfoMap = extractFormat("{1,2,3}", TestGrammars.setGrammar);
        printFormatInfo(formatInfoMap);
        assertEquals(5, formatInfoMap.size());
    }

    @Test
    public void setGrammarNestedInput() throws RecognitionException {
        Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> formatInfoMap = extractFormat("{1,{a,b,c},3}", TestGrammars.setGrammar);
        printFormatInfo(formatInfoMap);
        assertEquals(6, formatInfoMap.size());
    }

    @Test
    public void setGrammarNestedInputMoreComplex() throws RecognitionException {
        Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> formatInfoMap = extractFormat("{1,{a,b,c},{{},{a,b,c}}}", TestGrammars.setGrammar);
        printFormatInfo(formatInfoMap);
        assertEquals(7, formatInfoMap.size());
    }

    @Test
    public void setGrammarFormatted() throws RecognitionException {
        String input = "{\n" +
                "\ta,\n" +
                "\t{a,b,c},\n" +
                "\tc\n" +
                "}\n";
        Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> formatInfoMap = extractFormat(input, TestGrammars.setGrammar);
        printFormatInfo(formatInfoMap);
    }

    @Test
    public void expressionGrammarVerySimple() throws RecognitionException {
        Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> formatInfoMap = extractFormat("(1 + 1) * 2", TestGrammars.expressionGrammar);
        printFormatInfo(formatInfoMap);
    }

    private Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> extractFormat(String input, String grammar) throws RecognitionException {
        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(grammar);
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();
        return FormatExtractor.extract(grammarInfo, grammar, input);
    }

    @Test
    public void expressionGrammarMoreComplex() throws RecognitionException {
        Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> formatInfoMap = extractFormat("((2*1) + 1) * 2", TestGrammars.expressionGrammar);
        printFormatInfo(formatInfoMap);
    }

    @Test
    public void setGrammarSimpleFormat() throws RecognitionException {
        String input = "{\n" +
                "  a,\n" +
                "  b,\n" +
                "  c\n" +
                "}\n" +
                "\n";
        Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> formatInfoMap = extractFormat(input, TestGrammars.setGrammar);
        printFormatInfo(formatInfoMap);
    }


    @Test
    public void cimpleFibonacci() throws RecognitionException {
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

        String cimple = TestGrammars.loadCimple();
        Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> formatInfoMap = extractFormat(input, cimple);

        dump(formatInfoMap);
    }


    @Test
    public void cimpleFibonacciMinimalFormat() throws RecognitionException {
        String input = "fn fib(i){if(i < 2){return 1;}else{return fib(i - 1)  + fib(i - 2);}}\n" +
                "i = 10;\n" +
                "for(j = 0; j < i ; j = j+1){res = fib(j);print res;}";

        String cimple = TestGrammars.loadCimple();
        Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> formatInfoMap = extractFormat(input, cimple);

        dump(formatInfoMap);
    }


    @Test
    public void cimpleFibonacciNoFormat() throws RecognitionException {
        String input = "fn fib(i){if(i < 2){return 1;}else{return fib(i - 1) + fib(i - 2);}} i = 10; for(j = 0; j < i ; j = j+1){res = fib(j);print res;}";

        String cimple = TestGrammars.loadCimple();
        Map<Pair<ParserRule, Alternative>, List<RuleFormatInfo>> formatInfoMap = extractFormat(input, cimple);

        dump(formatInfoMap);
    }


}
