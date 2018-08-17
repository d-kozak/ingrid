package premun.mps.ingrid.importer;

import org.junit.Test;
import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.formatter.utils.Pair;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.model.ParserRule;

import java.util.Collections;
import java.util.Map;

import static premun.mps.ingrid.formatter.boundary.FormatExtractor.asRuleNameAlternativeIndexMap;
import static premun.mps.ingrid.formatter.utils.FormatInfoAsserts.verifyFormatInfoMap;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.AppliedRule.rule;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.*;
import static premun.mps.ingrid.formatter.utils.FormatInfoMapDump.dumpSimplifiedMap;
import static premun.mps.ingrid.importer.GrammarImporter.fullIngridPipeline;

/**
 * Tests that verify that the content of formatInfo map is correct.
 *
 * @author dkozak
 */
public class SetGrammarOnlyFormatInfoExtractionTest {

    @Test
    public void setGrammarEmptySet() {
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(TestGrammars.setGrammar), Collections.singletonList("{}"), Collections.emptyList(), false, null);
        Pair<GrammarInfo, Map<Pair<ParserRule, Alternative>, RuleFormatInfo>> grammarInfoMapPair = fullIngridPipeline(ingridConfiguration);
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = asRuleNameAlternativeIndexMap(grammarInfoMapPair.second);

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
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(TestGrammars.setGrammar), Collections.singletonList(input), Collections.emptyList(), false, null);
        Pair<GrammarInfo, Map<Pair<ParserRule, Alternative>, RuleFormatInfo>> grammarInfoMapPair = fullIngridPipeline(ingridConfiguration);
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = asRuleNameAlternativeIndexMap(grammarInfoMapPair.second);

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
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(TestGrammars.setGrammar), Collections.singletonList("{1,2,3}"), Collections.emptyList(), false, null);
        Pair<GrammarInfo, Map<Pair<ParserRule, Alternative>, RuleFormatInfo>> grammarInfoMapPair = fullIngridPipeline(ingridConfiguration);
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = asRuleNameAlternativeIndexMap(grammarInfoMapPair.second);

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
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(TestGrammars.setGrammar), Collections.singletonList("{1, 2, 3}"), Collections.emptyList(), false, null);
        Pair<GrammarInfo, Map<Pair<ParserRule, Alternative>, RuleFormatInfo>> grammarInfoMapPair = fullIngridPipeline(ingridConfiguration);
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = asRuleNameAlternativeIndexMap(grammarInfoMapPair.second);

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
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(TestGrammars.setGrammar), Collections.singletonList("{1,2,3\n}"), Collections.emptyList(), false, null);
        Pair<GrammarInfo, Map<Pair<ParserRule, Alternative>, RuleFormatInfo>> grammarInfoMapPair = fullIngridPipeline(ingridConfiguration);
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = asRuleNameAlternativeIndexMap(grammarInfoMapPair.second);

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
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(TestGrammars.setGrammar), Collections.singletonList("{\n1,2,3}"), Collections.emptyList(), false, null);
        Pair<GrammarInfo, Map<Pair<ParserRule, Alternative>, RuleFormatInfo>> grammarInfoMapPair = fullIngridPipeline(ingridConfiguration);
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = asRuleNameAlternativeIndexMap(grammarInfoMapPair.second);

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
    public void setGrammarNestedInput() {
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(TestGrammars.setGrammar), Collections.singletonList("{1,{a,b,c},3}"), Collections.emptyList(), false, null);
        Pair<GrammarInfo, Map<Pair<ParserRule, Alternative>, RuleFormatInfo>> grammarInfoMapPair = fullIngridPipeline(ingridConfiguration);
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = asRuleNameAlternativeIndexMap(grammarInfoMapPair.second);

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
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(TestGrammars.setGrammar), Collections.singletonList("{1,{a,b,c},{{},{a,b,c}}}"), Collections.emptyList(), false, null);
        Pair<GrammarInfo, Map<Pair<ParserRule, Alternative>, RuleFormatInfo>> grammarInfoMapPair = fullIngridPipeline(ingridConfiguration);
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = asRuleNameAlternativeIndexMap(grammarInfoMapPair.second);

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
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(TestGrammars.setGrammar), Collections.singletonList(input), Collections.emptyList(), false, null);
        Pair<GrammarInfo, Map<Pair<ParserRule, Alternative>, RuleFormatInfo>> grammarInfoMapPair = fullIngridPipeline(ingridConfiguration);
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = asRuleNameAlternativeIndexMap(grammarInfoMapPair.second);

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
        IngridConfiguration ingridConfiguration = new IngridConfiguration(Collections.singletonList(TestGrammars.setGrammar), Collections.singletonList(input), Collections.emptyList(), false, null);
        Pair<GrammarInfo, Map<Pair<ParserRule, Alternative>, RuleFormatInfo>> grammarInfoMapPair = fullIngridPipeline(ingridConfiguration);
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = asRuleNameAlternativeIndexMap(grammarInfoMapPair.second);
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
