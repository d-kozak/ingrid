package premun.mps.ingrid.formatter.formatextraction;

import org.junit.Test;
import premun.mps.ingrid.formatter.boundary.FormatExtractor;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.GrammarInfo;

import static premun.mps.ingrid.formatter.utils.FormatExtraction.extractFormat;
import static premun.mps.ingrid.formatter.utils.FormatInfoAsserts.verifyFormatInfo;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.AppliedRule.rule;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.*;
import static premun.mps.ingrid.formatter.utils.FormatInfoDump.dumpFormatting;

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
                        rule("forLoop", 0,
                                handle(
                                        collection("for", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 9,
                                handle(
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("<", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("returnStatement", 0,
                                handle(
                                        collection("return", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("variableAssignment", 0,
                                handle(
                                        collection("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("=", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("arguments", 0,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("program", 0,
                                handle(
                                        collection("statement", newLine(false), space(true), childrenOnNewLine(true), childrenIndented(false))
                                )
                        ),
                        rule("printStatement", 0,
                                handle(
                                        collection("print", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("ifStatement", 1,
                                handle(
                                        collection("if", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("else", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 17,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 16,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("INT", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("functionDefinition", 0,
                                handle(
                                        collection("fn", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("parameters", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("block", 0,
                                handle(
                                        collection("{", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("statement", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(true)),
                                        collection("}", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 6,
                                handle(
                                        collection("functionDefinition", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("parameters", 0,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 7,
                                handle(
                                        collection("returnStatement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 4,
                                handle(
                                        collection("forLoop", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 5,
                                handle(
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("+", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 6,
                                handle(
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("-", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 2,
                                handle(
                                        collection("printStatement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        collection("ifStatement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        collection("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        collection("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("arguments", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 0,
                                handle(
                                        collection("functionCall", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
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
                        rule("forLoop", 0,
                                handle(
                                        collection("for", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 9,
                                handle(
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("<", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("returnStatement", 0,
                                handle(
                                        collection("return", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("variableAssignment", 0,
                                handle(
                                        collection("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("=", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("arguments", 0,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("program", 0,
                                handle(
                                        collection("statement", newLine(false), space(true), childrenOnNewLine(true), childrenIndented(false))
                                )
                        ),
                        rule("printStatement", 0,
                                handle(
                                        collection("print", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("ifStatement", 1,
                                handle(
                                        collection("if", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("else", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 17,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 16,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("INT", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("functionDefinition", 0,
                                handle(
                                        collection("fn", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("parameters", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("block", 0,
                                handle(
                                        collection("{", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("statement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("}", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 6,
                                handle(
                                        collection("functionDefinition", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("parameters", 0,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 7,
                                handle(
                                        collection("returnStatement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 4,
                                handle(
                                        collection("forLoop", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 5,
                                handle(
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("+", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 6,
                                handle(
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("-", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 2,
                                handle(
                                        collection("printStatement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        collection("ifStatement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        collection("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        collection("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("arguments", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 0,
                                handle(
                                        collection("functionCall", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
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
                        rule("forLoop", 0,
                                handle(
                                        collection("for", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 9,
                                handle(
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("<", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("returnStatement", 0,
                                handle(
                                        collection("return", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("variableAssignment", 0,
                                handle(
                                        collection("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("=", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("arguments", 0,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("program", 0,
                                handle(
                                        collection("statement", newLine(false), space(true), childrenOnNewLine(true), childrenIndented(false))
                                )
                        ),
                        rule("printStatement", 0,
                                handle(
                                        collection("print", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("ifStatement", 1,
                                handle(
                                        collection("if", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("else", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 17,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 16,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("INT", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("functionDefinition", 0,
                                handle(
                                        collection("fn", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("parameters", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("block", 0,
                                handle(
                                        collection("{", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("statement", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(true)),
                                        collection("}", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 6,
                                handle(
                                        collection("functionDefinition", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("parameters", 0,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 7,
                                handle(
                                        collection("returnStatement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 4,
                                handle(
                                        collection("forLoop", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 5,
                                handle(
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("+", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 6,
                                handle(
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("-", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 2,
                                handle(
                                        collection("printStatement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        collection("ifStatement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        collection("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        collection("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("arguments", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 0,
                                handle(
                                        collection("functionCall", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
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
                        rule("forLoop", 0,
                                handle(
                                        collection("for", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 9,
                                handle(
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("<", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("returnStatement", 0,
                                handle(
                                        collection("return", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("variableAssignment", 0,
                                handle(
                                        collection("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("=", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("arguments", 0,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("program", 0,
                                handle(
                                        collection("statement", newLine(false), space(true), childrenOnNewLine(true), childrenIndented(false))
                                )
                        ),
                        rule("printStatement", 0,
                                handle(
                                        collection("print", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("ifStatement", 1,
                                handle(
                                        collection("if", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("else", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 17,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 16,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("INT", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("functionDefinition", 0,
                                handle(
                                        collection("fn", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("parameters", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("block", 0,
                                handle(
                                        collection("{", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("statement", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(true)),
                                        collection("}", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 6,
                                handle(
                                        collection("functionDefinition", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("parameters", 0,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 7,
                                handle(
                                        collection("returnStatement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 4,
                                handle(
                                        collection("forLoop", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 5,
                                handle(
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("+", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 6,
                                handle(
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("-", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 2,
                                handle(
                                        collection("printStatement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        collection("ifStatement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        collection("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        collection("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("arguments", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 0,
                                handle(
                                        collection("functionCall", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
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
                        rule("forLoop", 0,
                                handle(
                                        collection("for", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 9,
                                handle(
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("<", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("returnStatement", 0,
                                handle(
                                        collection("return", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("variableAssignment", 0,
                                handle(
                                        collection("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("=", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("arguments", 0,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("program", 0,
                                handle(
                                        collection("statement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("printStatement", 0,
                                handle(
                                        collection("print", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("ifStatement", 1,
                                handle(
                                        collection("if", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("else", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 17,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 16,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("INT", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("functionDefinition", 0,
                                handle(
                                        collection("fn", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("parameters", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("block", 0,
                                handle(
                                        collection("{", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("statement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("}", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 6,
                                handle(
                                        collection("functionDefinition", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("parameters", 0,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 7,
                                handle(
                                        collection("returnStatement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 4,
                                handle(
                                        collection("forLoop", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 5,
                                handle(
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("+", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 6,
                                handle(
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("-", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 2,
                                handle(
                                        collection("printStatement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        collection("ifStatement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        collection("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        collection("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("arguments", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 0,
                                handle(
                                        collection("functionCall", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
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
                        rule("forLoop", 0,
                                handle(
                                        collection("for", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 9,
                                handle(
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("<", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("returnStatement", 0,
                                handle(
                                        collection("return", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("variableAssignment", 0,
                                handle(
                                        collection("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("=", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("arguments", 0,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("program", 0,
                                handle(
                                        collection("statement", newLine(false), space(true), childrenOnNewLine(true), childrenIndented(false))
                                )
                        ),
                        rule("printStatement", 0,
                                handle(
                                        collection("print", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("ifStatement", 1,
                                handle(
                                        collection("if", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("else", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 17,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 16,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("INT", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("functionDefinition", 0,
                                handle(
                                        collection("fn", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("parameters", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("block", 0,
                                handle(
                                        collection("{", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("statement", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(true)),
                                        collection("}", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 6,
                                handle(
                                        collection("functionDefinition", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("parameters", 0,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 7,
                                handle(
                                        collection("returnStatement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 4,
                                handle(
                                        collection("forLoop", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 5,
                                handle(
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("+", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 6,
                                handle(
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("-", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 2,
                                handle(
                                        collection("printStatement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        collection("ifStatement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        collection("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        collection("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("arguments", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 0,
                                handle(
                                        collection("functionCall", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
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
                        rule("forLoop", 0,
                                handle(
                                        collection("for", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 9,
                                handle(
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("<", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("returnStatement", 0,
                                handle(
                                        collection("return", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("variableAssignment", 0,
                                handle(
                                        collection("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("=", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("arguments", 0,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("program", 0,
                                handle(
                                        collection("statement", newLine(false), space(true), childrenOnNewLine(true), childrenIndented(false))
                                )
                        ),
                        rule("printStatement", 0,
                                handle(
                                        collection("print", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("ifStatement", 1,
                                handle(
                                        collection("if", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("else", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 17,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 16,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("INT", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("functionDefinition", 0,
                                handle(
                                        collection("fn", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("parameters", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("block", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("block", 0,
                                handle(
                                        collection("{", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("statement", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(true)),
                                        collection("}", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 6,
                                handle(
                                        collection("functionDefinition", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("parameters", 0,
                                handle(
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("UNKNOWN", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 7,
                                handle(
                                        collection("returnStatement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 4,
                                handle(
                                        collection("forLoop", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 5,
                                handle(
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("+", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 6,
                                handle(
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("-", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 2,
                                handle(
                                        collection("printStatement", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 3,
                                handle(
                                        collection("ifStatement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        collection("variableAssignment", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(";", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        collection("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("arguments", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("expression", 0,
                                handle(
                                        collection("functionCall", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );
    }
}
