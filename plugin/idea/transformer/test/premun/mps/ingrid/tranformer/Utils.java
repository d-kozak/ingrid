package premun.mps.ingrid.tranformer;

import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.model.ParserRule;
import premun.mps.ingrid.model.RuleReference;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertNotNull;

/**
 * Helper methods used in tests
 *
 * @author dkozak
 */
class Utils {

    /**
     * Loads the content of given file
     *
     * @param filename name of file
     * @return content of the file
     * @throws IOException if file could not be opened or read successfully
     */
    static String loadFileContent(String filename) throws IOException {
        String path = InlineRulesAlgorithmTest.class.getResource(filename)
                                                    .getPath();
        return new String(Files.readAllBytes(Paths.get(path)));
    }


    /**
     * verififes that all formatInfo references are set in RuleReference in alternatives of ParserRules
     *
     * @param grammarInfo
     */
    public static void verifyAllFormatInfoNotNull(GrammarInfo grammarInfo) {
        for (ParserRule parserRule : grammarInfo.getParserRules()) {
            for (Alternative alternative : parserRule.alternatives) {
                for (int i = 0; i < alternative.elements.size(); i++) {
                    RuleReference ruleReference = alternative.elements.get(i);
                    assertNotNull("Rule reference on index " + i + " in rule " + parserRule.name + ":" + parserRule.alternatives.indexOf(alternative) + " has no format info", ruleReference.formatInfo);
                }
            }
        }
    }
}
