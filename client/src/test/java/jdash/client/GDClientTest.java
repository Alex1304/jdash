package jdash.client;

import jdash.client.exception.MissingAccessException;
import jdash.common.*;
import jdash.common.entity.*;
import jdash.common.internal.InternalUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public final class GDClientTest {

    private GDCacheMockUp cache;
    private GDRouterMockUp router;
    private GDClient client;
    private GDClient authClient;

    @BeforeEach
    public void setUp() {
        cache = new GDCacheMockUp();
        router = new GDRouterMockUp();
        client = GDClient.create()
                .withCache(cache)
                .withRouter(router);
        authClient = client.withAuthentication(1,1, "test");
    }

    @Test
    public void cacheTest() {
        assertTrue(cache.getMap().isEmpty()); // Ensure the cache is empty at first

        var response = client.findLevelById(10565740).block(); // Make a request
        assertNotNull(response);
        assertEquals(1, router.getRequestCount()); // Check that it hit the router
        assertEquals(1, cache.getMap().size()); // Check that the response was added to cache

        // Check the object added to cache is the same as the returned response
        var cached = cache.getMap().values().stream().findAny().orElseThrow();
        assertEquals(List.of(response), cached);

        var response2 = client.findLevelById(10565740).block(); // Make the same request again
        assertEquals(1, router.getRequestCount()); // Check that it didn't hit the router (requestCount didn't
                                                            // increment). It means it properly hit the cache.
        assertEquals(response2, response); // Check the new response is consistent with the first one

        cache.clear();
        assertTrue(cache.getMap().isEmpty()); // Ensure the cache cleared properly

        client.withCacheDisabled().findLevelById(10565740).block(); // Make the same request but with cache off
        assertEquals(2, router.getRequestCount()); // It should be hitting the router
        assertTrue(cache.getMap().isEmpty()); // The cache should still be empty
    }

    @Test
    public void loginTest() {
        assertFalse(client.isAuthenticated());
        var newClient = client.login("Alex1304", "F3keP4ssw0rd").block();
        assertNotNull(newClient);
        assertTrue(newClient.isAuthenticated());
        assertThrows(MissingAccessException.class, client.login("Alex1304", "WrongPassword")::block);
    }

    @Test
    public void findLevelByIdTest() {
        var expected = ImmutableGDLevel.builder()
                .coinCount(0)
                .creatorId(503085)
                .creatorName("Riot")
                .demonDifficulty(DemonDifficulty.EXTREME)
                .description("Whose blood will be spilt in the Bloodbath? Who will the victors be? How many will " +
                        "survive? Good luck...")
                .difficulty(Difficulty.INSANE)
                .downloads(26672952)
                .likes(1505455)
                .featuredScore(10330)
                .gameVersion(21)
                .hasCoinsVerified(false)
                .id(10565740)
                .isAuto(false)
                .isDemon(true)
                .isEpic(false)
                .length(Length.LONG)
                .levelVersion(3)
                .name("Bloodbath")
                .objectCount(24746)
                .originalLevelId(7679228)
                .requestedStars(0)
                .songId(467339)
                .song(ImmutableGDSong.builder()
                        .artist("Dimrain47")
                        .title("At the Speed of Light")
                        .size("9.56")
                        .downloadUrl(InternalUtils.urlDecode("http%3A%2F%2Faudio.ngfiles" +
                                ".com%2F467000%2F467339_At_the_Speed_of_Light_FINA.mp3"))
                        .id(467339)
                        .build())
                .stars(10)
                .build();
        var actual = client.findLevelById(10565740).block();
        assertEquals(expected, actual);
    }

    @Test
    public void browseLevelsTest() {
        var expected = List.of(10565740L, 10792915L, 21761387L, 13615973L, 10578973L, 35717743L, 11797073L,
                38601659L, 19274064L, 10978435L);
        var actual = client.browseLevels(LevelBrowseMode.REGULAR, "Bloodbath", null, 0)
                .map(GDLevel::id)
                .collectList()
                .block();
        assertEquals(expected, actual);
    }

    @Test
    public void getUserProfileTest() {
        var expected = ImmutableGDUserProfile.builder()
                .commentHistoryPolicy(PrivacySetting.OPENED_TO_ALL)
                .privateMessagePolicy(PrivacySetting.OPENED_TO_ALL)
                .hasFriendRequestsEnabled(true)
                .role(Role.MODERATOR)
                .twitch("gd_alex1304")
                .twitter("gd_alex1304")
                .youtube("UC0hFAVN-GAbZYuf_Hfk1Iog")
                .hasGlowOutline(true)
                .spiderIconId(15)
                .robotIconId(21)
                .waveIconId(24)
                .ballIconId(30)
                .accountId(98006)
                .ufoIconId(3)
                .shipIconId(7)
                .cubeIconId(29)
                .globalRank(33266)
                .diamonds(19336)
                .demons(46)
                .creatorPoints(21)
                .stars(5658)
                .userCoins(818)
                .color2Id(9)
                .color1Id(12)
                .secretCoins(100)
                .name("Alex1304")
                .playerId(4063664)
                .build();
        var actual = client.getUserProfile(98006).block();
        assertEquals(expected, actual);
    }

    @Test
    public void searchUsersTest() {
        var expected = ImmutableGDUserStats.builder()
                .hasGlowOutline(true)
                .accountId(98006)
                .diamonds(0)
                .demons(23)
                .creatorPoints(21)
                .stars(3411)
                .userCoins(545)
                .color2Id(9)
                .color1Id(12)
                .secretCoins(100)
                .name("Alex1304")
                .playerId(4063664)
                .mainIconId(29)
                .mainIconType(IconType.CUBE)
                .build();
        var actual = client.searchUsers("Alex1304", 0).blockFirst();
        assertEquals(expected, actual);
    }

    @Test
    public void getSongInfoTest() {
        var expected = ImmutableGDSong.builder()
                .id(844899)
                .title("~:Space soup:~")
                .artist("lchavasse")
                .size("8.79")
                .downloadUrl(InternalUtils.urlDecode("https%3A%2F%2Faudio.ngfiles.com%2F844000%2F844899_Space-soup" +
                        ".mp3%3Ff1548488779"))
                .build();
        var actual = client.getSongInfo(844899).block();
        assertEquals(expected, actual);
    }

    @Test
    public void downloadLevelTest() {
        var actual = client.downloadLevel(10565740).block();
        assertNotNull(actual);
        var expected = ImmutableGDLevelDownload.builder()
                .coinCount(0)
                .creatorId(503085)
                .demonDifficulty(DemonDifficulty.EXTREME)
                .description("Whose blood will be spilt in the Bloodbath? Who will the victors be? How many will " +
                        "survive? Good luck...")
                .difficulty(Difficulty.INSANE)
                .downloads(26746554)
                .likes(1506769)
                .featuredScore(10330)
                .gameVersion(21)
                .hasCoinsVerified(false)
                .id(10565740)
                .isAuto(false)
                .isDemon(true)
                .isEpic(false)
                .length(Length.LONG)
                .levelVersion(3)
                .name("Bloodbath")
                .objectCount(24746)
                .originalLevelId(7679228)
                .requestedStars(0)
                .songId(467339)
                .stars(10)
                .uploadedAgo("5 years")
                .updatedAgo("6 months")
                .isCopyable(false)
                .data(actual.data())
                .build();
        assertEquals(expected, actual);
    }

    @Test
    public void getDailyLevelInfoTest() {
        var expected = ImmutableGDTimelyInfo.builder()
                .number(1623)
                .nextIn(Duration.ofSeconds(27231))
                .build();
        var actual = client.getDailyLevelInfo().block();
        assertEquals(expected, actual);
    }

    @Test
    public void getWeeklyDemonInfoTest() {
        var expected = ImmutableGDTimelyInfo.builder()
                .number(194)
                .nextIn(Duration.ofSeconds(459229))
                .build();
        var actual = client.getWeeklyDemonInfo().block();
        assertEquals(expected, actual);
    }

    @Test
    public void getCommentsForLevelTest() {
        var expectedFirstComment = ImmutableGDComment.builder()
                .id(52732574)
                .author(ImmutableGDUser.builder()
                        .playerId(43568619)
                        .accountId(7702228)
                        .name("iIKappali")
                        .color1Id(41)
                        .color2Id(3)
                        .hasGlowOutline(true)
                        .role(Role.USER)
                        .mainIconId(46)
                        .mainIconType(IconType.CUBE)
                        .build())
                .content("i love my life ;)")
                .likes(44564)
                .postedAgo("3 years")
                .percentage(99)
                .build();
        var expectedTop10Ids = List.of(52732574L, 7869561L, 53363972L, 52808092L, 22585688L, 34959218L, 27087361L,
                39618945L, 61020768L, 51507881L);
        var actual = client.getCommentsForLevel(10565740, CommentSortMode.MOST_LIKED, 0, 40)
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
        var expectedFirstMessage = ImmutableGDPrivateMessage.builder()
                .id(58947681)
                .userAccountId(14414152)
                .userPlayerId(142565671)
                .subject("hellow")
                .userName("Andresgiln78")
                .sentAgo("1 day")
                .isUnread(true)
                .isSender(false)
                .build();
        var expectedTop10Ids = List.of(58947681L, 58941413L, 58929342L,
                58853706L, 58829707L, 58816845L, 58808850L, 58790947L, 58678031L, 58664382L);
        var actual = authClient.getPrivateMessages(0).take(10).collectList().block();
        assertNotNull(actual);
        assertEquals(10, actual.size());
        assertEquals(expectedFirstMessage, actual.get(0));
        assertEquals(expectedTop10Ids, actual.stream().map(GDPrivateMessage::id).collect(Collectors.toList()));
    }

    @Test
    public void downloadPrivateMessageTest() {
        var expected = ImmutableGDPrivateMessageDownload.builder()
                .id(58947681)
                .userAccountId(14414152)
                .userPlayerId(142565671)
                .subject("hellow")
                .userName("Andresgiln78")
                .sentAgo("1 day")
                .isUnread(true)
                .isSender(false)
                .body("hello :)")
                .build();
        var actual = authClient.downloadPrivateMessage(58947681).block();
        assertEquals(expected, actual);
    }

    @Test
    public void getBlockedUsersTest() {
        var expectedFirstUser = ImmutableGDUser.builder()
                .playerId(132275057)
                .accountId(13652339)
                .name("AzhirVerifier")
                .color1Id(12)
                .color2Id(13)
                .hasGlowOutline(false)
                .mainIconId(13)
                .mainIconType(IconType.SPIDER)
                .build();
        var expectedIds = List.of(132275057L, 125097769L, 35167443L, 39574573L, 55186340L, 19919817L);
        var actual = authClient.getBlockedUsers().collectList().block();
        assertNotNull(actual);
        assertEquals(6, actual.size());
        assertEquals(expectedFirstUser, actual.get(0));
        assertEquals(expectedIds, actual.stream().map(GDUser::playerId).collect(Collectors.toList()));
    }

    @Test
    public void getLeaderboardTest() {
        var actual = client.getLeaderboard(LeaderboardType.GLOBAL, 50).collectList().block();
        assertNotNull(actual);
        assertFalse(actual.get(0).leaderboardRank().isEmpty());
        assertEquals(12537, actual.get(0).leaderboardRank().orElseThrow());
    }
}
