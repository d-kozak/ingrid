package premun.mps.ingrid.formatter;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import premun.mps.ingrid.formatter.model.FormatInfo;
import premun.mps.ingrid.formatter.model.FormatInfoMapKey;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.parser.GrammarParser;

import java.util.Map;

public class FormatExtractorTest {

    @Test
    public void setGrammarNoFormatting() throws RecognitionException {
        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(TestGrammars.setGrammar);
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();
        String testInput = "{1,2,3}";
        Map<FormatInfoMapKey, FormatInfo> formatInfoMap = FormatExtractor.extract(grammarInfo, TestGrammars.setGrammar, testInput);
        System.out.println(formatInfoMap);
    }
}
