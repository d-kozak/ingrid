package premun.mps.ingrid.formatter;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.tool.Grammar;
import org.junit.Test;
import premun.mps.ingrid.formatter.utils.TestGrammars;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @see InterpretingParser
 * @author dkozak
 */
public class InterpretingParserTest {

    /**
     * Checks whether sentences in set language was parsed correctly
     */
    @Test
    public void parsingSetLanguage() throws RecognitionException {
        Grammar grammar = new Grammar(TestGrammars.setGrammar);
        String input = "{a,b,{}}";
        ParseTree parseTree = InterpretingParser.parse(grammar, input, "compilationUnit");
        assertEquals(2, parseTree.getChildCount()); // set EOF
        ParseTree set = parseTree.getChild(0);

        assertEquals(7, set.getChildCount()); // { a , b , {} } <- 7 children (empty set counts as one)

        assertTrue(set.getChild(0) instanceof TerminalNode);
        assertEquals("{", ((TerminalNode) set.getChild(0)).getSymbol()
                                                          .getText());

        assertTrue(set.getChild(1) instanceof ParserRuleContext);
        assertEquals(2, ((ParserRuleContext) set.getChild(1)).getRuleIndex()); // 2 == index of collection rule

        assertTrue(set.getChild(2) instanceof TerminalNode);
        assertEquals(",", (((TerminalNode) set.getChild(2)).getSymbol()
                                                           .getText()));

        assertTrue(set.getChild(3) instanceof ParserRuleContext);
        assertEquals(2, ((ParserRuleContext) set.getChild(3)).getRuleIndex());// 2 == index of collection rule

        assertTrue(set.getChild(4) instanceof TerminalNode);
        assertEquals(",", ((TerminalNode) set.getChild(4)).getSymbol()
                                                          .getText());

        assertTrue(set.getChild(5) instanceof ParserRuleContext);
        assertEquals(2, ((ParserRuleContext) set.getChild(5)).getRuleIndex());// 2 == index of set rule

        ParseTree elemAsSetWrapper = set.getChild(5); // collection
        assertEquals(1, elemAsSetWrapper.getChildCount()); // collection should have one child, the inner set
        ParseTree innerSet = elemAsSetWrapper.getChild(0);
        assertTrue(innerSet instanceof ParserRuleContext);
        assertEquals(2, innerSet.getChildCount()); // inner set has just two children, the curly brackets { }

        assertTrue(set.getChild(6) instanceof TerminalNode && "}".equals(((TerminalNode) set.getChild(6)).getSymbol()
                                                                                                         .getText()));
    }
}
