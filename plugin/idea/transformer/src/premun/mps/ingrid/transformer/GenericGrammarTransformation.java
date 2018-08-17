package premun.mps.ingrid.transformer;

import premun.mps.ingrid.model.GrammarInfo;

/**
 * Represents a revertable grammar transformation.
 * That is a transformation after which the grammar can be still serialized back into Antl4 form.
 * The transformation is done on the input as a side effect.
 *
 * @author dkozak
 */
public interface GenericGrammarTransformation {

    /**
     * Transforms the grammar, but still keeps it in Antlr4 compliant form.
     * The transformation is done on the input as a side effect.
     *
     * @param input grammar to be transformed
     */
    void transform(GrammarInfo input);
}
