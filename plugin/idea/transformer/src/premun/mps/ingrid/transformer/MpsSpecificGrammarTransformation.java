package premun.mps.ingrid.transformer;

import premun.mps.ingrid.model.GrammarInfo;

/**
 * Represents a transformation that produces a version of GrammarInfo that can no longer be fully
 * serialized into it's Antlr4 form without losing some information.
 * The transformation is done on the input as a side effect.
 *
 * @author dkozak
 */
public interface MpsSpecificGrammarTransformation {

    /**
     * Transforms the grammar and adds some MPS specific things that cannot be represented in the Antlr4 grammar.
     * Therefore the GrammarInfo can no longer be serialized.
     * The transformation is done on the input as a side effect.
     *
     * @param grammarInfo grammar to be transformed
     */
    GrammarInfo transform(GrammarInfo grammarInfo);
}
