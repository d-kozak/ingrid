package premun.mps.ingrid.tranformer;

import org.junit.Test;
import premun.mps.ingrid.formatter.boundary.FormatExtractor;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.*;
import premun.mps.ingrid.model.format.SimpleFormatInfo;
import premun.mps.ingrid.parser.GrammarParser;
import premun.mps.ingrid.transformer.DetectListWithSeparatorsAlgorithm;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static premun.mps.ingrid.formatter.utils.FormatInfoAsserts.verifyFormatInfo;
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

    private GrammarInfo processGrammar(String grammar) {
        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(grammar);
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();

        for (RuleReference ruleReference : grammarInfo.getRuleReferences()) {
            ruleReference.formatInfo = SimpleFormatInfo.UNKNOWN;
        }

        DetectListWithSeparatorsAlgorithm detectListWithSeparatorsAlgorithm = new DetectListWithSeparatorsAlgorithm();
        return detectListWithSeparatorsAlgorithm.transform(grammarInfo);

    }

    @Test
    public void args__shouldFindArgsAndParameters__checkingTheFormatInfomap() throws IOException {
        String grammar = loadFileContent("/Args.g4");
        String input = "foo(a,b,c) bar(a,b)";

        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(grammar);
        GrammarInfo parsed = grammarParser.resolveGrammar();

        GrammarInfo witFormatting = FormatExtractor.fullyProcessMultipleFiles(parsed, grammar, Collections.singletonList(input));

        verifyFormatInfo(
                witFormatting,
                rules(
                        rule("statements", 0,
                                handle(
                                        collection("statement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        element("functionCall", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 1,
                                handle(
                                        element("idList", newLine(false), space(true))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("args", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("args", 0,
                                handle(
                                        element("arg", newLine(false), space(false)),
                                        collection("args_block_1_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("args_block_1_1", 0,
                                handle(
                                        element(",", newLine(false), space(false)),
                                        element("arg", newLine(false), space(true))
                                )
                        ),
                        rule("arg", 0,
                                handle(
                                        element("ID", newLine(false), space(true))
                                )
                        )
                )
        );


        DetectListWithSeparatorsAlgorithm detectListWithSeparatorsAlgorithm = new DetectListWithSeparatorsAlgorithm();

        GrammarInfo withSeparators = detectListWithSeparatorsAlgorithm.transform(witFormatting);

        verifyFormatInfo(
                withSeparators,
                rules(
                        rule("statements", 0,
                                handle(
                                        collection("statement", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(null))
                                )
                        ),
                        rule("statement", 0,
                                handle(
                                        element("functionCall", newLine(false), space(true))
                                )
                        ),
                        rule("statement", 1,
                                handle(
                                        element("idList", newLine(false), space(true))
                                )
                        ),
                        rule("functionCall", 0,
                                handle(
                                        element("ID", newLine(false), space(false)),
                                        element("(", newLine(false), space(false)),
                                        element("args", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        ),
                        rule("args", 0,
                                handle(
                                        collection("arg", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(","))
                                )
                        ),
                        rule("args_block_1_1", 0,
                                handle(
                                        element(",", newLine(false), space(false)),
                                        element("arg", newLine(false), space(true))
                                )
                        ),
                        rule("arg", 0,
                                handle(
                                        element("ID", newLine(false), space(true))
                                )
                        ),
                        rule("idList", 0,
                                handle(
                                        collection("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(","))
                                )
                        )
                )
        );


    }

}
