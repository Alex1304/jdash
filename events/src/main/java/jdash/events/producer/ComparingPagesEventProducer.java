package jdash.events.producer;

import jdash.client.GDClient;
import org.jspecify.annotations.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static reactor.function.TupleUtils.function;

class ComparingPagesEventProducer<T> implements GDEventProducer {

    private final PagesComparator<T> pagesComparator;
    private @Nullable Set<T> previous0;
    private @Nullable Set<T> previous1;

    ComparingPagesEventProducer(PagesComparator<T> pagesComparator) {
        this.pagesComparator = pagesComparator;
    }

    private Set<T> intersection(Set<T> a, Set<T> b) {
        return a.stream()
                .filter(aEl -> b.stream().anyMatch(bEl -> idEquals(aEl, bEl)))
                .collect(Collectors.toUnmodifiableSet());
    }

    private Set<T> union(Set<T> a, Set<T> b) {
        return Set.copyOf(Stream.concat(a.stream(), b.stream())
                .collect(Collectors.toUnmodifiableMap(pagesComparator::idGetter, Function.identity(), (o1, o2) -> o1))
                .values());
    }

    private Set<T> subtract(Set<T> a, Set<T> b) {
        return a.stream()
                .filter(aEl -> b.stream().noneMatch(bEl -> idEquals(aEl, bEl)))
                .collect(Collectors.toUnmodifiableSet());
    }

    private boolean idEquals(T e1, T e2) {
        return pagesComparator.idGetter(e1) == pagesComparator.idGetter(e2);
    }

    @Override
    public Flux<Object> produce(GDClient client) {
        return Mono.zip(pagesComparator.fetchPage(client, 0).collect(Collectors.toUnmodifiableSet()),
                        pagesComparator.fetchPage(client, 1).collect(Collectors.toUnmodifiableSet()))
                .flatMapMany(function((page0, page1) -> {
                    final var previous0 = this.previous0;
                    final var previous1 = this.previous1;
                    this.previous0 = page0;
                    this.previous1 = page1;
                    if (previous0 == null || previous1 == null) {
                        return Flux.empty();
                    }
                    final var allOld = union(previous0, previous1);
                    final var allNew = union(page0, page1);
                    final var added = Flux.fromIterable(subtract(page0, allOld))
                            .map(pagesComparator::createAddEvent)
                            .flatMap(Mono::justOrEmpty);
                    final var removed = Flux.fromIterable(subtract(previous0, allNew))
                            .map(pagesComparator::createRemoveEvent)
                            .flatMap(Mono::justOrEmpty);
                    final var updated = Flux.fromIterable(intersection(previous0, allNew))
                            .map(item -> Tuples.of(
                                    previous0.stream()
                                            .filter(p -> idEquals(item, p))
                                            .findAny()
                                            .orElseThrow(),
                                    allNew.stream()
                                            .filter(n -> idEquals(item, n))
                                            .findAny()
                                            .orElseThrow()))
                            .map(function(pagesComparator::createUpdateEvent))
                            .flatMap(Mono::justOrEmpty);
                    return Flux.concat(added, removed, updated);
                }));
    }
}
