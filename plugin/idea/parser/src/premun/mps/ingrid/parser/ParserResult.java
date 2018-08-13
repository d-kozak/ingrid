package premun.mps.ingrid.parser;

import premun.mps.ingrid.model.Rule;

import java.util.HashMap;
import java.util.Map;

public class ParserResult {
    public String grammarName;
    public Map<String, Rule> rules;
    public String rootRule;

    public ParserResult() {
        this.rules = new HashMap<>();
    }

    public ParserResult(String grammarName, Map<String, Rule> rules, String rootRule) {
        this.grammarName = grammarName;
        this.rules = rules;
        this.rootRule = rootRule;
    }

    @Override
    public String toString() {
        return "ParserResult{" +
                "grammarName='" + grammarName + '\'' +
                ", rules=" + rules +
                ", rootRule='" + rootRule + '\'' +
                '}';
    }
}
