package jdash.client;

import jdash.client.cache.GDCache;
import jdash.common.LevelSearchFilter;
import jdash.common.LevelSearchType;
import jdash.common.RobTopsWeakEncryption;
import jdash.common.entity.GDLevel;
import jdash.common.internal.LevelSearchResultDeserializer;
import jdash.client.request.GDRequest;
import jdash.client.request.GDRouter;
import jdash.client.request.GDRequests;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static java.util.function.Predicate.not;

public class GDClient {

    private static final GDRouter DEFAULT_ROUTER = null;
    private static final GDCache DEFAULT_CACHE = GDCache.disabled();

    private final GDRouter router;
    private final GDCache cache;
    private final AuthenticationInfo auth;

    private GDClient(GDRouter router, GDCache cache, AuthenticationInfo auth) {
        this.router = router;
        this.cache = cache;
        this.auth = auth;
    }

    public static GDClient create() {
        return new GDClient(DEFAULT_ROUTER, DEFAULT_CACHE, null);
    }

    private Map<String, String> authParams() {
        if (auth == null) {
            return Map.of();
        }
        return Map.of("accountId", "" + auth.accountId, "gjp", auth.gjp);
    }

    public GDClient withRouter(GDRouter router) {
        return new GDClient(router, cache, auth);
    }

    public GDClient withCache(GDCache cache) {
        return new GDClient(router, cache, auth);
    }

    public GDClient withCacheDisabled() {
        return new GDClient(router, GDCache.disabled(), auth);
    }

    public GDClient withAuthentication(long accountId, String password) {
        return new GDClient(router, cache, new AuthenticationInfo(accountId, password));
    }

    public GDRouter getRouter() {
        return router;
    }

    public GDCache getCache() {
        return cache;
    }

    public boolean isAuthenticated() {
        return auth != null;
    }

    public Mono<GDLevel> getLevelById(long levelId) {
        return GDRequest.of(GDRequests.LEVEL_SEARCH)
                .addParameters(GDRequests.commonParams())
                .addParameters(LevelSearchFilter.create().toParams())
                .addParameter("page", 0)
                .addParameter("type", LevelSearchType.REGULAR.getVal())
                .addParameter("str", levelId)
                .execute(cache, router)
                .deserialize(new LevelSearchResultDeserializer())
                .filter(not(List::isEmpty))
                .map(list -> list.get(0));
    }

    public Flux<GDLevel> searchLevels(String query, LevelSearchFilter filter, int page) {
        return GDRequest.of(GDRequests.LEVEL_SEARCH)
                .addParameters(GDRequests.commonParams())
                .addParameters(filter.toParams())
                .addParameter("page", page)
                .addParameter("type", LevelSearchType.REGULAR.getVal())
                .addParameter("str", query)
                .execute(cache, router)
                .deserialize(new LevelSearchResultDeserializer())
                .flatMapMany(Flux::fromIterable);
    }

    private static class AuthenticationInfo {

        private final long accountId;
        private final String gjp;

        private AuthenticationInfo(long accountId, String password) {
            this.accountId = accountId;
            this.gjp = RobTopsWeakEncryption.encodeGDAccountPassword(password);
        }
    }
}
