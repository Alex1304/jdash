package com.github.alex1304.jdash.client;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

import com.github.alex1304.jdash.entity.GDLevel;
import com.github.alex1304.jdash.entity.GDTimelyLevel;
import com.github.alex1304.jdash.entity.GDTimelyLevel.TimelyType;
import com.github.alex1304.jdash.entity.GDUser;
import com.github.alex1304.jdash.entity.GDUserProfileData;
import com.github.alex1304.jdash.entity.GDUserSearchData;
import com.github.alex1304.jdash.exception.BadResponseException;
import com.github.alex1304.jdash.exception.CorruptedResponseContentException;
import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.exception.NoTimelyAvailableException;
import com.github.alex1304.jdash.util.GDPaginator;
import com.github.alex1304.jdash.util.LevelSearchFilters;
import com.github.alex1304.jdash.util.LevelSearchStrategy;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.PrematureCloseException;
import reactor.netty.resources.ConnectionProvider;

abstract class AbstractGDClient {
	private static final String GAME_VERSION = "21";
	private static final String BINARY_VERSION = "34";
	private static final String SECRET = "Wmfd2893gb7";

	private String host;
	private int maxConnections;
	private final HttpClient client;
	private final Map<GDRequest<?>, Object> cache;
	private final Map<GDRequest<?>, Long> cacheTime;
	private final long cacheTtl;

	AbstractGDClient(String host, long cacheTtl, int maxConnections) {
		this.host = host;
		this.maxConnections = maxConnections;
		this.client = HttpClient.create(ConnectionProvider.fixed("gd-connection-pool", maxConnections))
				.headers(h -> h.add("Content-Type", "application/x-www-form-urlencoded"));
		this.cache = new ConcurrentHashMap<>();
		this.cacheTime = new ConcurrentHashMap<>();
		this.cacheTtl = cacheTtl;
		cleanUpExpiredCacheEntries();
	}

	/**
	 * Sends the given request through the client and returns a Mono emtting the
	 * response object.
	 * 
	 * @param request the request object to send
	 * @return a Mono emtting the response object. If an error occurs when fetching
	 *         info to GD servers, it is emitted through the Mono.
	 */
	<E> Mono<E> fetch(GDRequest<E> request) {
		// Returning immediately if the value is cached already
		if (request.cacheable()) {
			@SuppressWarnings("unchecked")
			E cached = (E) cache.get(request);
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
		return client.baseUrl(host).post().uri(request.getPath())
				.send(ByteBufFlux.fromString(Flux.just(requestStr)))
				.responseSingle((response, content) -> {
					if (response.status().code() != 200) {
						return Mono.error(new BadResponseException(response));
					} else {
						return content.asString().defaultIfEmpty("");
					}
				})
				.retry(PrematureCloseException.class::isInstance)
				.flatMap(responseStr -> {
					try {
						E entity = request.parseResponse(responseStr.trim());
						if (entity == null) {
							return Mono.empty();
						}
						if (request.cacheable()) {
							cache.put(request, entity);
							cacheTime.put(request, System.currentTimeMillis());
						}
						return Mono.just(entity);
					} catch (GDClientException e) {
						return Mono.error(e);
					} catch (RuntimeException e) {
						return Mono.error(new CorruptedResponseContentException(e));
					}
				});
	}
	
	abstract void putExtraParams(Map<String, String> params);

	private void cleanUpExpiredCacheEntries() {
		Flux.interval(Duration.ofMillis(cacheTtl))
				.map(__ -> System.currentTimeMillis())
				.doOnNext(now -> {
					new HashMap<>(cacheTime).forEach((k, v) -> {
						if (now - v < cacheTtl) {
							cacheTime.remove(k);
							cache.remove(k);
						}
					});
				})
				.subscribe();
	}

	/**
	 * Gets a Geometry Dash user from their accountID
	 * 
	 * @param accountId the ID of the user's Geometry Dash account
	 * @return a Mono emitting the requested user, or an error if something goes
	 *         wrong (user not found, user has blocked the account that this client
	 *         is logged on, etc)
	 */
	public Mono<GDUser> getUserByAccountId(long accountId) {
		if (accountId < 1) {
			throw new IllegalArgumentException("Account ID must be greater than 0");
		}
		return fetch(new GDUserProfileDataRequest(this, accountId))
				.flatMap(u1 -> fetch(new GDUserSearchDataRequest(this, "" + u1.getId(), 0))
						.map(u2l -> GDUser.aggregate(u1, u2l.asList().get(0))));
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
			throw new IllegalArgumentException("Level ID must be greater than 0");
		}
		return fetch(new GDLevelSearchRequest(this, "" + levelId, LevelSearchFilters.create(), 0)).map(list -> list.asList().get(0));
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
	 * @param filters the search filters
	 * @param followed the collection containing followed users
	 * @param page    the page number
	 * @return a Mono emitting a paginator containing all levels found. Note that if
	 *         no levels are found, it will emit an error instead of just an empty
	 *         paginator. This is because the Geometry Dash API returns the same
	 *         response when the search produces no results and when an actual error
	 *         occurs while processing the request (blame RobTop for that!).
	 */
	public Mono<GDPaginator<GDLevel>> browseFollowedLevels(LevelSearchFilters filters, Collection<? extends GDUser> followed, int page) {
		return fetch(new GDLevelSearchRequest(this, filters, followed, page));
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
	 * Gets data from a user provided by the Search user feature.
	 * The data gotten from there might be inaccurate and severely outdated, that's why it is preferred to use 
	 * {@link #getUserDataFromProfile(long)} in order to fetch stats.
	 * 
	 * @param searchQuery the search query
	 * @return a Mono emitting the data found
	 */
	public Mono<GDUserSearchData> getUserDataFromSearch(String searchQuery) {
		return fetch(new GDUserSearchDataRequest(this, searchQuery, 0)).map(paginator -> paginator.asList().get(0));
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
	public long getCacheTtl() {
		return cacheTtl;
	}
	
	/**
	 * Gets the maximum number of simultaneous connections.
	 * 
	 * @return int
	 */
	public int getMaxConnections() {
		return maxConnections;
	}

	/**
	 * Clears the cache of previous requests.
	 */
	public void clearCache() {
		cache.clear();
		cacheTime.clear();
	}
}
