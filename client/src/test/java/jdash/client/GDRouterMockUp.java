package jdash.client;


import jdash.client.exception.MissingAccessException;
import jdash.client.request.GDRequest;
import jdash.client.request.GDRequests;
import jdash.client.request.GDRouter;
import jdash.common.LevelSearchFilter;
import jdash.common.LevelSearchType;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class GDRouterMockUp implements GDRouter {

    private static final Map<GDRequest, String> SAMPLES = Map.ofEntries(
            Map.entry(GDRequest.of(GDRequests.LEVEL_SEARCH)
                    .addParameters(GDRequests.commonParams())
                    .addParameters(LevelSearchFilter.create().toMap())
                    .addParameter("page", 0)
                    .addParameter("type", LevelSearchType.REGULAR.getVal())
                    .addParameter("str", 10565740), "getLevelById"),
            Map.entry(GDRequest.of(GDRequests.LEVEL_SEARCH)
                    .addParameters(GDRequests.commonParams())
                    .addParameters(LevelSearchFilter.create().toMap())
                    .addParameter("page", 0)
                    .addParameter("type", LevelSearchType.REGULAR.getVal())
                    .addParameter("str", "Bloodbath"), "searchLevels"),
            Map.entry(GDRequest.of(GDRequests.LOGIN)
                    .addParameter("userName", "Alex1304")
                    .addParameter("password", "F3keP4ssw0rd")
                    .addParameter("udid", "jdash-client")
                    .addParameter("secret", "Wmfv3899gc9"), "login"),
            Map.entry(GDRequest.of(GDRequests.GET_USER_INFO)
                    .addParameters(GDRequests.commonParams())
                    .addParameter("targetAccountID", 98006), "getUserByAccountId"),
            Map.entry(GDRequest.of(GDRequests.USER_SEARCH)
                    .addParameters(GDRequests.commonParams())
                    .addParameter("str", "Alex1304")
                    .addParameter("page", 0), "searchUsers")
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
                return StandardCharsets.UTF_8.decode(ByteBuffer.wrap(in.readAllBytes())).toString();
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    public int getRequestCount() {
        return requestCount;
    }
}
