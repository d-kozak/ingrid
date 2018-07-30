package premun.mps.ingrid.formatter;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.tool.Grammar;
import premun.mps.ingrid.formatter.model.FormatInfoMapKey;
import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.model.GrammarInfo;

import java.util.List;
import java.util.Map;

public class FormatExtractor {

    public static Map<FormatInfoMapKey, List<RuleFormatInfo>> extract(GrammarInfo grammarInfo, String inputGrammar, String input) throws RecognitionException {
        Grammar grammar = new Grammar(inputGrammar);
        ParseTree ast = OnTheFlyParser.parse(grammar, input, grammar.rules.getElement(0).name);
        ParseTreeWalker walker = new ParseTreeWalker();
        FormatExtractingParseTreeListener listener = new FormatExtractingParseTreeListener(grammar, grammarInfo);
        walker.walk(listener, ast);
        return listener.getFormatInfo();
    }
}
