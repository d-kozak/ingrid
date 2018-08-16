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
import premun.mps.ingrid.transformer.InlineRulesAlgorithm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
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


    static String readFile(String path) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded);
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    static Pair<GrammarInfo, Map<Pair<ParserRule, Alternative>, RuleFormatInfo>> fullIngridPipeline(List<String> grammarFiles, List<String> inputFiles, List<String> rulesToInline) {
        return fullIngridPipeline(grammarFiles, inputFiles, rulesToInline, null);
    }

    /**
     * Processes a list of grammar files and a list of source files to extract formatting.
     * This method is static for easier testing.
     *
     * @param grammarFiles  grammar files to load
     * @param inputFiles    source files to extract formatting from
     * @param rulesToInline rules from the grammar to be inlined
     * @return grammar info of the processed grammar and format info map
     */
    static Pair<GrammarInfo, Map<Pair<ParserRule, Alternative>, RuleFormatInfo>> fullIngridPipeline(List<String> grammarFiles, List<String> inputFiles, List<String> rulesToInline, String startRule) {
        GrammarParser parser = new GrammarParser(startRule);
        for (String grammarFile : grammarFiles) {
            parser.parseString(grammarFile);
        }
        GrammarInfo grammarInfo = parser.resolveGrammar();
        GrammarInfo withInlinedRules = InlineRulesAlgorithm.inlineRules(grammarInfo, rulesToInline);
        String serialized = GrammarSerializer.serializeGrammar(grammarInfo);

        Map<Pair<ParserRule, Alternative>, RuleFormatInfo> pairRuleFormatInfoMap = FormatExtractor.fullyProcessMultipleFiles(withInlinedRules, serialized, inputFiles);
        return pair(withInlinedRules, pairRuleFormatInfoMap);
    }

    /**
     * Main method of the import process.
     *
     * @param files List of ANTLR grammar files to be imported.
     */
    public void importGrammars(File[] files, String startRule) {
        initializeLanguage();


        List<String> grammarFiles = Arrays.stream(files)
                                          .filter(file -> file.getName()
                                                              .endsWith(".g4"))
                                          .map(File::getPath)
                                          .map(GrammarImporter::readFile)
                                          .collect(toList());
        List<String> sourceFiles = Arrays.stream(files)
                                         .filter(file -> !file.getName()
                                                              .endsWith(".g4"))
                                         .map(File::getPath)
                                         .map(GrammarImporter::readFile)
                                         .collect(toList());


        Pair<GrammarInfo, Map<Pair<ParserRule, Alternative>, RuleFormatInfo>> grammarInfoMapPair = fullIngridPipeline(grammarFiles, sourceFiles, Collections.emptyList(), startRule);


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
