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
 * A set of tests of format extraction for Expression language
 *
 * @author dkozak
 * @see FormatExtractor
 * @see TestGrammars
 */
public class ExpressionGrammarFormatExtractionTest {

    @Test
    public void expressionGrammarVerySimple() {
        GrammarInfo grammarInfo = extractFormat("(1 + 1) * 2", TestGrammars.expressionGrammar);
        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule(
                                "expr", 5,
                                handle(
                                        element("INT", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 3,
                                handle(
                                        element("expr", newLine(false), space(true)),
                                        element("+", newLine(false), space(true)),
                                        element("expr", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 1,
                                handle(
                                        element("expr", newLine(false), space(true)),
                                        element("*", newLine(false), space(true)),
                                        element("expr", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 0,
                                handle(
                                        element("(", newLine(false), space(false)),
                                        element("expr", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        )
                )
        );
    }

    @Test
    public void expressionGrammarVerySimple__noSpaces() {
        GrammarInfo grammarInfo = extractFormat("(1+1)*2", TestGrammars.expressionGrammar);
        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule(
                                "expr", 5,
                                handle(
                                        element("INT", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 3,
                                handle(
                                        element("expr", newLine(false), space(false)),
                                        element("+", newLine(false), space(false)),
                                        element("expr", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 1,
                                handle(
                                        element("expr", newLine(false), space(false)),
                                        element("*", newLine(false), space(false)),
                                        element("expr", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 0,
                                handle(
                                        element("(", newLine(false), space(false)),
                                        element("expr", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        )
                )
        );
    }

    @Test
    public void expressionGrammarVerySimple__spacesEverywhere() {
        GrammarInfo grammarInfo = extractFormat("( 1 + 1 ) * 2", TestGrammars.expressionGrammar);
        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule(
                                "expr", 5,
                                handle(
                                        element("INT", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 3,
                                handle(
                                        element("expr", newLine(false), space(true)),
                                        element("+", newLine(false), space(true)),
                                        element("expr", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 1,
                                handle(
                                        element("expr", newLine(false), space(true)),
                                        element("*", newLine(false), space(true)),
                                        element("expr", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 0,
                                handle(
                                        element("(", newLine(false), space(true)),
                                        element("expr", newLine(false), space(true)),
                                        element(")", newLine(false), space(true))
                                )
                        )
                )
        );
    }

    @Test
    public void expressionGrammarVerySimple__plusHasNewLinesExpectForRightExpr() {
        GrammarInfo grammarInfo = extractFormat("(1\n+\n1)*2", TestGrammars.expressionGrammar);
        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule(
                                "expr", 5,
                                handle(
                                        element("INT", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 3,
                                handle(
                                        element("expr", newLine(true), space(false)),
                                        element("+", newLine(true), space(false)),
                                        element("expr", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 1,
                                handle(
                                        element("expr", newLine(false), space(false)),
                                        element("*", newLine(false), space(false)),
                                        element("expr", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 0,
                                handle(
                                        element("(", newLine(false), space(false)),
                                        element("expr", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        )
                )
        );
    }

    @Test
    public void expressionGrammarVerySimple__plusHasNewLines__brackHasNewLineAfterLeftBracket() {
        GrammarInfo grammarInfo = extractFormat("(\n1\n+\n1)*2", TestGrammars.expressionGrammar);
        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule(
                                "expr", 5,
                                handle(
                                        element("INT", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 3,
                                handle(
                                        element("expr", newLine(true), space(false)),
                                        element("+", newLine(true), space(false)),
                                        element("expr", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 1,
                                handle(
                                        element("expr", newLine(false), space(false)),
                                        element("*", newLine(false), space(false)),
                                        element("expr", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 0,
                                handle(
                                        element("(", newLine(true), space(false)),
                                        element("expr", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        )
                )
        );
    }

    @Test
    public void expressionGrammarVerySimple__plusHasNewLines__brackHasNewLineAfterLeftBracket__multHasSpaceAfterLeftExpr() {
        GrammarInfo grammarInfo = extractFormat("(\n1\n+\n1) *2", TestGrammars.expressionGrammar);
        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule(
                                "expr", 5,
                                handle(
                                        element("INT", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 3,
                                handle(
                                        element("expr", newLine(true), space(false)),
                                        element("+", newLine(true), space(false)),
                                        element("expr", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 1,
                                handle(
                                        element("expr", newLine(false), space(true)),
                                        element("*", newLine(false), space(false)),
                                        element("expr", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 0,
                                handle(
                                        element("(", newLine(true), space(false)),
                                        element("expr", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        )
                )
        );
    }

    @Test
    public void expressionGrammarVerySimple__plusHasNewLines__brackHasNewLineAfterLeftBracket__multHasSpaceAfterLeftExprAndSpaceAfterTheMultSymbol() {
        GrammarInfo grammarInfo = extractFormat("(\n1\n+\n1) *\n2", TestGrammars.expressionGrammar);
        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule(
                                "expr", 5,
                                handle(
                                        element("INT", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 3,
                                handle(
                                        element("expr", newLine(true), space(false)),
                                        element("+", newLine(true), space(false)),
                                        element("expr", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 1,
                                handle(
                                        element("expr", newLine(false), space(true)),
                                        element("*", newLine(true), space(false)),
                                        element("expr", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 0,
                                handle(
                                        element("(", newLine(true), space(false)),
                                        element("expr", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        )
                )
        );
    }

    @Test
    public void expressionGrammarMoreComplex() {
        GrammarInfo grammarInfo = extractFormat("((2*1) + 1)*2", TestGrammars.expressionGrammar);
        dumpFormatting(grammarInfo);

        verifyFormatInfo(
                grammarInfo,
                rules(
                        rule(
                                "expr", 5,
                                handle(
                                        element("INT", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 3,
                                handle(
                                        element("expr", newLine(false), space(true)),
                                        element("+", newLine(false), space(true)),
                                        element("expr", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 1,
                                handle(
                                        element("expr", newLine(false), space(false)),
                                        element("*", newLine(false), space(false)),
                                        element("expr", newLine(false), space(true))
                                )
                        ),
                        rule(
                                "expr", 0,
                                handle(
                                        element("(", newLine(false), space(false)),
                                        element("expr", newLine(false), space(false)),
                                        element(")", newLine(false), space(true))
                                )
                        )
                )
        );
    }
}
