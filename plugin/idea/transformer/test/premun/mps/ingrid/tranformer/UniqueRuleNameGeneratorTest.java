package premun.mps.ingrid.tranformer;

import org.junit.Test;
import premun.mps.ingrid.transformer.UniqueRuleNameGenerator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Verifies that the unique name generator works as expected.
 *
 * @author dkozak
 * @see UniqueRuleNameGenerator
 */
public class UniqueRuleNameGeneratorTest {

    @Test
    public void noRules() {
        UniqueRuleNameGenerator uniqueRuleNameGenerator = new UniqueRuleNameGenerator(Collections.emptySet());
        assertEquals("GENERATED_1", uniqueRuleNameGenerator.newUniqueName());
        assertEquals("GENERATED_2", uniqueRuleNameGenerator.newUniqueName());
        assertEquals("GENERATED_3", uniqueRuleNameGenerator.newUniqueName());
    }

    @Test
    public void rulesButNoConflicts() {
        UniqueRuleNameGenerator uniqueRuleNameGenerator = new UniqueRuleNameGenerator(new HashSet<>(Arrays.asList("foo", "bar", "baz")));
        assertEquals("GENERATED_1", uniqueRuleNameGenerator.newUniqueName());
        assertEquals("GENERATED_2", uniqueRuleNameGenerator.newUniqueName());
        assertEquals("GENERATED_3", uniqueRuleNameGenerator.newUniqueName());
    }

    @Test
    public void rulesWithSimpleConflict() {
        UniqueRuleNameGenerator uniqueRuleNameGenerator = new UniqueRuleNameGenerator(new HashSet<>(Arrays.asList("foo", "bar", "baz", "GENERATED_")));
        assertEquals("GENERATED__1", uniqueRuleNameGenerator.newUniqueName());
        assertEquals("GENERATED__2", uniqueRuleNameGenerator.newUniqueName());
        assertEquals("GENERATED__3", uniqueRuleNameGenerator.newUniqueName());
    }

    @Test
    public void rulesWithMoreIntenseConflict() {
        UniqueRuleNameGenerator uniqueRuleNameGenerator = new UniqueRuleNameGenerator(new HashSet<>(Arrays.asList("foo", "bar", "baz", "GENERATED_", "GENERATED__", "GENERATED___", "GENERATED_____")));
        assertEquals("GENERATED______1", uniqueRuleNameGenerator.newUniqueName());
        assertEquals("GENERATED______2", uniqueRuleNameGenerator.newUniqueName());
        assertEquals("GENERATED______3", uniqueRuleNameGenerator.newUniqueName());
    }

    @Test
    public void justOneRuleButBigConflic() {
        UniqueRuleNameGenerator uniqueRuleNameGenerator = new UniqueRuleNameGenerator(new HashSet<>(Collections.singletonList("GENERATED_____")));
        assertEquals("GENERATED______1", uniqueRuleNameGenerator.newUniqueName());
        assertEquals("GENERATED______2", uniqueRuleNameGenerator.newUniqueName());
        assertEquals("GENERATED______3", uniqueRuleNameGenerator.newUniqueName());
    }

    @Test
    public void bruteForceTest__namesAsExpected() {
        UniqueRuleNameGenerator uniqueRuleNameGenerator = new UniqueRuleNameGenerator(new HashSet<>(Arrays.asList("one", "two", "three", "GENERATED_____")));
        for (int i = 1; i <= 100_000; i++) {
            String expected = "GENERATED______" + i;
            String generated = uniqueRuleNameGenerator.newUniqueName();
            assertEquals(expected, generated);
        }
    }

    @Test
    public void bruteForceTest__namesAreUnique() {
        Set<String> ruleNames = new HashSet<>(Arrays.asList("one", "two", "three", "GENERATED_____"));
        UniqueRuleNameGenerator uniqueRuleNameGenerator = new UniqueRuleNameGenerator(ruleNames);
        for (int i = 1; i <= 100_000; i++) {
            String generated = uniqueRuleNameGenerator.newUniqueName();
            if (ruleNames.contains(generated)) {
                throw new AssertionError("Name " + generated + " is not unique in " + ruleNames);
            }
            ruleNames.add(generated);
        }
    }

    @Test
    public void overflowTest__oneLastSuccessfulGeneration() throws NoSuchFieldException, IllegalAccessException {
        UniqueRuleNameGenerator uniqueRuleNameGenerator = new UniqueRuleNameGenerator(Collections.emptySet());
        Field count = uniqueRuleNameGenerator.getClass()
                                             .getDeclaredField("count");
        count.setAccessible(true);
        count.set(uniqueRuleNameGenerator, Long.MAX_VALUE - 1);

        String expected = "GENERATED_" + Long.MAX_VALUE;
        assertEquals(expected, uniqueRuleNameGenerator.newUniqueName());
    }

    @Test(expected = IllegalStateException.class)
    public void overflowTest__shouldFail() throws NoSuchFieldException, IllegalAccessException {
        UniqueRuleNameGenerator uniqueRuleNameGenerator = new UniqueRuleNameGenerator(Collections.emptySet());
        Field count = uniqueRuleNameGenerator.getClass()
                                             .getDeclaredField("count");
        count.setAccessible(true);
        count.set(uniqueRuleNameGenerator, Long.MAX_VALUE - 1);

        uniqueRuleNameGenerator.newUniqueName();
        uniqueRuleNameGenerator.newUniqueName();
    }
}
