package com.github.alex1304.jdash.old.client;

import java.time.Duration;
import java.util.Map;

import com.github.alex1304.jdash.old.exception.BadResponseException;
import com.github.alex1304.jdash.old.exception.CorruptedResponseContentException;
import com.github.alex1304.jdash.old.exception.MissingAccessException;

import reactor.core.publisher.Mono;

/**
 * An HTTP client specifically designed to make anonymous requests to Geometry
 * Dash servers. To create an instance of this class, use
 * {@link GDClientBuilder#buildAnonymous()}.
 * 
 * <p>
 * Unless mentionned otherwise on the said methods, all methods which return
 * type is a {@link Mono} may emit one of the following errors if the underlying
 * request fails:
 * </p>
 * <ul>
 * <li>{@link BadResponseException} - happens when the Geometry Dash server
 * returns an HTTP error (like a <code>404 Not Found</code> or a
 * <code>500 Internal Server Error</code> for instance)</li>
 * <li>{@link MissingAccessException} - certainly the most common error. Happens
 * when nothing is found (e.g a level search gave no results), or when access to
 * the resource is denied (e.g trying to fetch a user profile with an
 * authenticated client, and the user has blocked the account that the client is
 * logged on). Unfortunately there is no way to distinguish those two situations
 * due to how the Geometry Dash API is designed. For those who are familiar with
 * the raw Geometry Dash API, this is when the server returns
 * <code>-1</code>.</li>
 * <li>{@link CorruptedResponseContentException} - happens when the Geometry
 * Dash server returns a response that cannot be parsed to the desired object.
 * This should rarely happen in normal conditions, but it may be more frequent
 * when you use JDash on a Geometry Dash private server.</li>
 * </ul>
 */
public final class AnonymousGDClient extends AbstractGDClient {
	AnonymousGDClient(String host, Duration cacheTtl, Duration requestTimeout) {
		super(host, cacheTtl, requestTimeout);
	}
	@Override
	void putExtraParams(Map<String, String> params) {
	}
}
