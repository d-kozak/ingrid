package premun.mps.ingrid.parser;

import org.junit.Test;
import premun.mps.ingrid.model.LiteralRule;

import static junit.framework.TestCase.assertEquals;
import static premun.mps.ingrid.parser.GrammarResolver.escapeLiteral;

/**
 * @author dkozak
 */
public class EscapeLiteralTest {

    private static String escape(String value) {
        return escapeLiteral(new LiteralRule(value));
    }

    @Test
    public void noEscaping() {
        assertEquals("abc", escape("abc"));
    }

    @Test
    public void newline() {
        assertEquals("\\n", escape("\\n"));
    }

    @Test
    public void caryNewline() {
        assertEquals("\\r\\n", escape("\\r\\n"));
    }


    @Test
    public void dot() {
        assertEquals("\\.", escape("."));
    }

    @Test
    public void all() {
        assertEquals("\\.\\[\\]\\{\\}\\(\\)\\*\\+\\-\\?\\^\\$\\|", escape(".[]{}()*+-?^$|"));
    }

    @Test
    public void allAndAlsoNormalStuff() {
        assertEquals("\\.a\\[b\\]c\\{d\\}\\(\\)\\*\\+\\-\\?\\^\\$f\\|", escape(".a[b]c{d}()*+-?^$f|"));
    }
}
