package jdash.events.producer;

import jdash.client.GDClient;
import jdash.common.LevelSearchMode;
import jdash.common.entity.GDLevel;
import jdash.events.object.AwardedLevelAdd;
import jdash.events.object.AwardedLevelRemove;
import jdash.events.object.AwardedLevelUpdate;
import reactor.core.publisher.Flux;

import java.util.Optional;

import static jdash.common.internal.InternalUtils.haveDifferentFields;

class AwardedLevelPagesComparator implements PagesComparator<GDLevel> {

    @Override
    public Flux<GDLevel> fetchPage(GDClient client, int page) {
        return client.searchLevels(LevelSearchMode.AWARDED, null, null, page);
    }

    @Override
    public long idGetter(GDLevel element) {
        return element.id();
    }

    @Override
    public Optional<Object> createAddEvent(GDLevel element) {
        return Optional.of(new AwardedLevelAdd(element));
    }

    @Override
    public Optional<Object> createRemoveEvent(GDLevel element) {
        return Optional.of(new AwardedLevelRemove(element));
    }

    @Override
    public Optional<Object> createUpdateEvent(GDLevel oldElement, GDLevel newElement) {
        if (haveDifferentFields(oldElement, newElement,
                GDLevel::rewards,
                GDLevel::difficulty,
                GDLevel::demonDifficulty,
                GDLevel::featuredScore,
                GDLevel::qualityRating)) {
            return Optional.of(new AwardedLevelUpdate(oldElement, newElement));
        }
        return Optional.empty();
    }

}
