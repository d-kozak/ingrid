package premun.mps.ingrid.model;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests whether equals work as expected.
 * More complex test are done in parser_test, when we can actually test
 * the grammar info returned from parser.
 *
 * @author dkozak
 */
public class EqualsTest {

    @Test
    public void literalRuleEquals() {
        LiteralRule leftBracketOne = new LiteralRule("{");
        LiteralRule leftBracketTwo = new LiteralRule("{");

        LiteralRule rightBracket = new LiteralRule("}");

        checkEqualsMethod(leftBracketOne, leftBracketTwo, rightBracket);
    }

    @Test
    public void regexRuleEquals() {
        RegexRule lowerCaseOne = new RegexRule("lowerCase", "[a-z]");
        RegexRule loweCaseTwo = new RegexRule("lowerCase", "[a-z]");

        RegexRule upperCase = new RegexRule("upperCase", "[A-Z]");
        checkEqualsMethod(lowerCaseOne, loweCaseTwo, upperCase);
    }


    @Test
    public void verifySetGrammar() {
        GrammarInfo setGrammarInfo = Utils.createSetGrammarInfo();

        ArrayList<Rule> rules = new ArrayList<>(setGrammarInfo.rules.values());
        for (int i = 0; i < rules.size(); i++) {
            Rule rule = rules.get(i);
            assertEquals(rule, rule);

            for (int j = 0; j < rules.size(); j++) {
                if (i != j) {
                    assertNotEquals(rule, rules.get(j));
                }
            }
        }
    }

    private void checkEqualsMethod(Object firstEquals, Object secondEquals, Object differentOne) {
        assertEquals(firstEquals, secondEquals);
        assertEquals(secondEquals, firstEquals);

        assertNotEquals(firstEquals, differentOne);
        assertNotEquals(differentOne, firstEquals);
        assertNotEquals(secondEquals, differentOne);
        assertNotEquals(differentOne, secondEquals);
    }
}
