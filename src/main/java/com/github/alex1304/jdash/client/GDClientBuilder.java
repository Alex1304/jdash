package com.github.alex1304.jdash.client;

import java.util.Objects;
import java.util.Optional;

import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.exception.GDLoginFailedException;
import com.github.alex1304.jdash.util.Routes;

/**
 * Builds a Geometry Dash client step by step.
 */
public final class GDClientBuilder {
	public static final long DEFAULT_CACHE_LIFETIME = 900_000;

	private Optional<String> host;
	private Optional<Long> cacheLifetime;

	private GDClientBuilder(Optional<String> host, Optional<Long> cacheLifetime) {
		this.host = host;
		this.cacheLifetime = cacheLifetime;
	}

	public static GDClientBuilder create() {
		return new GDClientBuilder(Optional.empty(), Optional.empty());
	}

	/**
	 * Specifies a custom host for the client to send the requests to. This allows
	 * the use of {@link AnonymousGDClient} for Geometry Dash private servers
	 * (GDPS).
	 * 
	 * @param host the host address (WITHOUT protocol and WITHOUT trailing slash!)
	 * @return this (for method chaining purposes)
	 * @throws IllegalArgumentException if host starts with 'http://', ends with '/'
	 * @throws NullPointerException     if host is <code>null</code>
	 */
	public GDClientBuilder withHost(String host) {
		if (host.startsWith("http://") || host.endsWith("/")) {
			throw new IllegalArgumentException("Host should not start with 'http://' nor end with '/'");
		}
		this.host = Optional.of(Objects.requireNonNull(host));
		return this;
	}

	/**
	 * Specifies how long a request should stay in cache. Setting 0 as value
	 * disables the cache.
	 * 
	 * @param time the time to set for the cache lifetime
	 * @return this (for method chaining purposes)
	 * @throws IllegalArgumentException if time is negative
	 */
	public GDClientBuilder withCacheLifetime(long time) {
		if (time < 0) {
			throw new IllegalArgumentException("time cannot be negative");
		}
		this.cacheLifetime = Optional.of(time);
		return this;
	}

	/**
	 * Builds an anonymous Geometry Dash client.
	 * 
	 * @return {@link AnonymousGDClient}
	 */
	public AnonymousGDClient buildAnonymous() {
		return new AnonymousGDClient(host.orElse(Routes.BASE_URL), cacheLifetime.orElse(DEFAULT_CACHE_LIFETIME));
	}
	
	public AuthenticatedGDClient buildAuthenticated(String username, String password) throws GDLoginFailedException {
		AnonymousGDClient client = buildAnonymous();
		try {
			long[] details = client.fetch(new GDLoginRequest(client, username, password, "jdash-client")).block();
			return new AuthenticatedGDClient(details[0], details[1], username, password, client.getHost(), client.getCacheLifetime());
		} catch (GDClientException e) {
			throw new GDLoginFailedException(e);
		}
	}
}
