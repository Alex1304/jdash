package jdash.graphics.internal;

import java.util.function.BiFunction;
import java.util.function.Function;

record Tuple<T>(T a, T b) {

    static <T> Tuple<T> parse(String s, Function<String, T> parser) {
        String[] split = s.substring(1, s.length() - 1).split(",");
        return new Tuple<>(parser.apply(split[0]), parser.apply(split[1]));
    }

    <R> R as(BiFunction<T, T, R> f) {
        return f.apply(a, b);
    }
}
