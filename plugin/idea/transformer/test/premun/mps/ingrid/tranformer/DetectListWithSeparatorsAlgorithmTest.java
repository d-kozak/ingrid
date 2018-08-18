package premun.mps.ingrid.tranformer;

import org.junit.Test;
import premun.mps.ingrid.formatter.boundary.FormatExtractor;
import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.formatter.utils.Pair;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.*;
import premun.mps.ingrid.parser.GrammarParser;
import premun.mps.ingrid.transformer.DetectListWithSeparatorsAlgorithm;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static premun.mps.ingrid.formatter.utils.FormatInfoAsserts.verifyFormatInfoMap;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.AppliedRule.rule;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.*;
import static premun.mps.ingrid.tranformer.Utils.loadFileContent;

public class DetectListWithSeparatorsAlgorithmTest {

    @Test
    public void cimple__shouldFindArgsAndParameters__checkingOnlyGrammar() {
        String grammar = TestGrammars.loadCimple();

        GrammarInfo grammarInfo = processGrammar(grammar);
        assertRuleHasJustOneRuleReference(grammarInfo, "arguments", "expression");
        assertRuleHasJustOneRuleReference(grammarInfo, "parameters", "ID");
    }

    @Test
    public void args__shouldFindArgsAndIdList__checkingOnlyGrammar() throws IOException {
        String grammar = loadFileContent("/Args.g4");

        GrammarInfo grammarInfo = processGrammar(grammar);

        assertRuleHasJustOneRuleReference(grammarInfo, "args", "arg");
        assertRuleHasJustOneRuleReference(grammarInfo, "idList", "ID");
    }


    private void assertRuleHasJustOneRuleReference(GrammarInfo grammarInfo, String checkedRuleName, String referencedRuleName) {
        try {
            ParserRule parserRule = (ParserRule) grammarInfo.rules.get(checkedRuleName);
            assertEquals(1, parserRule.alternatives.size());
            Alternative alternative = parserRule.alternatives.get(0);
            List<RuleReference> elements = alternative.elements;
            assertEquals(1, elements.size());
            RuleReference ruleReference = elements.get(0);
            assertEquals(referencedRuleName, ruleReference.rule.name);
            assertEquals(Quantity.ANY, ruleReference.quantity);
        } catch (AssertionError ex) {
            throw new AssertionError("When checking '" + checkedRuleName + "' and looking for reference to '" + referencedRuleName + "' in grammar " + grammarInfo, ex);
        }
    }

    private GrammarInfo processGrammar(String cimple) {
        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(cimple);
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();

        DetectListWithSeparatorsAlgorithm detectListWithSeparatorsAlgorithm = new DetectListWithSeparatorsAlgorithm();
        detectListWithSeparatorsAlgorithm.transform(grammarInfo, new LinkedHashMap<>());
        return grammarInfo;
    }

    @Test
    public void args__shouldFindArgsAndParameters__checkingTheFormatInfomap() throws IOException {
        String grammar = loadFileContent("/Args.g4");
        String input = "foo(a,b,c) bar(a,b)";

        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(grammar);
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();

        Map<Pair<ParserRule, Alternative>, RuleFormatInfo> complexFormatInfoMap = FormatExtractor.merge(FormatExtractor.extract(grammarInfo, grammar, input));
        Map<Pair<String, Integer>, RuleFormatInfo> simplifiedFormatInfoMap = FormatExtractor.asRuleNameAlternativeIndexMap(complexFormatInfoMap);

        // BEFORE
        verifyFormatInfoMap(
                simplifiedFormatInfoMap,
                rules(
                        rule("arg", 0,
                                handle(
                                        collection("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("args_block_1_1", 0,
                                handle(
                                        collection(",", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("arg", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("args", 0,
                                handle(
                                        collection("arg", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("args_block_1_1_alt_0", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        collection("functionCall", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        collection("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("args", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statements", 0,
                                handle(
                                        collection("statement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );

        DetectListWithSeparatorsAlgorithm detectListWithSeparatorsAlgorithm = new DetectListWithSeparatorsAlgorithm();
        detectListWithSeparatorsAlgorithm.transform(grammarInfo, complexFormatInfoMap);

        simplifiedFormatInfoMap = FormatExtractor.asRuleNameAlternativeIndexMap(complexFormatInfoMap);

        // AFTER
        verifyFormatInfoMap(
                simplifiedFormatInfoMap,
                rules(
                        rule("arg", 0,
                                handle(
                                        collection("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("args_block_1_1", 0,
                                handle(
                                        collection(",", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("arg", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("args", 0,
                                handle(
                                        collection("arg", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(","))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        collection("functionCall", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        collection("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("args", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule("statements", 0,
                                handle(
                                        collection("statement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),

                        // get "magically" added :)
                        rule("idList", 0,
                                handle(
                                        collection("ID", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(","))
                                )
                        ),
                        rule("idList_block_1_1", 0,
                                handle()
                        ),
                        rule("statement", 1,
                                handle()
                        )

                )
        );

    }

}
