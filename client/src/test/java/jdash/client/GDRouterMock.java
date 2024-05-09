package jdash.client;


import jdash.client.request.GDRequest;
import jdash.client.request.GDRouter;
import jdash.common.CommentSortMode;
import jdash.common.LeaderboardType;
import jdash.common.LevelSearchFilter;
import jdash.common.LevelSearchMode;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static jdash.client.request.GDRequests.*;
import static jdash.common.RobTopsWeakEncryption.encodeGjp2;

public final class GDRouterMock implements GDRouter {

    private static final Map<String, String> AUTH_PARAMS = Map.of("accountID", "1", "gjp2",
            encodeGjp2("test"));

    private static final Map<GDRequest, String> SAMPLES = Map.ofEntries(
            Map.entry(GDRequest.of(GET_GJ_LEVELS_21)
                    .addParameters(commonParams())
                    .addParameters(LevelSearchFilter.create().toMap())
                    .addParameter("page", 0)
                    .addParameter("type", LevelSearchMode.SEARCH.getType())
                    .addParameter("str", 10565740), "findLevelById"),
            Map.entry(GDRequest.of(GET_GJ_LEVELS_21)
                    .addParameters(commonParams())
                    .addParameters(LevelSearchFilter.create().toMap())
                    .addParameter("page", 0)
                    .addParameter("type", LevelSearchMode.SEARCH.getType())
                    .addParameter("str", "Bloodbath"), "searchLevels"),
            Map.entry(GDRequest.of(GET_GJ_LEVEL_LISTS)
                    .addParameters(commonParams())
                    .addParameters(LevelSearchFilter.create().toMap())
                    .addParameter("page", 0)
                    .addParameter("type", LevelSearchMode.AWARDED.getType())
                    .addParameter("str", ""), "searchLists"),
            Map.entry(GDRequest.of(LOGIN_GJ_ACCOUNT)
                    .addParameter("userName", "Alex1304")
                    .addParameter("gjp2", encodeGjp2("F3keP4ssw0rd"))
                    .addParameter("udid", "jdash-client")
                    .addParameter("secret", "Wmfv3899gc9"), "login"),
            Map.entry(GDRequest.of(GET_GJ_USER_INFO_20)
                    .addParameters(commonParams())
                    .addParameter("targetAccountID", 98006), "getUserProfile"),
            Map.entry(GDRequest.of(GET_GJ_USERS_20)
                    .addParameters(commonParams())
                    .addParameter("str", "Alex1304")
                    .addParameter("page", 0), "searchUsers"),
            Map.entry(GDRequest.of(GET_GJ_SONG_INFO)
                    .addParameter("songID", 844899)
                    .addParameter("secret", SECRET), "getSongInfo"),
            Map.entry(GDRequest.of(DOWNLOAD_GJ_LEVEL_22)
                    .addParameters(commonParams())
                    .addParameter("levelID", 10565740), "downloadLevel"),
            Map.entry(GDRequest.of(GET_GJ_DAILY_LEVEL)
                    .addParameters(commonParams())
                    .addParameter("weekly", 0), "getDailyLevelInfo"),
            Map.entry(GDRequest.of(GET_GJ_DAILY_LEVEL)
                    .addParameters(commonParams())
                    .addParameter("weekly", 1), "getWeeklyDemonInfo"),
            Map.entry(GDRequest.of(GET_GJ_COMMENTS_21)
                    .addParameters(commonParams())
                    .addParameter("levelID", 10565740)
                    .addParameter("total", 0)
                    .addParameter("count", 40)
                    .addParameter("page", 0)
                    .addParameter("mode", CommentSortMode.MOST_LIKED.ordinal()), "getCommentsForLevel"),
            Map.entry(GDRequest.of(GET_GJ_MESSAGES_20)
                    .addParameters(AUTH_PARAMS)
                    .addParameters(commonParams())
                    .addParameter("page", 0)
                    .addParameter("total", 0), "getPrivateMessages"),
            Map.entry(GDRequest.of(DOWNLOAD_GJ_MESSAGE_20)
                    .addParameters(AUTH_PARAMS)
                    .addParameters(commonParams())
                    .addParameter("messageID", 58947681), "downloadPrivateMessage"),
            Map.entry(GDRequest.of(GET_GJ_USER_LIST_20)
                    .addParameters(AUTH_PARAMS)
                    .addParameters(commonParams())
                    .addParameter("type", 1), "getBlockedUsers"),
            Map.entry(GDRequest.of(GET_GJ_SCORES_20)
                    .addParameters(commonParams())
                    .addParameter("type", LeaderboardType.RELATIVE.name().toLowerCase())
                    .addParameter("count", 50), "getLeaderboard")
    );

    private int requestCount;

    @Override
    public Mono<String> send(GDRequest request) {
        requestCount++;
        final var sample = SAMPLES.get(request);
        if (sample == null) {
            return Mono.error(new RuntimeException("No mockup found for request " + request));
        }
        return Mono.fromCallable(() -> {
            try (var in = ClassLoader.getSystemClassLoader().getResourceAsStream(sample + ".txt")) {
                if (in == null) {
                    throw new AssertionError("Missing resource: " + sample);
                }
                return StandardCharsets.UTF_8.decode(ByteBuffer.wrap(in.readAllBytes())).toString().strip();
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    public int getRequestCount() {
        return requestCount;
    }
}
