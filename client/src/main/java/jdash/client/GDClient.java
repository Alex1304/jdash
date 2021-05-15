package jdash.client;

import jdash.client.cache.GDCache;
import jdash.client.exception.ActionFailedException;
import jdash.client.exception.GDClientException;
import jdash.client.request.GDRequest;
import jdash.client.request.GDRouter;
import jdash.common.*;
import jdash.common.entity.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.Nullable;

import java.util.*;
import java.util.stream.Collectors;

import static jdash.client.request.GDRequests.*;
import static jdash.client.response.GDResponseDeserializers.*;
import static jdash.common.RobTopsWeakEncryption.*;
import static jdash.common.internal.InternalUtils.b64Encode;
import static jdash.common.internal.InternalUtils.randomString;
import static reactor.function.TupleUtils.function;

/**
 * Allows to request Geometry Dash data, such as levels, users, comments, private messages, etc. A {@link GDClient} is
 * immutable: when calling one of the <code>with*</code> methods, a new instance is created with the new properties.
 */
public final class GDClient {

    private final GDRouter router;
    private final GDCache cache;
    private final String uniqueDeviceId;
    private final AuthenticationInfo auth;
    private final Collection<Long> followedAccountIds;

    private GDClient(GDRouter router, GDCache cache, String uniqueDeviceId, AuthenticationInfo auth,
                     Collection<Long> followedAccountIds) {
        this.router = router;
        this.cache = cache;
        this.uniqueDeviceId = uniqueDeviceId;
        this.auth = auth;
        this.followedAccountIds = followedAccountIds;
    }

    /**
     * Creates a new {@link GDClient} with the {@link GDRouter#defaultRouter() default router}, a {@link
     * GDCache#disabled() disabled cache}, a {@link UUID#randomUUID() random UUID}, an empty set of followed account IDs
     * and without authentication. To customize it further, chain one or more
     * <code>with*</code> methods after this call.
     *
     * @return a new {@link GDClient}
     */
    public static GDClient create() {
        return new GDClient(GDRouter.defaultRouter(), GDCache.disabled(), UUID.randomUUID().toString(), null, Set.of());
    }

    private static Mono<Void> validatePositiveInteger(Mono<Integer> source) {
        return source.doOnNext(result -> {
            if (result < 0) {
                throw new ActionFailedException("" + result, "Action failed");
            }
        }).then();
    }

    private void requireAuthentication() {
        if (auth == null) {
            throw new IllegalStateException("Client must be authenticated to call this method");
        }
    }

    private Map<String, String> authParams() {
        return Map.of("accountID", "" + auth.accountId, "gjp", auth.gjp);
    }

    /**
     * Creates a new {@link GDClient} derived from this one, but with the specified router.
     *
     * @param router the router to set
     * @return a new {@link GDClient}
     */
    public GDClient withRouter(GDRouter router) {
        Objects.requireNonNull(router);
        return new GDClient(router, cache, uniqueDeviceId, auth, followedAccountIds);
    }

    /**
     * Creates a new {@link GDClient} derived from this one, but with the specified cache.
     *
     * @param cache the cache to set
     * @return a new {@link GDClient}
     */
    public GDClient withCache(GDCache cache) {
        Objects.requireNonNull(cache);
        return new GDClient(router, cache, uniqueDeviceId, auth, followedAccountIds);
    }

    /**
     * Creates a new {@link GDClient} derived from this one, but with the cache disabled. It is a shorthand for:
     * <pre>
     *     withCache(GDCache.disabled())
     * </pre>
     *
     * @return a new {@link GDClient}
     */
    public GDClient withCacheDisabled() {
        return new GDClient(router, GDCache.disabled(), uniqueDeviceId, auth, followedAccountIds);
    }

    /**
     * Creates a new {@link GDClient} derived from this one, but with the specified unique device ID. This ID is used by
     * some requests to uniquely identify the device used to play Geometry Dash.
     *
     * @param uniqueDeviceId the unique device ID to set
     * @return a new {@link GDClient}
     */
    public GDClient withUniqueDeviceId(String uniqueDeviceId) {
        Objects.requireNonNull(uniqueDeviceId);
        return new GDClient(router, cache, uniqueDeviceId, auth, followedAccountIds);
    }

    /**
     * Creates a new {@link GDClient} derived from this one, but with the specified authentication information. This
     * method allows to manually supply authentication details without calling {@link #login(String, String)}, which
     * means the credentials will not be validated. But it can be useful to get an authenticated client without having
     * to perform any request.
     *
     * @param playerId  the player ID
     * @param accountId the account ID
     * @param password  the account password (in plain text)
     * @return a new {@link GDClient}
     */
    public GDClient withAuthentication(long playerId, long accountId, String password) {
        Objects.requireNonNull(password);
        return new GDClient(router, cache, uniqueDeviceId, new AuthenticationInfo(playerId, accountId, password),
                followedAccountIds);
    }

    /**
     * Creates a new {@link GDClient} derived from this one, but with the specified collection of followed account IDs.
     * It will be used when browsing levels with {@link LevelBrowseMode#FOLLOWED} via {@link
     * #browseLevels(LevelBrowseMode, String, LevelSearchFilter, int)}.
     * <p>
     * This method makes a defensive copy of the given collection to guarantee the immutability of the resulting
     * client.
     *
     * @param followedAccountIds the followed account IDs to set
     * @return a new {@link GDClient}
     */
    public GDClient withFollowedAccountIds(Collection<Long> followedAccountIds) {
        Objects.requireNonNull(followedAccountIds);
        return new GDClient(router, cache, uniqueDeviceId, auth, Set.copyOf(followedAccountIds));
    }

    /**
     * Gets the router used by this client.
     *
     * @return the router
     */
    public GDRouter getRouter() {
        return router;
    }

    /**
     * Gets the cache used by this client.
     *
     * @return the cache
     */
    public GDCache getCache() {
        return cache;
    }

    /**
     * Gets the unique device ID used by this client.
     *
     * @return the unique device ID
     */
    public String getUniqueDeviceId() {
        return uniqueDeviceId;
    }

    /**
     * Gets whether this client is authenticated. It will be true if and only if the client comes from a previous call
     * of {@link #login(String, String)} or {@link #withAuthentication(long, long, String)}.
     *
     * @return whether the client is authenticated
     */
    public boolean isAuthenticated() {
        return auth != null;
    }

    /**
     * Gets the followed account IDs. The returned collection is unmodifiable.
     *
     * @return the followed account IDs
     */
    public Collection<Long> getFollowedAccountIds() {
        return followedAccountIds;
    }

    /**
     * Sends a login request to Geometry Dash servers with the given username and password. If successful, it will
     * return a new client instance carrying authentication details.
     *
     * @param username the username of the GD account
     * @param password the password of the GD account
     * @return a Mono emitting a new {@link GDClient} capable of executing requests requiring authentication. A {@link
     * GDClientException} will be emitted if an error occurs
     */
    public Mono<GDClient> login(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        return Mono.defer(() -> GDRequest.of(LOGIN_GJ_ACCOUNT)
                .addParameter("userName", username)
                .addParameter("password", password)
                .addParameter("udid", "jdash-client")
                .addParameter("secret", "Wmfv3899gc9") // Overrides the default one
                .execute(cache, router)
                .deserialize(loginResponse())
                .map(function((accountId, playerId) -> withAuthentication(playerId, accountId, password))));
    }

    /**
     * Finds a level by its ID. It is a shorthand for:
     * <pre>
     *     browseLevels(LevelBrowseMode.REGULAR, "" + levelId, null, 0).next()
     * </pre>
     *
     * @param levelId the level ID
     * @return a Mono emitting a {@link GDLevel} corresponding to the level found. A {@link GDClientException} will be
     * emitted if an error occurs.
     */
    public Mono<GDLevel> findLevelById(long levelId) {
        return browseLevels(LevelBrowseMode.REGULAR, "" + levelId, null, 0).next();
    }

    /**
     * Browses levels by a specific user.
     *
     * @param playerId the player ID of the user
     * @param page     the page to load, the first one being 0
     * @return a Flux emitting all {@link GDLevel}s found. A {@link GDClientException} will be emitted if an error
     * occurs.
     */
    public Flux<GDLevel> browseLevelsByUser(long playerId, int page) {
        return browseLevels(LevelBrowseMode.BY_USER, "" + playerId, null, page);
    }

    /**
     * Browses levels in Geometry Dash.
     *
     * @param mode   the browsing mode, which can impact how levels are sorted, or how the query is interpreted
     * @param query  if mode is {@link LevelBrowseMode#REGULAR}, represents the search query. If mode is {@link
     *               LevelBrowseMode#BY_USER}, represents the player ID of the user. If mode is any other mode, it will
     *               be ignored and can be set to <code>null</code>.
     * @param filter the search filter to apply
     * @param page   the page to load, the first one being 0
     * @return a Flux emitting all {@link GDLevel}s found. A {@link GDClientException} will be emitted if an error
     * occurs.
     */
    public Flux<GDLevel> browseLevels(LevelBrowseMode mode, @Nullable String query, @Nullable LevelSearchFilter filter,
                                      int page) {
        Objects.requireNonNull(mode);
        return Flux.defer(() -> {
            var request = GDRequest.of(GET_GJ_LEVELS_21)
                    .addParameters(commonParams())
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
                    .deserialize(levelSearchResponse())
                    .flatMapMany(Flux::fromIterable);
        });
    }

    public Mono<GDLevelDownload> downloadLevel(long levelId) {
        return Mono.defer(() -> GDRequest.of(DOWNLOAD_GJ_LEVEL_22)
                .addParameters(commonParams())
                .addParameter("levelID", levelId)
                .execute(cache, router)
                .deserialize(levelDownloadResponse()));
    }

    public Mono<GDLevelDownload> downloadDailyLevel() {
        return downloadLevel(-1);
    }

    public Mono<GDLevelDownload> downloadWeeklyDemon() {
        return downloadLevel(-2);
    }

    private Mono<GDTimelyInfo> getTimelyInfo(int weekly) {
        return Mono.defer(() -> GDRequest.of(GET_GJ_DAILY_LEVEL)
                .addParameters(commonParams())
                .addParameter("weekly", weekly)
                .execute(cache, router)
                .deserialize(timelyInfoResponse()));
    }

    public Mono<GDTimelyInfo> getDailyLevelInfo() {
        return getTimelyInfo(0);
    }

    public Mono<GDTimelyInfo> getWeeklyDemonInfo() {
        return getTimelyInfo(1);
    }

    public Mono<GDUserProfile> getUserProfile(long accountId) {
        return Mono.defer(() -> GDRequest.of(GET_GJ_USER_INFO_20)
                .addParameters(commonParams())
                .addParameter("targetAccountID", accountId)
                .execute(cache, router)
                .deserialize(userProfileResponse()));
    }

    public Flux<GDUserStats> searchUsers(String query, int page) {
        Objects.requireNonNull(query);
        return Flux.defer(() -> GDRequest.of(GET_GJ_USERS_20)
                .addParameters(commonParams())
                .addParameter("str", query)
                .addParameter("page", page)
                .execute(cache, router)
                .deserialize(userStatsListResponse())
                .flatMapMany(Flux::fromIterable));
    }

    public Mono<GDSong> getSongInfo(long songId) {
        return Mono.defer(() -> GDRequest.of(GET_GJ_SONG_INFO)
                .addParameter("songID", songId)
                .addParameter("secret", SECRET)
                .execute(cache, router)
                .deserialize(songInfoResponse()));
    }

    public Flux<GDComment> getCommentsForLevel(long levelId, CommentSortMode sorting, int page, int count) {
        Objects.requireNonNull(sorting);
        return Flux.defer(() -> GDRequest.of(GET_GJ_COMMENTS_21)
                .addParameters(commonParams())
                .addParameter("levelID", levelId)
                .addParameter("total", 0)
                .addParameter("count", count)
                .addParameter("page", page)
                .addParameter("mode", sorting.ordinal())
                .execute(cache, router)
                .deserialize(commentsResponse())
                .flatMapMany(Flux::fromIterable));
    }

    public Flux<GDUserStats> getLeaderboard(LeaderboardType type, int count) {
        Objects.requireNonNull(type);
        if (type == LeaderboardType.FRIENDS) {
            requireAuthentication();
        }
        return Flux.defer(() -> {
            var request = GDRequest.of(GET_GJ_SCORES_20);
            if (type == LeaderboardType.FRIENDS) {
                request.addParameters(authParams());
            }
            return request.addParameters(commonParams())
                    .addParameter("type", type.getValue())
                    .addParameter("count", count)
                    .execute(cache, router)
                    .deserialize(userStatsListResponse())
                    .flatMapMany(Flux::fromIterable)
                    .sort(Comparator.comparing(GDUserStats::leaderboardRank,
                            Comparator.comparingInt(Optional::orElseThrow)));
        });
    }

    public Flux<GDPrivateMessage> getPrivateMessages(int page) {
        requireAuthentication();
        return Flux.defer(() -> GDRequest.of(GET_GJ_MESSAGES_20)
                .addParameters(authParams())
                .addParameters(commonParams())
                .addParameter("page", page)
                .addParameter("total", 0)
                .execute(cache, router)
                .deserialize(privateMessagesResponse())
                .flatMapMany(Flux::fromIterable));
    }

    public Mono<GDPrivateMessageDownload> downloadPrivateMessage(int messageId) {
        requireAuthentication();
        return Mono.defer(() -> GDRequest.of(DOWNLOAD_GJ_MESSAGE_20)
                .addParameters(authParams())
                .addParameters(commonParams())
                .addParameter("messageID", messageId)
                .execute(cache, router)
                .deserialize(privateMessageDownloadResponse()));
    }

    public Mono<Void> sendPrivateMessage(long recipientAccountId, String subject, String body) {
        Objects.requireNonNull(subject);
        Objects.requireNonNull(body);
        requireAuthentication();
        return Mono.defer(() -> GDRequest.of(UPLOAD_GJ_MESSAGE_20)
                .addParameters(authParams())
                .addParameters(commonParams())
                .addParameter("toAccountID", recipientAccountId)
                .addParameter("subject", b64Encode(subject))
                .addParameter("body", encodePrivateMessageBody(body))
                .execute(cache, router)
                .deserialize(Integer::parseInt)
                .transform(GDClient::validatePositiveInteger));
    }

    public Mono<Void> rateLevelStars(long levelId, int stars) {
        requireAuthentication();
        return Mono.defer(() -> {
            var rs = randomString(10);
            return GDRequest.of(RATE_GJ_STARS_211)
                    .addParameters(authParams())
                    .addParameters(commonParams())
                    .addParameter("udid", uniqueDeviceId)
                    .addParameter("uuid", auth.playerId)
                    .addParameter("levelID", levelId)
                    .addParameter("stars", stars)
                    .addParameter("rs", rs)
                    .addParameter("chk", encodeChk(levelId, stars, rs, auth.accountId, uniqueDeviceId, auth.playerId,
                            "ysg6pUrtjn0J"))
                    .execute(cache, router)
                    .deserialize(Integer::parseInt)
                    .transform(GDClient::validatePositiveInteger);
        });
    }

    public Mono<Void> rateLevelDemonDifficulty(long levelId, DemonDifficulty demonDifficulty) {
        Objects.requireNonNull(demonDifficulty);
        requireAuthentication();
        return Mono.defer(() -> GDRequest.of(RATE_GJ_DEMON_21)
                .addParameters(authParams())
                .addParameters(commonParams())
                .addParameter("levelID", levelId)
                .addParameter("rating", demonDifficulty.ordinal() + 1)
                .addParameter("secret", "Wmfp3879gc3") // Overrides the default one
                .execute(cache, router)
                .deserialize(Integer::parseInt)
                .transform(GDClient::validatePositiveInteger));
    }

    public Flux<GDUser> getFriends() {
        return getUserList(0);
    }

    public Flux<GDUser> getBlockedUsers() {
        return getUserList(1);
    }

    private Flux<GDUser> getUserList(int type) {
        requireAuthentication();
        return Flux.defer(() -> GDRequest.of(GET_GJ_USER_LIST_20)
                .addParameters(authParams())
                .addParameters(commonParams())
                .addParameter("type", type)
                .execute(cache, router)
                .deserialize(userListResponse())
                .flatMapMany(Flux::fromIterable));
    }

    public Mono<Void> blockUser(long targetAccountId) {
        return blockUnblockRequest(targetAccountId, BLOCK_GJ_USER_20);
    }

    public Mono<Void> unblockUser(long targetAccountId) {
        return blockUnblockRequest(targetAccountId, UNBLOCK_GJ_USER_20);
    }

    private Mono<Void> blockUnblockRequest(long targetAccountId, String uri) {
        requireAuthentication();
        return Mono.defer(() -> GDRequest.of(uri)
                .addParameters(authParams())
                .addParameters(commonParams())
                .addParameter("targetAccountID", targetAccountId)
                .execute(cache, router)
                .deserialize(Integer::parseInt)
                .transform(GDClient::validatePositiveInteger));
    }

    private static class AuthenticationInfo {

        private final long playerId;
        private final long accountId;
        private final String gjp;

        private AuthenticationInfo(long playerId, long accountId, String password) {
            this.playerId = playerId;
            this.accountId = accountId;
            this.gjp = encodeAccountPassword(password);
        }
    }
}
