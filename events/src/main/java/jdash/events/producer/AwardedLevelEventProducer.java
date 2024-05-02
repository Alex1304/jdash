package jdash.events.producer;

import jdash.client.GDClient;
import jdash.common.LevelBrowseMode;
import jdash.common.entity.GDLevel;
import jdash.events.object.AwardedLevelAdd;
import jdash.events.object.AwardedLevelRemove;
import jdash.events.object.AwardedLevelUpdate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.util.Arrays;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static reactor.function.TupleUtils.function;
import static reactor.function.TupleUtils.predicate;

class AwardedLevelEventProducer implements GDEventProducer {

    private Set<GDLevel> previous0;
    private Set<GDLevel> previous1;

    private static Set<GDLevel> intersection(Set<GDLevel> a, Set<GDLevel> b) {
        return a.stream()
                .filter(l -> b.stream().map(WrappedLevel::new).anyMatch(new WrappedLevel(l)::equals))
                .collect(Collectors.toUnmodifiableSet());
    }

    private static Set<GDLevel> union(Set<GDLevel> a, Set<GDLevel> b) {
        return Stream.concat(a.stream().map(WrappedLevel::new), b.stream().map(WrappedLevel::new))
                .distinct()
                .map(WrappedLevel::unwrap)
                .collect(Collectors.toUnmodifiableSet());
    }

    private static Set<GDLevel> subtract(Set<GDLevel> a, Set<GDLevel> b) {
        return a.stream()
                .filter(l -> b.stream().map(WrappedLevel::new).noneMatch(new WrappedLevel(l)::equals))
                .collect(Collectors.toUnmodifiableSet());
    }

    @SafeVarargs
    private static BiPredicate<GDLevel, GDLevel> haveDifferentFields(Function<GDLevel, ?>... fieldGetters) {
        return (l1, l2) -> Arrays.stream(fieldGetters)
                .anyMatch(fieldGetter -> !fieldGetter.apply(l1).equals(fieldGetter.apply(l2)));
    }

    @Override
    public Flux<Object> produce(GDClient client) {
        return Mono.zip(
                        client.browseLevels(LevelBrowseMode.AWARDED, null, null, 0)
                                .collect(Collectors.toUnmodifiableSet()),
                        client.browseLevels(LevelBrowseMode.AWARDED, null, null, 1)
                                .collect(Collectors.toUnmodifiableSet()))
                .flatMapMany(function((levels0, levels1) -> {
                    var previous0 = this.previous0;
                    var previous1 = this.previous1;
                    this.previous0 = levels0;
                    this.previous1 = levels1;
                    if (previous0 == null || previous1 == null) {
                        return Flux.empty();
                    }
                    var allOld = union(previous0, previous1);
                    var allNew = union(levels0, levels1);
                    var added = Flux.fromIterable(subtract(levels0, allOld))
                            .map(AwardedLevelAdd::new);
                    var removed = Flux.fromIterable(subtract(previous0, allNew))
                            .map(AwardedLevelRemove::new);
                    var updated = Flux.fromIterable(intersection(previous0, allNew))
                            .map(level -> Tuples.of(
                                    previous0.stream()
                                            .filter(l -> l.id() == level.id())
                                            .findAny()
                                            .orElseThrow(),
                                    allNew.stream()
                                            .filter(l -> l.id() == level.id())
                                            .findAny()
                                            .orElseThrow()))
                            .filter(predicate(haveDifferentFields(
                                    GDLevel::rewards,
                                    GDLevel::difficulty,
                                    GDLevel::demonDifficulty,
                                    GDLevel::featuredScore,
                                    GDLevel::qualityRating
                            )))
                            .map(function(AwardedLevelUpdate::new));
                    return Flux.concat(added, removed, updated);
                }));
    }

    /**
     * Redefines equals and hashCode on level ID only
     */
    private record WrappedLevel(GDLevel level) {

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

        public GDLevel unwrap() {
            return level;
        }
    }
}
