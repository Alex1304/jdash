package jdash.client;


import jdash.client.exception.MissingAccessException;
import jdash.client.request.GDRequest;
import jdash.client.request.GDRequests;
import jdash.client.request.GDRouter;
import jdash.common.CommentSortMode;
import jdash.common.LevelSearchFilter;
import jdash.common.LevelBrowseMode;
import jdash.common.RobTopsWeakEncryption;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class GDRouterMockUp implements GDRouter {

    private static final Map<String, String> AUTH_PARAMS = Map.of("accountID", "1", "gjp",
            RobTopsWeakEncryption.encodeGDAccountPassword("test"));

    private static final Map<GDRequest, String> SAMPLES = Map.ofEntries(
            Map.entry(GDRequest.of(GDRequests.GET_GJ_LEVELS_21)
                    .addParameters(GDRequests.commonParams())
                    .addParameters(LevelSearchFilter.create().toMap())
                    .addParameter("page", 0)
                    .addParameter("type", LevelBrowseMode.REGULAR.getType())
                    .addParameter("str", 10565740), "findLevelById"),
            Map.entry(GDRequest.of(GDRequests.GET_GJ_LEVELS_21)
                    .addParameters(GDRequests.commonParams())
                    .addParameters(LevelSearchFilter.create().toMap())
                    .addParameter("page", 0)
                    .addParameter("type", LevelBrowseMode.REGULAR.getType())
                    .addParameter("str", "Bloodbath"), "browseLevels"),
            Map.entry(GDRequest.of(GDRequests.LOGIN_GJ_ACCOUNT)
                    .addParameter("userName", "Alex1304")
                    .addParameter("password", "F3keP4ssw0rd")
                    .addParameter("udid", "jdash-client")
                    .addParameter("secret", "Wmfv3899gc9"), "login"),
            Map.entry(GDRequest.of(GDRequests.GET_GJ_USER_INFO_20)
                    .addParameters(GDRequests.commonParams())
                    .addParameter("targetAccountID", 98006), "getUserProfile"),
            Map.entry(GDRequest.of(GDRequests.GET_GJ_USERS_20)
                    .addParameters(GDRequests.commonParams())
                    .addParameter("str", "Alex1304")
                    .addParameter("page", 0), "searchUsers"),
            Map.entry(GDRequest.of(GDRequests.GET_GJ_SONG_INFO)
                    .addParameter("songID", 844899)
                    .addParameter("secret", GDRequests.SECRET), "getSongInfo"),
            Map.entry(GDRequest.of(GDRequests.DOWNLOAD_GJ_LEVEL_22)
                    .addParameters(GDRequests.commonParams())
                    .addParameter("levelID", 10565740), "downloadLevel"),
            Map.entry(GDRequest.of(GDRequests.GET_GJ_DAILY_LEVEL)
                    .addParameters(GDRequests.commonParams())
                    .addParameter("weekly", 0), "getDailyLevelInfo"),
            Map.entry(GDRequest.of(GDRequests.GET_GJ_DAILY_LEVEL)
                    .addParameters(GDRequests.commonParams())
                    .addParameter("weekly", 1), "getWeeklyDemonInfo"),
            Map.entry(GDRequest.of(GDRequests.GET_GJ_COMMENTS_21)
                    .addParameters(GDRequests.commonParams())
                    .addParameter("levelID", 10565740)
                    .addParameter("total", 0)
                    .addParameter("count", 40)
                    .addParameter("page", 0)
                    .addParameter("mode", CommentSortMode.MOST_LIKED.ordinal()), "getCommentsForLevel"),
            Map.entry(GDRequest.of(GDRequests.GET_GJ_MESSAGES_20)
                    .addParameters(AUTH_PARAMS)
                    .addParameters(GDRequests.commonParams())
                    .addParameter("page", 0)
                    .addParameter("total", 0), "getPrivateMessages"),
            Map.entry(GDRequest.of(GDRequests.DOWNLOAD_GJ_MESSAGE_20)
                    .addParameters(AUTH_PARAMS)
                    .addParameters(GDRequests.commonParams())
                    .addParameter("messageID", 58947681), "downloadPrivateMessage")
    );

    private int requestCount;

    @Override
    public Mono<String> send(GDRequest request) {
        requestCount++;
        var sample = SAMPLES.get(request);
        if (sample == null) {
            return Mono.error(new MissingAccessException());
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
