package premun.mps.ingrid.formatter.formatextraction;

import org.junit.Test;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.GrammarInfo;

import static premun.mps.ingrid.formatter.utils.FormatInfoAsserts.verifyFormatInfo;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.AppliedRule.rule;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.*;
import static premun.mps.ingrid.formatter.utils.FormatInfoDump.dumpFormatting;
import static premun.mps.ingrid.formatter.utils.Parser.extractFormat;

/**
 * Place for other tests, usually testing various edge cases.
 *
 * @author dkozak
 */
public class OtherFormatExtrationTests {

    /**
     * This tests verifies that newlines and indentation is properly extracted even when there is only one element in the list.
     */
    @Test
    public void CimpleJustFunctionDefinitionWithInlinedBlockRule() {
        String grammar = TestGrammars.loadResource("/CimpleFunc.g4");
        String input = "fn foo(a,b,c){\n" +
                "    foo();\n" +
                "    foo2();\n" +
                "}";

        GrammarInfo grammarInfo = extractFormat(input, grammar);

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
                                        element("functionDefinition", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 1,
                                handle(
                                        element("functionCall", newLine(false), space(false)),
                                        element(";", newLine(false), space(true))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("functionDefinition", 0,
                                handle(
                                        element("fn", newLine(false), space(true)),
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("parameters", newLine(false), space(false)),
                                        element(")", newLine(false), space(false)),
                                        element("{", newLine(true), space(false)),
                                        collection("statement", newLine(true), space(false), childrenOnNewLine(true), childrenIndented(true), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
                                )
                        ),
                        rule("parameters", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        collection("parameters_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("parameters_block_1_1", 0,
                                handle(
                                        element(",", newLine(false), space(false)),
                                        element("ID", newLine(false), space(true))
                                )
                        )
                )
        );


    }
}
