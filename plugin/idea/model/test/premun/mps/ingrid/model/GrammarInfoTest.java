package premun.mps.ingrid.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static premun.mps.ingrid.model.Utils.createSetGrammarInfo;

/**
 * Tests of the grammar info object, mainly testing it's copy
 * constructor, but other things such as equals are tested transitively.
 *
 * @author dkozak
 */
public class GrammarInfoTest {

    @Test
    public void copyConstructorTest__copyIsEqualWithOriginal() {
        GrammarInfo setGrammarInfo = createSetGrammarInfo();

        GrammarInfo copy = new GrammarInfo(setGrammarInfo);
        assertEquals(setGrammarInfo, copy);
    }


    @Test
    public void copyConstructorTest__copyContainRulesWithSameName() {
        GrammarInfo setGrammarInfo = createSetGrammarInfo();

        GrammarInfo copy = new GrammarInfo(setGrammarInfo);

        assertEquals(
                setGrammarInfo.rules.keySet(), copy.rules.keySet());
    }

    @Test
    public void copyConstructorTest__rootRuleIsTheSame() {
        GrammarInfo setGrammarInfo = createSetGrammarInfo();

        GrammarInfo copy = new GrammarInfo(setGrammarInfo);

        assertEquals(
                setGrammarInfo.rootRule.name, copy.rootRule.name);
        assertEquals(setGrammarInfo.rootRule, copy.rootRule);

        assertNotNull(copy.rules.get(copy.rootRule.name));
    }


}
