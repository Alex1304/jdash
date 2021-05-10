package jdash.client;

import jdash.client.exception.MissingAccessException;
import jdash.common.*;
import jdash.common.entity.GDLevel;
import jdash.common.entity.ImmutableGDLevel;
import jdash.common.entity.ImmutableGDSong;
import jdash.common.entity.ImmutableGDUser;
import jdash.common.internal.InternalUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public final class GDClientTest {

    private GDCacheMockUp cache;
    private GDRouterMockUp router;
    private GDClient client;

    @BeforeEach
    public void setUp() {
        cache = new GDCacheMockUp();
        router = new GDRouterMockUp();
        client = GDClient.create()
                .withCache(cache)
                .withRouter(router);
    }

    @Test
    public void cacheTest() {
        assertTrue(cache.getMap().isEmpty()); // Ensure the cache is empty at first

        var response = client.getLevelById(10565740).block(); // Make a request
        assertEquals(1, router.getRequestCount()); // Check that it hit the router
        assertEquals(1, cache.getMap().size()); // Check that the response was added to cache

        // Check the object added to cache is the same as the returned response
        var cached = cache.getMap().values().stream().findAny().orElseThrow();
        assertEquals(List.of(response), cached);

        var response2 = client.getLevelById(10565740).block(); // Make the same request again
        assertEquals(1, router.getRequestCount()); // Check that it didn't hit the router (requestCount didn't
                                                            // increment). It means it properly hit the cache.
        assertEquals(response2, response); // Check the new response is consistent with the first one
    }

    @Test
    public void loginTest() {
        assertFalse(client.isAuthenticated());
        assertTrue(client.login("Alex1304", "F3keP4ssw0rd").block().isAuthenticated());
        assertThrows(MissingAccessException.class, client.login("Alex1304", "WrongPassword")::block);
    }

    @Test
    public void getLevelByIdTest() {
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
                .song(ImmutableGDSong.builder()
                        .songAuthorName("Dimrain47")
                        .songTitle("At the Speed of Light")
                        .songSize("9.56")
                        .downloadUrl(InternalUtils.urlDecode("http%3A%2F%2Faudio.ngfiles" +
                                ".com%2F467000%2F467339_At_the_Speed_of_Light_FINA.mp3"))
                        .id(467339)
                        .isCustom(true)
                        .build())
                .stars(10)
                .build();
        var actual = client.getLevelById(10565740).block();
        assertEquals(expected, actual);
    }

    @Test
    public void searchLevelsTest() {
        var expected = List.of(10565740L, 10792915L, 21761387L, 13615973L, 10578973L, 35717743L, 11797073L,
                38601659L, 19274064L, 10978435L);
        var actual = client.searchLevels("Bloodbath", LevelSearchFilter.create(), 0)
                .map(GDLevel::id)
                .collectList()
                .block();
        assertEquals(expected, actual);
    }

    @Test
    public void getUserByAccountIdTest() {
        var expected = ImmutableGDUser.builder()
                .commentHistoryPolicy(PrivacySetting.OPENED_TO_ALL)
                .privateMessagePolicy(PrivacySetting.OPENED_TO_ALL)
                .hasFriendRequestsEnabled(true)
                .role(Role.MODERATOR)
                .twitch("gd_alex1304")
                .twitter("gd_alex1304")
                .youtube("UC0hFAVN-GAbZYuf_Hfk1Iog")
                .deathEffectId(0)
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
        var actual = client.getUserByAccountId(98006).block();
        assertEquals(expected, actual);
    }

    @Test
    public void searchUsersTest() {
        var expected = ImmutableGDUser.builder()
                .hasGlowOutline(true)
                .accountId(98006)
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
}
