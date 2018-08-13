package premun.mps.ingrid.formatter.formatextraction;

import org.junit.Test;
import premun.mps.ingrid.formatter.boundary.FormatExtractor;
import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.formatter.utils.Pair;
import premun.mps.ingrid.formatter.utils.TestGrammars;

import java.util.Map;

import static premun.mps.ingrid.formatter.utils.FormatExtraction.extractFormat;
import static premun.mps.ingrid.formatter.utils.FormatInfoAsserts.verifyFormatInfoMap;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.AppliedRule.rule;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.*;
import static premun.mps.ingrid.formatter.utils.FormatInfoMapDump.dumpSimplifiedMap;

/**
 * A set of tests of format extraction for Cimple language
 *
 * @author dkozak
 * @see FormatExtractor
 * @see TestGrammars
 */
public class CimpleGrammarFormatExtractionTest {

    @Test
    public void cimpleFibonacci__kindaTypicalFormatting() {
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
    public void cimpleFibonacci__eachTopLevelStatementOnOneLine() {
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
    public void cimpleFibonacci__kindaTypicalFormatting__inIfElseThereAreSpacesAroundExpressionInIf() {
        String input = "fn fib(i){\n" +
                "    if (i < 2) {\n" +
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
                                        elem("if", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expression", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
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
    public void cimpleFibonacci__kindaTypicalFormatting__inIfElseThereAreNoSpacesAroundElse() {
        String input = "fn fib(i){\n" +
                "    if(i < 2){\n" +
                "        return 1;\n" +
                "    }else{\n" +
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
    public void cimpleFibonacci__noFormat() {
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

    @Test
    public void cimpleFibonacci__kindaTypicalFormatting__blockStatementLeftBracketOnNewLineInFunctionDefinition() {
        String input = "fn fib(i)\n{\n" +
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
                                        elem(")", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
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
    public void cimpleFibonacci__kindaTypicalFormatting__blockStatementLeftBracketOnNewLineInIfElse() {
        String input = "fn fib(i){\n" +
                "    if(i < 2)\n{\n" +
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
                                        elem(")", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
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
}
