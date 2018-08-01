package premun.mps.ingrid.formatter.model;

import java.util.List;
import java.util.Objects;

/**
 * A key in the format info map, consists of the context ( list of rules)
 * that lead to a given rule and an index of alternative that was used
 *
 * @author dkozak
 */
public final class FormatInfoMapKey {
    /**
     * Sequence of rules that lead to this point
     */
    public final List<String> context;

    /**
     * Index of alternative that was used(into the list of alternatives of the last rule in context)
     */
    public final int alternative;

    public FormatInfoMapKey(List<String> context, int alternative) {
        this.context = context;
        this.alternative = alternative;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormatInfoMapKey that = (FormatInfoMapKey) o;
        return alternative == that.alternative &&
                Objects.equals(context, that.context);
    }

    @Override
    public int hashCode() {
        return Objects.hash(context, alternative);
    }

    @Override
    public String toString() {
        return "< " + context + " : " + alternative + " >";
    }
}
