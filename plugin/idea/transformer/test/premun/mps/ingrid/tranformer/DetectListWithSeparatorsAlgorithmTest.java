package premun.mps.ingrid.tranformer;

import org.junit.Test;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.parser.GrammarParser;
import premun.mps.ingrid.transformer.DetectListWithSeparatorsAlgorithm;

public class DetectListWithSeparatorsAlgorithmTest {

    @Test
    public void cimple__shouldFindArgsAndParameters() {
        String cimple = TestGrammars.loadCimple();

        GrammarInfo grammarInfo = processGrammar(cimple);
    }

    private GrammarInfo processGrammar(String cimple) {
        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(cimple);
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();

        return DetectListWithSeparatorsAlgorithm.detectListsWithSeparators(grammarInfo);
    }
}
