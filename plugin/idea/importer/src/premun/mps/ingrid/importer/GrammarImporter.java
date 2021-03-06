package premun.mps.ingrid.importer;

import jetbrains.mps.lang.smodel.generator.smodelAdapter.SModelOperations;
import jetbrains.mps.lang.smodel.generator.smodelAdapter.SNodeOperations;
import org.jetbrains.mps.openapi.model.SModel;
import premun.mps.ingrid.formatter.boundary.FormatExtractor;
import premun.mps.ingrid.importer.exceptions.IngridException;
import premun.mps.ingrid.importer.steps.*;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.model.RuleReference;
import premun.mps.ingrid.model.format.FormatInfo;
import premun.mps.ingrid.parser.GrammarParser;
import premun.mps.ingrid.transformer.DetectListWithSeparatorsAlgorithm;
import premun.mps.ingrid.transformer.FlatReferencesWithQuantifiesAlgorithm;
import premun.mps.ingrid.transformer.InlineRulesAlgorithm;
import premun.mps.ingrid.transformer.RemoveUnusedRulesAlgorithm;

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
    public static GrammarInfo fullIngridPipeline(IngridConfiguration ingridConfiguration) {
        GrammarParser parser = new GrammarParser(ingridConfiguration.getRootRule());
        for (String grammarFile : ingridConfiguration.getGrammarFiles()) {
            parser.parseString(grammarFile);
        }
        GrammarInfo grammarInfo = parser.resolveGrammar();

        grammarInfo = extractFormatting(ingridConfiguration, grammarInfo);


        InlineRulesAlgorithm inlineRulesAlgorithm = new InlineRulesAlgorithm(ingridConfiguration.getRulesToInline());
        grammarInfo = inlineRulesAlgorithm.transform(grammarInfo);


        if (ingridConfiguration.isSimplifyListsWithSeparators()) {
            DetectListWithSeparatorsAlgorithm detectListWithSeparatorsAlgorithm = new DetectListWithSeparatorsAlgorithm();
            grammarInfo = detectListWithSeparatorsAlgorithm.transform(grammarInfo);
        }

        FlatReferencesWithQuantifiesAlgorithm flatReferencesWithQuantifiesAlgorithm = new FlatReferencesWithQuantifiesAlgorithm();
        grammarInfo = flatReferencesWithQuantifiesAlgorithm.transform(grammarInfo);


        RemoveUnusedRulesAlgorithm removeUnusedRulesAlgorithm = new RemoveUnusedRulesAlgorithm();
        grammarInfo = removeUnusedRulesAlgorithm.transform(grammarInfo);

        return grammarInfo;
    }

    /**
     * Methods that interacts with formatter module
     *
     * @param ingridConfiguration configuration from InputForm
     * @param grammarInfo         grammar to be processed
     * @return updated version of the grammar containing formatting info
     */
    private static GrammarInfo extractFormatting(IngridConfiguration ingridConfiguration, GrammarInfo grammarInfo) {
        if (!ingridConfiguration.getSourceFiles()
                                .isEmpty()) {
            if (ingridConfiguration.getGrammarFiles()
                                   .size() == 1) {
                String grammarFile = ingridConfiguration.getGrammarFiles()
                                                        .get(0);
                grammarInfo = FormatExtractor.fullyProcessMultipleFiles(grammarInfo, grammarFile, ingridConfiguration.getSourceFiles());
            } else if (ingridConfiguration.getGrammarFiles()
                                          .size() == 2) {
                String lexerGrammar = ingridConfiguration.getGrammarFiles()
                                                         .get(0);
                String parserGrammar = ingridConfiguration.getGrammarFiles()
                                                          .get(1);
                grammarInfo = FormatExtractor.fullyProcessMultipleFiles(grammarInfo, lexerGrammar, parserGrammar, ingridConfiguration.getSourceFiles());

            } else {
                throw new IllegalArgumentException("Only single combined grammar file or separate lexer and parser grammar file expected(in this order)");
            }
        } else {
            // no source files, just insert unknown
            for (RuleReference ruleReference : grammarInfo.getRuleReferences()) {
                ruleReference.formatInfo = FormatInfo.UNKNOWN;
            }
        }
        return grammarInfo;
    }

    /**
     * Main method of the import process.
     *
     * @param ingridConfiguration configuration from the ImportForm
     */
    public void importGrammars(IngridConfiguration ingridConfiguration) {
        try {
            initializeLanguage();
            if (ingridConfiguration.getGrammarFiles()
                                   .size() > 2) {
                throw new IllegalArgumentException("At most two grammar files supported. First one should be the lexer file, second should be the parser file");
            }

            this.grammar = fullIngridPipeline(ingridConfiguration);
            this.importInfo = new ImportInfo(this.grammar.rootRule.name);


            ImportStep[] steps = new ImportStep[]{
                    new RegexTransformer(),
                    new ConceptImporter(),
                    new ConceptLinker(),
                    new AliasFinder(),
                    new EditorBuilder(),
                    new TextGenBuilder()
            };
            this.executeSteps(steps);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IngridException("Failed with exception " + ex.getClass()
                                                                   .getName() + ", reason: " + ex.getMessage());
        }
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
