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
                                        elem("INT", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 3,
                                handle(
                                        elem("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("+", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 1,
                                handle(
                                        elem("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("*", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 0,
                                handle(
                                        elem("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expr", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
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
                                        elem("INT", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 3,
                                handle(
                                        elem("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("+", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 1,
                                handle(
                                        elem("expr", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("*", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expr", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        ),
                        rule(
                                "expr", 0,
                                handle(
                                        elem("(", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem("expr", newLine(false), space(false), childrenOnNewLine(false), childrenIndented(false)),
                                        elem(")", newLine(false), space(true), childrenOnNewLine(false), childrenIndented(false))
                                )
                        )
                )
        );
    }
}
