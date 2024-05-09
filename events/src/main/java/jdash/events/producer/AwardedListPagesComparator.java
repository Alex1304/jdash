package jdash.events.producer;

import jdash.client.GDClient;
import jdash.common.LevelSearchMode;
import jdash.common.entity.GDList;
import jdash.events.object.AwardedListAdd;
import jdash.events.object.AwardedListRemove;
import jdash.events.object.AwardedListUpdate;
import reactor.core.publisher.Flux;

import java.util.Optional;

import static jdash.common.internal.InternalUtils.haveDifferentFields;

class AwardedListPagesComparator implements PagesComparator<GDList> {

    @Override
    public Flux<GDList> fetchPage(GDClient client, int page) {
        return client.searchLists(LevelSearchMode.AWARDED, null, null, page);
    }

    @Override
    public long idGetter(GDList element) {
        return element.id();
    }

    @Override
    public Optional<Object> createAddEvent(GDList element) {
        return Optional.of(new AwardedListAdd(element));
    }

    @Override
    public Optional<Object> createRemoveEvent(GDList element) {
        return Optional.of(new AwardedListRemove(element));
    }

    @Override
    public Optional<Object> createUpdateEvent(GDList oldElement, GDList newElement) {
        if (haveDifferentFields(oldElement, newElement, GDList::diamonds, GDList::iconId, GDList::isFeatured)) {
            return Optional.of(new AwardedListUpdate(oldElement, newElement));
        }
        return Optional.empty();
    }

}
