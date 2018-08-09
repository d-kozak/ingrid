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

        verifyFormatInfoMap(
                formatInfoMap,
                rules(
                        rule("forLoop", 0,
                                handle(
                                        elem("for", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 9,
                                handle(
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("<", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("returnStatement", 0,
                                handle(
                                        elem("return", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("variableAssignment", 0,
                                handle(
                                        elem("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("=", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("arguments", 0,
                                handle(
                                        elem("NULL", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("NULL", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("program", 0,
                                handle(
                                        elem("statement", newLine(false), space(true), childrenOnNewLine(true), childrenIndented(false))
                                )
                        ),
                        rule("printStatement", 0,
                                handle(
                                        elem("print", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("ifStatement", 1,
                                handle(
                                        elem("if", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("else", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 17,
                                handle(
                                        elem("NULL", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 16,
                                handle(
                                        elem("NULL", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("INT", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("functionDefinition", 0,
                                handle(
                                        elem("fn", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("parameters", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("block", 0,
                                handle(
                                        elem("{", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("statement", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(true)),
                                        elem("}", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 6,
                                handle(
                                        elem("functionDefinition", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("parameters", 0,
                                handle(
                                        elem("NULL", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("NULL", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 7,
                                handle(
                                        elem("returnStatement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 4,
                                handle(
                                        elem("forLoop", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 5,
                                handle(
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("+", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 6,
                                handle(
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("-", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 2,
                                handle(
                                        elem("printStatement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        elem("ifStatement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        elem("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        elem("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("arguments", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 0,
                                handle(
                                        elem("functionCall", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );
    }

    @Test
    public void cimpleFibonacciMinimalFormat() {
        String input = "fn fib(i){if(i < 2){return 1;}else{return fib(i - 1)  + fib(i - 2);}}\n" +
                "i = 10;\n" +
                "for(j = 0; j < i ; j = j+1){res = fib(j);print res;}";

        String cimple = TestGrammars.loadCimple();
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat(input, cimple);

        dumpSimplifiedMap(formatInfoMap);

        verifyFormatInfoMap(
                formatInfoMap,
                rules(
                        rule("forLoop", 0,
                                handle(
                                        elem("for", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 9,
                                handle(
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("<", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("returnStatement", 0,
                                handle(
                                        elem("return", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("variableAssignment", 0,
                                handle(
                                        elem("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("=", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("arguments", 0,
                                handle(
                                        elem("NULL", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("NULL", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("program", 0,
                                handle(
                                        elem("statement", newLine(false), space(true), childrenOnNewLine(true), childrenIndented(false))
                                )
                        ),
                        rule("printStatement", 0,
                                handle(
                                        elem("print", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("ifStatement", 1,
                                handle(
                                        elem("if", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("block", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("else", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 17,
                                handle(
                                        elem("NULL", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 16,
                                handle(
                                        elem("NULL", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("INT", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("functionDefinition", 0,
                                handle(
                                        elem("fn", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("parameters", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("block", 0,
                                handle(
                                        elem("{", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("statement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("}", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 6,
                                handle(
                                        elem("functionDefinition", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("parameters", 0,
                                handle(
                                        elem("NULL", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("NULL", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 7,
                                handle(
                                        elem("returnStatement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 4,
                                handle(
                                        elem("forLoop", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 5,
                                handle(
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("+", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 6,
                                handle(
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("-", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 2,
                                handle(
                                        elem("printStatement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        elem("ifStatement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        elem("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        elem("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("arguments", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 0,
                                handle(
                                        elem("functionCall", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );
    }


    @Test
    public void cimpleFibonacciNoFormat() {
        String input = "fn fib(i){if(i < 2){return 1;}else{return fib(i - 1) + fib(i - 2);}} i = 10; for(j = 0; j < i ; j = j+1){res = fib(j);print res;}";

        String cimple = TestGrammars.loadCimple();
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat(input, cimple);

        dumpSimplifiedMap(formatInfoMap);

        verifyFormatInfoMap(
                formatInfoMap,
                rules(
                        rule("forLoop", 0,
                                handle(
                                        elem("for", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 9,
                                handle(
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("<", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("returnStatement", 0,
                                handle(
                                        elem("return", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("variableAssignment", 0,
                                handle(
                                        elem("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("=", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("arguments", 0,
                                handle(
                                        elem("NULL", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("NULL", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("program", 0,
                                handle(
                                        elem("statement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("printStatement", 0,
                                handle(
                                        elem("print", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("ifStatement", 1,
                                handle(
                                        elem("if", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("block", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("else", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 17,
                                handle(
                                        elem("NULL", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 16,
                                handle(
                                        elem("NULL", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("INT", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("functionDefinition", 0,
                                handle(
                                        elem("fn", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("parameters", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("block", 0,
                                handle(
                                        elem("{", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("statement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("}", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 6,
                                handle(
                                        elem("functionDefinition", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("parameters", 0,
                                handle(
                                        elem("NULL", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("NULL", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 7,
                                handle(
                                        elem("returnStatement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 4,
                                handle(
                                        elem("forLoop", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 5,
                                handle(
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("+", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 6,
                                handle(
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("-", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 2,
                                handle(
                                        elem("printStatement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        elem("ifStatement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        elem("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        elem("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("arguments", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 0,
                                handle(
                                        elem("functionCall", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );
    }


    private Map<Pair<String, Integer>, RuleFormatInfo> extractFormat(String input, String grammar) {
        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(grammar);
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();
        Map<Pair<ParserRule, Alternative>, RuleFormatInfo> merge = merge(extract(grammarInfo, grammar, input));
        return asRuleNameAlternativeIndexMap(merge);
    }
}
