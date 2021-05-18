package jdash.events.producer;

import jdash.client.GDClient;
import jdash.client.cache.GDCache;
import jdash.client.request.GDRequest;
import jdash.client.request.GDRequests;
import jdash.common.DemonDifficulty;
import jdash.common.Difficulty;
import jdash.common.Length;
import jdash.common.entity.GDLevel;
import jdash.common.entity.GDTimelyInfo;
import jdash.common.entity.ImmutableGDLevel;
import jdash.common.entity.ImmutableGDTimelyInfo;
import jdash.events.object.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class GDEventProducerTest {

    private AwardedCache cache;
    private GDClient client;
    private AwardedEventProducer awardedProducer;
    private TimelyEventProducer timelyProducer;

    private static GDLevel createLevel(long id, int stars) {
        return ImmutableGDLevel.builder()
                .coinCount(0)
                .creatorPlayerId(0)
                .creatorName("")
                .demonDifficulty(DemonDifficulty.HARD)
                .description("")
                .difficulty(Difficulty.NA)
                .downloads(0)
                .likes(0)
                .featuredScore(0)
                .gameVersion(0)
                .hasCoinsVerified(false)
                .id(id)
                .isAuto(false)
                .isDemon(false)
                .isEpic(false)
                .length(Length.TINY)
                .levelVersion(1)
                .name("test level " + id)
                .objectCount(0)
                .originalLevelId(0)
                .requestedStars(0)
                .songId(0)
                .stars(stars)
                .build();
    }

    private static GDTimelyInfo createTimelyInfo(long number) {
        return ImmutableGDTimelyInfo.builder().number(number).nextIn(Duration.ofSeconds(1)).build();
    }

    @BeforeEach
    public void setUp() {
        cache = new AwardedCache();
        client = GDClient.create().withCache(cache);
        awardedProducer = new AwardedEventProducer();
        timelyProducer = new TimelyEventProducer();
    }

    @Test
    public void produceNothingTest() {
        var eventsA = awardedProducer.produce(client).collectList().block();
        assertNotNull(eventsA);
        assertTrue(eventsA.isEmpty()); // First iteration should yield nothing
        var eventsB = timelyProducer.produce(client).collectList().block();
        assertNotNull(eventsB);
        assertTrue(eventsB.isEmpty()); // First iteration should yield nothing

        var eventsA2 = awardedProducer.produce(client).collectList().block();
        assertNotNull(eventsA2);
        assertTrue(eventsA2.isEmpty()); // No modification detected
        var eventsB2 = timelyProducer.produce(client).collectList().block();
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
        assertEquals(List.of(ImmutableAwardedAdd.of(added)), events2);
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
        assertEquals(List.of(ImmutableAwardedRemove.of(removed)), events2);
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
        assertEquals(List.of(ImmutableAwardedUpdate.of(before, after)), events2);
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
                ImmutableAwardedAdd.of(added1),
                ImmutableAwardedAdd.of(added2),
                ImmutableAwardedRemove.of(removed1),
                ImmutableAwardedRemove.of(removed2),
                ImmutableAwardedRemove.of(removed3),
                ImmutableAwardedUpdate.of(before, after)), events2);
    }

    @Test
    public void produceTimelyChangeTest() {
        var events = timelyProducer.produce(client).collectList().block();
        assertNotNull(events);
        assertTrue(events.isEmpty()); // First iteration should yield nothing

        // Simulate timely change
        var oldDaily = createTimelyInfo(1);
        var oldWeekly = createTimelyInfo(10);
        var newDaily = createTimelyInfo(2);
        var newWeekly = createTimelyInfo(11);
        cache.daily = newDaily;
        cache.weekly = newWeekly;

        var events2 = timelyProducer.produce(client).collect(Collectors.toUnmodifiableSet()).block();
        assertNotNull(events2);
        assertEquals(events2, Set.of(
                ImmutableDailyLevelChange.of(oldDaily, newDaily),
                ImmutableWeeklyDemonChange.of(oldWeekly, newWeekly)
        ));
    }

    private static class AwardedCache implements GDCache {

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
        GDTimelyInfo daily = createTimelyInfo(1);
        GDTimelyInfo weekly = createTimelyInfo(10);

        @Override
        public Optional<Object> retrieve(GDRequest request) {
            switch (request.getUri()) {
                case GDRequests.GET_GJ_LEVELS_21:
                    return Optional.ofNullable(request.getParams().get("page"))
                            .map(value -> {
                                switch (value) {
                                    case "0":
                                        return page0;
                                    case "1":
                                        return page1;
                                    default:
                                        return null;
                                }
                            });
                case GDRequests.GET_GJ_DAILY_LEVEL:
                    return Optional.ofNullable(request.getParams().get("weekly"))
                            .map(value -> {
                                switch (value) {
                                    case "0":
                                        return daily;
                                    case "1":
                                        return weekly;
                                    default:
                                        return null;
                                }
                            });
                default:
                    return Optional.empty();
            }
        }

        @Override
        public void put(GDRequest request, Object cached) {

        }

        @Override
        public void clear() {

        }
    }
}