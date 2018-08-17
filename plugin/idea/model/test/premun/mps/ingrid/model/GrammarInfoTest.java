package premun.mps.ingrid.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

    /**
     * Manually creates the set grammar info object
     */
    private GrammarInfo createSetGrammarInfo() {
        GrammarInfo grammarInfo = new GrammarInfo("foo");

        // create all the rules
        ParserRule compilationUnit = new ParserRule("compilationUnit");
        grammarInfo.rootRule = compilationUnit;
        grammarInfo.rules.put(compilationUnit.name, compilationUnit);

        ParserRule set = new ParserRule("set");
        grammarInfo.rules.put(set.name, set);

        ParserRule elem = new ParserRule("elem");
        grammarInfo.rules.put(elem.name, elem);

        ParserRule simpleElement = new ParserRule("simpleElement");
        grammarInfo.rules.put(simpleElement.name, simpleElement);

        RegexRule ELEM = new RegexRule("ELEM", "[A-Za-Z0-9]+");
        grammarInfo.rules.put(ELEM.name, ELEM);

        RegexRule WS = new RegexRule("WS", "[ \\t\\r\\n]+");
        grammarInfo.rules.put(WS.name, WS);

        LiteralRule leftBracket = new LiteralRule("{", "{");
        grammarInfo.rules.put(leftBracket.name, leftBracket);

        LiteralRule rightBracket = new LiteralRule("}", "}");
        grammarInfo.rules.put(rightBracket.name, rightBracket);

        LiteralRule comma = new LiteralRule(",", ",");
        grammarInfo.rules.put(comma.name, comma);

        LiteralRule eof = new LiteralRule("EOF", "EOF");
        grammarInfo.rules.put(eof.name, eof);


        ParserRule setBlockRule = new ParserRule("set_block_1_0");
        grammarInfo.rules.put(setBlockRule.name, setBlockRule);


        // create alternatives with references

        compilationUnit.alternatives.add(
                new Alternative(
                        Arrays.asList(
                                new RuleReference(set),
                                new RuleReference(eof)
                        )
                ));

        set.alternatives.add(
                new Alternative(
                        Arrays.asList(
                                new RuleReference(leftBracket),
                                new RuleReference(rightBracket)
                        )
                ));
        set.alternatives.add(
                new Alternative(
                        Arrays.asList(
                                new RuleReference(leftBracket),
                                new RuleReference(elem),
                                new RuleReference(setBlockRule, Quantity.ANY),
                                new RuleReference(rightBracket)
                        )
                )
        );

        setBlockRule.alternatives.add(
                new Alternative(
                        Arrays.asList(
                                new RuleReference(comma),
                                new RuleReference(elem))
                ));

        elem.alternatives.add(
                new Alternative(
                        Collections.singletonList(new RuleReference(simpleElement))
                )
        );

        elem.alternatives.add(
                new Alternative(
                        Collections.singletonList(new RuleReference(set))
                )
        );

        simpleElement.alternatives.add(
                new Alternative(
                        Collections.singletonList(new RuleReference(ELEM))
                )
        );


        return grammarInfo;
    }
}
