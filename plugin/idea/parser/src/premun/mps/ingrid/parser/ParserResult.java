package premun.mps.ingrid.parser;

import premun.mps.ingrid.model.Rule;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ParserResult {
    public String grammarName;
    public Map<String, Rule> rules;
    public String rootRule;

    public Set<String> fragmentLexerRules = new HashSet<>();

    public ParserResult() {
        this.rules = new LinkedHashMap<>();
    }


    public ParserResult(String grammarName, Map<String, Rule> rules, String rootRule, Set<String> fragmentLexerRules) {
        this.grammarName = grammarName;
        this.rules = rules;
        this.rootRule = rootRule;
        this.fragmentLexerRules = fragmentLexerRules;
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
