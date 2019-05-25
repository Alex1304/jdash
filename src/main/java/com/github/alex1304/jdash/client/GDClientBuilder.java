package com.github.alex1304.jdash.client;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import com.github.alex1304.jdash.exception.GDClientException;
import com.github.alex1304.jdash.exception.GDLoginFailedException;
import com.github.alex1304.jdash.util.Routes;

import reactor.core.publisher.Mono;

/**
 * Builds a Geometry Dash client step by step.
 */
public final class GDClientBuilder {
	public static final Duration DEFAULT_CACHE_TTL = Duration.ofMinutes(15);
	public static final int DEFAULT_MAX_CONNECTIONS = Runtime.getRuntime().availableProcessors();
	public static final Duration DEFAULT_REQUEST_TIMEOUT = Duration.ofMillis(Long.MAX_VALUE);

	private Optional<String> host;
	private Optional<Duration> cacheTtl;
	private Optional<Integer> maxConnections;
	private Optional<Duration> requestTimeout; 

	private GDClientBuilder(Optional<String> host, Optional<Duration> cacheTtl, Optional<Integer> maxConnections, Optional<Duration> requestTimeout) {
		this.host = host;
		this.cacheTtl = cacheTtl;
		this.maxConnections = maxConnections;
		this.requestTimeout = requestTimeout;
	}

	public static GDClientBuilder create() {
		return new GDClientBuilder(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
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
	 * @param time the time to set for the cache ttl
	 * @return this (for method chaining purposes)
	 * @throws IllegalArgumentException if time is negative
	 */
	public GDClientBuilder withCacheTtl(Duration time) {
		if (Objects.requireNonNull(time).isNegative()) {
			throw new IllegalArgumentException("time cannot be negative");
		}
		this.cacheTtl = Optional.of(time);
		return this;
	}

	/**
	 * Specifies how many connections to the GD servers can be simultaneously open.
	 * 
	 * @param maxConnections the number of max connections
	 * @return this (for method chaining purposes)
	 * @throws IllegalArgumentException if maxConnections is &lt; 1
	 */
	public GDClientBuilder withMaxConnections(int maxConnections) {
		if (maxConnections < 1) {
			throw new IllegalArgumentException("maxConnections must be >= 1");
		}
		this.maxConnections = Optional.of(maxConnections);
		return this;
	}

	/**
	 * Specifies a timeout for requests to complete. If a request does not complete
	 * in time, a {@link TimeoutException} will be propagated for the said request.
	 * 
	 * @param time the time to set for the cache ttl
	 * @return this (for method chaining purposes)
	 * @throws IllegalArgumentException if time is negative
	 */
	public GDClientBuilder withRequestTimeout(Duration time) {
		if (Objects.requireNonNull(time).isNegative()) {
			throw new IllegalArgumentException("time cannot be negative");
		}
		this.requestTimeout = Optional.of(time);
		return this;
	}

	/**
	 * Builds an anonymous Geometry Dash client.
	 * 
	 * @return {@link AnonymousGDClient}
	 */
	public AnonymousGDClient buildAnonymous() {
		return new AnonymousGDClient(host.orElse(Routes.BASE_URL), cacheTtl.orElse(DEFAULT_CACHE_TTL), maxConnections.orElse(DEFAULT_MAX_CONNECTIONS),
				requestTimeout.orElse(DEFAULT_REQUEST_TIMEOUT));
	}
	
	/**
	 * Builds a GD client authenticated with a username and a password.
	 * 
	 * @param username the username
	 * @param password the password
	 * @return the authenticated GD client
	 * @throws GDLoginFailedException if the authentication fails
	 * 
	 * @deprecated This method is synchronous and blocking. Use
	 *             {@link #buildAuthenticated(Credentials)} instead which builds the
	 *             authenticated client asynchronously.
	 */
	@Deprecated
	public AuthenticatedGDClient buildAuthenticated(String username, String password) throws GDLoginFailedException {
		AnonymousGDClient client = buildAnonymous();
		try {
			long[] details = client.fetch(new GDLoginRequest(client, username, password, "jdash-client")).block();
			return new AuthenticatedGDClient(details[0], details[1], username, password, client.getHost(), client.getCacheTtl(), client.getMaxConnections(),
					client.getRequestTimeout());
		} catch (GDClientException e) {
			throw new GDLoginFailedException(e);
		}
	}
	
	/**
	 * Builds a GD client authenticated with a username and a password. It
	 * internally builds an anonymous GD client in order to perform a login request,
	 * then constructs an authenticated client based on the login response.
	 * 
	 * @param credentials the credentials used to authenticate the client
	 * @return a Mono that emites the authenticated client upon successful login. If
	 *         the login fails, it will emit an error of type
	 *         {@link GDLoginFailedException}
	 */
	public Mono<AuthenticatedGDClient> buildAuthenticated(Credentials credentials) {
		return Mono.fromCallable(this::buildAnonymous)
				.flatMap(anonClient -> anonClient.fetch(new GDLoginRequest(anonClient, credentials.username, credentials.password, "jdash-client"))
						.map(details -> new AuthenticatedGDClient(details[0], details[1], credentials.username, credentials.password,
								anonClient.getHost(), anonClient.getCacheTtl(), anonClient.getMaxConnections(), anonClient.getRequestTimeout()))
						.onErrorMap(GDClientException.class, GDLoginFailedException::new));
	}
	
	/**
	 * Encapsulates a username and a password field.
	 */
	public static class Credentials {
		
		private final String username, password;
		
		/**
		 * Creates a Credentials object with the given usernama and password.
		 * 
		 * @param username the username
		 * @param password the password
		 */
		public Credentials(String username, String password) {
			this.username = username;
			this.password = password;
		}
	}
}
