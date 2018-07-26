package premun.mps.ingrid.formatter.model;

import java.util.List;
import java.util.Objects;

public final class FormatInfoMapKey {
    public final List<String> context;
    public final int alternative;
    public final int referenceIndex;

    public FormatInfoMapKey(List<String> context, int alternative, int referenceIndex) {
        this.context = context;
        this.alternative = alternative;
        this.referenceIndex = referenceIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormatInfoMapKey that = (FormatInfoMapKey) o;
        return alternative == that.alternative &&
                referenceIndex == that.referenceIndex &&
                Objects.equals(context, that.context);
    }

    @Override
    public int hashCode() {
        return Objects.hash(context, alternative, referenceIndex);
    }

    @Override
    public String toString() {
        return "FormatInfoMapKey{" +
                "context=" + context +
                ", alternative=" + alternative +
                ", referenceIndex=" + referenceIndex +
                '}';
    }
}
