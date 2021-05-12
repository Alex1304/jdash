package jdash.client;

import jdash.client.cache.GDCache;
import jdash.client.request.GDRequest;
import jdash.client.request.GDRequests;
import jdash.client.request.GDRouter;
import jdash.client.response.GDResponseDeserializers;
import jdash.common.CommentSortMode;
import jdash.common.LevelBrowseMode;
import jdash.common.LevelSearchFilter;
import jdash.common.RobTopsWeakEncryption;
import jdash.common.entity.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static reactor.function.TupleUtils.function;

public final class GDClient {

    private static final GDRouter DEFAULT_ROUTER = null;
    private static final GDCache DEFAULT_CACHE = GDCache.disabled();

    private final GDRouter router;
    private final GDCache cache;
    private final AuthenticationInfo auth;
    private final Collection<Long> followedAccountIds;

    private GDClient(GDRouter router, GDCache cache, AuthenticationInfo auth, Collection<Long> followedAccountIds) {
        this.router = router;
        this.cache = cache;
        this.auth = auth;
        this.followedAccountIds = followedAccountIds;
    }

    public static GDClient create() {
        return new GDClient(DEFAULT_ROUTER, DEFAULT_CACHE, null, Set.of());
    }

    private Map<String, String> authParams() {
        if (auth == null) {
            throw new IllegalStateException("Client must be authenticated to call this method");
        }
        return Map.of("accountId", "" + auth.accountId, "gjp", auth.gjp);
    }

    public GDClient withRouter(GDRouter router) {
        return new GDClient(router, cache, auth, followedAccountIds);
    }

    public GDClient withCache(GDCache cache) {
        return new GDClient(router, cache, auth, followedAccountIds);
    }

    public GDClient withCacheDisabled() {
        return new GDClient(router, GDCache.disabled(), auth, followedAccountIds);
    }

    public GDClient withAuthentication(long accountId, String password) {
        return new GDClient(router, cache, new AuthenticationInfo(accountId, password), followedAccountIds);
    }

    public GDClient withFollowedAccountIds(Collection<Long> followedAccountIds) {
        return new GDClient(router, cache, auth, followedAccountIds);
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
        return GDRequest.of(GDRequests.LOGIN_GJ_ACCOUNT)
                .addParameter("userName", username)
                .addParameter("password", password)
                .addParameter("udid", "jdash-client")
                .addParameter("secret", "Wmfv3899gc9") // Overrides the default one
                .execute(cache, router)
                .deserialize(GDResponseDeserializers.loginResponse())
                .map(function((accountId, playerId) -> withAuthentication(accountId, password)));
    }

    public Mono<GDLevel> findLevelById(long levelId) {
        return browseLevels(LevelBrowseMode.REGULAR, "" + levelId, null, 0).next();
    }

    public Flux<GDLevel> findLevelsByUser(long playerId) {
        return browseLevels(LevelBrowseMode.BY_USER, "" + playerId, null, 0);
    }

    public Flux<GDLevel> browseLevels(LevelBrowseMode mode, @Nullable String query, @Nullable LevelSearchFilter filter,
                                      int page) {
        Objects.requireNonNull(mode);
        var request = GDRequest.of(GDRequests.GET_GJ_LEVELS_21)
                .addParameters(GDRequests.commonParams())
                .addParameters(Objects.requireNonNullElse(filter, LevelSearchFilter.create()).toMap())
                .addParameter("page", page)
                .addParameter("type", mode.getType())
                .addParameter("str", Objects.requireNonNullElse(query, ""));
        if (mode == LevelBrowseMode.FOLLOWED) {
            request.addParameter("followed", followedAccountIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")));
        }
        return request.execute(cache, router)
                .deserialize(GDResponseDeserializers.levelSearchResponse())
                .flatMapMany(Flux::fromIterable);
    }

    public Mono<GDLevelDownload> downloadLevel(long levelId) {
        return GDRequest.of(GDRequests.DOWNLOAD_GJ_LEVEL_22)
                .addParameters(GDRequests.commonParams())
                .addParameter("levelID", levelId)
                .execute(cache, router)
                .deserialize(GDResponseDeserializers.levelDownloadResponse());
    }

    public Mono<GDLevelDownload> downloadDailyLevel() {
        return downloadLevel(-1);
    }

    public Mono<GDLevelDownload> downloadWeeklyDemon() {
        return downloadLevel(-2);
    }

    private Mono<GDTimelyInfo> getTimelyInfo(int weekly) {
        return GDRequest.of(GDRequests.GET_GJ_DAILY_LEVEL)
                .addParameters(GDRequests.commonParams())
                .addParameter("weekly", weekly)
                .execute(cache, router)
                .deserialize(GDResponseDeserializers.timelyInfoResponse());
    }

    public Mono<GDTimelyInfo> getDailyLevelInfo() {
        return getTimelyInfo(0);
    }

    public Mono<GDTimelyInfo> getWeeklyDemonInfo() {
        return getTimelyInfo(1);
    }

    public Mono<GDUserProfile> getUserProfile(long accountId) {
        return GDRequest.of(GDRequests.GET_GJ_USER_INFO_20)
                .addParameters(GDRequests.commonParams())
                .addParameter("targetAccountID", accountId)
                .execute(cache, router)
                .deserialize(GDResponseDeserializers.userProfileResponse());
    }

    public Flux<GDUserStats> searchUsers(String query, int page) {
        Objects.requireNonNull(query);
        return GDRequest.of(GDRequests.GET_GJ_USERS_20)
                .addParameters(GDRequests.commonParams())
                .addParameter("str", query)
                .addParameter("page", page)
                .execute(cache, router)
                .deserialize(GDResponseDeserializers.userSearchResponse())
                .flatMapMany(Flux::fromIterable);
    }

    public Mono<GDSong> getSongInfo(long songId) {
        return GDRequest.of(GDRequests.GET_GJ_SONG_INFO)
                .addParameter("songID", songId)
                .addParameter("secret", GDRequests.SECRET)
                .execute(cache, router)
                .deserialize(GDResponseDeserializers.songInfoResponse());
    }

    public Flux<GDComment> getCommentsForLevel(long levelId, CommentSortMode sorting, int page, int count) {
        Objects.requireNonNull(sorting);
        return GDRequest.of(GDRequests.GET_GJ_COMMENTS_21)
                .addParameters(GDRequests.commonParams())
                .addParameter("levelID", levelId)
                .addParameter("total", 0)
                .addParameter("count", count)
                .addParameter("page", page)
                .addParameter("mode", sorting.ordinal())
                .execute(cache, router)
                .deserialize(GDResponseDeserializers.commentsResponse())
                .flatMapMany(Flux::fromIterable);
    }

    public Flux<GDMessage> getPrivateMessages(int page) {
        return GDRequest.of(GDRequests.GET_GJ_MESSAGES_20)
                .addParameters(authParams())
                .addParameters(GDRequests.commonParams())
                .addParameter("page", page)
                .execute(cache, router)
                .deserialize(GDResponseDeserializers.privateMessagesResponse())
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
