package premun.mps.ingrid.tranformer;

import org.junit.Test;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.parser.GrammarParser;
import premun.mps.ingrid.serialization.GrammarSerializer;
import premun.mps.ingrid.transformer.GrammarTransformer;

import java.util.Collections;
import java.util.List;

import static premun.mps.ingrid.tranformer.GrammarAsserts.assertGrammarEquals;

/**
 * Tests of the rule inline algorithm
 *
 * @author dkozak
 */
public class GrammarTransformerTest {

    @Test
    public void bookGrammar__inlineBookName() {
        String grammar = TestGrammars.bookGrammar;
        List<String> toInline = Collections.singletonList("bookName");

        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(grammar);
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();

        GrammarInfo simplified = GrammarTransformer.inlineRules(grammarInfo, toInline);

        String result = GrammarSerializer.serializeGrammar(simplified);


        String expected = "grammar Book;\n" +
                "\n" +
                "book\n" +
                "    : (shortcut | nickname) ',' STR+ ',' author EOF\n" +
                "    | (shortcut | nickname) ',' STR+ ',' author ',' YEAR EOF\n" +
                "    ;\n" +
                "\n" +
                "shortcut : 'shortcut' STR;\n" +
                "nickname : 'nickname' STR;\n" +
                "\n" +
                "author : STR STR;\n" +
                "\n" +
                "STR : [a-zA-Z]+ ;\n" +
                "\n" +
                "YEAR: [1-9]+;\n" +
                "\n" +
                "\n" +
                "WS : [ \\t\\n] -> skip;";

        System.out.println(result);


        assertGrammarEquals(expected, result);

    }

}
