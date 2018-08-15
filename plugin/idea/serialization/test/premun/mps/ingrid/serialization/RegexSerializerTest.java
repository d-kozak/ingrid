package premun.mps.ingrid.serialization;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Verifies that regex expressions are serialized correctly.
 *
 * @author dkozak
 */
public class RegexSerializerTest {

    @Test
    public void booleanParseTest() {
        assertEquals("('true'|'false')",
                RegexSerializer.serializeRegex("(true|false)"));
    }

    @Test
    public void addQuotesAroundLiteralTest__1() {
        assertEquals("'0'('_'+)?[0-7](((([0-7]|'_'))+)?[0-7])?", RegexSerializer.serializeRegex("0(_+)?[0-7](((([0-7]|_))+)?[0-7])?"));
    }

    @Test
    public void addQuotesAroundLiteralTest__2() {
        assertEquals("'0'[xX][0-9a-fA-F](((([0-9a-fA-F]|'_'))+)?[0-9a-fA-F])?([lL])?", RegexSerializer.serializeRegex("0[xX][0-9a-fA-F](((([0-9a-fA-F]|_))+)?[0-9a-fA-F])?([lL])?"));
    }

    @Test
    public void addQuotesAroundLiteralTest__3() {
        assertEquals("[01](((([01]|'_'))+)?[01])?", RegexSerializer.serializeRegex("[01](((([01]|_))+)?[01])?"));
    }

    @Test
    public void addQuotesAroundLiteralTest__4() {
        assertEquals("(([01]|'_'))+", RegexSerializer.serializeRegex("(([01]|_))+"));
    }

    @Test
    public void addQuotesAroundLiteralTest__5() {
        assertEquals(
                "((((('0'|[1-9])(((((('0'|[1-9]))|'_'))+)?('0'|[1-9]))?'.'(('0'|[1-9])(((((('0'|[1-9]))|'_'))+)?('0'|[1-9]))?)?([eE]([+-])?('0'|[1-9])(((((('0'|[1-9]))|'_'))+)?('0'|[1-9]))?)?([fFdD])?)|('.'('0'|[1-9])(((((('0'|[1-9]))|'_'))+)?('0'|[1-9]))?([eE]([+-])?('0'|[1-9])(((((('0'|[1-9]))|'_'))+)?('0'|[1-9]))?)?([fFdD])?)|(('0'|[1-9])(((((('0'|[1-9]))|'_'))+)?('0'|[1-9]))?[eE]([+-])?('0'|[1-9])(((((('0'|[1-9]))|'_'))+)?('0'|[1-9]))?([fFdD])?)|(('0'|[1-9])(((((('0'|[1-9]))|'_'))+)?('0'|[1-9]))?[fFdD])))|((('0'[xX][0-9a-fA-F](((([0-9a-fA-F]|'_'))+)?[0-9a-fA-F])?('.')?)|('0'[xX]([0-9a-fA-F](((([0-9a-fA-F]|'_'))+)?[0-9a-fA-F])?)?'.'[0-9a-fA-F](((([0-9a-fA-F]|'_'))+)?[0-9a-fA-F])?))[pP]([+-])?('0'|[1-9])(((((('0'|[1-9]))|'_'))+)?('0'|[1-9]))?([fFdD])?))"
                , RegexSerializer.serializeRegex(
                        "(((((0|[1-9])((((((0|[1-9]))|_))+)?(0|[1-9]))?\\.((0|[1-9])((((((0|[1-9]))|_))+)?(0|[1-9]))?)?([eE]([+-])?(0|[1-9])((((((0|[1-9]))|_))+)?(0|[1-9]))?)?([fFdD])?)|(\\.(0|[1-9])((((((0|[1-9]))|_))+)?(0|[1-9]))?([eE]([+-])?(0|[1-9])((((((0|[1-9]))|_))+)?(0|[1-9]))?)?([fFdD])?)|((0|[1-9])((((((0|[1-9]))|_))+)?(0|[1-9]))?[eE]([+-])?(0|[1-9])((((((0|[1-9]))|_))+)?(0|[1-9]))?([fFdD])?)|((0|[1-9])((((((0|[1-9]))|_))+)?(0|[1-9]))?[fFdD])))|(((0[xX][0-9a-fA-F](((([0-9a-fA-F]|_))+)?[0-9a-fA-F])?(\\.)?)|(0[xX]([0-9a-fA-F](((([0-9a-fA-F]|_))+)?[0-9a-fA-F])?)?\\.[0-9a-fA-F](((([0-9a-fA-F]|_))+)?[0-9a-fA-F])?))[pP]([+-])?(0|[1-9])((((((0|[1-9]))|_))+)?(0|[1-9]))?([fFdD])?))"
                ));
    }

    @Test
    public void addQuotesAroundLiteralTest__6() {
        String input = "(\\\\'~['\\\\\\r\\n]\\\\'|(\\\\'(\\\\\\\\[btnfr\"'\\\\]|((\\\\\\\\[0-7]|\\\\\\\\[0-7][0-7]|\\\\\\\\[0-3][0-7][0-7]))|\\\\\\\\u+[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F])\\\\'))";
        String expected = "('\\''~['\\\\\\r\\n]'\\''|('\\''('\\\\'[btnfr\"'\\\\]|(('\\\\'[0-7]|'\\\\'[0-7][0-7]|'\\\\'[0-3][0-7][0-7]))|'\\\\''u'+[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F])'\\''))";

        assertEquals(expected, RegexSerializer.serializeRegex(input));
    }


    @Test
    public void removeOuterBackslashTest__simple() {
        String input = "\\\\[\\\\]\\\\\\\\";

        String expected = "\\[\\\\]\\\\";

        assertEquals(expected, RegexSerializer.removeOuterDoubleBackSlash(input));
    }

    @Test
    public void removeOuterBackslashTest__advanced() {
        String input = "(\\\\'~['\\\\\\r\\n]\\\\'|(\\\\'(\\\\\\\\[btnfr\"'\\\\]|((\\\\\\\\[0-7]|\\\\\\\\[0-7][0-7]|\\\\\\\\[0-3][0-7][0-7]))|\\\\\\\\u+[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F])\\\\'))";

        String expected = "(\\'~['\\\\\\r\\n]\\'|(\\'(\\\\[btnfr\"'\\\\]|((\\\\[0-7]|\\\\[0-7][0-7]|\\\\[0-3][0-7][0-7]))|\\\\u+[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F])\\'))";

        assertEquals(expected, RegexSerializer.removeOuterDoubleBackSlash(input));
    }

}
