package premun.mps.ingrid.model;

import org.jetbrains.mps.openapi.model.SNode;
import premun.mps.ingrid.model.format.FormatInfo;

import java.util.Objects;

/**
 * Class wrapper used inside rule alternatives. Represents a single alternative element.
 */
public class RuleReference {
    /**
     * Referenced rule
     */
    public Rule rule;

    /**
     * Cardinality of the element
     */
    public Quantity quantity = Quantity.EXACTLY_ONE;

    /**
     * All information about formatting of this rule, it is inserted during the execution of the FormatExtraction algorithm.
     */
    public FormatInfo formatInfo;

    /**
     * Holds the MPS reference - either a PropertyDeclaration or a LinkDeclaration node.
     * This is not the target (referenced) concept, but the reference itself!
     */
    public SNode nodeReference = null;

    public RuleReference(Rule rule) {
        this.rule = rule;
    }

    public RuleReference(Rule rule, Quantity quantity) {
        this.rule = rule;
        this.quantity = quantity;
    }

    /**
     * Copy constructor, makes a shallow copy
     *
     * @param oldReference blueprint for the copy
     */
    public RuleReference(RuleReference oldReference) {
        this.rule = oldReference.rule;
        this.quantity = oldReference.quantity;
        this.nodeReference = oldReference.nodeReference;
        this.formatInfo = oldReference.formatInfo;
    }

    @Override
    public String toString() {
        return this.rule.toString() + this.quantity.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuleReference that = (RuleReference) o;
        return Objects.equals(rule, that.rule) &&
                quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rule, quantity);
    }
}
