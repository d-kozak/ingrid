package premun.mps.ingrid.tranformer;

import org.junit.Test;
import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.formatter.utils.Pair;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.model.ParserRule;
import premun.mps.ingrid.parser.GrammarParser;
import premun.mps.ingrid.transformer.DetectListWithSeparatorsAlgorithm;

import java.util.LinkedHashMap;
import java.util.Map;

public class DetectListWithSeparatorsAlgorithmTest {

    @Test
    public void cimple__shouldFindArgsAndParameters() {
        String cimple = TestGrammars.loadCimple();

        Pair<GrammarInfo, Map<Pair<ParserRule, Alternative>, RuleFormatInfo>> grammarInfo = processGrammar(cimple);
    }

    private Pair<GrammarInfo, Map<Pair<ParserRule, Alternative>, RuleFormatInfo>> processGrammar(String cimple) {
        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(cimple);
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();

        DetectListWithSeparatorsAlgorithm detectListWithSeparatorsAlgorithm = new DetectListWithSeparatorsAlgorithm();
        detectListWithSeparatorsAlgorithm.transform(grammarInfo, new LinkedHashMap<>());
        return Pair.pair(grammarInfo, new LinkedHashMap<>());
    }
}
