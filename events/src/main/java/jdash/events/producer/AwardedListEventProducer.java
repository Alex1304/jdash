package jdash.events.producer;

import jdash.client.GDClient;
import jdash.common.LevelBrowseMode;
import jdash.common.entity.GDList;
import jdash.events.object.AwardedListAdd;
import jdash.events.object.AwardedListRemove;
import jdash.events.object.AwardedListUpdate;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import static jdash.common.internal.InternalUtils.haveDifferentFields;

class AwardedListEventProducer extends AwardedEventProducer<GDList> {

    @Override
    Mono<Tuple2<Set<GDList>, Set<GDList>>> fetchFirstTwoPages(GDClient client) {
        return Mono.zip(
                client.browseLists(LevelBrowseMode.AWARDED, null, null, 0)
                        .collect(Collectors.toUnmodifiableSet()),
                client.browseLists(LevelBrowseMode.AWARDED, null, null, 1)
                        .collect(Collectors.toUnmodifiableSet()));
    }

    @Override
    Wrapper<GDList> wrap(GDList element) {
        return new WrappedLevel(element);
    }

    @Override
    Object createAddEvent(GDList element) {
        return new AwardedListAdd(element);
    }

    @Override
    Object createRemoveEvent(GDList element) {
        return new AwardedListRemove(element);
    }

    @Override
    Object createUpdateEvent(GDList oldData, GDList newData) {
        return new AwardedListUpdate(oldData, newData);
    }

    @Override
    BiPredicate<GDList, GDList> fieldsComparator() {
        return haveDifferentFields(
                GDList::diamonds,
                GDList::iconId,
                GDList::isFeatured
        );
    }

    /**
     * Redefines equals and hashCode on level ID only
     */
    private record WrappedLevel(GDList level) implements Wrapper<GDList> {

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

        public GDList unwrap() {
            return level;
        }
    }
}
