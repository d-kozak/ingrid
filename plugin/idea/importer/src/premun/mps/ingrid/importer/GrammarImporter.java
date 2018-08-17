package premun.mps.ingrid.importer;

import jetbrains.mps.lang.smodel.generator.smodelAdapter.SModelOperations;
import jetbrains.mps.lang.smodel.generator.smodelAdapter.SNodeOperations;
import org.jetbrains.mps.openapi.model.SModel;
import premun.mps.ingrid.formatter.boundary.FormatExtractor;
import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.formatter.utils.Pair;
import premun.mps.ingrid.importer.steps.*;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.model.ParserRule;
import premun.mps.ingrid.parser.GrammarParser;
import premun.mps.ingrid.serialization.IngridModelToAntlrSerializer;
import premun.mps.ingrid.transformer.DetectListWithSeparatorsAlgorithm;
import premun.mps.ingrid.transformer.InlineRulesAlgorithm;

import java.util.Map;

import static premun.mps.ingrid.formatter.utils.Pair.pair;

public class GrammarImporter {
    private SModel editorModel;
    private SModel structureModel;
    private SModel textGenModel;

    private GrammarInfo grammar;
    private ImportInfo importInfo;

    public GrammarImporter(SModel structureModel, SModel editorModel, SModel textGenModel) {
        this.editorModel = editorModel;
        this.structureModel = structureModel;
        this.textGenModel = textGenModel;
    }

    /**
     * Prepares the target language for import (clears it away).
     */
    private void initializeLanguage() {
        // Delete all nodes
        SModelOperations
                .nodes(this.structureModel, null)
                .stream()
                .forEach(SNodeOperations::deleteNode);

        SModelOperations
                .nodes(this.editorModel, null)
                .stream()
                .forEach(SNodeOperations::deleteNode);

        SModelOperations
                .nodes(this.textGenModel, null)
                .stream()
                .forEach(SNodeOperations::deleteNode);
    }

    /**
     * Processes a list of grammar files and a list of source files to extract formatting.
     * This method is static for easier testing.
     *
     * @param ingridConfiguration configuration from the ImportForm
     * @return grammar info of the processed grammar AND format info map
     */
    static Pair<GrammarInfo, Map<Pair<ParserRule, Alternative>, RuleFormatInfo>> fullIngridPipeline(IngridConfiguration ingridConfiguration) {
        GrammarParser parser = new GrammarParser(ingridConfiguration.getRootRule());
        for (String grammarFile : ingridConfiguration.getGrammarFiles()) {
            parser.parseString(grammarFile);
        }
        GrammarInfo grammarInfo = parser.resolveGrammar();

        InlineRulesAlgorithm inlineRulesAlgorithm = new InlineRulesAlgorithm(ingridConfiguration.getRulesToInline());
        inlineRulesAlgorithm.transform(grammarInfo);

        String serialized = IngridModelToAntlrSerializer.serializeGrammar(grammarInfo);

        Map<Pair<ParserRule, Alternative>, RuleFormatInfo> pairRuleFormatInfoMap = FormatExtractor.fullyProcessMultipleFiles(grammarInfo, serialized, ingridConfiguration.getSourceFiles());

        if (ingridConfiguration.isSimplifyListsWithSeparators()) {
            DetectListWithSeparatorsAlgorithm detectListWithSeparatorsAlgorithm = new DetectListWithSeparatorsAlgorithm();
            detectListWithSeparatorsAlgorithm.transform(grammarInfo, pairRuleFormatInfoMap);
        }

        return pair(grammarInfo, pairRuleFormatInfoMap);
    }

    /**
     * Main method of the import process.
     * @param ingridConfiguration configuration from the ImportForm
     */
    public void importGrammars(IngridConfiguration ingridConfiguration) {
        initializeLanguage();

        Pair<GrammarInfo, Map<Pair<ParserRule, Alternative>, RuleFormatInfo>> grammarInfoMapPair = fullIngridPipeline(ingridConfiguration);

        this.grammar = grammarInfoMapPair.first;
        this.importInfo = new ImportInfo(this.grammar.rootRule.name);


        ImportStep[] steps = new ImportStep[]{
                new RegexTransformer(),
                new ConceptImporter(),
                new ConceptLinker(),
                new AliasFinder(),
                new EditorBuilder(grammarInfoMapPair.second),
                new TextGenBuilder()
        };
        this.executeSteps(steps);
    }

    public ImportInfo getImportInfo() {
        return this.importInfo;
    }

    private void executeSteps(ImportStep[] steps) {
        // Initialize steps with data
        for (ImportStep step : steps) {
            step.Initialize(this.grammar, this.structureModel, this.editorModel, this.textGenModel, this.importInfo);
        }

        // Execute steps
        for (ImportStep step : steps) {
            step.Execute();
        }
    }
}
