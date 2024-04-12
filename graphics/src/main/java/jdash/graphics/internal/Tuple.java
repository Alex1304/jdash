package jdash.graphics.internal;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

final class Tuple<T> {

    private final T a;
    private final T b;

    Tuple(T a, T b) {
        this.a = a;
        this.b = b;
    }

    static <T> Tuple<T> parse(String s, Function<String, T> parser) {
        String[] split = s.substring(1, s.length() - 1).split(",");
        return new Tuple<>(parser.apply(split[0]), parser.apply(split[1]));
    }

    T getA() {
        return a;
    }

    T getB() {
        return b;
    }

    <R> R as(BiFunction<T, T, R> f) {
        return f.apply(a, b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple)) return false;
        Tuple<?> tuple = (Tuple<?>) o;
        return Objects.equals(a, tuple.a) && Objects.equals(b, tuple.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }
}
