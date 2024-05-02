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
import jdash.events.object.AwardedLevelAdd;
import jdash.events.object.AwardedLevelRemove;
import jdash.events.object.AwardedLevelUpdate;
import jdash.events.object.DailyLevelChange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class GDEventProducerTest {

    private EventProducerTestCache cache;
    private GDClient client;
    private AwardedLevelEventProducer awardedProducer;
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

    private static GDDailyInfo createDailyInfo(long number) {
        return new GDDailyInfo(number, Duration.ofSeconds(1));
    }

    @BeforeEach
    public void setUp() {
        cache = new EventProducerTestCache();
        client = GDClient.create().withCache(cache);
        awardedProducer = new AwardedLevelEventProducer();
        dailyProducer = new DailyEventProducer();
    }

    @Test
    public void produceNothingTest() {
        var eventsA = awardedProducer.produce(client).collectList().block();
        assertNotNull(eventsA);
        assertTrue(eventsA.isEmpty()); // First iteration should yield nothing
        var eventsB = dailyProducer.produce(client).collectList().block();
        assertNotNull(eventsB);
        assertTrue(eventsB.isEmpty()); // First iteration should yield nothing

        var eventsA2 = awardedProducer.produce(client).collectList().block();
        assertNotNull(eventsA2);
        assertTrue(eventsA2.isEmpty()); // No modification detected
        var eventsB2 = dailyProducer.produce(client).collectList().block();
        assertNotNull(eventsB2);
        assertTrue(eventsB2.isEmpty()); // No modification detected
    }

    @Test
    public void produceAwardedAddTest() {
        var events = awardedProducer.produce(client).collectList().block();
        assertNotNull(events);
        assertTrue(events.isEmpty()); // First iteration should yield nothing

        // Simulate awarded add
        var added = createLevel(25, 4);
        cache.page0 = List.of(
                added,
                createLevel(1, 10),
                createLevel(2, 10),
                createLevel(3, 10),
                createLevel(4, 10)
        );
        cache.page1 = List.of(
                createLevel(5, 10),
                createLevel(6, 10),
                createLevel(7, 10),
                createLevel(8, 10),
                createLevel(9, 10)
        );

        var events2 = awardedProducer.produce(client).collectList().block();
        assertNotNull(events2);
        assertEquals(List.of(new AwardedLevelAdd(added)), events2);
    }

    @Test
    public void produceAwardedRemoveTest() {
        var events = awardedProducer.produce(client).collectList().block();
        assertNotNull(events);
        assertTrue(events.isEmpty()); // First iteration should yield nothing

        // Simulate awarded remove
        var removed = createLevel(3, 10);
        cache.page0 = List.of(
                createLevel(1, 10),
                createLevel(2, 10),
                createLevel(4, 10),
                createLevel(5, 10),
                createLevel(6, 10)
        );
        cache.page1 = List.of(
                createLevel(7, 10),
                createLevel(8, 10),
                createLevel(9, 10),
                createLevel(10, 10),
                createLevel(11, 10)
        );

        var events2 = awardedProducer.produce(client).collectList().block();
        assertNotNull(events2);
        assertEquals(List.of(new AwardedLevelRemove(removed)), events2);
    }

    @Test
    public void produceAwardedUpdateTest() {
        var events = awardedProducer.produce(client).collectList().block();
        assertNotNull(events);
        assertTrue(events.isEmpty()); // First iteration should yield nothing

        // Simulate awarded update
        var before = createLevel(3, 10);
        var after = createLevel(3, 8);
        cache.page0 = List.of(
                createLevel(1, 10),
                createLevel(2, 10),
                after,
                createLevel(4, 10),
                createLevel(5, 10)
        );

        var events2 = awardedProducer.produce(client).collectList().block();
        assertNotNull(events2);
        assertEquals(List.of(new AwardedLevelUpdate(before, after)), events2);
    }

    @Test
    public void produceMixedTest() {
        var events = awardedProducer.produce(client).collectList().block();
        assertNotNull(events);
        assertTrue(events.isEmpty()); // First iteration should yield nothing

        // Simulate many events
        var added1 = createLevel(25, 4);
        var added2 = createLevel(26, 7);
        var removed1 = createLevel(2, 10);
        var removed2 = createLevel(3, 10);
        var removed3 = createLevel(5, 10);
        var before = createLevel(1, 10);
        var after = createLevel(1, 9);
        cache.page0 = List.of(
                added1,
                added2,
                after,
                createLevel(4, 10),
                createLevel(6, 10)
        );
        cache.page1 = List.of(
                createLevel(7, 10),
                createLevel(8, 10),
                createLevel(9, 10),
                createLevel(10, 10),
                createLevel(11, 10)
        );

        var events2 = awardedProducer.produce(client).collect(Collectors.toUnmodifiableSet()).block();
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
    public void produceTimelyChangeTest() {
        var events = dailyProducer.produce(client).collectList().block();
        assertNotNull(events);
        assertTrue(events.isEmpty()); // First iteration should yield nothing

        // Simulate daily change
        var oldDaily = createDailyInfo(1);
        var oldWeekly = createDailyInfo(10);
        var newDaily = createDailyInfo(2);
        var newWeekly = createDailyInfo(11);
        cache.daily = newDaily;
        cache.weekly = newWeekly;

        var events2 = dailyProducer.produce(client).collect(Collectors.toUnmodifiableSet()).block();
        assertNotNull(events2);
        assertEquals(events2, Set.of(
                new DailyLevelChange(oldDaily, newDaily, false),
                new DailyLevelChange(oldWeekly, newWeekly, true)
        ));
    }

    private static class EventProducerTestCache implements GDCache {

        List<GDLevel> page0 = List.of(
                createLevel(1, 10),
                createLevel(2, 10),
                createLevel(3, 10),
                createLevel(4, 10),
                createLevel(5, 10)
        );
        List<GDLevel> page1 = List.of(
                createLevel(6, 10),
                createLevel(7, 10),
                createLevel(8, 10),
                createLevel(9, 10),
                createLevel(10, 10)
        );
        GDDailyInfo daily = createDailyInfo(1);
        GDDailyInfo weekly = createDailyInfo(10);

        @Override
        public Optional<Object> retrieve(GDRequest request) {
            return switch (request.getUri()) {
                case GDRequests.GET_GJ_LEVELS_21 -> Optional.ofNullable(request.getParams().get("page"))
                        .map(value -> switch (value) {
                            case "0" -> page0;
                            case "1" -> page1;
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