package premun.mps.ingrid.model;

import org.jetbrains.mps.openapi.model.SNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Alternative {
    public List<RuleReference> elements = new ArrayList<>();

    public String comment;

    public SNode node = null;

    public String alias = null;

    public Alternative() {
    }

    public Alternative(List<RuleReference> elements) {
        this.elements = elements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alternative that = (Alternative) o;
        return Objects.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }

    @Override
    public String toString() {
        return elements.stream()
                       .map(ruleReference -> {
                           if (ruleReference.rule instanceof LiteralRule)
                               return ((LiteralRule) ruleReference.rule).value + ruleReference.quantity;
                           else return ruleReference.rule.name + ruleReference.quantity;
                       })
                       .collect(Collectors.joining(" "));
    }
}
