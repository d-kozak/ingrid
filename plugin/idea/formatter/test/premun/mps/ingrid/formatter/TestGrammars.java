package premun.mps.ingrid.formatter;

import org.junit.Test;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.parser.GrammarParser;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

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

    /**
     * A very simple grammar, it's rule 'rule' has two alternatives, it can used for a very minimalistic check of the alternative resolving.
     */
    public static final String simpleGrammar = "grammar test;\n" +
            "\n" +
            "rule\n" +
            "\t: e f g '}'\n" +
            "\t| '{'\n" +
            "\t;\n" +
            "e : 'e';\n" +
            "f : 'f';\n" +
            "g : 'g';";


    /**
     * Grammar which contains inner block rules that can cause pain in the alternative resolving.
     */
    public static final String bookGrammar = "grammar Book;\n" +
            "\n" +
            "book\n" +
            "    : (shortcut | nickname) ',' bookName ',' author EOF\n" +
            "    | (shortcut | nickname) ',' bookName ',' author ',' YEAR EOF\n" +
            "    ;\n" +
            "\n" +
            "shortcut : 'shortcut' STR;\n" +
            "nickname : 'nickname' STR;\n" +
            "\n" +
            "bookName : STR+;\n" +
            "\n" +
            "author : STR STR;\n" +
            "\n" +
            "STR : [a-zA-Z]+ ;\n" +
            "\n" +
            "YEAR: [1-9]+;\n" +
            "\n" +
            "\n" +
            "WS : [ \\t\\n] -> skip;";

    /**
     * Grammar with nested blocks that can be simplified during the flattening.
     * Ideally it should be temporarily changed to:
     * r1 : 'a' ;
     * r2 : 'b' ;
     * r3 : c ;
     * r4 : 'd' ;
     */
    public static final String nestedBlockGrammar = "grammar nested;\n" +
            "\n" +
            "r : ('a' | ('b' | ( c | 'd'))) ; \n" +
            "c : 'c';";

    /**
     * Parses the grammar specified by the grammarName. GrammarName has to correspond to a static String field in this class, "Grammar" substring excluded
     */
    public static GrammarInfo prepareGrammar(String grammarName) {
        try {
            String grammar = (String) Arrays.stream(TestGrammars.class.getFields())
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

    /**
     * Parses a given grammar
     *
     * @param input grammar to parse
     * @return parsed grammar representation
     */
    public static GrammarInfo parseGrammar(String input) {
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


    public static String loadJava9() {
        return loadResource("/Java9.cpp");
    }

    public static String loadCpp14() {
        return loadResource("/CPP14.g4");
    }

    public static String loadResource(String resourcePath) {
        try {
            String path = TestGrammars.class.getResource(resourcePath)
                                            .getPath();
            return Files.lines(Paths.get(path))
                        .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
