package premun.mps.ingrid.importer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static premun.mps.ingrid.importer.Utils.readFile;

/**
 * DTO to pass configuration from the ImportForm to the GrammarImporter through the MPS plugin
 *
 * @author dkozak
 */
public class IngridConfiguration {

    /**
     * All grammar files
     */
    private final List<String> grammarFiles;

    /**
     * All source files
     */
    private final List<String> sourceFiles;

    /**
     * Names of rules which should be inlined
     */
    private final List<String> rulesToInline;

    /**
     * Enables/disables the list with separators simplification
     */
    private final boolean simplifyListsWithSeparators;

    /**
     * Name of root rule, null or empty value means 'Use the first rule in grammar file as root'
     */
    private final String rootRule;

    public IngridConfiguration(List<String> grammarFiles, List<String> sourceFiles, List<String> rulesToInline, boolean simplifyListsWithSeparators, String rootRule) {
        this.grammarFiles = grammarFiles;
        this.sourceFiles = sourceFiles;
        this.rulesToInline = rulesToInline;
        this.simplifyListsWithSeparators = simplifyListsWithSeparators;
        this.rootRule = rootRule;
    }

    public IngridConfiguration(File[] files, String rootRule, List<String> rulesToInline, boolean simplifyListsWithSeparators) {
        this.grammarFiles = new ArrayList<>();
        this.sourceFiles = new ArrayList<>();
        this.rootRule = rootRule;
        this.rulesToInline = rulesToInline;
        this.simplifyListsWithSeparators = simplifyListsWithSeparators;

        readFiles(files);
    }

    /**
     * Reads the content of the input files and save them either into grammarfiles or source files, based on the extension of the file:
     * .g4 for grammar files, anything else are source files
     *
     * @param files files to process
     */
    private void readFiles(File[] files) {
        for (File file : files) {
            String content = readFile(file.getPath());
            if (file.getName()
                    .endsWith(".g4"))
                grammarFiles.add(content);
            else sourceFiles.add(content);
        }
    }

    public String getRootRule() {
        return rootRule;
    }

    public List<String> getGrammarFiles() {
        return grammarFiles;
    }

    public List<String> getSourceFiles() {
        return sourceFiles;
    }

    public List<String> getRulesToInline() {
        return rulesToInline;
    }

    public boolean isSimplifyListsWithSeparators() {
        return simplifyListsWithSeparators;
    }
}
