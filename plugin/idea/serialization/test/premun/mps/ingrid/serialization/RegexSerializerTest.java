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
}
