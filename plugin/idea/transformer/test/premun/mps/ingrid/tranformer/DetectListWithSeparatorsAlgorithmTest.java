package premun.mps.ingrid.tranformer;

import org.junit.Test;
import premun.mps.ingrid.formatter.utils.TestGrammars;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.model.ParserRule;
import premun.mps.ingrid.model.RuleReference;
import premun.mps.ingrid.parser.GrammarParser;
import premun.mps.ingrid.transformer.DetectListWithSeparatorsAlgorithm;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static premun.mps.ingrid.tranformer.Utils.loadFileContent;

public class DetectListWithSeparatorsAlgorithmTest {

    @Test
    public void cimple__shouldFindArgsAndParameters__checkingOnlyGrammar() {
        String grammar = TestGrammars.loadCimple();

        GrammarInfo grammarInfo = processGrammar(grammar);
        assertRuleHasJustOneRuleReference(grammarInfo, "arguments", "expression");
        assertRuleHasJustOneRuleReference(grammarInfo, "parameters", "ID");
    }

    @Test
    public void args__shouldFindArgsAndIdList__checkingOnlyGrammar() throws IOException {
        String grammar = loadFileContent("/Args.g4");

        GrammarInfo grammarInfo = processGrammar(grammar);

        assertRuleHasJustOneRuleReference(grammarInfo, "args", "arg");
        assertRuleHasJustOneRuleReference(grammarInfo, "idList", "ID");
    }


    private void assertRuleHasJustOneRuleReference(GrammarInfo grammarInfo, String checkedRuleName, String referencedRuleName) {
        try {
            ParserRule parserRule = (ParserRule) grammarInfo.rules.get(checkedRuleName);
            assertEquals(1, parserRule.alternatives.size());
            Alternative alternative = parserRule.alternatives.get(0);
            List<RuleReference> elements = alternative.elements;
            assertEquals(1, elements.size());
            RuleReference ruleReference = elements.get(0);
            assertEquals(referencedRuleName, ruleReference.rule.name);
        } catch (AssertionError ex) {
            throw new AssertionError("When checking '" + checkedRuleName + "' and looking for reference to '" + referencedRuleName + "' in grammar " + grammarInfo, ex);
        }
    }

    private GrammarInfo processGrammar(String cimple) {
        GrammarParser grammarParser = new GrammarParser();
        grammarParser.parseString(cimple);
        GrammarInfo grammarInfo = grammarParser.resolveGrammar();

        DetectListWithSeparatorsAlgorithm detectListWithSeparatorsAlgorithm = new DetectListWithSeparatorsAlgorithm();
        detectListWithSeparatorsAlgorithm.transform(grammarInfo, new LinkedHashMap<>());
        return grammarInfo;
    }


}
