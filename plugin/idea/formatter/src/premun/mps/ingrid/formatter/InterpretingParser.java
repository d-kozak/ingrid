package premun.mps.ingrid.formatter;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.LexerInterpreter;
import org.antlr.v4.runtime.ParserInterpreter;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.tool.Grammar;
import premun.mps.ingrid.formatter.utils.Pair;

/**
 * Parses input based on specified grammar without using any generated code,
 * it uses new antlr4 feature of interpreted lexing and parsing.
 *
 * @author dkozak
 */
public class InterpretingParser {

    public static ParseTree parse(Grammar grammar, String input, String startRule) {
        LexerInterpreter lexerInterpreter = grammar.createLexerInterpreter(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexerInterpreter);
        ParserInterpreter parserInterpreter = grammar.createParserInterpreter(tokens);
        return parserInterpreter.parse(grammar.rules.get(startRule).index);
    }

    public static Pair<CommonTokenStream, ParseTree> tokenizeAndParse(Grammar grammar, String input, String startRule) {
        LexerInterpreter lexerInterpreter = grammar.createLexerInterpreter(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexerInterpreter);
        ParserInterpreter parserInterpreter = grammar.createParserInterpreter(tokens);
        ParseTree ast = parserInterpreter.parse(grammar.rules.get(startRule).index);
        return Pair.of(tokens, ast);
    }

    public static ParseTree parse(String inputGrammar, String inputText, String startRule) throws RecognitionException {
        return parse(new Grammar(inputGrammar), inputText, startRule);
    }
}
