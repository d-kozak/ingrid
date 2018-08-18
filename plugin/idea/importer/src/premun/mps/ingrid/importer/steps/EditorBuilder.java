package premun.mps.ingrid.importer.steps;

import org.jetbrains.mps.openapi.model.SNode;
import premun.mps.ingrid.library.EditorHelper;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.ParserRule;

/**
 * Import step that creates projection editors for all concepts.
 */
public class EditorBuilder extends ImportStep {
    private EditorHelper editorHelper;

    public EditorBuilder() {
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
            SNode editor = this.editorHelper.createEditor(rule, alternative);
            this.editorModel.addRootNode(editor);
        }
    }
}
