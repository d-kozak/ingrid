package premun.mps.ingrid.formatter;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.LexerInterpreter;
import org.antlr.v4.runtime.ParserInterpreter;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.tool.Grammar;
import premun.mps.ingrid.formatter.utils.Pair;

import static premun.mps.ingrid.formatter.utils.Pair.pair;

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
     * @param grammar   grammar according to which the input should be parsed
     * @param input     input to be parsed
     * @param startRule the first rule to use when parsing
     * @return parse tree returned by the Antlr4 parserInterpreter
     */
    public static ParseTree parse(Grammar grammar, String input, String startRule) {
        LexerInterpreter lexerInterpreter = grammar.createLexerInterpreter(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexerInterpreter);
        ParserInterpreter parserInterpreter = grammar.createParserInterpreter(tokens);
        return parserInterpreter.parse(grammar.rules.get(startRule).index);
    }

    /**
     * Parse the input based on the grammar from specified startRule.
     *
     * @param grammar   grammar according to which the input should be parsed
     * @param input     input to be parsed
     * @param startRule the first rule to use when parsing
     * @return stream of tokens from the lexer and parse tree returned by the Antlr4 parserInterpreter
     */
    public static Pair<CommonTokenStream, ParseTree> tokenizeAndParse(Grammar grammar, String input, String startRule) {
        LexerInterpreter lexerInterpreter = grammar.createLexerInterpreter(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexerInterpreter);
        ParserInterpreter parserInterpreter = grammar.createParserInterpreter(tokens);
        ParseTree ast = parserInterpreter.parse(grammar.rules.get(startRule).index);
        return pair(tokens, ast);
    }

    /**
     * Parse the input based on the grammar from specified startRule.
     * @param inputGrammar grammar according to which the input should be parsed
     * @param inputText input to be parsed
     * @param startRule the first rule to use when parsing
     * @return parse tree returned by the Antlr4 parserInterpreter
     */
    public static ParseTree parse(String inputGrammar, String inputText, String startRule) throws RecognitionException {
        return parse(new Grammar(inputGrammar), inputText, startRule);
    }
}
