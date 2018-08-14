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
import premun.mps.ingrid.serialization.GrammarSerializer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

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


    static String readFile(String path) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded);
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * Main method of the import process.
     *
     * @param files List of ANTLR grammar files to be imported.
     */
    public void importGrammars(File[] files) {
        initializeLanguage();


        List<File> grammarFiles = Arrays.stream(files)
                                        .filter(file -> file.getName()
                                                            .endsWith(".g4"))
                                        .collect(toList());
        List<File> sourceFiles = Arrays.stream(files)
                                       .filter(file -> !file.getName()
                                                            .endsWith(".g4"))
                                       .collect(toList());

        GrammarParser parser = new GrammarParser();
        for (File grammarFile : grammarFiles) {
            parser.parseFile(grammarFile.getPath());
        }

        this.grammar = parser.resolveGrammar();
        this.importInfo = new ImportInfo(this.grammar.rootRule.name);

        String inputGrammar = GrammarSerializer.serializeGrammar(this.grammar);

        Map<Pair<ParserRule, Alternative>, RuleFormatInfo> formatInfoMap = sourceFiles.stream()
                                                                                      .map(File::getPath)
                                                                                      .map(GrammarImporter::readFile)
                                                                                      .map(fileContent -> FormatExtractor.merge(FormatExtractor.extract(this.grammar, inputGrammar, fileContent)))
                                                                                      .reduce(FormatExtractor::mergeFormatInfoMaps)
                                                                                      .orElseGet(HashMap::new);


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
