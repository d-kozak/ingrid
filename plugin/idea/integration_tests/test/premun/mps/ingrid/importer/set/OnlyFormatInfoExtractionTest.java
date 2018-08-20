package premun.mps.ingrid.importer.set;

import org.junit.Test;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.importer.IngridConfiguration;
import premun.mps.ingrid.model.GrammarInfo;

import java.util.Collections;

import static premun.mps.ingrid.formatter.utils.FormatInfoAsserts.verifyFormatInfo;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.AppliedRule.rule;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.*;
import static premun.mps.ingrid.importer.GrammarImporter.fullIngridPipeline;

/**
 * Tests that verify that the content of formatInfo map is correct.
 *
 * @author dkozak
 */
public class OnlyFormatInfoExtractionTest {

    @Test
    public void setGrammarEmptySet() {
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(TestGrammars.setGrammar), Collections.singletonList("{}"), Collections.emptyList(), false, null);

        GrammarInfo grammarInfo = fullIngridPipeline(ingridConfiguration);

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
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(TestGrammars.setGrammar), Collections.singletonList(input), Collections.emptyList(), false, null);

        GrammarInfo grammarInfo = fullIngridPipeline(ingridConfiguration);

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
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(TestGrammars.setGrammar), Collections.singletonList("{1,2,3}"), Collections.emptyList(), false, null);

        GrammarInfo grammarInfo = fullIngridPipeline(ingridConfiguration);

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
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(TestGrammars.setGrammar), Collections.singletonList("{1, 2, 3}"), Collections.emptyList(), false, null);

        GrammarInfo grammarInfo = fullIngridPipeline(ingridConfiguration);

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
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(TestGrammars.setGrammar), Collections.singletonList("{1,2,3\n}"), Collections.emptyList(), false, null);

        GrammarInfo grammarInfo = fullIngridPipeline(ingridConfiguration);

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
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(TestGrammars.setGrammar), Collections.singletonList("{\n1,2,3}"), Collections.emptyList(), false, null);

        GrammarInfo grammarInfo = fullIngridPipeline(ingridConfiguration);

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
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(TestGrammars.setGrammar), Collections.singletonList("{1,{a,b,c},3}"), Collections.emptyList(), false, null);

        GrammarInfo grammarInfo = fullIngridPipeline(ingridConfiguration);

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
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(TestGrammars.setGrammar), Collections.singletonList("{1,{a,b,c},{{},{a,b,c}}}"), Collections.emptyList(), false, null);

        GrammarInfo grammarInfo = fullIngridPipeline(ingridConfiguration);

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
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(TestGrammars.setGrammar), Collections.singletonList(input), Collections.emptyList(), false, null);

        GrammarInfo grammarInfo = fullIngridPipeline(ingridConfiguration);

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
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(TestGrammars.setGrammar), Collections.singletonList(input), Collections.emptyList(), false, null);

        GrammarInfo grammarInfo = fullIngridPipeline(ingridConfiguration);

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
