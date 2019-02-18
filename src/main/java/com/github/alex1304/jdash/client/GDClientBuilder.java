package com.github.alex1304.jdash.client;

import java.util.Objects;
import java.util.Optional;

import com.github.alex1304.jdash.util.Routes;

/**
 * Builds a Geometry Dash client step by step.
 */
public class GDClientBuilder {
	public static final long DEFAULT_CACHE_LIFETIME = 900_000;

	private final class Auth {
		final long accountId;
		final String password;

		public Auth(long accountId, String password) {
			if (accountId <= 0) {
				throw new IllegalArgumentException("Account ID must be strictly positive");
			}
			this.accountId = accountId;
			this.password = Objects.requireNonNull(password);
		}
	}

	private Optional<Auth> auth;
	private Optional<String> host;
	private Optional<Long> cacheLifetime;

	private GDClientBuilder(Optional<Auth> auth, Optional<String> host, Optional<Long> cacheLifetime) {
		this.auth = auth;
		this.host = host;
		this.cacheLifetime = cacheLifetime;
	}

	public static GDClientBuilder create() {
		return new GDClientBuilder(Optional.empty(), Optional.empty(), Optional.empty());
	}

	/**
	 * Specifies an account ID and a password to authenticate requests made with the
	 * client.
	 * 
	 * @param accountId the account ID
	 * @param password  the account password
	 * @return this (for method chaining purposes)
	 * @throws IllegalArgumentException if accountID is less than or equal to 0
	 * @throws NullPointerException     if password is <code>null</code>
	 */
	public GDClientBuilder withAuthenticationDetails(long accountId, String password) {
		this.auth = Optional.of(new Auth(accountId, password));
		return this;
	}

	/**
	 * Specifies a custom host for the client to send the requests to. This allows
	 * the use of {@link GeometryDashClient} for Geometry Dash private servers
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
	 * Specifies a custom lifetime for the request cache. Setting 0 as value
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
	 * Builds the Geometry Dash client.
	 * 
	 * @return {@link GeometryDashClient}
	 */
	public GeometryDashClient build() {
		return new GeometryDashClient(
				auth.map(auth -> auth.accountId).orElse(0L),
				auth.map(auth -> auth.password).orElse(""),
				host.orElse(Routes.BASE_URL),
				cacheLifetime.orElse(DEFAULT_CACHE_LIFETIME));
	}
}
