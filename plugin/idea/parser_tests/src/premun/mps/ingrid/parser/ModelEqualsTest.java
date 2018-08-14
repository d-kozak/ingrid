package premun.mps.ingrid.parser;

import org.junit.Test;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static premun.mps.ingrid.formatter.utils.Pair.pair;

/**
 * Tests whether equals method on the Ingrid model works as expected
 *
 * @author dkozak
 */
public class ModelEqualsTest {


    @Test
    public void setGrammar() {
        testEquals(TestGrammars.setGrammar);

    }

    @Test
    public void exprGrammar() {
        testEquals(TestGrammars.expressionGrammar);
    }

    @Test
    public void bookGrammar() {
        testEquals(TestGrammars.bookGrammar);
    }

    @Test
    public void simpleGrammar() {
        testEquals(TestGrammars.simpleGrammar);
    }

    @Test
    public void nestedBlockGrammarGrammar() {
        testEquals(TestGrammars.nestedBlockGrammar);
    }

    @Test
    public void CimpleGrammar() {
        testEquals(TestGrammars.loadCimple());
    }

    @Test
    public void Java9Grammar() {
        testEquals(TestGrammars.loadJava9());
    }

    @Test
    public void setAndExprNotEquals() {
        testNotEquals(TestGrammars.setGrammar, TestGrammars.expressionGrammar);
    }

    @Test
    public void exprAndBookNotEquals() {
        testNotEquals(TestGrammars.expressionGrammar, TestGrammars.bookGrammar);
    }

    @Test
    public void cimpleAndJava9NotEquals() {
        testNotEquals(TestGrammars.loadCimple(), TestGrammars.loadJava9());
    }


    private void testNotEquals(String grammarOne, String grammarTwo) {
        GrammarInfo infoOne = getAndCheckGrammarInfo(grammarOne);
        GrammarInfo infoTwo = getAndCheckGrammarInfo(grammarTwo);
        assertNotEquals(infoOne, infoTwo);
        assertNotEquals(infoOne.hashCode(), infoTwo.hashCode());
    }


    private void testEquals(String grammar) {
        GrammarInfo infoOne = getAndCheckGrammarInfo(grammar);
        GrammarInfo infoTwo = getAndCheckGrammarInfo(grammar);
        assertEquals(infoOne, infoTwo);
        assertEquals(infoOne.hashCode(), infoTwo.hashCode());
    }

    private GrammarInfo getAndCheckGrammarInfo(String grammar) {
        GrammarParser parserOne = new GrammarParser();
        parserOne.parseString(grammar);
        GrammarInfo grammarInfo = parserOne.resolveGrammar();
        checkGrammarInfo(grammarInfo);
        return grammarInfo;
    }

    private void checkGrammarInfo(GrammarInfo grammarInfo) {
        List<Rule> rules = new ArrayList<>(grammarInfo.rules.values());
        for (int ruleIndex = 0; ruleIndex < rules.size(); ruleIndex++) {
            Rule rule = rules.get(ruleIndex);
            assertEquals(rule, rule);
            assertEquals(rule.hashCode(), rule.hashCode());
            for (int i = 0; i < rules.size(); i++) {
                if (i != ruleIndex) {
                    assertNotEquals(rule, rules.get(i));
                }
            }
            assertNotNull(grammarInfo.rules.get(rule.name));
            if (rule instanceof ParserRule) {
                List<Alternative> alternatives = ((ParserRule) rule).alternatives;
                for (int alternativeIndex = 0; alternativeIndex < alternatives.size(); alternativeIndex++) {
                    Alternative alternative = alternatives.get(alternativeIndex);
                    assertEquals(alternative, alternative);
                    assertEquals(alternative.hashCode(), alternative.hashCode());
                    assertEquals(pair(rule, alternative), pair(rule, alternative));
                    assertEquals(pair(rule, alternative).hashCode(), pair(rule, alternative).hashCode());
                    for (int i = 0; i < alternatives.size(); i++) {
                        if (i != alternativeIndex) {
                            assertNotEquals(alternative, alternatives.get(i));
                        }
                    }
                    for (RuleReference element : alternative.elements) {
                        assertEquals(element, element);
                        assertEquals(element.hashCode(), element.hashCode());
                        if (element.rule instanceof ParserRule) {
                            assertNotNull("Rule " + element.rule.name + " not found in " + grammarInfo.rules, grammarInfo.rules.get(element.rule.name));
                        }
                    }
                }
            }
        }
    }

}
