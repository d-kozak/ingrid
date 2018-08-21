package premun.mps.ingrid.formatter;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.tool.Grammar;
import org.antlr.v4.tool.LexerGrammar;
import premun.mps.ingrid.model.utils.Pair;

import static premun.mps.ingrid.model.utils.Pair.pair;

/**
 * Parses input based on specified grammar without using any generated code.
 * It uses new Antlr4 feature: interpreted lexing and parsing.
 *
 * @author dkozak
 */
public class InterpretingParser {

    /**
     * Parse the input based on the grammar from specified startRule.
     *
     * @param grammar  grammar according to which the input should be parsed
     * @param input    input to be parsed
     * @param rootRule the first rule to use when parsing
     * @return parse tree returned by the Antlr4 parserInterpreter
     */
    public static ParseTree parse(Grammar grammar, String input, String rootRule) {
        LexerInterpreter lexerInterpreter = grammar.createLexerInterpreter(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexerInterpreter);
        ParserInterpreter parserInterpreter = grammar.createParserInterpreter(tokens);
        return parserInterpreter.parse(grammar.rules.get(rootRule).index);
    }

    /**
     * Parse the input based on the grammar from specified startRule.
     *
     * @param grammar  grammar according to which the input should be parsed
     * @param input    input to be parsed
     * @param rootRule the first rule to use when parsing
     * @return stream of tokens from the lexer and parse tree returned by the Antlr4 parserInterpreter
     */
    public static Pair<CommonTokenStream, ParseTree> tokenizeAndParse(Grammar grammar, String input, String rootRule) {
        LexerInterpreter lexerInterpreter = grammar.createLexerInterpreter(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexerInterpreter);
        ParserInterpreter parserInterpreter = grammar.createParserInterpreter(tokens);
        ParseTree ast = parserInterpreter.parse(grammar.rules.get(rootRule).index);
        return pair(tokens, ast);
    }

    /**
     * Parse the input based on the grammar from specified startRule.
     *
     * @param inputGrammar grammar according to which the input should be parsed
     * @param inputText    input to be parsed
     * @param startRule    the first rule to use when parsing
     * @return parse tree returned by the Antlr4 parserInterpreter
     */
    public static ParseTree parse(String inputGrammar, String inputText, String startRule) throws RecognitionException {
        return parse(new Grammar(inputGrammar), inputText, startRule);
    }

    /**
     * @param lexerGrammar  lexer part of the grammar
     * @param parserGrammar parser part of the grammar
     * @param inputText     text to parse
     * @param startRule     the first rule to use when parsing
     * @return tokens, grammar and the parse tree
     */
    public static InterpretingParserResult tokenizeAndParse(String lexerGrammar, String parserGrammar, String inputText, String startRule) throws RecognitionException {
        LexerGrammar lg = new LexerGrammar(lexerGrammar);
        Grammar g = new Grammar(parserGrammar, lg);
        LexerInterpreter lexerInterpreter = lg.createLexerInterpreter(new ANTLRInputStream(inputText));
        CommonTokenStream tokens = new CommonTokenStream(lexerInterpreter);
        ParserInterpreter parserInterpreter = g.createParserInterpreter(tokens);
        ParserRuleContext ast = parserInterpreter.parse(g.getRule(startRule).index);
        return new InterpretingParserResult(tokens, g, ast);
    }


    public static class InterpretingParserResult {
        public final CommonTokenStream tokens;
        public final Grammar grammar;
        public final ParseTree parseTree;

        public InterpretingParserResult(CommonTokenStream tokens, Grammar grammar, ParseTree parseTree) {
            this.tokens = tokens;
            this.grammar = grammar;
            this.parseTree = parseTree;
        }
    }

}
