package premun.mps.ingrid.tranformer;

import org.junit.Test;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.model.Rule;
import premun.mps.ingrid.transformer.RemoveUnusedRulesAlgorithm;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;
import static premun.mps.ingrid.formatter.utils.TestGrammars.parseGrammar;
import static premun.mps.ingrid.tranformer.Utils.loadFileContent;

/**
 * @author dkozak
 */
public class RemoveUnusedRulesAlgorithmTest {

    private static void assertRulesRemoved(GrammarInfo grammarInfo, List<String> ruleNames) {
        for (String ruleName : ruleNames) {
            assertNotNull(ruleName + " not found in the grammar rules", grammarInfo.rules.get(ruleName));
        }

        RemoveUnusedRulesAlgorithm removeUnusedRulesAlgorithm = new RemoveUnusedRulesAlgorithm();
        GrammarInfo processedGrammarInfo = removeUnusedRulesAlgorithm.transform(grammarInfo);

        assertNotEquals(processedGrammarInfo, grammarInfo);
        assertEquals(grammarInfo.name, processedGrammarInfo.name);
        assertEquals(grammarInfo.rootRule, processedGrammarInfo.rootRule);

        for (String ruleName : ruleNames) {
            assertNull(ruleName + " should not longer be in the grammar, but still is. ", processedGrammarInfo.rules.get(ruleName));
        }
    }

    private static void assertUnchanged(GrammarInfo grammarInfo) {
        RemoveUnusedRulesAlgorithm removeUnusedRulesAlgorithm = new RemoveUnusedRulesAlgorithm();
        GrammarInfo processedGrammarInfo = removeUnusedRulesAlgorithm.transform(grammarInfo);

        String errorMsg = "When comparing original " + grammarInfo + " with new " + processedGrammarInfo;

        assertEquals(errorMsg, grammarInfo.rules.size(), processedGrammarInfo.rules.size());
        assertEquals(errorMsg, grammarInfo.name, processedGrammarInfo.name);
        assertEquals(errorMsg, grammarInfo.rootRule, processedGrammarInfo.rootRule);

        for (String ruleName : grammarInfo.rules.keySet()) {
            Rule originalRule = grammarInfo.rules.get(ruleName);
            Rule processedRule = processedGrammarInfo.rules.get(ruleName);
            assertEquals(originalRule, processedRule);
        }
    }

    @Test
    public void ArgsNoUnusedRules__grammarShouldBeUnchanged() throws IOException {
        String grammar = loadFileContent("/ArgsNoUnused.g4");
        GrammarInfo grammarInfo = parseGrammar(grammar);
        assertUnchanged(grammarInfo);
    }

    @Test
    public void Args_WSruleShouldBeRemoved() throws IOException {
        String grammar = loadFileContent("/Args.g4");
        grammar += "foo : arg arg arg ;";
        GrammarInfo grammarInfo = parseGrammar(grammar);

        assertRulesRemoved(grammarInfo, Arrays.asList("WS", "foo"));
    }

    @Test
    public void BooksNoUnusedRules__grammarShouldBeUnchanged() throws IOException {
        String grammar = loadFileContent("/BookNoUnused.g4");
        GrammarInfo grammarInfo = parseGrammar(grammar);
        grammarInfo.rules.remove("NEWLINE"); // literal rules will be shown only in the editor aspect, they can therefore be removed if they are in the map.
        assertUnchanged(grammarInfo);
    }

    @Test
    public void Book__WSruleShouldBeRemoved() throws IOException {
        String grammar = loadFileContent("/Book.g4");
        grammar += "foo : firstName ;";
        grammar += "bar : foo foo ;";
        grammar += "baz : foo bar ;";
        GrammarInfo grammarInfo = parseGrammar(grammar);
        assertRulesRemoved(grammarInfo, Arrays.asList("WS", "foo", "bar", "baz"));
    }


}
