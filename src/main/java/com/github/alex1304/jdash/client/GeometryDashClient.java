package com.github.alex1304.jdash.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

import com.github.alex1304.jdash.entity.GDEntity;
import com.github.alex1304.jdash.entity.GDLevel;
import com.github.alex1304.jdash.entity.GDPaginator;
import com.github.alex1304.jdash.entity.GDUser;
import com.github.alex1304.jdash.exception.BadResponseException;
import com.github.alex1304.jdash.exception.CorruptedResponseContentException;
import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.util.Indexes;
import com.github.alex1304.jdash.util.LevelSearchFilters;
import com.github.alex1304.jdash.util.LevelSearchStrategy;
import com.github.alex1304.jdash.util.robtopsweakcrypto.RobTopsWeakCrypto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.PrematureCloseException;
import reactor.netty.resources.ConnectionProvider;

/**
 * An HTTP client specifically designed to make requests to Geometry Dash
 * servers. To create an instance of this class, use {@link GDClientBuilder}.
 */
public class GeometryDashClient {
	public static final String GAME_VERSION = "21";
	public static final String BINARY_VERSION = "34";
	public static final String SECRET = "Wmfd2893gb7";
	public static final int CLEANUP_CACHE_EVERY = 50;

	private final long accountID;
	private final String password;
	private final String passwordEncoded;
	private final boolean isAuthenticated;
	private String host;
	private final HttpClient client;
	private final Map<GDRequest<?>, GDEntity> cache;
	private final Map<GDRequest<?>, Long> cacheTime;
	private final long cacheLifetime;
	private long totalNumberOfRequestsMade;

	GeometryDashClient(long accountID, String password, String host, long cacheLifetime) {
		this.accountID = accountID;
		this.password = password;
		this.passwordEncoded = RobTopsWeakCrypto.encodeGDAccountPassword(Objects.requireNonNull(password));
		this.isAuthenticated = accountID > 0;
		this.host = host;
		this.client = HttpClient.create(ConnectionProvider.newConnection()).headers(h -> h.add("Content-Type", "application/x-www-form-urlencoded"));
		this.cache = new ConcurrentHashMap<>();
		this.cacheTime = new ConcurrentHashMap<>();
		this.cacheLifetime = cacheLifetime;
		this.totalNumberOfRequestsMade = 0;
	}

	/**
	 * Sends the given request through the client and returns a Mono emtting the
	 * response object.
	 * 
	 * @param request the request object to send
	 * @return a Mono emtting the response object. If an error occurs when fetching
	 *         info to GD servers, it is emitted through the Mono.
	 */
	<E extends GDEntity> Mono<E> fetch(GDRequest<E> request) {
		// Returning immediately if the value is cached already
		@SuppressWarnings("unchecked")
		E cached = (E) cache.get(request);
		if (cached != null && System.currentTimeMillis() - cacheTime.getOrDefault(request, 0L) <= cacheLifetime) {
			return Mono.just(cached);
		}
		// Building the request string
		StringJoiner sj = new StringJoiner("&");
		HashMap<String, String> params = new HashMap<>();
		params.put("gameVersion", GAME_VERSION);
		params.put("binaryVersion", BINARY_VERSION);
		params.put("gdw", "0");
		params.put("secret", SECRET);
		if (isAuthenticated) {
			params.put("accountID", "" + accountID);
			params.put("gjp", passwordEncoded);
		}
		params.putAll(request.getParams());
		params.forEach((k, v) -> sj.add(k + "=" + v));
		String requestStr = sj.toString();
		// Parsing, caching and returning the response
		return client.baseUrl(host).post().uri(request.getPath())
				.send(ByteBufFlux.fromString(Flux.just(requestStr)))
				.responseSingle((response, content) -> {
					if (response.status().code() >= 400 && response.status().code() < 600) {
						return Mono.error(new BadResponseException(response));
					} else {
						return content.asString();
					}
				}).publishOn(Schedulers.elastic()).subscribeOn(Schedulers.elastic())
				.flatMap(r -> {
					try {
						System.out.println(requestStr);
						E entity = request.parseResponse(r);
						cache.put(request, entity);
						cacheTime.put(request, System.currentTimeMillis());
						totalNumberOfRequestsMade++;
						if (totalNumberOfRequestsMade % CLEANUP_CACHE_EVERY == 0) {
							cleanUpExpiredCacheEntries();
						}
						return Mono.just(entity);
					} catch (GDClientException e) {
						return Mono.error(e);
					} catch (RuntimeException e) {
						return Mono.error(new CorruptedResponseContentException(e));
					}
				}).retry(PrematureCloseException.class::isInstance);
	}

	private void cleanUpExpiredCacheEntries() {
		Mono.just(System.currentTimeMillis()).doOnNext(now -> {
			new HashMap<>(cacheTime).forEach((k, v) -> {
				if (now - v < cacheLifetime) {
					cacheTime.remove(k);
					cache.remove(k);
				}
			});
		}).subscribe();
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
		return fetch(new GDUserPart1Request(this, accountId)).flatMap(
				u1 -> fetch(new GDUserPart2Request(this, "" + u1.getId(), 0)).map(u2l -> GDUser.aggregate(u1, u2l.asList().get(0))));
	}

	/**
	 * Submits a search query and returns the corresponding user.
	 * 
	 * @param searchQuery the user to search for
	 * @return a Mono emitting the user found, or an error if not found.
	 */
	public Mono<GDUser> searchUser(String searchQuery) {
		Objects.requireNonNull(searchQuery);
		return fetch(new GDUserPart2Request(this, searchQuery, 0)).map(paginator -> paginator.asList().get(0))
				.flatMap(u2 -> fetch(new GDUserPart1Request(this, u2.getAccountId()))
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
	 *         something went wrong.
	 */
	public Mono<GDLevel> getDailyLevel() {
		return fetch(new GDLevelSingleRequest(this, Indexes.DAILY_LEVEL_ID));
	}

	/**
	 * Gets the current Weekly Demon on Geometry Dash.
	 * 
	 * @return a Mono emitting the Daily level, or an error if not found or
	 *         something went wrong.
	 */
	public Mono<GDLevel> getWeeklyDemon() {
		return fetch(new GDLevelSingleRequest(this, Indexes.WEEKLY_DEMON_ID));
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
	 * Gets the account ID of this client
	 * 
	 * @return the account ID
	 * @throws IllegalStateException if this client was built with no account ID
	 *                               specified
	 */
	public long getAccountID() {
		if (accountID == 0) {
			throw new IllegalStateException("Cannot get the account ID of an unauthenticated GeometryDashClient.");
		}
		return accountID;
	}

	/**
	 * Gets the GD account password of this client
	 * 
	 * @return the password
	 * @throws IllegalStateException if this client was built with no password
	 *                               specified
	 */
	public String getPassword() {
		if (password.isEmpty()) {
			throw new IllegalStateException("Cannot get the password of an unauthenticated GeometryDashClient.");
		}
		return password;
	}

	/**
	 * Gets whether this client is authenticated with a GD account.
	 * 
	 * @return boolean
	 */
	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	/**
	 * Gets the host
	 *
	 * @return String
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Clears the cache of previous requests.
	 */
	public void clearCache() {
		cache.clear();
		cacheTime.clear();
	}

	/**
	 * Gets the total number of requests that this client has made to Geometry Dash
	 * servers since the creation of this client. This value is not guaranteed to
	 * increase everytime a method such as {@link #getUserByAccountId(long)} is
	 * called, because sometimes the client caches the responses in order to reduce
	 * the number of requests and improve performance. For example, 50 successive
	 * calls of {@link #getUserByAccountId(long)} with the same argument is likely
	 * to increase this number by only 1 or 2. That's why the returned value should
	 * only be used for performance monitoring purposes.
	 * 
	 * @return the total number of requests sent to GD servers during the lifetime
	 *         of this client.
	 */
	public long getTotalNumberOfRequestsMade() {
		return totalNumberOfRequestsMade;
	}
}
