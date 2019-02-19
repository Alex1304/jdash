package com.github.alex1304.jdash.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

import com.github.alex1304.jdash.entity.GDEntity;
import com.github.alex1304.jdash.entity.GDUser;
import com.github.alex1304.jdash.util.Utils;
import com.github.alex1304.jdash.util.robtopsweakcrypto.RobTopsWeakCrypto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;

/**
 * An HTTP client specifically designed to make requests to Geometry Dash servers.
 */
public class GeometryDashClient {
	public static final String GAME_VERSION = "21";
	public static final String BINARY_VERSION = "34";
	public static final String SECRET = "Wmfd2893gb7";
	
	private final long accountID;
	private final String password;
	private final String passwordEncoded;
	private final boolean isAuthenticated;
	private String host;
	private final HttpClient client;
	private final Map<GDRequest<?>, GDEntity> cache;
	private final Map<GDRequest<?>, Long> cacheTime;
	private final long cacheLifetime;

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
				.responseContent()
				.aggregate()
				.asString()
				.flatMap(r -> {
					try {
						E response = request.parseResponse(r);
						cache.put(request, response);
						cacheTime.put(request, System.currentTimeMillis());
						return Mono.just(response);
					} catch (GDClientException e) {
						return Mono.error(e);
					} catch (RuntimeException e) {
						return Mono.error(new GDClientException(e));
					}
				});
	}
	
	public Mono<GDUser> getUserByAccountId(long accountId) {
		return fetch(new GDUserPart1Request(accountId))
				.flatMap(u1 -> fetch(new GDUserPart2Request("" + u1.getId(), 0))
						.map(u2l -> GDUser.aggregate(u1, u2l.get(0))));
	}
	
	/**
	 * Gets the account ID of this client
	 * 
	 * @return the account ID, or 0 if the client isn't authenticated
	 */
	public long getAccountID() {
		return accountID;
	}

	/**
	 * Gets the GD account password of this client
	 * 
	 * @return the password, or an empty string if not authenticated
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets whether this client is authenticated with a GD account
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
}
