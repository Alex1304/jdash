package jdash.events.producer;

import jdash.client.GDClient;
import jdash.common.entity.GDLevel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static reactor.function.TupleUtils.function;
import static reactor.function.TupleUtils.predicate;

abstract class AwardedEventProducer<T> implements GDEventProducer {

    private Set<T> previous0;
    private Set<T> previous1;

    private static <T> Set<T> intersection(Set<T> a, Set<T> b, Function<? super T, ? extends Wrapper<T>> wrap) {
        return a.stream()
                .filter(el -> b.stream().map(wrap).anyMatch(wrap.apply(el)::equals))
                .collect(Collectors.toUnmodifiableSet());
    }

    private static <T> Set<T> union(Set<T> a, Set<T> b, Function<? super T, ? extends Wrapper<T>> wrap) {
        return Stream.concat(a.stream().map(wrap), b.stream().map(wrap))
                .distinct()
                .map(Wrapper::unwrap)
                .collect(Collectors.toUnmodifiableSet());
    }

    private static <T> Set<T> subtract(Set<T> a, Set<T> b, Function<? super T, ? extends Wrapper<T>> wrap) {
        return a.stream()
                .filter(el -> b.stream().map(wrap).noneMatch(wrap.apply(el)::equals))
                .collect(Collectors.toUnmodifiableSet());
    }

    abstract Mono<Tuple2<Set<T>, Set<T>>> fetchFirstTwoPages(GDClient client);

    abstract Wrapper<T> wrap(T element);

    abstract Object createAddEvent(T element);

    abstract Object createRemoveEvent(T element);

    abstract Object createUpdateEvent(T oldData, T newData);

    abstract BiPredicate<T, T> fieldsComparator();

    @Override
    public Flux<Object> produce(GDClient client) {
        return fetchFirstTwoPages(client)
                .flatMapMany(function((page0, page1) -> {
                    final var previous0 = this.previous0;
                    final var previous1 = this.previous1;
                    this.previous0 = page0;
                    this.previous1 = page1;
                    if (previous0 == null || previous1 == null) {
                        return Flux.empty();
                    }
                    final var allOld = union(previous0, previous1, this::wrap);
                    final var allNew = union(page0, page1, this::wrap);
                    final var added = Flux.fromIterable(subtract(page0, allOld, this::wrap))
                            .map(this::createAddEvent);
                    final var removed = Flux.fromIterable(subtract(previous0, allNew, this::wrap))
                            .map(this::createRemoveEvent);
                    final var updated = Flux.fromIterable(intersection(previous0, allNew, this::wrap))
                            .map(item -> Tuples.of(
                                    previous0.stream()
                                            .map(this::wrap)
                                            .filter(wrap(item)::equals)
                                            .map(Wrapper::unwrap)
                                            .findAny()
                                            .orElseThrow(),
                                    allNew.stream()
                                            .map(this::wrap)
                                            .filter(wrap(item)::equals)
                                            .map(Wrapper::unwrap)
                                            .findAny()
                                            .orElseThrow()))
                            .filter(predicate(fieldsComparator()))
                            .map(function(this::createUpdateEvent));
                    return Flux.concat(added, removed, updated);
                }));
    }

    interface Wrapper<T> {
        T unwrap();
    }

    /**
     * Redefines equals and hashCode on level ID only
     */
    private record WrappedLevel(GDLevel level) implements Wrapper<GDLevel> {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WrappedLevel that = (WrappedLevel) o;
            return level.id() == that.level.id();
        }

        @Override
        public int hashCode() {
            return Long.hashCode(level.id());
        }

        @Override
        public GDLevel unwrap() {
            return level;
        }
    }
}
