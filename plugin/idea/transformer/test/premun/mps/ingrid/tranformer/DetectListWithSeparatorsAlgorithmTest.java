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
    public void cimple__shouldFindArgsAndParameters__checkingTheFormatInfoMap() throws IOException {
        String grammar = TestGrammars.loadCimple();
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

        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(grammar);
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();

        GrammarInfo withFormatting = FormatExtractor.fullyProcessMultipleFiles(grammarInfo, grammar, Collections.singletonList(input));

        verifyFormatInfo(
                withFormatting,
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

        DetectListWithSeparatorsAlgorithm detectListWithSeparatorsAlgorithm = new DetectListWithSeparatorsAlgorithm();
        GrammarInfo withSeparators = detectListWithSeparatorsAlgorithm.transform(withFormatting);

        verifyFormatInfo(
                withSeparators,
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
                                        collection("expression", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(","))
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
                                        collection("ID", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(","))
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
    public void args__shouldFindArgsAndParameters__checkingTheFormatInfoMap() throws IOException {
        String grammar = loadFileContent("/Args.g4");
        String input = "foo(a,b,c) bar(a,b)";

        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(grammar);
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();

        GrammarInfo witFormatting = FormatExtractor.fullyProcessMultipleFiles(grammarInfo, grammar, Collections.singletonList(input));

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
