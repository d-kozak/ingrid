package premun.mps.ingrid.formatter;

import org.junit.Test;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.parser.GrammarParser;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Grammars used for testing, saved as strings
 *
 * @author dkozak
 */
public final class TestGrammars {

    /**
     * Simple grammar for set language
     */
    public static final String setGrammar = "grammar Set;\n" +
            "\n" +
            "compilationUnit : set EOF ;\n" +
            "\n" +
            "set\n" +
            "    : '{' '}' # emptySet\n" +
            "    | '{' elem (',' elem)* '}' # nonEmptySet\n" +
            "    ;\n" +
            "\n" +
            "elem\n" +
            "    : simpleElement\n" +
            "    | set\n" +
            "    ;\n" +
            "\n" +
            "simpleElement : ELEM;\n" +
            "\n" +
            "ELEM\n" +
            "    : ('A'..'Z' | 'a'..'z' | '0'..'9' |  '_')+\n" +
            "    ;\n" +
            "\n" +
            "WS : [ \\t\\r\\n]+ -> skip ;";


    /**
     * Simple grammar for arithmetic expressions
     */
    public static final String expressionGrammar = "grammar expr;\n" +
            "\n" +
            "expr\n" +
            "    : '(' expr ')' # bracket\n" +
            "    | expr '*' expr # mult\n" +
            "    | expr '/' expr # div\n" +
            "    | expr '+' expr # plus\n" +
            "    | expr '-' expr # minus\n" +
            "    | INT # int\n" +
            "    | VAR # var\n" +
            "    ;\n" +
            "\n" +
            "VAR\n" +
            "    : [a-zA-Z_]+\n" +
            "    ;\n" +
            "\n" +
            "INT\n" +
            "    : [0-9]+\n" +
            "    ;\n" +
            "\n" +
            "WS\n" +
            "    : [ \\t\\r\\n]+ -> skip // skip spaces, tabs, newlines\n" +
            "    ;";

    public static final String simpleGrammar = "grammar test;\n" +
            "\n" +
            "rule\n" +
            "\t: e f g '}'\n" +
            "\t| '{'\n" +
            "\t;\ngrammar test;\n" +
            "\n" +
            "rule\n" +
            "\t: e f g '}'\n" +
            "\t| '{'\n" +
            "\t;\n";


    /**
     * Parses the grammar specified by the grammarName. GrammarName has to correspond to a static String field in this class, "Grammar" substring excluded
     */
    public GrammarInfo prepareGrammar(String grammarName) {
        try {
            String grammar = (String) Arrays.stream(getClass().getFields())
                                            .filter(field -> Modifier.isStatic(field.getModifiers()))
                                            .filter(field -> String.class
                                                    .equals(field.getType()))
                                            .filter(field -> field.getName()
                                                                  .endsWith("Grammar"))
                                            .filter(field -> (grammarName + "Grammar").equals(field.getName()))
                                            .findFirst()
                                            .orElseThrow(() -> new IllegalArgumentException("Corresponding grammar not found"))
                                            .get(null);
            return parseGrammar(grammar);
        } catch (IllegalAccessException ex) {
            throw new IllegalArgumentException("Could not access specified grammar field");
        }

    }

    private GrammarInfo parseGrammar(String input) {
        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(input);
        return grammarParser.resolveGrammar();
    }


    @Test
    public void prepareGrammarTest() {
        GrammarInfo expression = prepareGrammar("expression");
        assertEquals("expr", expression.name);
        assertEquals(4, expression.rules.size());
        assertEquals("expr", expression.rootRule.name);
    }


    @Test(expected = IllegalArgumentException.class)
    public void nonExistingGrammar() {
        prepareGrammar("abraka dabra");
    }
}
