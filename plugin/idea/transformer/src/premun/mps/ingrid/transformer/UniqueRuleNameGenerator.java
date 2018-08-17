package premun.mps.ingrid.transformer;

import java.util.Set;

/**
 * Class that generates unique names for newly created rules.
 * It uses prefix specified in RULE_PREFIX. And then it keeps generating '_'
 * as suffix until there is no rule whose name would contain the full prefix
 * (that is RULE_PREFIX with some '_' afterwards). When we know that our current prefix
 * is not contained in any rule, we can be sure that anything longer will also not
 * be in there. Therefore we know that the rules will be unique as long as the suffixes we
 * generate are unique, which will be true until the count variable overflows, that is
 * after 2^64 calls of the newUniqueName method. And there should never be a need
 * to generate that many new ruleso.
 *
 * @author dkozak
 */
public class UniqueRuleNameGenerator {

    /**
     * Unique prefix that will be part of any rule
     */
    public static final String RULE_PREFIX = "GENERATED";

    /**
     * Suffix consisting of n chars '_', where n is the smallest
     * possible number such as the generated string is not a substring of any
     * currently known rule.
     */
    private final String suffix;

    /**
     * Each generated name gets prepended with a number representing it's count.
     * Once the variable overflows, there might be duplicities, therefore
     * IllegalStateException is thrown.
     */
    private long count = 0;

    /**
     * Test whether count overflown already
     */
    private boolean isExhausted = false;

    /**
     * @param existingRuleNames all currently known rule names, needed to generate a unique prefix
     */
    public UniqueRuleNameGenerator(Set<String> existingRuleNames) {
        this.suffix = generateUniqueSuffix(existingRuleNames);
    }

    /**
     * Generates a unique suffix. Keeps adding '_' to the end of RULE_PREFIX
     * until the resulting String is a not a substring of any known rule name.
     *
     * @param existingRuleNames currently
     * @return unique suffix consisting purely of '_' to be added after RULE_PREFIX to
     * guarentee uniqueness
     */
    private String generateUniqueSuffix(Set<String> existingRuleNames) {
        StringBuilder suffix = new StringBuilder();
        boolean isUnique = false;
        while (!isUnique) {
            suffix.append("_");
            String fullPrefix = RULE_PREFIX + suffix.toString();
            isUnique = existingRuleNames.stream()
                                        .noneMatch(name -> name.contains(fullPrefix));
        }
        return suffix.toString();
    }


    /**
     * Generates a unique name for a rule.
     * The name consists of RULE_PREFIX, suffix and a number
     *
     * @return unique name for new rule
     */
    public String newUniqueName() {
        if (isExhausted)
            throw new IllegalStateException("Count overflow");
        long num = ++count;
        if (num == Long.MAX_VALUE)
            isExhausted = true;
        return RULE_PREFIX + suffix + num;
    }
}
