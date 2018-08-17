package premun.mps.ingrid.transformer;

import premun.mps.ingrid.formatter.model.RuleFormatInfo;
import premun.mps.ingrid.formatter.utils.Pair;
import premun.mps.ingrid.model.Alternative;
import premun.mps.ingrid.model.GrammarInfo;
import premun.mps.ingrid.model.ParserRule;

import java.util.Map;

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
     * @param grammarInfo   grammar to be transformed
     * @param formatInfoMap information about formatting, it might have to be tweaked as well
     */
    void transform(GrammarInfo grammarInfo, Map<Pair<ParserRule, Alternative>, RuleFormatInfo> formatInfoMap);
}
