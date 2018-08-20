package premun.mps.ingrid.tranformer;

import org.junit.Test;
import premun.mps.ingrid.formatter.boundary.FormatExtractor;
import premun.mps.ingrid.formatter.utils.FormatInfoMapToDSLConvertor;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.parser.GrammarParser;
import premun.mps.ingrid.transformer.DetectListWithSeparatorsAlgorithm;
import premun.mps.ingrid.transformer.FlatReferencesWithQuantifiesAlgorithm;

import java.util.Collections;

import static premun.mps.ingrid.formatter.utils.FormatInfoAsserts.verifyFormatInfo;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.AppliedRule.rule;
import static premun.mps.ingrid.formatter.utils.FormatInfoDSL.*;
import static premun.mps.ingrid.tranformer.Utils.verifyAllFormatInfoNotNull;

/**
 * @author dkozak
 */
public class FlatReferencesWithQuantifiesAlgorithmTest {


    @Test
    public void cimple__shouldAddNewRuleForIds() {
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

        // the easiest way to come up with a test case is to use the ouput of Detetect list with separator, since we know that it produces
        // that kind of rules we want to rewrite
        DetectListWithSeparatorsAlgorithm detectListWithSeparatorsAlgorithm = new DetectListWithSeparatorsAlgorithm();
        GrammarInfo withSeparators = detectListWithSeparatorsAlgorithm.transform(withFormatting);

        FlatReferencesWithQuantifiesAlgorithm flatReferencesWithQuantifiesAlgorithm = new FlatReferencesWithQuantifiesAlgorithm();
        GrammarInfo withNewRules = flatReferencesWithQuantifiesAlgorithm.transform(withSeparators);

        verifyAllFormatInfoNotNull(withNewRules);

        FormatInfoMapToDSLConvertor.print(withNewRules);


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
                                        element("minus_2", newLine(false), space(false)),
                                        element("INT", newLine(false), space(true))
                                )
                        ),
                        rule("expression", 17,
                                handle(
                                        element("minus_3", newLine(false), space(false)),
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
                                        collection("id_1", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false), childrenSeparator(","))
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
                        ),
                        rule("minus_1", 0,
                                handle(
                                        element("-", newLine(false), space(true))
                                )
                        ),
                        rule("minus_2", 0,
                                handle(
                                        element("-", newLine(false), space(true))
                                )
                        ),
                        rule("minus_3", 0,
                                handle(
                                        element("-", newLine(false), space(true))
                                )
                        ),
                        rule("id_1", 0,
                                handle(
                                        element("ID", newLine(false), space(true))
                                )
                        )
                )
        );
    }
}