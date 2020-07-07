package com.github.alex1304.jdash.client;

import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import com.github.alex1304.jdash.entity.*;
import com.github.alex1304.jdash.entity.GDTimelyLevel.TimelyType;
import com.github.alex1304.jdash.exception.BadResponseException;
import com.github.alex1304.jdash.exception.CorruptedResponseContentException;
import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.exception.NoTimelyAvailableException;
import com.github.alex1304.jdash.exception.SongNotAllowedForUseException;
import com.github.alex1304.jdash.exception.UserSearchDataNotFoundException;
import com.github.alex1304.jdash.util.GDPaginator;
import com.github.alex1304.jdash.util.CommentSortMode;
import com.github.alex1304.jdash.util.LevelSearchFilters;
import com.github.alex1304.jdash.util.LevelSearchStrategy;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;
import reactor.retry.Retry;
import reactor.util.Logger;
import reactor.util.Loggers;

abstract class AbstractGDClient {
	private static final String GAME_VERSION = "21";
	private static final String BINARY_VERSION = "34";
	private static final String SECRET = "Wmfd2893gb7";
	
	private static final Logger LOGGER = Loggers.getLogger("jdash");
	
	private final Scheduler scheduler;
	private String host;
	private final HttpClient client;
	private final Cache<GDRequest<?>, Object> cache;
	private final Duration cacheTtl;
	private final Duration requestTimeout;

	AbstractGDClient(String host, Duration cacheTtl, Duration requestTimeout) {
		this.scheduler = Schedulers.boundedElastic();
		this.host = host;
		this.client = HttpClient.create()
				.baseUrl(host)
				.headers(h -> h.add("Content-Type", "application/x-www-form-urlencoded"));
		this.cache = Caffeine.newBuilder()
				.expireAfterAccess(cacheTtl)
				.build();
		this.cacheTtl = cacheTtl;
		this.requestTimeout = requestTimeout;
	}

	/**
	 * Sends the given request through the client and returns a Mono emtting the
	 * response object.
	 * 
	 * @param request the request object to send
	 * @return a Mono emitting the response object. If an error occurs when fetching
	 *         info to GD servers, it is emitted through the Mono.
	 */
	<E> Mono<E> fetch(GDRequest<E> request) {
		// Returning immediately if the value is cached already
		if (request.cacheable()) {
			@SuppressWarnings("unchecked")
			E cached = (E) cache.getIfPresent(request);
			if (cached != null) {
				return Mono.just(cached);
			}
		}
		// Building the request string
		StringJoiner sj = new StringJoiner("&");
		HashMap<String, String> params = new HashMap<>();
		params.put("gameVersion", GAME_VERSION);
		params.put("binaryVersion", BINARY_VERSION);
		params.put("gdw", "0");
		params.put("secret", SECRET);
		putExtraParams(params);
		params.putAll(request.getParams());
		params.forEach((k, v) -> sj.add(k + "=" + v));
		String requestStr = sj.toString();
		// Parsing, caching and returning the response
		return client.doAfterRequest((httpreq, connection) -> LOGGER.debug("Request sent: {}", request))
				.post()
				.uri(request.getPath())
				.send(ByteBufFlux.fromString(Flux.just(requestStr)))
				.responseSingle((response, content) -> {
					if (response.status().code() != 200) {
						return Mono.error(new BadResponseException(response));
					} else {
						return content.asString().defaultIfEmpty("");
					}
				})
				.publishOn(scheduler)
				.flatMap(responseStr -> {
					LOGGER.debug("Received response: {}", responseStr);
					try {
						E entity = request.parseResponse(responseStr.trim());
						if (entity == null) {
							return Mono.empty();
						}
						if (request.cacheable()) {
							cache.put(request, entity);
						}
						LOGGER.debug("Successfully parsed response into entity: {}", entity);
						return Mono.just(entity);
					} catch (GDClientException e) {
						return Mono.error(e);
					} catch (RuntimeException e) {
						return Mono.error(new CorruptedResponseContentException(e, request.getPath(), request.getParams(), responseStr));
					}
				})
				.retryWhen(Retry.anyOf(IOException.class)
						.exponentialBackoffWithJitter(Duration.ofMillis(100), Duration.ofSeconds(10))
						.doOnRetry(retryCtx -> {
								LOGGER.info("Retrying attempt {} in {}ms for failed request {} ({}: {})", retryCtx.iteration(),
										retryCtx.backoff().toMillis(), request, retryCtx.exception().getClass().getCanonicalName(), 
										retryCtx.exception().getMessage() == null ? "(no message)" : retryCtx.exception().getMessage());
								LOGGER.debug("I/O error occured", retryCtx.exception());
						}))
				.timeout(requestTimeout);
	}
	
	abstract void putExtraParams(Map<String, String> params);

	/**
	 * Gets a Geometry Dash user from their accountID
	 * 
	 * @param accountId the ID of the user's Geometry Dash account
	 * @return a Mono emitting the requested user, or an error if something goes
	 *         wrong (user not found, user has blocked the account that this client
	 *         is logged on, etc). If the user profile is found but was unable to retrieve
	 *         search result data for that user, a {@link UserSearchDataNotFoundException}
	 *         will be emitted.
	 */
	public Mono<GDUser> getUserByAccountId(long accountId) {
		if (accountId < 1) {
			return Mono.error(new IllegalArgumentException("Account ID must be greater than 0"));
		}
		return fetch(new GDUserProfileDataRequest(this, accountId))
				.flatMap(u1 -> fetch(new GDUserSearchDataRequest(this, "" + u1.getId(), 0))
						.map(u2l -> GDUser.aggregate(u1, u2l.asList().stream()
								.filter(usr -> usr.getAccountId() == u1.getAccountId())
								.findAny()
								.orElseThrow(() -> new UserSearchDataNotFoundException()))));
	}

	/**
	 * Submits a search query and returns the corresponding user.
	 * 
	 * @param searchQuery the user to search for
	 * @return a Mono emitting the user found, or an error if not found.
	 */
	public Mono<GDUser> searchUser(String searchQuery) {
		Objects.requireNonNull(searchQuery);
		return fetch(new GDUserSearchDataRequest(this, searchQuery, 0)).map(paginator -> paginator.asList().get(0))
				.flatMap(u2 -> fetch(new GDUserProfileDataRequest(this, u2.getAccountId()))
						.map(u1 -> GDUser.aggregate(u1, u2)));
	}

	/**
	 * Gets a Geometry Dash level from its ID.
	 * 
	 * @param levelId the ID of the level to get
	 * @return a Mono emitting the level with the corresponding ID, or an error if
	 *         not found or something went wrong.
	 */
	public Mono<GDLevel> getLevelById(long levelId) {
		if (levelId < 1) {
			return Mono.error(new IllegalArgumentException("Level ID must be greater than 0"));
		}
		return fetch(new GDLevelSearchRequest(this, "" + levelId, LevelSearchFilters.create(), 0)).map(list -> list.asList().get(0));
	}
	
	/**
	 * Gets information on a custom song from its ID.
	 * 
	 * @param songId the ID of the song
	 * @return a Mono emitting the song info, or an error if not found or something
	 *         went wrong. If the song is not allowed for use in Geometry Dash, the
	 *         error {@link SongNotAllowedForUseException} will be emitted.
	 */
	public Mono<GDSong> getSongById(long songId) {
		if (songId < 1) {
			return Mono.error(new IllegalArgumentException("Song ID must be greater than 0"));
		}
		return fetch(new GDSongInfoRequest(this, songId));
	}

	/**
	 * Gets the current Daily Level on Geometry Dash.
	 * 
	 * @return a Mono emitting the Daily level, or an error if not found or
	 *         something went wrong. If no Daily level is available, the error
	 *         {@link NoTimelyAvailableException} is emitted.
	 */
	public Mono<GDTimelyLevel> getDailyLevel() {
		return fetch(new GDTimelyRequest(this, TimelyType.DAILY));
	}

	/**
	 * Gets the current Weekly Demon on Geometry Dash.
	 * 
	 * @return a Mono emitting the Weekly demon, or an error if not found or
	 *         something went wrong. If no Weekly demon is available, the error
	 *         {@link NoTimelyAvailableException} is emitted.
	 */
	public Mono<GDTimelyLevel> getWeeklyDemon() {
		return fetch(new GDTimelyRequest(this, TimelyType.WEEKLY));
	}

	/**
	 * Search for levels in Geometry Dash.
	 * 
	 * @param query   the search query
	 * @param filters the search filters
	 * @param page    the page number
	 * @return a Mono emitting a paginator containing all levels found. Note that if
	 *         no levels are found, it will emit an error instead of just an empty
	 *         paginator. This is because the Geometry Dash API returns the same
	 *         response when the search produces no results and when an actual error
	 *         occurs while processing the request (blame RobTop for that!).
	 */
	public Mono<GDPaginator<GDLevel>> searchLevels(String query, LevelSearchFilters filters, int page) {
		return fetch(new GDLevelSearchRequest(this, query, filters, page));
	}

	/**
	 * Gets levels uploaded by a specific user in Geometry Dash.
	 * 
	 * @param user the user
	 * @param page the page number
	 * @return a Mono emitting a paginator containing all levels found. Note that if
	 *         no levels are found, it will emit an error instead of just an empty
	 *         paginator. This is because the Geometry Dash API returns the same
	 *         response when the search produces no results and when an actual error
	 *         occurs while processing the request (blame RobTop for that!).
	 */
	public Mono<GDPaginator<GDLevel>> getLevelsByUser(GDUser user, int page) {
		return fetch(new GDLevelSearchRequest(this, user, page));
	}
	
	private Mono<GDPaginator<GDLevel>> browseSection(LevelSearchStrategy strategy, LevelSearchFilters filters, int page) {
		return fetch(new GDLevelSearchRequest(this, strategy, filters, page));
	}

	/**
	 * Browse levels in the Awarded section of Geometry Dash.
	 * 
	 * @param filters the search filters
	 * @param page    the page number
	 * @return a Mono emitting a paginator containing all levels found. Note that if
	 *         no levels are found, it will emit an error instead of just an empty
	 *         paginator. This is because the Geometry Dash API returns the same
	 *         response when the search produces no results and when an actual error
	 *         occurs while processing the request (blame RobTop for that!).
	 */
	public Mono<GDPaginator<GDLevel>> browseAwardedLevels(LevelSearchFilters filters, int page) {
		return browseSection(LevelSearchStrategy.AWARDED, filters, page);
	}

	/**
	 * Browse levels in the Recent section of Geometry Dash.
	 * 
	 * @param filters the search filters
	 * @param page    the page number
	 * @return a Mono emitting a paginator containing all levels found. Note that if
	 *         no levels are found, it will emit an error instead of just an empty
	 *         paginator. This is because the Geometry Dash API returns the same
	 *         response when the search produces no results and when an actual error
	 *         occurs while processing the request (blame RobTop for that!).
	 */
	public Mono<GDPaginator<GDLevel>> browseRecentLevels(LevelSearchFilters filters, int page) {
		return browseSection(LevelSearchStrategy.RECENT, filters, page);
	}

	/**
	 * Browse levels in the Featured section of Geometry Dash.
	 * 
	 * @param page    the page number
	 * @return a Mono emitting a paginator containing all levels found. Note that if
	 *         no levels are found, it will emit an error instead of just an empty
	 *         paginator. This is because the Geometry Dash API returns the same
	 *         response when the search produces no results and when an actual error
	 *         occurs while processing the request (blame RobTop for that!).
	 */
	public Mono<GDPaginator<GDLevel>> browseFeaturedLevels(int page) {
		return browseSection(LevelSearchStrategy.FEATURED, LevelSearchFilters.create(), page);
	}

	/**
	 * Browse levels in the Hall of Fame section of Geometry Dash.
	 * 
	 * @param page    the page number
	 * @return a Mono emitting a paginator containing all levels found. Note that if
	 *         no levels are found, it will emit an error instead of just an empty
	 *         paginator. This is because the Geometry Dash API returns the same
	 *         response when the search produces no results and when an actual error
	 *         occurs while processing the request (blame RobTop for that!).
	 */
	public Mono<GDPaginator<GDLevel>> browseHallOfFameLevels(int page) {
		return browseSection(LevelSearchStrategy.HALL_OF_FAME, LevelSearchFilters.create(), page);
	}

	/**
	 * Browse levels in the Most Downloaded section of Geometry Dash.
	 * 
	 * @param filters the search filters
	 * @param page    the page number
	 * @return a Mono emitting a paginator containing all levels found. Note that if
	 *         no levels are found, it will emit an error instead of just an empty
	 *         paginator. This is because the Geometry Dash API returns the same
	 *         response when the search produces no results and when an actual error
	 *         occurs while processing the request (blame RobTop for that!).
	 */
	public Mono<GDPaginator<GDLevel>> browseMostDownloadedLevels(LevelSearchFilters filters, int page) {
		return browseSection(LevelSearchStrategy.MOST_DOWNLOADED, filters, page);
	}

	/**
	 * Browse levels in the Most Liked section of Geometry Dash.
	 * 
	 * @param filters the search filters
	 * @param page    the page number
	 * @return a Mono emitting a paginator containing all levels found. Note that if
	 *         no levels are found, it will emit an error instead of just an empty
	 *         paginator. This is because the Geometry Dash API returns the same
	 *         response when the search produces no results and when an actual error
	 *         occurs while processing the request (blame RobTop for that!).
	 */
	public Mono<GDPaginator<GDLevel>> browseMostLikedLevels(LevelSearchFilters filters, int page) {
		return browseSection(LevelSearchStrategy.MOST_LIKED, filters, page);
	}

	/**
	 * Browse levels in the Trending section of Geometry Dash.
	 * 
	 * @param filters the search filters
	 * @param page    the page number
	 * @return a Mono emitting a paginator containing all levels found. Note that if
	 *         no levels are found, it will emit an error instead of just an empty
	 *         paginator. This is because the Geometry Dash API returns the same
	 *         response when the search produces no results and when an actual error
	 *         occurs while processing the request (blame RobTop for that!).
	 */
	public Mono<GDPaginator<GDLevel>> browseTrendingLevels(LevelSearchFilters filters, int page) {
		return browseSection(LevelSearchStrategy.TRENDING, filters, page);
	}

	/**
	 * Browse levels in the Magic section of Geometry Dash.
	 * 
	 * @param filters the search filters
	 * @param page    the page number
	 * @return a Mono emitting a paginator containing all levels found. Note that if
	 *         no levels are found, it will emit an error instead of just an empty
	 *         paginator. This is because the Geometry Dash API returns the same
	 *         response when the search produces no results and when an actual error
	 *         occurs while processing the request (blame RobTop for that!).
	 */
	public Mono<GDPaginator<GDLevel>> browseMagicLevels(LevelSearchFilters filters, int page) {
		return browseSection(LevelSearchStrategy.MAGIC, filters, page);
	}

	/**
	 * Browse levels in the Followed section of Geometry Dash.
	 * 
	 * @param filters  the search filters
	 * @param followed the collection containing the instances of followed users
	 * @param page     the page number
	 * @return a Mono emitting a paginator containing all levels found. Note that if
	 *         no levels are found, it will emit an error instead of just an empty
	 *         paginator. This is because the Geometry Dash API returns the same
	 *         response when the search produces no results and when an actual error
	 *         occurs while processing the request (blame RobTop for that!).
	 * 
	 * @deprecated This requires that all GDUser instances must have been fetched
	 *             beforehand, while account IDs are technically sufficient to
	 *             perform this request. Please use browseFollowedIds(...) instead.
	 */
	@Deprecated
	public Mono<GDPaginator<GDLevel>> browseFollowedLevels(LevelSearchFilters filters, Collection<? extends GDUser> followed, int page) {
		return browseFollowedIds(filters, followed.stream().map(GDUser::getAccountId).collect(Collectors.toSet()), page);
	}

	/**
	 * Browse levels in the Followed section of Geometry Dash.
	 * 
	 * @param filters            the search filters
	 * @param followedAccountIDs the collection containing the account IDs of
	 *                           followed users
	 * @param page               the page number
	 * @return a Mono emitting a paginator containing all levels found. Note that if
	 *         no levels are found, it will emit an error instead of just an empty
	 *         paginator. This is because the Geometry Dash API returns the same
	 *         response when the search produces no results and when an actual error
	 *         occurs while processing the request (blame RobTop for that!).
	 */
	public Mono<GDPaginator<GDLevel>> browseFollowedIds(LevelSearchFilters filters, Collection<? extends Long> followedAccountIDs, int page) {
		return fetch(new GDLevelSearchRequest(this, filters, followedAccountIDs, page));
	}

	/**
	 * Gets comments of specific level in Geometry dash
	 *
	 * @param levelId the level ID to get comments
	 * @param mode the mode to sort comments
	 * @param page the page number
	 * @return a Mono emitting a paginator containing all comments in level. Note that if
	 *         no comments are found, it will emit an empty paginator. (In this case,
	 *         fortunately, Geometry Dash API does not return the same response
	 *         when an actual error occurs while processing the request).
	 */
	public Mono<GDPaginator<GDComment>> getCommentsForLevel(long levelId, CommentSortMode mode, int page){
		return fetch(new GDLevelCommentsRequest(this, levelId, mode, page));
	}

	/**
	 * Gets data from a user provided by their profile. Unlike
	 * {@link #getUserByAccountId(long)}, this doesn't include some data that is
	 * only obtainable by using {@link #getUserDataFromSearch(String)}
	 * 
	 * @param accountId the account ID of the user to fetch
	 * @return a Mono emitting the data found
	 */
	public Mono<GDUserProfileData> getUserDataFromProfile(long accountId) {
		return fetch(new GDUserProfileDataRequest(this, accountId));
	}
	
	/**
	 * Gets data from a user provided by the Search user feature. The data gotten
	 * from there might be inaccurate and severely outdated, that's why it is
	 * preferred to use {@link #getUserDataFromProfile(long)} in order to fetch
	 * stats.
	 * 
	 * @param searchQuery the search query
	 * @return a Mono emitting the data found
	 */
	public Mono<GDUserSearchData> getUserDataFromSearch(String searchQuery) {
		return fetch(new GDUserSearchDataRequest(this, searchQuery, 0)).map(paginator -> paginator.asList().get(0));
	}

	/**
	 * Gets comments of specific user in Geometry dash
	 * @param accountId this account ID to get comments
	 * @param page the page number
	 * @return a Mono emitting a paginator containing all comments in user. Note that if
	 *         no comments are found, it will emit an empty paginator. (In this case,
	 *         fortunately, Geometry Dash API does not return the same response
	 *         when an actual error occurs while processing the request).
	 */
	public Mono<GDPaginator<GDComment>> getCommentsForUser(long accountId, int page){
		return fetch(new GDUserCommentsRequest(this, accountId, page));
	}
	
	/**
	 * Gets the host.
	 *
	 * @return String
	 */
	public String getHost() {
		return host;
	}
	
	/**
	 * Gets the time to live of a cache entry in milliseconds.
	 * 
	 * @return long
	 */
	public Duration getCacheTtl() {
		return cacheTtl;
	}
	
	/**
	 * Gets the maximum number of simultaneous connections.
	 * 
	 * @return int
	 * 
	 * @deprecated limiting max connections is no longer supported. This method will always return -1
	 */
	@Deprecated
	public int getMaxConnections() {
		return -1;
	}
	
	/**
	 * Gets the maximum time in milliseconds to wait if a request takes too long
	 * to complete.
	 * 
	 * @return long
	 */
	public Duration getRequestTimeout() {
		return requestTimeout;
	}

	/**
	 * Clears the cache of previous requests.
	 */
	public void clearCache() {
		cache.invalidateAll();
	}
}
