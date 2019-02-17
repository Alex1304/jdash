package com.github.alex1304.jdash.client;

import java.util.HashMap;
import java.util.Objects;
import java.util.StringJoiner;

import com.github.alex1304.jdash.entity.GDEntity;
import com.github.alex1304.jdash.request.GDRequest;
import com.github.alex1304.jdash.util.Routes;
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
	
	private long accountID;
	private String password;
	private boolean isAuthenticated;
	private String host;
	private HttpClient client;

	/**
	 * @param accountID
	 *            - The GD account ID
	 * @param password
	 *            - The GD account password
	 */
	public GeometryDashClient(long accountID, String password) {
		this.accountID = accountID;
		this.password = RobTopsWeakCrypto.encodeGDAccountPassword(Objects.requireNonNull(password));
		this.isAuthenticated = true;
		this.host = Routes.BASE_URL;
		this.client = HttpClient.create().headers(h -> h.add("Content-Type", "application/x-www-form-urlencoded"));
	}
	
	/**
	 * Constructor that creates an anonymous (logged out) client.
	 */
	public GeometryDashClient() {
		this(0, "");
		this.isAuthenticated = false;
	}
	
	/**
	 * Sends the given request through the client and returns a Mono emtting the response object.
	 * 
	 * @param request - the request object to send
	 * @return a Mono emtting the response object. If an error occurs when fetching info to GD servers, it is emitted through the Mono.
	 */
	public <E extends GDEntity> Mono<E> fetch(GDRequest<E> request) {
		StringJoiner sj = new StringJoiner("&");
		HashMap<String, String> params = new HashMap<>();
		params.put("gameVersion", GAME_VERSION);
		params.put("binaryVersion", BINARY_VERSION);
		params.put("gdw", "0");
		params.put("secret", SECRET);
		if (isAuthenticated) {
			params.put("accountID", "" + accountID);
			params.put("gjp", password);
		}
		params.putAll(request.getParams());
		params.forEach((k, v) -> sj.add(k + "=" + v));
		return client.baseUrl(host).post()
				.uri(request.getPath())
				.send(ByteBufFlux.fromString(Flux.just(sj.toString())))
				.responseContent()
				.aggregate()
				.asString()
				.flatMap(r -> {
					try {
						return Mono.just(request.parseResponse(r));
					} catch (Exception e) {
						return Mono.error(e);
					}
				});
	}
	
	/**
	 * Gets the GD account ID
	 * 
	 * @return long
	 */
	public long getAccountID() {
		return accountID;
	}

	/**
	 * Gets the GD account password
	 * 
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets whether the client is authenticated with a GD account
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
	 * Sets the host
	 *
	 * @param host - String
	 */
	public void setHost(String host) {
		this.host = Objects.requireNonNull(host);
	}
}
