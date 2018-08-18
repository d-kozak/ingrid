package premun.mps.ingrid.formatter.boundary;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.tool.Grammar;
import premun.mps.ingrid.formatter.InterpretingParser;
import premun.mps.ingrid.formatter.RuleEnterParseTreeListener;
import premun.mps.ingrid.formatter.model.CollectionFormatInfo;
import premun.mps.ingrid.formatter.utils.Pair;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.model.RuleReference;

import java.util.List;

/**
 * Interface of the module
 *
 * @author dkozak
 */
public class FormatExtractor {


    /**
     * Extracts formatting from multiple files and merges it into single formatInfoMap
     */
    public static GrammarInfo fullyProcessMultipleFiles(GrammarInfo grammarInfo, String inputGrammar, List<String> sourceFiles) {
        try {
            addCollectionFormatInfoToAllRuleReferences(grammarInfo);
            Grammar grammar = new Grammar(inputGrammar);
            for (String sourceFile : sourceFiles) {
                Pair<CommonTokenStream, ParseTree> pair = InterpretingParser.tokenizeAndParse(grammar, sourceFile, grammarInfo.rootRule.name);
                CommonTokenStream tokens = pair.first;
                ParseTree ast = pair.second;
                ParseTreeWalker walker = new ParseTreeWalker();
                RuleEnterParseTreeListener listener = new RuleEnterParseTreeListener(grammar, grammarInfo, tokens);
                walker.walk(listener, ast);
            }
            return grammarInfo;
        } catch (RecognitionException ex) {
            throw new IllegalArgumentException(ex);
        }

    }

    /**
     * Traverses the grammar and initializes all formatInfo references in rule references to CollectionFormatInfo
     *
     * @param grammarInfo grammar to be processed
     */
    private static void addCollectionFormatInfoToAllRuleReferences(GrammarInfo grammarInfo) {
        for (RuleReference ruleReference : grammarInfo.getRuleReferences()) {
            if (ruleReference.formatInfo != null) {
                throw new IllegalStateException("All formatInfo references in RuleReferences should be null, this one is not: " + ruleReference);
            }
            ruleReference.formatInfo = new CollectionFormatInfo();
        }
    }
}
