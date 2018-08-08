package premun.mps.ingrid.formatter.utils;

import java.util.Objects;


/**
 * A simple pair DTO class, used when you need to return
 * more than one thing from a function and you do not want
 * to create a special class for that.
 *
 * @author dkozak
 */
public final class Pair<T, U> {
    public final T first;
    public final U second;

    private Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public static <T, U> Pair<T, U> pair(T first, U second) {
        return new Pair<>(first, second);
    }

    public T fst() {
        return first;
    }

    public U snd() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) &&
                Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
