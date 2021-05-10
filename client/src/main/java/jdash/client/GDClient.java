package jdash.client;

import jdash.client.cache.GDCache;
import jdash.client.request.GDRequest;
import jdash.client.request.GDRequests;
import jdash.client.request.GDRouter;
import jdash.client.response.GDResponseDeserializers;
import jdash.common.LevelSearchFilter;
import jdash.common.LevelSearchType;
import jdash.common.RobTopsWeakEncryption;
import jdash.common.entity.GDLevel;
import jdash.common.entity.GDUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.function.Predicate.not;
import static reactor.function.TupleUtils.function;

public final class GDClient {

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

    /**
     * Sends a login request to Geometry Dash servers with the given username and password. If successful, it will
     * return a new client instance carrying authentication details.
     *
     * @param username the username of the GD account
     * @param password the password of the GD account
     * @return a Mono emitting a new {@link GDClient} capable of executing requests requiring authentication. If an
     * error occurs, it will be emitted through the Mono.
     */
    public Mono<GDClient> login(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        return GDRequest.of(GDRequests.LOGIN)
                .addParameter("userName", username)
                .addParameter("password", password)
                .addParameter("udid", "jdash-client")
                .addParameter("secret", "Wmfv3899gc9") // Overrides the default one
                .execute(cache, router)
                .deserialize(GDResponseDeserializers.loginResponse())
                .map(function((accountId, playerId) -> withAuthentication(accountId, password)));
    }

    public Mono<GDLevel> getLevelById(long levelId) {
        return GDRequest.of(GDRequests.LEVEL_SEARCH)
                .addParameters(GDRequests.commonParams())
                .addParameters(LevelSearchFilter.create().toMap())
                .addParameter("page", 0)
                .addParameter("type", LevelSearchType.REGULAR.getVal())
                .addParameter("str", levelId)
                .execute(cache, router)
                .deserialize(GDResponseDeserializers.levelSearchResponse())
                .filter(not(List::isEmpty))
                .map(list -> list.get(0));
    }

    public Flux<GDLevel> searchLevels(String query, LevelSearchFilter filter, int page) {
        Objects.requireNonNull(query);
        Objects.requireNonNull(filter);
        return GDRequest.of(GDRequests.LEVEL_SEARCH)
                .addParameters(GDRequests.commonParams())
                .addParameters(filter.toMap())
                .addParameter("page", page)
                .addParameter("type", LevelSearchType.REGULAR.getVal())
                .addParameter("str", query)
                .execute(cache, router)
                .deserialize(GDResponseDeserializers.levelSearchResponse())
                .flatMapMany(Flux::fromIterable);
    }

    public Mono<GDUser> getUserByAccountId(long accountId) {
        return GDRequest.of(GDRequests.GET_USER_INFO)
                .addParameters(GDRequests.commonParams())
                .addParameter("targetAccountID", accountId)
                .execute(cache, router)
                .deserialize(GDResponseDeserializers.userProfileResponse());
    }

    public Flux<GDUser> searchUsers(String query, int page) {
        Objects.requireNonNull(query);
        return GDRequest.of(GDRequests.USER_SEARCH)
                .addParameters(GDRequests.commonParams())
                .addParameter("str", query)
                .addParameter("page", page)
                .execute(cache, router)
                .deserialize(GDResponseDeserializers.userSearchResponse())
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
