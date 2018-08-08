package premun.mps.ingrid.importer.steps;

import org.jetbrains.mps.openapi.model.SNode;
import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.formatter.utils.Pair;
import premun.mps.ingrid.library.EditorHelper;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.ParserRule;

import java.util.Map;

import static premun.mps.ingrid.formatter.utils.Pair.pair;

/**
 * Import step that creates projection editors for all concepts.
 */
public class EditorBuilder extends ImportStep {
    private final Map<Pair<ParserRule, Alternative>, RuleFormatInfo> formatInfoMap;
    private EditorHelper editorHelper;

    public EditorBuilder(Map<Pair<ParserRule, Alternative>, RuleFormatInfo> formatInfoMap) {
        this.formatInfoMap = formatInfoMap;
    }

    @Override
    public void Execute() {
        this.editorHelper = new EditorHelper();

        this.grammar.rules
                .values()
                .stream()
                .filter(r -> r instanceof ParserRule)
                .map(r -> (ParserRule) r)
                .forEach(this::buildEditor);
    }

    /**
     * Builds an editor for given rule.
     *
     * @param rule Rule for which the editor is built.
     */
    private void buildEditor(ParserRule rule) {

        // Interface - we need to find implementors
        for (Alternative alternative : rule.alternatives) {
            SNode editor = this.editorHelper.createEditor(rule, alternative, formatInfoMap.get(pair(rule, alternative)));
            this.editorModel.addRootNode(editor);
        }
    }
}
