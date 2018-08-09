package premun.mps.ingrid.formatter;

import org.junit.Test;
import premun.mps.ingrid.formatter.boundary.FormatExtractor;
import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.formatter.utils.Pair;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.model.ParserRule;
import premun.mps.ingrid.parser.GrammarParser;

import java.util.Map;

import static premun.mps.ingrid.formatter.boundary.FormatExtractor.*;
import static premun.mps.ingrid.formatter.utils.FormatInfoAsserts.verifyFormatInfoMap;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.AppliedRule.rule;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.*;
import static premun.mps.ingrid.formatter.utils.FormatInfoMapDump.dumpSimplifiedMap;

/**
 * A set of simple tests that should pass where all we care about is that no exception is thrown,
 * the ouput of the formatting algorithm is not checked yet, because it is not yet known, what it will look like.
 * TODO add more asserts
 *
 * @author dkozak
 * @see FormatExtractor
 */
public class FormatExtractorBasicTest {

    @Test
    public void setGrammarEmptySet() {
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat("{}", TestGrammars.setGrammar);

        dumpSimplifiedMap(formatInfoMap);

        verifyFormatInfoMap(
                formatInfoMap,
                rules(
                        rule("set", 0,
                                handle(
                                        elem("{", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("}", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("compilationUnit", 0,
                                handle(
                                        elem("set", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );
    }

    @Test
    public void setGrammarSimpleInput() {
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat("{1,2,3}", TestGrammars.setGrammar);

        dumpSimplifiedMap(formatInfoMap);

        verifyFormatInfoMap(
                formatInfoMap,
                rules(
                        rule("simpleElement", 0,
                                handle(
                                        elem("ELEM", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "elem", 0,
                                handle(
                                        elem("simpleElement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("set", 1,
                                handle(
                                        elem("{", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("elem", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("set_block_2_1_alt_0", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("}", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "compilationUnit", 0,
                                handle(
                                        elem("set", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "set_block_2_1", 0,
                                handle(
                                        elem(",", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("elem", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );
    }

    @Test
    public void setGrammarNestedInput() {
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat("{1,{a,b,c},3}", TestGrammars.setGrammar);

        dumpSimplifiedMap(formatInfoMap);

        verifyFormatInfoMap(
                formatInfoMap,
                rules(
                        rule("simpleElement", 0,
                                handle(
                                        elem("ELEM", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "elem", 0,
                                handle(
                                        elem("simpleElement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("elem", 1,
                                handle(
                                        elem("set", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("set", 1,
                                handle(
                                        elem("{", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("elem", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("set_block_2_1_alt_0", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("}", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "compilationUnit", 0,
                                handle(
                                        elem("set", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "set_block_2_1", 0,
                                handle(
                                        elem(",", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("elem", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );
    }

    @Test
    public void setGrammarNestedInputMoreComplex() {
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat("{1,{a,b,c},{{},{a,b,c}}}", TestGrammars.setGrammar);

        dumpSimplifiedMap(formatInfoMap);

        verifyFormatInfoMap(
                formatInfoMap,
                rules(
                        rule("simpleElement", 0,
                                handle(
                                        elem("ELEM", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "elem", 0,
                                handle(
                                        elem("simpleElement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("elem", 1,
                                handle(
                                        elem("set", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "set", 0,
                                handle(
                                        elem("{", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("}", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("set", 1,
                                handle(
                                        elem("{", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("elem", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("set_block_2_1_alt_0", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("}", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "compilationUnit", 0,
                                handle(
                                        elem("set", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "set_block_2_1", 0,
                                handle(
                                        elem(",", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("elem", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );
    }

    @Test
    public void setGrammarFormatted() {
        String input = "{\n" +
                "\ta,\n" +
                "\t{a,b,c},\n" +
                "\tc\n" +
                "}\n";
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat(input, TestGrammars.setGrammar);

        dumpSimplifiedMap(formatInfoMap);

        verifyFormatInfoMap(
                formatInfoMap,
                rules(
                        rule("simpleElement", 0,
                                handle(
                                        elem("ELEM", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "elem", 0,
                                handle(
                                        elem("simpleElement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("elem", 1,
                                handle(
                                        elem("set", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("set", 1,
                                handle(
                                        elem("{", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("elem", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("set_block_2_1_alt_0", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(true)),
                                        elem("}", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "compilationUnit", 0,
                                handle(
                                        elem("set", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "set_block_2_1", 0,
                                handle(
                                        elem(",", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("elem", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );
    }

    @Test
    public void setGrammarSimpleFormat() {
        String input = "{\n" +
                "  a,\n" +
                "  b,\n" +
                "  c\n" +
                "}\n" +
                "\n";
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat(input, TestGrammars.setGrammar);
        dumpSimplifiedMap(formatInfoMap);

        verifyFormatInfoMap(
                formatInfoMap,
                rules(
                        rule("simpleElement", 0,
                                handle(
                                        elem("ELEM", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "elem", 0,
                                handle(
                                        elem("simpleElement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("set", 1,
                                handle(
                                        elem("{", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("elem", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("set_block_2_1_alt_0", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(true)),
                                        elem("}", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "compilationUnit", 0,
                                handle(
                                        elem("set", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "set_block_2_1", 0,
                                handle(
                                        elem(",", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("elem", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );
    }


    @Test
    public void expressionGrammarVerySimple() {
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat("(1 + 1) * 2", TestGrammars.expressionGrammar);
        dumpSimplifiedMap(formatInfoMap);

        verifyFormatInfoMap(
                formatInfoMap,
                rules(
                        rule(
                                "expr", 5,
                                handle(
                                        elem("INT", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 3,
                                handle(
                                        elem("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("+", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 1,
                                handle(
                                        elem("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("*", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 0,
                                handle(
                                        elem("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expr", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );
    }

    @Test
    public void expressionGrammarMoreComplex() {
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat("((2*1) + 1)*2", TestGrammars.expressionGrammar);
        dumpSimplifiedMap(formatInfoMap);

        verifyFormatInfoMap(
                formatInfoMap,
                rules(
                        rule(
                                "expr", 5,
                                handle(
                                        elem("INT", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 3,
                                handle(
                                        elem("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("+", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 1,
                                handle(
                                        elem("expr", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("*", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 0,
                                handle(
                                        elem("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expr", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );
    }


    @Test
    public void cimpleFibonacci() {
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
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat(input, cimple);

        dumpSimplifiedMap(formatInfoMap);
    }


    @Test
    public void cimpleFibonacciMinimalFormat() {
        String input = "fn fib(i){if(i < 2){return 1;}else{return fib(i - 1)  + fib(i - 2);}}\n" +
                "i = 10;\n" +
                "for(j = 0; j < i ; j = j+1){res = fib(j);print res;}";

        String cimple = TestGrammars.loadCimple();
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat(input, cimple);

        dumpSimplifiedMap(formatInfoMap);
    }


    @Test
    public void cimpleFibonacciNoFormat() {
        String input = "fn fib(i){if(i < 2){return 1;}else{return fib(i - 1) + fib(i - 2);}} i = 10; for(j = 0; j < i ; j = j+1){res = fib(j);print res;}";

        String cimple = TestGrammars.loadCimple();
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat(input, cimple);

        dumpSimplifiedMap(formatInfoMap);
    }


    private Map<Pair<String, Integer>, RuleFormatInfo> extractFormat(String input, String grammar) {
        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(grammar);
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();
        Map<Pair<ParserRule, Alternative>, RuleFormatInfo> merge = merge(extract(grammarInfo, grammar, input));
        return asRuleNameAlternativeIndexMap(merge);
    }
}
