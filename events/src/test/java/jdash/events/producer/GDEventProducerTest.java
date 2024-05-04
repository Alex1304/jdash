package jdash.events.producer;

import jdash.client.GDClient;
import jdash.client.cache.GDCache;
import jdash.client.request.GDRequest;
import jdash.client.request.GDRequests;
import jdash.common.DemonDifficulty;
import jdash.common.Difficulty;
import jdash.common.Length;
import jdash.common.QualityRating;
import jdash.common.entity.GDDailyInfo;
import jdash.common.entity.GDLevel;
import jdash.common.entity.GDList;
import jdash.events.object.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class GDEventProducerTest {

    private EventProducerTestCache cache;
    private GDClient client;
    private AwardedLevelEventProducer awardedLevelProducer;
    private AwardedListEventProducer awardedListProducer;
    private DailyEventProducer dailyProducer;

    private static GDLevel createLevel(long id, int stars) {
        return new GDLevel(
                id,
                "test level " + id,
                0,
                "",
                Difficulty.NA,
                DemonDifficulty.HARD,
                stars,
                0,
                QualityRating.NONE,
                0,
                0,
                Length.TINY,
                0,
                false,
                1,
                0,
                0,
                false,
                false,
                Optional.empty(),
                0,
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
    }

    private static GDList createList(long id, int diamonds) {
        return new GDList(
                id,
                "test list " + id,
                "",
                1,
                0,
                0,
                false,
                Instant.EPOCH,
                Instant.EPOCH,
                0,
                0,
                "",
                List.of(),
                diamonds,
                0
        );
    }

    private static GDDailyInfo createDailyInfo(long number) {
        return new GDDailyInfo(number, Duration.ofSeconds(1));
    }

    @BeforeEach
    public void setUp() {
        cache = new EventProducerTestCache();
        client = GDClient.create().withCache(cache);
        awardedLevelProducer = new AwardedLevelEventProducer();
        awardedListProducer = new AwardedListEventProducer();
        dailyProducer = new DailyEventProducer();
        
        final var eventsA = awardedLevelProducer.produce(client).collectList().block();
        assertNotNull(eventsA);
        assertTrue(eventsA.isEmpty()); // First iteration should yield nothing
        final var eventsB = dailyProducer.produce(client).collectList().block();
        assertNotNull(eventsB);
        assertTrue(eventsB.isEmpty()); // First iteration should yield nothing
        final var eventsC = awardedListProducer.produce(client).collectList().block();
        assertNotNull(eventsC);
        assertTrue(eventsC.isEmpty()); // First iteration should yield nothing
    }

    @Test
    public void produceNothingTest() {
        final var levelEvents = awardedLevelProducer.produce(client).collectList().block();
        assertNotNull(levelEvents);
        assertTrue(levelEvents.isEmpty()); // No modification detected
        final var dailyEvents = dailyProducer.produce(client).collectList().block();
        assertNotNull(dailyEvents);
        assertTrue(dailyEvents.isEmpty()); // No modification detected
        final var listEvents = awardedListProducer.produce(client).collectList().block();
        assertNotNull(listEvents);
        assertTrue(listEvents.isEmpty()); // No modification detected
    }

    @Test
    public void produceAwardedAddTest() {
        final var addedLevel = createLevel(25, 4);
        final var addedList = createList(25, 4);
        cache.levels0 = List.of(
                addedLevel,
                createLevel(1, 10),
                createLevel(2, 10),
                createLevel(3, 10),
                createLevel(4, 10)
        );
        cache.levels1 = List.of(
                createLevel(5, 10),
                createLevel(6, 10),
                createLevel(7, 10),
                createLevel(8, 10),
                createLevel(9, 10)
        );
        cache.lists0 = List.of(
                addedList,
                createList(1, 10),
                createList(2, 10),
                createList(3, 10),
                createList(4, 10)
        );
        cache.lists1 = List.of(
                createList(5, 10),
                createList(6, 10),
                createList(7, 10),
                createList(8, 10),
                createList(9, 10)
        );

        final var levelEvents = awardedLevelProducer.produce(client).collectList().block();
        assertNotNull(levelEvents);
        assertEquals(List.of(new AwardedLevelAdd(addedLevel)), levelEvents);
        final var listEvents = awardedListProducer.produce(client).collectList().block();
        assertNotNull(listEvents);
        assertEquals(List.of(new AwardedListAdd(addedList)), listEvents);
    }

    @Test
    public void produceAwardedRemoveTest() {
        final var removedLevel = createLevel(3, 10);
        final var removedList = createList(3, 10);
        cache.levels0 = List.of(
                createLevel(1, 10),
                createLevel(2, 10),
                createLevel(4, 10),
                createLevel(5, 10),
                createLevel(6, 10)
        );
        cache.levels1 = List.of(
                createLevel(7, 10),
                createLevel(8, 10),
                createLevel(9, 10),
                createLevel(10, 10),
                createLevel(11, 10)
        );
        cache.lists0 = List.of(
                createList(1, 10),
                createList(2, 10),
                createList(4, 10),
                createList(5, 10),
                createList(6, 10)
        );
        cache.lists1 = List.of(
                createList(7, 10),
                createList(8, 10),
                createList(9, 10),
                createList(10, 10),
                createList(11, 10)
        );

        final var levelEvents = awardedLevelProducer.produce(client).collectList().block();
        assertNotNull(levelEvents);
        assertEquals(List.of(new AwardedLevelRemove(removedLevel)), levelEvents);
        final var listEvents = awardedListProducer.produce(client).collectList().block();
        assertNotNull(listEvents);
        assertEquals(List.of(new AwardedListRemove(removedList)), listEvents);
    }

    @Test
    public void produceAwardedUpdateTest() {
        final var levelBefore = createLevel(3, 10);
        final var levelAfter = createLevel(3, 8);
        final var listBefore = createList(3, 10);
        final var listAfter = createList(3, 8);
        cache.levels0 = List.of(
                createLevel(1, 10),
                createLevel(2, 10),
                levelAfter,
                createLevel(4, 10),
                createLevel(5, 10)
        );
        cache.lists0 = List.of(
                createList(1, 10),
                createList(2, 10),
                listAfter,
                createList(4, 10),
                createList(5, 10)
        );

        final var levelEvents = awardedLevelProducer.produce(client).collectList().block();
        assertNotNull(levelEvents);
        assertEquals(List.of(new AwardedLevelUpdate(levelBefore, levelAfter)), levelEvents);
        final var listEvents = awardedListProducer.produce(client).collectList().block();
        assertNotNull(listEvents);
        assertEquals(List.of(new AwardedListUpdate(listBefore, listAfter)), listEvents);
    }

    @Test
    public void produceMixedLevelsTest() {
        final var added1 = createLevel(25, 4);
        final var added2 = createLevel(26, 7);
        final var removed1 = createLevel(2, 10);
        final var removed2 = createLevel(3, 10);
        final var removed3 = createLevel(5, 10);
        final var before = createLevel(1, 10);
        final var after = createLevel(1, 9);
        cache.levels0 = List.of(
                added1,
                added2,
                after,
                createLevel(4, 10),
                createLevel(6, 10)
        );
        cache.levels1 = List.of(
                createLevel(7, 10),
                createLevel(8, 10),
                createLevel(9, 10),
                createLevel(10, 10),
                createLevel(11, 10)
        );

        final var events2 = awardedLevelProducer.produce(client).collect(Collectors.toUnmodifiableSet()).block();
        assertNotNull(events2);
        assertEquals(Set.of(
                new AwardedLevelAdd(added1),
                new AwardedLevelAdd(added2),
                new AwardedLevelRemove(removed1),
                new AwardedLevelRemove(removed2),
                new AwardedLevelRemove(removed3),
                new AwardedLevelUpdate(before, after)), events2);
    }

    @Test
    public void produceMixedListsTest() {
        final var added1 = createList(25, 4);
        final var added2 = createList(26, 7);
        final var removed1 = createList(2, 10);
        final var removed2 = createList(3, 10);
        final var removed3 = createList(5, 10);
        final var before = createList(1, 10);
        final var after = createList(1, 9);
        cache.lists0 = List.of(
                added1,
                added2,
                after,
                createList(4, 10),
                createList(6, 10)
        );
        cache.lists1 = List.of(
                createList(7, 10),
                createList(8, 10),
                createList(9, 10),
                createList(10, 10),
                createList(11, 10)
        );

        final var events2 = awardedListProducer.produce(client).collect(Collectors.toUnmodifiableSet()).block();
        assertNotNull(events2);
        assertEquals(Set.of(
                new AwardedListAdd(added1),
                new AwardedListAdd(added2),
                new AwardedListRemove(removed1),
                new AwardedListRemove(removed2),
                new AwardedListRemove(removed3),
                new AwardedListUpdate(before, after)), events2);
    }

    @Test
    public void produceDailyChangeTest() {
        final var oldDaily = createDailyInfo(1);
        final var oldWeekly = createDailyInfo(10);
        final var newDaily = createDailyInfo(2);
        final var newWeekly = createDailyInfo(11);
        cache.daily = newDaily;
        cache.weekly = newWeekly;

        final var events2 = dailyProducer.produce(client).collect(Collectors.toUnmodifiableSet()).block();
        assertNotNull(events2);
        assertEquals(events2, Set.of(
                new DailyLevelChange(oldDaily, newDaily, false),
                new DailyLevelChange(oldWeekly, newWeekly, true)
        ));
    }

    private static class EventProducerTestCache implements GDCache {

        List<GDLevel> levels0 = List.of(
                createLevel(1, 10),
                createLevel(2, 10),
                createLevel(3, 10),
                createLevel(4, 10),
                createLevel(5, 10)
        );
        List<GDLevel> levels1 = List.of(
                createLevel(6, 10),
                createLevel(7, 10),
                createLevel(8, 10),
                createLevel(9, 10),
                createLevel(10, 10)
        );
        List<GDList> lists0 = List.of(
                createList(1, 10),
                createList(2, 10),
                createList(3, 10),
                createList(4, 10),
                createList(5, 10)
        );
        List<GDList> lists1 = List.of(
                createList(6, 10),
                createList(7, 10),
                createList(8, 10),
                createList(9, 10),
                createList(10, 10)
        );
        GDDailyInfo daily = createDailyInfo(1);
        GDDailyInfo weekly = createDailyInfo(10);

        @Override
        public Optional<Object> retrieve(GDRequest request) {
            return switch (request.getUri()) {
                case GDRequests.GET_GJ_LEVELS_21 -> Optional.ofNullable(request.getParams().get("page"))
                        .map(value -> switch (value) {
                            case "0" -> levels0;
                            case "1" -> levels1;
                            default -> null;
                        });
                case GDRequests.GET_GJ_LEVEL_LISTS -> Optional.ofNullable(request.getParams().get("page"))
                        .map(value -> switch (value) {
                            case "0" -> lists0;
                            case "1" -> lists1;
                            default -> null;
                        });
                case GDRequests.GET_GJ_DAILY_LEVEL -> Optional.ofNullable(request.getParams().get("weekly"))
                        .map(value -> switch (value) {
                            case "0" -> daily;
                            case "1" -> weekly;
                            default -> null;
                        });

                default -> Optional.empty();
            };
        }

        @Override
        public void put(GDRequest request, Object cached) {}

        @Override
        public void clear() {}
    }
}