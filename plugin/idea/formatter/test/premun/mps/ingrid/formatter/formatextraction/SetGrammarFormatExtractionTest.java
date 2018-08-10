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
 * A set of tests of format extraction for Set language
 *
 * @author dkozak
 * @see FormatExtractor
 * @see TestGrammars
 */
public class SetGrammarFormatExtractionTest {

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
    public void setGrammarEmptySet__withNewline() {
        String input = "{\n" +
                "}";
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat(input, TestGrammars.setGrammar);

        dumpSimplifiedMap(formatInfoMap);

        verifyFormatInfoMap(
                formatInfoMap,
                rules(
                        rule("set", 0,
                                handle(
                                        elem("{", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
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
    public void setGrammarSimpleInput__spaceAfterCommas() {
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat("{1, 2, 3}", TestGrammars.setGrammar);

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
                                        elem(",", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("elem", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );
    }

    @Test
    public void setGrammarSimpleInput__newlineAfterSetBlockRule() {
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat("{1,2,3\n}", TestGrammars.setGrammar);

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
                                        elem("set_block_2_1_alt_0", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
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
    public void setGrammarSimpleInput__newlineAfterOpeningBracket() {
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat("{\n1,2,3}", TestGrammars.setGrammar);

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
    public void setGrammarSimpleInput__spaceAfterComma() {
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat("{1, 2, 3}", TestGrammars.setGrammar);

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
                                        elem(",", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
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
                                        elem("set_block_2_1_alt_0", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
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
                                        elem("set_block_2_1_alt_0", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
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
}
