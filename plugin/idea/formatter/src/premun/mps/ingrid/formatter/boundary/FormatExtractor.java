package premun.mps.ingrid.formatter.boundary;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.tool.Grammar;
import premun.mps.ingrid.formatter.InterpretingParser;
import premun.mps.ingrid.formatter.RuleEnterParseTreeListener;
import premun.mps.ingrid.formatter.model.CollectionFormatInfo;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.model.ParserRule;
import premun.mps.ingrid.model.RuleReference;
import premun.mps.ingrid.model.format.FormatInfo;
import premun.mps.ingrid.model.format.SimpleFormatInfo;
import premun.mps.ingrid.model.utils.Pair;

import java.util.List;
import java.util.function.Function;

/**
 * Interface of the module
 *
 * @author dkozak
 */
public class FormatExtractor {

    /**
     * Processes sourceFiles and extracts formatting from them. The formatting is saved directly into the rule references in the grammarInfo object.
     *
     * @param grammarInfo  grammar to be processed
     * @param inputGrammar antlr4 representation of the grammar, used to create the InterpretedParser
     * @param sourceFiles  list of source files to extract formatting from
     * @return updated version of grammar info containg all extracting formatting
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
            return addSpaceAfterLastRuleReferences(mergeFormatInformation(grammarInfo, MergeFormatInfoOperation::merge));
        } catch (RecognitionException ex) {
            throw new IllegalArgumentException(ex);
        }

    }

    /**
     * Processes sourceFiles and extracts formatting from them. The formatting is saved directly into the rule references in the grammarInfo object.
     *
     * @param grammarInfo   grammar to be processed
     * @param lexerGrammar  lexer part of the antlr4 grammar
     * @param parserGrammar parser part of the antlr4 grammar
     * @param sourceFiles   list of source files to extract formating from
     * @return updated version of grammar info containg all extracting formatting
     */
    public static GrammarInfo fullyProcessMultipleFiles(GrammarInfo grammarInfo, String lexerGrammar, String parserGrammar, List<String> sourceFiles) {
        try {
            addCollectionFormatInfoToAllRuleReferences(grammarInfo);
            for (String sourceFile : sourceFiles) {
                InterpretingParser.InterpretingParserResult result = InterpretingParser.tokenizeAndParse(lexerGrammar, parserGrammar, sourceFile, grammarInfo.rootRule.name);
                ParseTreeWalker walker = new ParseTreeWalker();
                RuleEnterParseTreeListener listener = new RuleEnterParseTreeListener(result.grammar, grammarInfo, result.tokens);
                walker.walk(listener, result.parseTree);
            }
            return addSpaceAfterLastRuleReferences(mergeFormatInformation(grammarInfo, MergeFormatInfoOperation::merge));
        } catch (RecognitionException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * In MPS setting punctuation-right on the last element in the editor causes problems with jumping cursor when typing.
     *
     * @return updated version of grammar info
     */
    private static GrammarInfo addSpaceAfterLastRuleReferences(GrammarInfo grammarInfo) {
        for (Pair<ParserRule, Alternative> parserRulesWithAlternative : grammarInfo.getParserRulesWithAlternatives()) {
            Alternative alternative = parserRulesWithAlternative.second;
            if (!alternative.elements.isEmpty()) {
                RuleReference lastRuleReference = alternative.elements.get(alternative.elements.size() - 1);
                FormatInfo formatInfo = lastRuleReference.formatInfo;
                lastRuleReference.formatInfo =
                        new SimpleFormatInfo(formatInfo.appendNewLine(), true, formatInfo.areChildrenOnNewLine(), formatInfo.areChildrenIndented(), formatInfo.getChildrenSeparator(), formatInfo.isUnknown());
            } else {
                String message = "Alternative has an empty list of rule references, rule " + parserRulesWithAlternative.first.name + (":" + parserRulesWithAlternative.first.alternatives.indexOf(parserRulesWithAlternative.second));
                System.err.println(message);

            }
        }

        return grammarInfo;
    }

    /**
     * Traverses all rule references in the grammar and merges their CollectionFormatInfo into a single FormatInfo to be used outside
     * of this module.
     *
     * @param grammarInfo grammar to be processed
     * @param operation   describes how to merge the CollectionFormatInfo into FormatInfo
     * @return Updated version of the grammar without any collectionFormatInfo objects.
     */
    private static GrammarInfo mergeFormatInformation(GrammarInfo grammarInfo, Function<CollectionFormatInfo, FormatInfo> operation) {
        for (RuleReference ruleReference : grammarInfo.getRuleReferences()) {
            CollectionFormatInfo collectionFormatInfo = (CollectionFormatInfo) ruleReference.formatInfo;
            ruleReference.formatInfo = operation.apply(collectionFormatInfo);
        }
        return grammarInfo;
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
