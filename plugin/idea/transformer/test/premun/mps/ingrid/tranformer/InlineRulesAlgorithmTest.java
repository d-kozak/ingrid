package premun.mps.ingrid.tranformer;

import org.junit.Test;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.model.ParserRule;
import premun.mps.ingrid.parser.GrammarParser;
import premun.mps.ingrid.serialization.IngridModelToAntlrSerializer;
import premun.mps.ingrid.transformer.InlineRulesAlgorithm;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.*;
import static premun.mps.ingrid.tranformer.Utils.assertGrammarEquals;
import static premun.mps.ingrid.tranformer.Utils.loadFileContent;

/**
 * Tests of the rule inline algorithm
 *
 * @author dkozak
 */
public class InlineRulesAlgorithmTest {

    @Test
    public void bookGrammar__inlineNothing() throws IOException {

        String grammar = loadFileContent("/Book.g4");
        List<String> toInline = Collections.emptyList();

        String expected = "grammar Book;\n" +
                "\n" +
                "book : people NEWLINE shops;\n" +
                "\n" +
                "people : person+ ;\n" +
                "\n" +
                "shops : shop+ ;\n" +
                "\n" +
                "person : firstName ',' lastName ;\n" +
                "\n" +
                "firstName : NAME ;\n" +
                "\n" +
                "lastName : NAME ;\n" +
                "\n" +
                "shop : NAME ;\n" +
                "\n" +
                "NAME : [a-zA-Z_]+ ;\n" +
                "\n" +
                "\n" +
                "NEWLINE : '\\n';\n" +
                "\n" +
                "WS : [\\r ] -> skip;";

        inlineAndCompareWith(grammar, toInline, expected);

    }

    @Test
    public void bookGrammar__inlinePerson() throws IOException {

        String grammar = loadFileContent("/Book.g4");
        List<String> toInline = Collections.singletonList("person");

        String expected = "grammar Book;\n" +
                "\n" +
                "book : people NEWLINE shops;\n" +
                "\n" +
                "people : (firstName ',' lastName)+ ;\n" +
                "\n" +
                "shops : shop+ ;\n" +
                "\n" +
                "firstName : NAME ;\n" +
                "\n" +
                "lastName : NAME ;\n" +
                "\n" +
                "shop : NAME ;\n" +
                "\n" +
                "NAME : [a-zA-Z_]+ ;\n" +
                "\n" +
                "\n" +
                "NEWLINE : '\\n';\n" +
                "\n" +
                "WS : [\\r ] -> skip;";

        inlineAndCompareWith(grammar, toInline, expected);

    }

    @Test
    public void bookGrammar__inlinePersonAndPeople() throws IOException {

        String grammar = loadFileContent("/Book.g4");
        List<String> toInline = Arrays.asList("person", "people");

        String expected = "grammar Book;\n" +
                "\n" +
                "book : (firstName ',' lastName)+ NEWLINE shops;\n" +
                "\n" +
                "shops : shop+ ;\n" +
                "\n" +
                "firstName : NAME ;\n" +
                "\n" +
                "lastName : NAME ;\n" +
                "\n" +
                "shop : NAME ;\n" +
                "\n" +
                "NAME : [a-zA-Z_]+ ;\n" +
                "\n" +
                "\n" +
                "NEWLINE : '\\n';\n" +
                "\n" +
                "WS : [\\r ] -> skip;";

        inlineAndCompareWith(grammar, toInline, expected);

    }


    @Test
    public void bookGrammar__inlinePersonAndPeopleAndShop() throws IOException {

        String grammar = loadFileContent("/Book.g4");
        List<String> toInline = Arrays.asList("person", "people", "shop");

        String expected = "grammar Book;\n" +
                "\n" +
                "book : (firstName ',' lastName)+ NEWLINE shops;\n" +
                "\n" +
                "shops : (NAME)+ ;\n" +
                "\n" +
                "firstName : NAME ;\n" +
                "\n" +
                "lastName : NAME ;\n" +
                "\n" +
                "NAME : [a-zA-Z_]+ ;\n" +
                "\n" +
                "\n" +
                "NEWLINE : '\\n';\n" +
                "\n" +
                "WS : [\\r ] -> skip;";

        inlineAndCompareWith(grammar, toInline, expected);

    }

    @Test
    public void bookGrammar__inlinePersonAndPeopleAndShopAndShops() throws IOException {

        String grammar = loadFileContent("/Book.g4");
        List<String> toInline = Arrays.asList("person", "people", "shop", "shops");

        String expected = "grammar Book;\n" +
                "\n" +
                "book : (firstName ',' lastName)+ NEWLINE (NAME)+;\n" +
                "\n" +
                "firstName : NAME ;\n" +
                "\n" +
                "lastName : NAME ;\n" +
                "\n" +
                "NAME : [a-zA-Z_]+ ;\n" +
                "\n" +
                "\n" +
                "NEWLINE : '\\n';\n" +
                "\n" +
                "WS : [\\r ] -> skip;";

        inlineAndCompareWith(grammar, toInline, expected);

    }

    @Test
    public void bookGrammar__inlineFirstNameAndLastName() throws IOException {

        String grammar = loadFileContent("/Book.g4");
        List<String> toInline = Arrays.asList("firstName", "lastName");

        String expected = "grammar Book;\n" +
                "\n" +
                "book : people NEWLINE shops;\n" +
                "\n" +
                "people : person+ ;\n" +
                "\n" +
                "shops : shop+ ;\n" +
                "\n" +
                "person : NAME ',' NAME ;\n" +
                "\n" +
                "shop : NAME ;\n" +
                "\n" +
                "NAME : [a-zA-Z_]+ ;\n" +
                "\n" +
                "\n" +
                "NEWLINE : '\\n';\n" +
                "\n" +
                "WS : [\\r ] -> skip;";

        inlineAndCompareWith(grammar, toInline, expected);

    }


    @Test
    public void bookGrammar__inlinePeople() throws IOException {

        String grammar = loadFileContent("/Book.g4");
        List<String> toInline = Collections.singletonList("people");

        String expected = "grammar Book;\n" +
                "\n" +
                "book : person+ NEWLINE shops;\n" +
                "\n" +
                "shops : shop+ ;\n" +
                "\n" +
                "person : firstName ',' lastName ;\n" +
                "\n" +
                "firstName : NAME ;\n" +
                "\n" +
                "lastName : NAME ;\n" +
                "\n" +
                "shop : NAME ;\n" +
                "\n" +
                "NAME : [a-zA-Z_]+ ;\n" +
                "\n" +
                "\n" +
                "NEWLINE : '\\n';\n" +
                "\n" +
                "WS : [\\r ] -> skip;";

        inlineAndCompareWith(grammar, toInline, expected);

    }

    @Test
    public void bookGrammar__inlinePeopleAndShops() throws IOException {

        String grammar = loadFileContent("/Book.g4");
        List<String> toInline = Arrays.asList("people", "shops");

        String expected = "grammar Book;\n" +
                "\n" +
                "book : person+ NEWLINE shop+;\n" +
                "\n" +
                "person : firstName ',' lastName ;\n" +
                "\n" +
                "firstName : NAME ;\n" +
                "\n" +
                "lastName : NAME ;\n" +
                "\n" +
                "shop : NAME ;\n" +
                "\n" +
                "NAME : [a-zA-Z_]+ ;\n" +
                "\n" +
                "\n" +
                "NEWLINE : '\\n';\n" +
                "\n" +
                "WS : [\\r ] -> skip;";

        inlineAndCompareWith(grammar, toInline, expected);

    }


    @Test(expected = IllegalArgumentException.class)
    public void bookGrammar__tryingToInlineRuleThatDoesNotExist() throws IOException {

        String grammar = loadFileContent("/Book.g4");
        List<String> toInline = Collections.singletonList("foo");

        String expected = "grammar Book;\n" +
                "\n" +
                "book : people NEWLINE shops;\n" +
                "\n" +
                "people : person+ ;\n" +
                "\n" +
                "shops : shop+ ;\n" +
                "\n" +
                "person : firstName ',' lastName ;\n" +
                "\n" +
                "firstName : NAME ;\n" +
                "\n" +
                "lastName : NAME ;\n" +
                "\n" +
                "shop : NAME ;\n" +
                "\n" +
                "NAME : [a-zA-Z_]+ ;\n" +
                "\n" +
                "\n" +
                "NEWLINE : '\\n';\n" +
                "\n" +
                "WS : [\\r ] -> skip;";

        inlineAndCompareWith(grammar, toInline, expected);

    }

    /**
     * The difference compared to the previous one is that this time there are other rules which exists
     */
    @Test(expected = IllegalArgumentException.class)
    public void bookGrammar__tryingToInlineRuleThatDoesNotExist2() throws IOException {

        String grammar = loadFileContent("/Book.g4");
        List<String> toInline = Arrays.asList("person", "foo");

        String expected = "grammar Book;\n" +
                "\n" +
                "book : people NEWLINE shops;\n" +
                "\n" +
                "people : person+ ;\n" +
                "\n" +
                "shops : shop+ ;\n" +
                "\n" +
                "person : firstName ',' lastName ;\n" +
                "\n" +
                "firstName : NAME ;\n" +
                "\n" +
                "lastName : NAME ;\n" +
                "\n" +
                "shop : NAME ;\n" +
                "\n" +
                "NAME : [a-zA-Z_]+ ;\n" +
                "\n" +
                "\n" +
                "NEWLINE : '\\n';\n" +
                "\n" +
                "WS : [\\r ] -> skip;";

        inlineAndCompareWith(grammar, toInline, expected);

    }

    @Test
    public void bookGrammar__inlineShops() throws IOException {

        String grammar = loadFileContent("/Book.g4");
        List<String> toInline = Collections.singletonList("shops");

        String expected = "grammar Book;\n" +
                "\n" +
                "book : people NEWLINE shop+ ;\n" +
                "\n" +
                "people : person+ ;\n" +
                "\n" +
                "person : firstName ',' lastName ;\n" +
                "\n" +
                "firstName : NAME ;\n" +
                "\n" +
                "lastName : NAME ;\n" +
                "\n" +
                "shop : NAME ;\n" +
                "\n" +
                "NAME : [a-zA-Z_]+ ;\n" +
                "\n" +
                "\n" +
                "NEWLINE : '\\n';\n" +
                "\n" +
                "WS : [\\r ] -> skip;";

        inlineAndCompareWith(grammar, toInline, expected);

    }


    @Test
    public void bookGrammar__inlineBookName() {
        String grammar = TestGrammars.bookGrammar;
        List<String> toInline = Collections.singletonList("bookName");


        String expected = "grammar Book;\n" +
                "\n" +
                "book\n" +
                "    : (shortcut | nickname) ',' STR+ ',' author EOF\n" +
                "    | (shortcut | nickname) ',' STR+ ',' author ',' YEAR EOF\n" +
                "    ;\n" +
                "\n" +
                "shortcut : 'shortcut' STR;\n" +
                "nickname : 'nickname' STR;\n" +
                "\n" +
                "author : STR STR;\n" +
                "\n" +
                "STR : [a-zA-Z]+ ;\n" +
                "\n" +
                "YEAR: [1-9]+;\n" +
                "\n" +
                "\n" +
                "WS : [ \\t\\n] -> skip;";

        inlineAndCompareWith(grammar, toInline, expected);

    }

    @Test
    public void cimpleGrammar__inlineBlockRule() {
        String grammar = TestGrammars.loadCimple();
        List<String> toInline = Collections.singletonList("block");

        String expected = "grammar Cimple;\n" +
                "\n" +
                "program\n" +
                "    : statement+ EOF\n" +
                "    ;\n" +
                "\n" +
                "statement\n" +
                "    : variableAssignment SEMICOLON\n" +
                "    | inputStatement SEMICOLON\n" +
                "    | printStatement SEMICOLON\n" +
                "    | ifStatement\n" +
                "    | forLoop\n" +
                "    | functionCall SEMICOLON\n" +
                "    | functionDefinition\n" +
                "    | returnStatement SEMICOLON\n" +
                "    ;\n" +
                "\n" +
                "\n" +
                "expression\n" +
                "    : functionCall #functionCallExpression\n" +
                "    | MINUS? LPAREN expression RPAREN #bracketExpr\n" +
                "    | expression MULT expression #binExpr\n" +
                "    | expression DIV expression #binExpr\n" +
                "    | expression MOD expression #binExpr\n" +
                "    | expression PLUS expression #binExpr\n" +
                "    | expression MINUS expression #binExpr\n" +
                "    | expression EQUALS expression #binExpr\n" +
                "    | expression NOT_EQUALS expression #binExpr\n" +
                "    | expression LT expression #binExpr\n" +
                "    | expression LE expression #binExpr\n" +
                "    | expression GT expression #binExpr\n" +
                "    | expression GE expression #binExpr\n" +
                "    | NOT expression #notExpr\n" +
                "    | expression AND expression #binExpr\n" +
                "    | expression OR expression #binExpr\n" +
                "    | MINUS? INT #intConstant\n" +
                "    | MINUS? ID #varExpr\n" +
                "    ;\n" +
                "\n" +
                "functionCall\n" +
                "    : ID LPAREN arguments? RPAREN\n" +
                "    ;\n" +
                "\n" +
                "arguments\n" +
                "    : expression (COMMA expression)*\n" +
                "    ;\n" +
                "\n" +
                "forLoop\n" +
                "    : FOR LPAREN setup=variableAssignment SEMICOLON expression SEMICOLON increment=variableAssignment RPAREN LBRAC statement* RBRAC\n" +
                "    ;\n" +
                "\n" +
                "variableAssignment\n" +
                "    : ID ASSIGN expression\n" +
                "    ;\n" +
                "\n" +
                "printStatement\n" +
                "    : PRINT expression\n" +
                "    ;\n" +
                "\n" +
                "inputStatement\n" +
                "    : INPUT ID\n" +
                "    ;\n" +
                "\n" +
                "ifStatement\n" +
                "    : IF LPAREN expression RPAREN LBRAC statement* RBRAC #if\n" +
                "    |  IF LPAREN expression RPAREN LBRAC statement* RBRAC ELSE LBRAC statement* RBRAC #ifElse\n" +
                "    ;\n" +
                "\n" +
                "functionDefinition\n" +
                "    : FN ID LPAREN parameters? RPAREN LBRAC statement* RBRAC\n" +
                "    ;\n" +
                "\n" +
                "parameters\n" +
                "    : ID (COMMA ID)*\n" +
                "    ;\n" +
                "\n" +
                "\n" +
                "returnStatement\n" +
                "    : RETURN expression\n" +
                "    ;\n" +
                "\n" +
                "\n" +
                "EQUALS : '==';\n" +
                "NOT_EQUALS : '!=';\n" +
                "LT : '<' ;\n" +
                "LE : '<=' ;\n" +
                "GT : '>';\n" +
                "GE : '>=';\n" +
                "\n" +
                "MULT : '*';\n" +
                "DIV : '/' ;\n" +
                "PLUS : '+';\n" +
                "MINUS: '-';\n" +
                "MOD : '%' ;\n" +
                "\n" +
                "AND : 'and';\n" +
                "OR : 'or';\n" +
                "NOT : 'not';\n" +
                "\n" +
                "ASSIGN : '=';\n" +
                "SEMICOLON : ';';\n" +
                "PRINT : 'print';\n" +
                "INPUT : 'input';\n" +
                "\n" +
                "FN : 'fn';\n" +
                "\n" +
                "FOR : 'for';\n" +
                "IF : 'if';\n" +
                "ELSE : 'else';\n" +
                "\n" +
                "RETURN : 'return';\n" +
                "\n" +
                "COMMA : ',';\n" +
                "\n" +
                "LPAREN : '(';\n" +
                "RPAREN : ')';\n" +
                "LBRAC : '{';\n" +
                "RBRAC : '}';\n" +
                "\n" +
                "INT : [0-9]+;\n" +
                "ID : [a-zA-Z_][a-zA-Z0-9_]*;\n" +
                "\n" +
                "WS: [ \\t\\n] -> skip;\n";

        inlineAndCompareWith(grammar, toInline, expected);

    }

    private void inlineAndCompareWith(String grammar, List<String> toInline, String expected) {
        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(grammar);
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();

        InlineRulesAlgorithm inlineRulesAlgorithm = new InlineRulesAlgorithm(toInline);

        inlineRulesAlgorithm.transform(grammarInfo);

        String result = IngridModelToAntlrSerializer.serializeGrammar(grammarInfo);

        System.out.println(result);

        assertGrammarEquals(expected, result);
    }


    @Test
    public void inlineBlockRuleInJava() {
        List<String> grammarFiles = TestGrammars.loadJava();
        List<String> rulesToInline = Collections.singletonList("block");
        GrammarParser grammarParser = new GrammarParser("compilationUnit");
        for (String grammarFile : grammarFiles) {
            grammarParser.parseString(grammarFile);
        }
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();
        InlineRulesAlgorithm inlineRulesAlgorithm = new InlineRulesAlgorithm(rulesToInline);
        GrammarInfo withInlinedRules = inlineRulesAlgorithm.transform(grammarInfo);

        assertNull(withInlinedRules.rules.get("block"));
        ParserRule parserRule = (ParserRule) withInlinedRules.rules.get("statement");
        assertNotNull(parserRule);
        Alternative blockLabelAlternative = parserRule.alternatives.get(0);
        assertEquals(3, blockLabelAlternative.elements.size());
        Alternative tryAlternative = parserRule.alternatives.get(6);
        assertEquals(5, tryAlternative.elements.size());
    }
}
