package premun.mps.ingrid.formatter.formatextraction;

import org.junit.Test;
import premun.mps.ingrid.formatter.boundary.FormatExtractor;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.GrammarInfo;

import static premun.mps.ingrid.formatter.utils.FormatInfoAsserts.verifyFormatInfo;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.AppliedRule.rule;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.*;
import static premun.mps.ingrid.formatter.utils.FormatInfoDump.dumpFormatting;
import static premun.mps.ingrid.formatter.utils.Parser.extractFormat;

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
        GrammarInfo grammarInfo = extractFormat(input, cimple);

        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule("program", 0,
                                handle(
                                        collection("statement", newLine(false), space(true), childrenOnNewLine(true), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 2,
                                handle(
                                        element("printStatement", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        element("ifStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 4,
                                handle(
                                        element("forLoop", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 6,
                                handle(
                                        element("functionDefinition", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 7,
                                handle(
                                        element("returnStatement", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 0,
                                handle(
                                        element("functionCall", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 5,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("+", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 6,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("-", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 9,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("<", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 16,
                                handle(
                                        element("-", newLine(false), space(false)),
                                        element("INT", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 17,
                                handle(
                                        element("-", newLine(false), space(false)),
                                        element("ID", newLine(false), space(true))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("arguments", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("arguments", 0,
                                handle(
                                        element("expression", newLine(false), space(false)),
                                        collection("arguments_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("forLoop", 0,
                                handle(
                                        element("for", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(";", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true)),
                                        element(";", newLine(false), space(true)),
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("variableAssignment", 0,
                                handle(
                                        element("ID", newLine(false), space(true)),
                                        element("=", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("printStatement", 0,
                                handle(
                                        element("print", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("ifStatement", 1,
                                handle(
                                        element("if", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("expression", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(true)),
                                        element("else", newLine(false), space(true)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("functionDefinition", 0,
                                handle(
                                        element("fn", newLine(false), space(true)),
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("parameters", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("parameters", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        collection("parameters_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("returnStatement", 0,
                                handle(
                                        element("return", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("block", 0,
                                handle(
                                        element("{", newLine(true), space(false)),
                                        collection("statement", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(true), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
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
        GrammarInfo grammarInfo = extractFormat(input, cimple);

        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule("program", 0,
                                handle(
                                        collection("statement", newLine(false), space(true), childrenOnNewLine(true), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 2,
                                handle(
                                        element("printStatement", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        element("ifStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 4,
                                handle(
                                        element("forLoop", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 6,
                                handle(
                                        element("functionDefinition", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 7,
                                handle(
                                        element("returnStatement", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 0,
                                handle(
                                        element("functionCall", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 5,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("+", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 6,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("-", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 9,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("<", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 16,
                                handle(
                                        element("-", newLine(false), space(false)),
                                        element("INT", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 17,
                                handle(
                                        element("-", newLine(false), space(false)),
                                        element("ID", newLine(false), space(true))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("arguments", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("arguments", 0,
                                handle(
                                        element("expression", newLine(false), space(false)),
                                        collection("arguments_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("forLoop", 0,
                                handle(
                                        element("for", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(";", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true)),
                                        element(";", newLine(false), space(true)),
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("variableAssignment", 0,
                                handle(
                                        element("ID", newLine(false), space(true)),
                                        element("=", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("printStatement", 0,
                                handle(
                                        element("print", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("ifStatement", 1,
                                handle(
                                        element("if", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("expression", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(false)),
                                        element("else", newLine(false), space(false)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("functionDefinition", 0,
                                handle(
                                        element("fn", newLine(false), space(true)),
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("parameters", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("parameters", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        collection("parameters_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("returnStatement", 0,
                                handle(
                                        element("return", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("block", 0,
                                handle(
                                        element("{", newLine(false), space(false)),
                                        collection("statement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
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
        GrammarInfo grammarInfo = extractFormat(input, cimple);

        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule("program", 0,
                                handle(
                                        collection("statement", newLine(false), space(true), childrenOnNewLine(true), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 2,
                                handle(
                                        element("printStatement", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        element("ifStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 4,
                                handle(
                                        element("forLoop", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 6,
                                handle(
                                        element("functionDefinition", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 7,
                                handle(
                                        element("returnStatement", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 0,
                                handle(
                                        element("functionCall", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 5,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("+", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 6,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("-", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 9,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("<", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 16,
                                handle(
                                        element("-", newLine(false), space(false)),
                                        element("INT", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 17,
                                handle(
                                        element("-", newLine(false), space(false)),
                                        element("ID", newLine(false), space(true))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("arguments", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("arguments", 0,
                                handle(
                                        element("expression", newLine(false), space(false)),
                                        collection("arguments_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("forLoop", 0,
                                handle(
                                        element("for", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(";", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true)),
                                        element(";", newLine(false), space(true)),
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("variableAssignment", 0,
                                handle(
                                        element("ID", newLine(false), space(true)),
                                        element("=", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("printStatement", 0,
                                handle(
                                        element("print", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("ifStatement", 1,
                                handle(
                                        element("if", newLine(false), space(true)),
                                        element("(", newLine(false), space(false)),
                                        element("expression", newLine(false), space(false)),
                                        element(")", newLine(false), space(true)),
                                        element("block", newLine(false), space(true)),
                                        element("else", newLine(false), space(true)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("functionDefinition", 0,
                                handle(
                                        element("fn", newLine(false), space(true)),
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("parameters", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("parameters", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        collection("parameters_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("returnStatement", 0,
                                handle(
                                        element("return", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("block", 0,
                                handle(
                                        element("{", newLine(true), space(false)),
                                        collection("statement", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(true), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
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
        GrammarInfo grammarInfo = extractFormat(input, cimple);

        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule("program", 0,
                                handle(
                                        collection("statement", newLine(false), space(true), childrenOnNewLine(true), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 2,
                                handle(
                                        element("printStatement", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        element("ifStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 4,
                                handle(
                                        element("forLoop", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 6,
                                handle(
                                        element("functionDefinition", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 7,
                                handle(
                                        element("returnStatement", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 0,
                                handle(
                                        element("functionCall", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 5,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("+", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 6,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("-", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 9,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("<", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 16,
                                handle(
                                        element("-", newLine(false), space(false)),
                                        element("INT", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 17,
                                handle(
                                        element("-", newLine(false), space(false)),
                                        element("ID", newLine(false), space(true))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("arguments", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("arguments", 0,
                                handle(
                                        element("expression", newLine(false), space(false)),
                                        collection("arguments_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("forLoop", 0,
                                handle(
                                        element("for", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(";", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true)),
                                        element(";", newLine(false), space(true)),
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("variableAssignment", 0,
                                handle(
                                        element("ID", newLine(false), space(true)),
                                        element("=", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("printStatement", 0,
                                handle(
                                        element("print", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("ifStatement", 1,
                                handle(
                                        element("if", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("expression", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(false)),
                                        element("else", newLine(false), space(false)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("functionDefinition", 0,
                                handle(
                                        element("fn", newLine(false), space(true)),
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("parameters", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("parameters", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        collection("parameters_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("returnStatement", 0,
                                handle(
                                        element("return", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("block", 0,
                                handle(
                                        element("{", newLine(true), space(false)),
                                        collection("statement", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(true), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
                                )
                        )
                )
        );
    }



    @Test
    public void cimpleFibonacci__noFormat() {
        String input = "fn fib(i){if(i < 2){return 1;}else{return fib(i - 1) + fib(i - 2);}} i = 10; for(j = 0; j < i ; j = j+1){res = fib(j);print res;}";

        String cimple = TestGrammars.loadCimple();
        GrammarInfo grammarInfo = extractFormat(input, cimple);

        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule("program", 0,
                                handle(
                                        collection("statement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 2,
                                handle(
                                        element("printStatement", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        element("ifStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 4,
                                handle(
                                        element("forLoop", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 6,
                                handle(
                                        element("functionDefinition", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 7,
                                handle(
                                        element("returnStatement", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 0,
                                handle(
                                        element("functionCall", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 5,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("+", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 6,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("-", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 9,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("<", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 16,
                                handle(
                                        element("-", newLine(false), space(false)),
                                        element("INT", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 17,
                                handle(
                                        element("-", newLine(false), space(false)),
                                        element("ID", newLine(false), space(true))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("arguments", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("arguments", 0,
                                handle(
                                        element("expression", newLine(false), space(false)),
                                        collection("arguments_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("forLoop", 0,
                                handle(
                                        element("for", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(";", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true)),
                                        element(";", newLine(false), space(true)),
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("variableAssignment", 0,
                                handle(
                                        element("ID", newLine(false), space(true)),
                                        element("=", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("printStatement", 0,
                                handle(
                                        element("print", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("ifStatement", 1,
                                handle(
                                        element("if", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("expression", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(false)),
                                        element("else", newLine(false), space(false)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("functionDefinition", 0,
                                handle(
                                        element("fn", newLine(false), space(true)),
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("parameters", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("parameters", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        collection("parameters_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("returnStatement", 0,
                                handle(
                                        element("return", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("block", 0,
                                handle(
                                        element("{", newLine(false), space(false)),
                                        collection("statement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
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
        GrammarInfo grammarInfo = extractFormat(input, cimple);

        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule("program", 0,
                                handle(
                                        collection("statement", newLine(false), space(true), childrenOnNewLine(true), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 2,
                                handle(
                                        element("printStatement", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        element("ifStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 4,
                                handle(
                                        element("forLoop", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 6,
                                handle(
                                        element("functionDefinition", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 7,
                                handle(
                                        element("returnStatement", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 0,
                                handle(
                                        element("functionCall", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 5,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("+", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 6,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("-", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 9,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("<", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 16,
                                handle(
                                        element("-", newLine(false), space(false)),
                                        element("INT", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 17,
                                handle(
                                        element("-", newLine(false), space(false)),
                                        element("ID", newLine(false), space(true))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("arguments", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("arguments", 0,
                                handle(
                                        element("expression", newLine(false), space(false)),
                                        collection("arguments_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("forLoop", 0,
                                handle(
                                        element("for", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(";", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true)),
                                        element(";", newLine(false), space(true)),
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("variableAssignment", 0,
                                handle(
                                        element("ID", newLine(false), space(true)),
                                        element("=", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("printStatement", 0,
                                handle(
                                        element("print", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("ifStatement", 1,
                                handle(
                                        element("if", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("expression", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(true)),
                                        element("else", newLine(false), space(true)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("functionDefinition", 0,
                                handle(
                                        element("fn", newLine(false), space(true)),
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("parameters", newLine(false), space(false)),
                                        element(")", newLine(true), space(false)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("parameters", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        collection("parameters_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("returnStatement", 0,
                                handle(
                                        element("return", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("block", 0,
                                handle(
                                        element("{", newLine(true), space(false)),
                                        collection("statement", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(true), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
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
        GrammarInfo grammarInfo = extractFormat(input, cimple);

        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule("program", 0,
                                handle(
                                        collection("statement", newLine(false), space(true), childrenOnNewLine(true), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 2,
                                handle(
                                        element("printStatement", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        element("ifStatement", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 4,
                                handle(
                                        element("forLoop", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 6,
                                handle(
                                        element("functionDefinition", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 7,
                                handle(
                                        element("returnStatement", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 0,
                                handle(
                                        element("functionCall", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 5,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("+", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 6,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("-", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 9,
                                handle(
                                        element("expression", newLine(false), space(true)),
                                        element("<", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 16,
                                handle(
                                        element("-", newLine(false), space(false)),
                                        element("INT", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 17,
                                handle(
                                        element("-", newLine(false), space(false)),
                                        element("ID", newLine(false), space(true))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("arguments", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("arguments", 0,
                                handle(
                                        element("expression", newLine(false), space(false)),
                                        collection("arguments_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("forLoop", 0,
                                handle(
                                        element("for", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(";", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true)),
                                        element(";", newLine(false), space(true)),
                                        element("variableAssignment", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("variableAssignment", 0,
                                handle(
                                        element("ID", newLine(false), space(true)),
                                        element("=", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("printStatement", 0,
                                handle(
                                        element("print", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("ifStatement", 1,
                                handle(
                                        element("if", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("expression", newLine(false), space(false)),
                                        element(")", newLine(true), space(false)),
                                        element("block", newLine(false), space(true)),
                                        element("else", newLine(false), space(true)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("functionDefinition", 0,
                                handle(
                                        element("fn", newLine(false), space(true)),
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("parameters", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("block", newLine(false), space(true))
                                )
                        ),
                        rule("parameters", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        collection("parameters_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("returnStatement", 0,
                                handle(
                                        element("return", newLine(false), space(true)),
                                        element("expression", newLine(false), space(true))
                                )
                        ),
                        rule("block", 0,
                                handle(
                                        element("{", newLine(true), space(false)),
                                        collection("statement", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(true), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
                                )
                        )
                )
        );
    }
}
