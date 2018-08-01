package premun.mps.ingrid.importer;

import jetbrains.mps.lang.smodel.generator.smodelAdapter.SModelOperations;
import jetbrains.mps.lang.smodel.generator.smodelAdapter.SNodeOperations;
import org.jetbrains.mps.openapi.model.SModel;
import premun.mps.ingrid.formatter.boundary.FormatExtractor;
import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.importer.steps.*;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.parser.GrammarParser;

import java.io.File;
import java.util.Map;

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
     * Main method of the import process.
     *
     * @param files List of ANTLR grammar files to be imported.
     */
    public void importGrammars(File[] files) {
        initializeLanguage();

        GrammarParser parser = new GrammarParser();

        for (File f : files) {
            parser.parseFile(f.getPath());
        }

        this.grammar = parser.resolveGrammar();
        this.importInfo = new ImportInfo(this.grammar.rootRule.name);

        String input = "";
        String inputGrammar = "";

        Map<String, Map<Integer, RuleFormatInfo>> formatInfoMap = FormatExtractor.simplify(FormatExtractor.extract(this.grammar, inputGrammar, input));

        ImportStep[] steps = new ImportStep[]{
                new RegexTransformer(),
                new ConceptImporter(),
                new ConceptLinker(),
                new AliasFinder(),
                new EditorBuilder(formatInfoMap),
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
