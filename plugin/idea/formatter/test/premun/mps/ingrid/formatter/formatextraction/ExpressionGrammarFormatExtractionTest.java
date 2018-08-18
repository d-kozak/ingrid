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
 * A set of tests of format extraction for Expression language
 *
 * @author dkozak
 * @see FormatExtractor
 * @see TestGrammars
 */
public class ExpressionGrammarFormatExtractionTest {

    @Test
    public void expressionGrammarVerySimple() {
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat("(1 + 1) * 2", TestGrammars.expressionGrammar);
        dumpSimplifiedMap(formatInfoMap);

        verifyFormatInfoMap(
                formatInfoMap,
                rules(
                        rule(
                                "expr", 5,
                                handle(
                                        collection("INT", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 3,
                                handle(
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("+", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 1,
                                handle(
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("*", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 0,
                                handle(
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );
    }

    @Test
    public void expressionGrammarVerySimple__noSpaces() {
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat("(1+1)*2", TestGrammars.expressionGrammar);
        dumpSimplifiedMap(formatInfoMap);

        verifyFormatInfoMap(
                formatInfoMap,
                rules(
                        rule(
                                "expr", 5,
                                handle(
                                        collection("INT", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 3,
                                handle(
                                        collection("expr", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("+", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 1,
                                handle(
                                        collection("expr", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("*", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 0,
                                handle(
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );
    }

    @Test
    public void expressionGrammarVerySimple__spacesEverywhere() {
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat("( 1 + 1 ) * 2", TestGrammars.expressionGrammar);
        dumpSimplifiedMap(formatInfoMap);

        verifyFormatInfoMap(
                formatInfoMap,
                rules(
                        rule(
                                "expr", 5,
                                handle(
                                        collection("INT", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 3,
                                handle(
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("+", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 1,
                                handle(
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("*", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 0,
                                handle(
                                        collection("(", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );
    }

    @Test
    public void expressionGrammarVerySimple__plusHasNewLinesExpectForRightExpr() {
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat("(1\n+\n1)*2", TestGrammars.expressionGrammar);
        dumpSimplifiedMap(formatInfoMap);

        verifyFormatInfoMap(
                formatInfoMap,
                rules(
                        rule(
                                "expr", 5,
                                handle(
                                        collection("INT", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 3,
                                handle(
                                        collection("expr", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("+", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 1,
                                handle(
                                        collection("expr", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("*", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 0,
                                handle(
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );
    }

    @Test
    public void expressionGrammarVerySimple__plusHasNewLines__brackHasNewLineAfterLeftBracket() {
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat("(\n1\n+\n1)*2", TestGrammars.expressionGrammar);
        dumpSimplifiedMap(formatInfoMap);

        verifyFormatInfoMap(
                formatInfoMap,
                rules(
                        rule(
                                "expr", 5,
                                handle(
                                        collection("INT", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 3,
                                handle(
                                        collection("expr", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("+", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 1,
                                handle(
                                        collection("expr", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("*", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 0,
                                handle(
                                        collection("(", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );
    }

    @Test
    public void expressionGrammarVerySimple__plusHasNewLines__brackHasNewLineAfterLeftBracket__multHasSpaceAfterLeftExpr() {
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat("(\n1\n+\n1) *2", TestGrammars.expressionGrammar);
        dumpSimplifiedMap(formatInfoMap);

        verifyFormatInfoMap(
                formatInfoMap,
                rules(
                        rule(
                                "expr", 5,
                                handle(
                                        collection("INT", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 3,
                                handle(
                                        collection("expr", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("+", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 1,
                                handle(
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("*", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 0,
                                handle(
                                        collection("(", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );
    }

    @Test
    public void expressionGrammarVerySimple__plusHasNewLines__brackHasNewLineAfterLeftBracket__multHasSpaceAfterLeftExprAndSpaceAfterTheMultSymbol() {
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat("(\n1\n+\n1) *\n2", TestGrammars.expressionGrammar);
        dumpSimplifiedMap(formatInfoMap);

        verifyFormatInfoMap(
                formatInfoMap,
                rules(
                        rule(
                                "expr", 5,
                                handle(
                                        collection("INT", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 3,
                                handle(
                                        collection("expr", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("+", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 1,
                                handle(
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("*", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 0,
                                handle(
                                        collection("(", newLine(true), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );
    }

    @Test
    public void expressionGrammarMoreComplex() {
        Map<Pair<String, Integer>, RuleFormatInfo> formatInfoMap = extractFormat("((2*1) + 1)*2", TestGrammars.expressionGrammar);
        dumpSimplifiedMap(formatInfoMap);

        verifyFormatInfoMap(
                formatInfoMap,
                rules(
                        rule(
                                "expr", 5,
                                handle(
                                        collection("INT", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 3,
                                handle(
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("+", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 1,
                                handle(
                                        collection("expr", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("*", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 0,
                                handle(
                                        collection("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection("expr", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        collection(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );
    }
}
