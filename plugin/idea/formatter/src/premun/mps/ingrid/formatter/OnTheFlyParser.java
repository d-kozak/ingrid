package premun.mps.ingrid.formatter;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.LexerInterpreter;
import org.antlr.v4.runtime.ParserInterpreter;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.tool.Grammar;

public class OnTheFlyParser {

    public static ParseTree parse(Grammar grammar, String input, String startRule) {
        LexerInterpreter lexerInterpreter = grammar.createLexerInterpreter(new ANTLRInputStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexerInterpreter);
        ParserInterpreter parserInterpreter = grammar.createParserInterpreter(tokens);
        return parserInterpreter.parse(grammar.rules.get(startRule).index);
    }
}
