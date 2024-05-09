package jdash.client;

import jdash.client.exception.GDClientException;
import jdash.common.*;
import jdash.common.entity.*;
import jdash.common.internal.InternalUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public final class GDClientTest {

    private GDCacheMock cache;
    private GDRouterMock router;
    private GDClient client;
    private GDClient authClient;

    /* Not part of unit tests, this is only to test the real router implementation */
    public static void main(String[] args) {
        final var client = GDClient.create();
        System.out.println(client.downloadLevel(104043964).block());
    }

    @BeforeEach
    public void setUp() {
        cache = new GDCacheMock();
        router = new GDRouterMock();
        client = GDClient.create()
                .withUniqueDeviceId("jdash-client")
                .withCache(cache)
                .withRouter(router);
        authClient = client.withAuthentication(1, 1, "test");
    }

    @Test
    public void cacheTest() {
        assertTrue(cache.getMap().isEmpty()); // Ensure the cache is empty at first

        final var response = client.findLevelById(10565740).block(); // Make a request
        assertNotNull(response);
        assertEquals(1, router.getRequestCount()); // Check that it hit the router
        assertEquals(1, cache.getMap().size()); // Check that the response was added to cache

        // Check the object added to cache is the same as the returned response
        final var cached = cache.getMap().values().stream().findAny().orElseThrow();
        assertEquals(List.of(response), cached);

        final var response2 = client.findLevelById(10565740).block(); // Make the same request again
        assertEquals(1, router.getRequestCount()); // Check that it didn't hit the router (requestCount didn't
        // increment). It means it properly hit the cache.
        assertEquals(response2, response); // Check the new response is consistent with the first one

        cache.clear();
        assertTrue(cache.getMap().isEmpty()); // Ensure the cache cleared properly

        client.withCacheDisabled().findLevelById(10565740).block(); // Make the same request but with cache off
        assertEquals(2, router.getRequestCount()); // It should be hitting the router
        assertTrue(cache.getMap().isEmpty()); // The cache should still be empty

        client.withWriteOnlyCache().findLevelById(10565740).block(); // Make the same request but with write-only
        assertEquals(3, router.getRequestCount()); // It should be hitting the router
        assertEquals(1, cache.getMap().size()); // But the cache should not be empty
    }

    @Test
    public void loginTest() {
        assertFalse(client.isAuthenticated());
        final var newClient = client.login("Alex1304", "F3keP4ssw0rd").block();
        assertNotNull(newClient);
        assertTrue(newClient.isAuthenticated());
        assertThrows(GDClientException.class, client.login("Alex1304", "WrongPassword")::block);
    }

    @Test
    public void findLevelByIdTest() {
        final var expected = new GDLevel(
                10565740,
                "Bloodbath",
                503085,
                "Whose blood will be spilt in the Bloodbath? Who will the victors be? How many will " +
                        "survive? Good luck...",
                Difficulty.INSANE,
                DemonDifficulty.EXTREME,
                10,
                10330,
                QualityRating.FEATURED,
                26672952,
                1505455,
                Length.LONG,
                0,
                false,
                3,
                21,
                24746,
                true,
                false,
                Optional.of(7679228L),
                0,
                Optional.of(467339L),
                Optional.of(new GDSong(
                        467339,
                        "At the Speed of Light",
                        "Dimrain47",
                        Optional.of("9.56"),
                        Optional.ofNullable(InternalUtils.urlDecode("http%3A%2F%2Faudio.ngfiles" +
                                ".com%2F467000%2F467339_At_the_Speed_of_Light_FINA.mp3")))),
                Optional.of("Riot"),
                Optional.of(37415L)
        );
        final var actual = client.findLevelById(10565740).block();
        assertEquals(expected, actual);
    }

    @Test
    public void searchLevelsTest() {
        final var expected = List.of(10565740L, 10792915L, 21761387L, 13615973L, 10578973L, 35717743L, 11797073L,
                38601659L, 19274064L, 10978435L);
        final var actual = client.searchLevels(LevelSearchMode.SEARCH, "Bloodbath", null, 0)
                .map(GDLevel::id)
                .collectList()
                .block();
        assertEquals(expected, actual);
    }

    @Test
    public void searchListsTest() {
        final var expected = List.of(242270L, 98021L, 278569L, 52207L, 230832L, 279460L, 178665L, 48649L, 310214L,
                231005L);
        final var actual = client.searchLists(LevelSearchMode.AWARDED, null, null, 0)
                .map(GDList::id)
                .collectList()
                .block();
        assertEquals(expected, actual);
    }

    @Test
    public void getUserProfileTest() {
        final var user = new GDUser(
                4063664,
                98006,
                "Alex1304",
                12,
                9,
                true,
                Optional.empty(),
                Optional.empty(),
                Optional.of(Role.MODERATOR)
        );
        final var expected = new GDUserProfile(
                user,
                new GDUserStats(
                        user,
                        5658,
                        0,
                        19336,
                        100,
                        818,
                        46,
                        21,
                        Optional.empty()
                ),
                33266,
                29,
                7,
                3,
                30,
                24,
                21,
                15,
                22,
                1,
                9,
                "UC0hFAVN-GAbZYuf_Hfk1Iog",
                "gd_alex1304",
                "gd_alex1304",
                true,
                PrivacySetting.ALL,
                PrivacySetting.ALL,
                Optional.of(new GDUserProfile.CompletedClassicLevels(
                        151,
                        106,
                        197,
                        364,
                        129,
                        23,
                        275,
                        14
                )),
                Optional.of(new GDUserProfile.CompletedPlatformerLevels(
                        1,
                        1,
                        5,
                        6,
                        2,
                        0,
                        0
                )),
                Optional.of(new GDUserProfile.CompletedDemons(
                        20,
                        4,
                        4,
                        1,
                        1,
                        0,
                        1,
                        0,
                        0,
                        0,
                        14,
                        0
                ))
        );
        final var actual = client.getUserProfile(98006).block();
        assertEquals(expected, actual);
    }

    @Test
    public void searchUsersTest() {
        final var expected = new GDUserStats(
                new GDUser(
                        4063664,
                        98006,
                        "Alex1304",
                        12,
                        9,
                        true,
                        Optional.of(29),
                        Optional.of(IconType.CUBE),
                        Optional.empty()
                ),
                3411,
                0,
                0,
                100,
                545,
                23,
                21,
                Optional.empty()
        );
        final var actual = client.searchUsers("Alex1304", 0).blockFirst();
        assertEquals(expected, actual);
    }

    @Test
    public void getSongInfoTest() {
        final var expected = new GDSong(
                844899,
                "~:Space soup:~",
                "lchavasse",
                Optional.of("8.79"),
                Optional.ofNullable(InternalUtils.urlDecode("https%3A%2F%2Faudio.ngfiles" +
                        ".com%2F844000%2F844899_Space-soup" +
                        ".mp3%3Ff1548488779"))
        );
        final var actual = client.getSongInfo(844899).block();
        assertEquals(expected, actual);
    }

    @Test
    public void downloadLevelTest() {
        final var actual = client.downloadLevel(10565740).block();
        assertNotNull(actual);
        final var expected = new GDLevelDownload(
                new GDLevel(
                        10565740,
                        "Bloodbath",
                        503085,
                        "Whose blood will be spilt in the Bloodbath? Who will the victors be? How many will " +
                                "survive? Good luck...",
                        Difficulty.INSANE,
                        DemonDifficulty.EXTREME,
                        10,
                        10330,
                        QualityRating.FEATURED,
                        26746554,
                        1506769,
                        Length.LONG,
                        0,
                        false,
                        3,
                        21,
                        24746,
                        true,
                        false,
                        Optional.of(7679228L),
                        0,
                        Optional.of(467339L),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                ),
                false,
                Optional.empty(),
                "5 years",
                "6 months",
                false,
                List.of(),
                List.of(),
                actual.data(),
                Optional.of(Duration.ofSeconds(582)),
                Optional.of(Duration.ZERO)
        );
        assertEquals(expected, actual);
    }

    @Test
    public void getDailyLevelInfoTest() {
        final var expected = new GDDailyInfo(1623, Duration.ofSeconds(27231));
        final var actual = client.getDailyLevelInfo().block();
        assertEquals(expected, actual);
    }

    @Test
    public void getWeeklyDemonInfoTest() {
        final var expected = new GDDailyInfo(194, Duration.ofSeconds(459229));
        final var actual = client.getWeeklyDemonInfo().block();
        assertEquals(expected, actual);
    }

    @Test
    public void getCommentsForLevelTest() {
        final var expectedFirstComment = new GDComment(
                52732574,
                Optional.of(new GDUser(
                        43568619,
                        7702228,
                        "iIKappali",
                        41,
                        3,
                        true,
                        Optional.of(46),
                        Optional.of(IconType.CUBE),
                        Optional.of(Role.USER)
                )),
                "i love my life ;)",
                44564,
                "3 years",
                Optional.of(99),
                Optional.empty()
        );
        final var expectedTop10Ids = List.of(52732574L, 7869561L, 53363972L, 52808092L, 22585688L, 34959218L, 27087361L,
                39618945L, 61020768L, 51507881L);
        final var actual = client.getCommentsForLevel(10565740, CommentSortMode.MOST_LIKED, 0, 40)
                .take(10)
                .collectList()
                .block();
        assertNotNull(actual);
        assertEquals(10, actual.size());
        assertEquals(expectedFirstComment, actual.get(0));
        assertEquals(expectedTop10Ids, actual.stream().map(GDComment::id).collect(Collectors.toList()));
        assertFalse(actual.get(1).color().isEmpty());
        assertEquals(0x4BFF4B, actual.get(1).color().orElseThrow());
    }

    @Test
    public void getPrivateMessagesTest() {
        final var expectedFirstMessage = new GDPrivateMessage(
                58947681,
                14414152,
                142565671,
                "Andresgiln78",
                "hellow",
                true,
                false,
                "1 day"
        );
        final var expectedTop10Ids = List.of(58947681L, 58941413L, 58929342L,
                58853706L, 58829707L, 58816845L, 58808850L, 58790947L, 58678031L, 58664382L);
        final var actual = authClient.getPrivateMessages(0).take(10).collectList().block();
        assertNotNull(actual);
        assertEquals(10, actual.size());
        assertEquals(expectedFirstMessage, actual.get(0));
        assertEquals(expectedTop10Ids, actual.stream().map(GDPrivateMessage::id).collect(Collectors.toList()));
    }

    @Test
    public void downloadPrivateMessageTest() {
        final var expected = new GDPrivateMessageDownload(
                new GDPrivateMessage(
                        58947681,
                        14414152,
                        142565671,
                        "Andresgiln78",
                        "hellow",
                        true,
                        false,
                        "1 day"
                ),
                "hello :)"
        );
        final var actual = authClient.downloadPrivateMessage(58947681).block();
        assertEquals(expected, actual);
    }

    @Test
    public void getBlockedUsersTest() {
        final var expectedFirstUser = new GDUser(
                132275057,
                13652339,
                "AzhirVerifier",
                12,
                13,
                false,
                Optional.of(13),
                Optional.of(IconType.SPIDER),
                Optional.empty()
        );
        final var expectedIds = List.of(132275057L, 125097769L, 35167443L, 39574573L, 55186340L, 19919817L);
        final var actual = authClient.getBlockedUsers().collectList().block();
        assertNotNull(actual);
        assertEquals(6, actual.size());
        assertEquals(expectedFirstUser, actual.get(0));
        assertEquals(expectedIds, actual.stream().map(GDUser::playerId).collect(Collectors.toList()));
    }

    @Test
    public void getLeaderboardTest() {
        final var actual = client.getLeaderboard(LeaderboardType.RELATIVE, 50).collectList().block();
        assertNotNull(actual);
        assertFalse(actual.get(0).leaderboardRank().isEmpty());
        assertEquals(12537, actual.get(0).leaderboardRank().orElseThrow());
    }
}
