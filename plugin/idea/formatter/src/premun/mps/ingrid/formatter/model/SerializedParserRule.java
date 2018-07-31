package premun.mps.ingrid.formatter.model;

import premun.mps.ingrid.model.Rule;
import premun.mps.ingrid.model.RuleReference;

import java.util.List;

public class SerializedParserRule extends Rule {
    public final List<RuleReference> elements;

    public SerializedParserRule(String name, List<RuleReference> elements) {
        super(name);
        this.elements = elements;
    }
}
