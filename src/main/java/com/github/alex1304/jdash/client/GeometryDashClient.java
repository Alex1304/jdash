package com.github.alex1304.jdash.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

import com.github.alex1304.jdash.entity.GDEntity;
import com.github.alex1304.jdash.entity.GDUser;
import com.github.alex1304.jdash.exception.BadResponseException;
import com.github.alex1304.jdash.exception.CorruptedResponseContentException;
import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.util.Utils;
import com.github.alex1304.jdash.util.robtopsweakcrypto.RobTopsWeakCrypto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;

/**
 * An HTTP client specifically designed to make requests to Geometry Dash servers.
 * To create an instance of this class, use {@link GDClientBuilder}.
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
		this.client = HttpClient.create().headers(h -> h.add("Content-Type", "application/x-www-form-urlencoded"));
		this.cache = new ConcurrentHashMap<>();
		this.cacheTime = new ConcurrentHashMap<>();
		this.cacheLifetime = cacheLifetime;
		this.totalNumberOfRequestsMade = 0;
	}
	
	/**
	 * Sends the given request through the client and returns a Mono emtting the response object.
	 * 
	 * @param request - the request object to send
	 * @return a Mono emtting the response object. If an error occurs when fetching info to GD servers, it is emitted through the Mono.
	 */
	<E extends GDEntity> Mono<E> fetch(GDRequest<E> request) {
		@SuppressWarnings("unchecked")
		E cached = (E) cache.get(request);
		if (cached != null && System.currentTimeMillis() - cacheTime.getOrDefault(request, 0L) <= cacheLifetime) {
			return Mono.just(cached);
		}
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
		params.forEach((k, v) -> sj.add(k + "=" + Utils.urlEncode(v)));
		String requestStr = sj.toString();
		return client.baseUrl(host).post()
				.uri(request.getPath())
				.send(ByteBufFlux.fromString(Flux.just(requestStr)))
				.responseSingle((response, content) -> response.status().code() >= 400 && response.status().code() < 600 ?
						Mono.error(new BadResponseException(response)) : content.asString())
				.flatMap(r -> {
					try {
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
				});
	}
	
	private void cleanUpExpiredCacheEntries() {
		final long now = System.currentTimeMillis();
		new HashMap<>(cacheTime).forEach((k, v) -> {
			if (now - v < cacheLifetime) {
				cacheTime.remove(k);
				cache.remove(k);
			}
		});
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
		return fetch(new GDUserPart1Request(accountId))
				.flatMap(u1 -> fetch(new GDUserPart2Request("" + u1.getId(), 0))
						.map(u2l -> GDUser.aggregate(u1, u2l.get(0))));
	}

	/**
	 * Submits a search query and returns the users found at the given page.
	 * 
	 * @param searchQuery the users to search for
	 * @param page        the page of the search results to look at. The page number
	 *                    is zero-indexed, meaning that the first page corresponds
	 *                    to page 0.
	 * @return a Flux emitting all users found. Note that if no users are found, it
	 *         will return a Flux emitting an error instead of just an empty Flux.
	 *         This is because the Geometry Dash API returns the same response when
	 *         a user is not found and when an actual error occurs while processing
	 *         the request (blame RobTop for that!).
	 */
	public Flux<GDUser> searchUsers(String searchQuery, int page) {
		Objects.requireNonNull(searchQuery);
		if (page < 0) {
			throw new IllegalArgumentException("Page parameter must not be negative");
		}
		return fetch(new GDUserPart2Request(searchQuery, page))
				.flatMapMany(Flux::fromIterable)
				.flatMap(u2 -> fetch(new GDUserPart1Request(u2.getAccountId()))
						.map(u1 -> GDUser.aggregate(u1, u2)));
	}
	
	/**
	 * Gets the account ID of this client
	 * 
	 * @return the account ID
	 * @throws IllegalStateException if this client was built with no account ID specified
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
	 * @throws IllegalStateException if this client was built with no password specified
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
