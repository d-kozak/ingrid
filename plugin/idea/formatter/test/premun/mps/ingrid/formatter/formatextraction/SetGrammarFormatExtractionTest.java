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
 * A set of tests of format extraction for Set language
 *
 * @author dkozak
 * @see FormatExtractor
 * @see TestGrammars
 */
public class SetGrammarFormatExtractionTest {

    @Test
    public void setGrammarEmptySet() {
        GrammarInfo grammarInfo = extractFormat("{}", TestGrammars.setGrammar);

        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule("compilationUnit", 0,
                                handle(
                                        element("set", newLine(false), space(true))
                                )
                        ),
                        rule("set", 0,
                                handle(
                                        element("{", newLine(false), space(false)),
                                        element("}", newLine(false), space(true))
                                )
                        ),
                        rule("collection", 0,
                                handle(
                                        element("element", newLine(false), space(true))
                                )
                        ),
                        rule("collection", 1,
                                handle(
                                        element("set", newLine(false), space(true))
                                )
                        ),
                        rule("element", 0,
                                handle(
                                        element("ELEM", newLine(false), space(true))
                                )
                        )
                )
        );
    }

    @Test
    public void setGrammarEmptySet__withNewline() {
        String input = "{\n" +
                "}";
        GrammarInfo grammarInfo = extractFormat(input, TestGrammars.setGrammar);

        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule("compilationUnit", 0,
                                handle(
                                        element("set", newLine(false), space(true))
                                )
                        ),
                        rule("set", 0,
                                handle(
                                        element("{", newLine(true), space(false)),
                                        element("}", newLine(false), space(true))
                                )
                        ),
                        rule("collection", 0,
                                handle(
                                        element("element", newLine(false), space(true))
                                )
                        ),
                        rule("collection", 1,
                                handle(
                                        element("set", newLine(false), space(true))
                                )
                        ),
                        rule("element", 0,
                                handle(
                                        element("ELEM", newLine(false), space(true))
                                )
                        )
                )
        );

    }

    @Test
    public void setGrammarSimpleInput() {
        GrammarInfo grammarInfo = extractFormat("{1,2,3}", TestGrammars.setGrammar);

        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule("compilationUnit", 0,
                                handle(
                                        element("set", newLine(false), space(true))
                                )
                        ),
                        rule("set", 1,
                                handle(
                                        element("{", newLine(false), space(false)),
                                        element("collection", newLine(false), space(false)),
                                        collection("set_block_2_1", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
                                )
                        ),
                        rule("set_block_2_1", 0,
                                handle(
                                        element(",", newLine(false), space(false)),
                                        element("collection", newLine(false), space(true))
                                )
                        ),
                        rule("collection", 0,
                                handle(
                                        element("element", newLine(false), space(true))
                                )
                        ),
                        rule("collection", 1,
                                handle(
                                        element("set", newLine(false), space(true))
                                )
                        ),
                        rule("element", 0,
                                handle(
                                        element("ELEM", newLine(false), space(true))
                                )
                        )
                )
        );
    }


    @Test
    public void setGrammarSimpleInput__spaceAfterCommas() {
        GrammarInfo grammarInfo = extractFormat("{1, 2, 3}", TestGrammars.setGrammar);

        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule("compilationUnit", 0,
                                handle(
                                        element("set", newLine(false), space(true))
                                )
                        ),
                        rule("set", 1,
                                handle(
                                        element("{", newLine(false), space(false)),
                                        element("collection", newLine(false), space(false)),
                                        collection("set_block_2_1", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
                                )
                        ),
                        rule("set_block_2_1", 0,
                                handle(
                                        element(",", newLine(false), space(true)),
                                        element("collection", newLine(false), space(true))
                                )
                        ),
                        rule("collection", 0,
                                handle(
                                        element("element", newLine(false), space(true))
                                )
                        ),
                        rule("collection", 1,
                                handle(
                                        element("set", newLine(false), space(true))
                                )
                        ),
                        rule("element", 0,
                                handle(
                                        element("ELEM", newLine(false), space(true))
                                )
                        )
                )
        );

    }

    @Test
    public void setGrammarSimpleInput__newlineAfterSetBlockRule() {
        GrammarInfo grammarInfo = extractFormat("{1,2,3\n}", TestGrammars.setGrammar);

        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule("compilationUnit", 0,
                                handle(
                                        element("set", newLine(false), space(true))
                                )
                        ),
                        rule("set", 1,
                                handle(
                                        element("{", newLine(false), space(false)),
                                        element("collection", newLine(false), space(false)),
                                        collection("set_block_2_1", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
                                )
                        ),
                        rule("set_block_2_1", 0,
                                handle(
                                        element(",", newLine(false), space(false)),
                                        element("collection", newLine(false), space(true))
                                )
                        ),
                        rule("collection", 0,
                                handle(
                                        element("element", newLine(false), space(true))
                                )
                        ),
                        rule("collection", 1,
                                handle(
                                        element("set", newLine(false), space(true))
                                )
                        ),
                        rule("element", 0,
                                handle(
                                        element("ELEM", newLine(false), space(true))
                                )
                        )
                )
        );
    }

    @Test
    public void setGrammarSimpleInput__newlineAfterOpeningBracket() {
        GrammarInfo grammarInfo = extractFormat("{\n1,2,3}", TestGrammars.setGrammar);

        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule("compilationUnit", 0,
                                handle(
                                        element("set", newLine(false), space(true))
                                )
                        ),
                        rule("set", 1,
                                handle(
                                        element("{", newLine(true), space(false)),
                                        element("collection", newLine(false), space(false)),
                                        collection("set_block_2_1", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
                                )
                        ),
                        rule("set_block_2_1", 0,
                                handle(
                                        element(",", newLine(false), space(false)),
                                        element("collection", newLine(false), space(true))
                                )
                        ),
                        rule("collection", 0,
                                handle(
                                        element("element", newLine(false), space(true))
                                )
                        ),
                        rule("collection", 1,
                                handle(
                                        element("set", newLine(false), space(true))
                                )
                        ),
                        rule("element", 0,
                                handle(
                                        element("ELEM", newLine(false), space(true))
                                )
                        )
                )
        );
    }


    @Test
    public void setGrammarNestedInput() {
        GrammarInfo grammarInfo = extractFormat("{1,{a,b,c},3}", TestGrammars.setGrammar);

        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule("compilationUnit", 0,
                                handle(
                                        element("set", newLine(false), space(true))
                                )
                        ),
                        rule("set", 1,
                                handle(
                                        element("{", newLine(false), space(false)),
                                        element("collection", newLine(false), space(false)),
                                        collection("set_block_2_1", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
                                )
                        ),
                        rule("set_block_2_1", 0,
                                handle(
                                        element(",", newLine(false), space(false)),
                                        element("collection", newLine(false), space(true))
                                )
                        ),
                        rule("collection", 0,
                                handle(
                                        element("element", newLine(false), space(true))
                                )
                        ),
                        rule("collection", 1,
                                handle(
                                        element("set", newLine(false), space(true))
                                )
                        ),
                        rule("element", 0,
                                handle(
                                        element("ELEM", newLine(false), space(true))
                                )
                        )
                )
        );
    }

    @Test
    public void setGrammarNestedInputMoreComplex() {
        GrammarInfo grammarInfo = extractFormat("{1,{a,b,c},{{},{a,b,c}}}", TestGrammars.setGrammar);

        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule("compilationUnit", 0,
                                handle(
                                        element("set", newLine(false), space(true))
                                )
                        ),
                        rule("set", 0,
                                handle(
                                        element("{", newLine(false), space(false)),
                                        element("}", newLine(false), space(true))
                                )
                        ),
                        rule("set", 1,
                                handle(
                                        element("{", newLine(false), space(false)),
                                        element("collection", newLine(false), space(false)),
                                        collection("set_block_2_1", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
                                )
                        ),
                        rule("set_block_2_1", 0,
                                handle(
                                        element(",", newLine(false), space(false)),
                                        element("collection", newLine(false), space(true))
                                )
                        ),
                        rule("collection", 0,
                                handle(
                                        element("element", newLine(false), space(true))
                                )
                        ),
                        rule("collection", 1,
                                handle(
                                        element("set", newLine(false), space(true))
                                )
                        ),
                        rule("element", 0,
                                handle(
                                        element("ELEM", newLine(false), space(true))
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
        GrammarInfo grammarInfo = extractFormat(input, TestGrammars.setGrammar);

        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule("compilationUnit", 0,
                                handle(
                                        element("set", newLine(false), space(true))
                                )
                        ),
                        rule("set", 1,
                                handle(
                                        element("{", newLine(true), space(false)),
                                        element("collection", newLine(false), space(false)),
                                        collection("set_block_2_1", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
                                )
                        ),
                        rule("set_block_2_1", 0,
                                handle(
                                        element(",", newLine(true), space(false)),
                                        element("collection", newLine(false), space(true))
                                )
                        ),
                        rule("collection", 0,
                                handle(
                                        element("element", newLine(false), space(true))
                                )
                        ),
                        rule("collection", 1,
                                handle(
                                        element("set", newLine(false), space(true))
                                )
                        ),
                        rule("element", 0,
                                handle(
                                        element("ELEM", newLine(false), space(true))
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
        GrammarInfo grammarInfo = extractFormat(input, TestGrammars.setGrammar);

        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule("compilationUnit", 0,
                                handle(
                                        element("set", newLine(false), space(true))
                                )
                        ),
                        rule("set", 1,
                                handle(
                                        element("{", newLine(true), space(false)),
                                        element("collection", newLine(false), space(false)),
                                        collection("set_block_2_1", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null)),
                                        element("}", newLine(false), space(true))
                                )
                        ),
                        rule("set_block_2_1", 0,
                                handle(
                                        element(",", newLine(true), space(false)),
                                        element("collection", newLine(false), space(true))
                                )
                        ),
                        rule("collection", 0,
                                handle(
                                        element("element", newLine(false), space(true))
                                )
                        ),
                        rule("collection", 1,
                                handle(
                                        element("set", newLine(false), space(true))
                                )
                        ),
                        rule("element", 0,
                                handle(
                                        element("ELEM", newLine(false), space(true))
                                )
                        )
                )
        );
    }
}
