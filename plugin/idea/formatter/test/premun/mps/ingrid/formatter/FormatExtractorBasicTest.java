package premun.mps.ingrid.formatter;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import premun.mps.ingrid.formatter.model.FormatInfo;
import premun.mps.ingrid.formatter.model.FormatInfoMapKey;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.parser.GrammarParser;

import java.util.Map;

public class FormatExtractorBasicTest {

    @Test
    public void setGrammarEmptySet() throws RecognitionException {
        Map<FormatInfoMapKey, FormatInfo> formatInfoMap = extractFormat("{}", TestGrammars.setGrammar);
        System.out.println(formatInfoMap);
    }

    @Test
    public void setGrammarSimpleInput() throws RecognitionException {
        Map<FormatInfoMapKey, FormatInfo> formatInfoMap = extractFormat("{1,2,3}", TestGrammars.setGrammar);
        System.out.println(formatInfoMap);
    }

    @Test
    public void setGrammarNestedInput() throws RecognitionException {
        Map<FormatInfoMapKey, FormatInfo> formatInfoMap = extractFormat("{1,{a,b,c},3}", TestGrammars.setGrammar);
        System.out.println(formatInfoMap);
    }


    @Test
    public void setGrammarNestedInputMoreComplex() throws RecognitionException {
        Map<FormatInfoMapKey, FormatInfo> formatInfoMap = extractFormat("{1,{a,b,c},{{},{a,b,c}}}", TestGrammars.setGrammar);
        System.out.println(formatInfoMap);
    }

    @Test
    public void expressionGrammarVerySimple() throws RecognitionException {
        Map<FormatInfoMapKey, FormatInfo> formatInfoMap = extractFormat("(1 + 1) * 2", TestGrammars.expressionGrammar);
        System.out.println(formatInfoMap);
    }

    @Test
    public void expressionGrammarMoreComplex() throws RecognitionException {
        Map<FormatInfoMapKey, FormatInfo> formatInfoMap = extractFormat("((2*1) + 1) * 2", TestGrammars.expressionGrammar);
        System.out.println(formatInfoMap);
    }

    private Map<FormatInfoMapKey, FormatInfo> extractFormat(String input, String grammar) throws RecognitionException {
        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(grammar);
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();
        return FormatExtractor.extract(grammarInfo, grammar, input);
    }
}
