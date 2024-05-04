package jdash.events.producer;

import jdash.client.GDClient;
import jdash.common.LevelBrowseMode;
import jdash.common.entity.GDLevel;
import jdash.events.object.AwardedLevelAdd;
import jdash.events.object.AwardedLevelRemove;
import jdash.events.object.AwardedLevelUpdate;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import static jdash.common.internal.InternalUtils.haveDifferentFields;

class AwardedLevelEventProducer extends AwardedEventProducer<GDLevel> {

    @Override
    Mono<Tuple2<Set<GDLevel>, Set<GDLevel>>> fetchFirstTwoPages(GDClient client) {
        return Mono.zip(
                client.browseLevels(LevelBrowseMode.AWARDED, null, null, 0)
                        .collect(Collectors.toUnmodifiableSet()),
                client.browseLevels(LevelBrowseMode.AWARDED, null, null, 1)
                        .collect(Collectors.toUnmodifiableSet()));
    }

    @Override
    Wrapper<GDLevel> wrap(GDLevel element) {
        return new WrappedLevel(element);
    }

    @Override
    Object createAddEvent(GDLevel element) {
        return new AwardedLevelAdd(element);
    }

    @Override
    Object createRemoveEvent(GDLevel element) {
        return new AwardedLevelRemove(element);
    }

    @Override
    Object createUpdateEvent(GDLevel oldData, GDLevel newData) {
        return new AwardedLevelUpdate(oldData, newData);
    }

    @Override
    BiPredicate<GDLevel, GDLevel> fieldsComparator() {
        return haveDifferentFields(
                GDLevel::rewards,
                GDLevel::difficulty,
                GDLevel::demonDifficulty,
                GDLevel::featuredScore,
                GDLevel::qualityRating
        );
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

        public GDLevel unwrap() {
            return level;
        }
    }
}
