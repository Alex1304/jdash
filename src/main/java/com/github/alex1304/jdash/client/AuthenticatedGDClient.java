package com.github.alex1304.jdash.client;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;

import com.github.alex1304.jdash.entity.GDMessage;
import com.github.alex1304.jdash.entity.GDUser;
import com.github.alex1304.jdash.exception.BadResponseException;
import com.github.alex1304.jdash.exception.CorruptedResponseContentException;
import com.github.alex1304.jdash.exception.MissingAccessException;
import com.github.alex1304.jdash.util.GDPaginator;
import com.github.alex1304.jdash.util.robtopsweakcrypto.RobTopsWeakCrypto;

import reactor.core.publisher.Mono;

/**
 * An HTTP client specifically designed to make authenticated requests to
 * Geometry Dash servers. It provides the same methods as
 * {@link AnonymousGDClient}, but allows to do more stuff that requires
 * authentication, such as reading private messages, etc. To create an instance
 * of this class, use
 * {@link GDClientBuilder#buildAuthenticated(String, String)}.
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
public final class AuthenticatedGDClient extends AbstractGDClient {

	private final long accountID;
	private final long playerID;
	private final String username;
	private final String password;
	private final String passwordEncoded;

	AuthenticatedGDClient(long accountID, long playerID, String username, String password, String host,
			Duration cacheTtl, int maxConnections, Duration requestTimeout) {
		super(host, cacheTtl, maxConnections, requestTimeout);
		this.accountID = accountID;
		this.playerID = playerID;
		this.username = username;
		this.password = password;
		this.passwordEncoded = RobTopsWeakCrypto.encodeGDAccountPassword(Objects.requireNonNull(password));
	}

	@Override
	void putExtraParams(Map<String, String> params) {
		params.put("accountID", "" + accountID);
		params.put("gjp", passwordEncoded);
	}

	/**
	 * Gets the private messages of the account that this client is logged on.
	 * 
	 * @param page the page number
	 * @return a Mono emitting a paginator containing all messages found. Note that
	 *         if no messages are found, it will emit an error instead of just an
	 *         empty paginator. This is because the Geometry Dash API returns the
	 *         same response when nothing is found and when an actual error occurs
	 *         while processing the request (blame RobTop for that!).
	 * @throws UnsupportedOperationException if this client is not logged in to any
	 *                                       account
	 */
	public Mono<GDPaginator<GDMessage>> getPrivateMessages(int page) {
		return fetch(new GDMessageInboxRequest(this, page));
	}

	/**
	 * Sends a private message to a user.
	 * 
	 * @param user    the recipient of the message
	 * @param subject the message subject
	 * @param body    the message body
	 * @return a Mono completing empty if succeeded, an error otherwise.
	 * @throws UnsupportedOperationException if this client is not logged it to any
	 *                                       account
	 * @throws IllegalArgumentException      if user isn't a registered user
	 *                                       {@code (user.getAccountId() == 0)}
	 */
	public Mono<Void> sendPrivateMessage(GDUser user, String subject, String body) {
		return sendPrivateMessage(user.getAccountId(), subject, body);
	}

	/**
	 * Sends a private message to a user by their ID.
	 * 
	 * @param recipientAccountId the ID of the recipient of the message
	 * @param subject            the message subject
	 * @param body               the message body
	 * @return a Mono completing empty if succeeded, an error otherwise.
	 * @throws UnsupportedOperationException if this client is not logged it to any
	 *                                       account
	 * @throws IllegalArgumentException      if user isn't a registered user
	 *                                       {@code (user.getAccountId() == 0)}
	 */
	public Mono<Void> sendPrivateMessage(long recipientAccountId, String subject, String body) {
		if (recipientAccountId <= 0) {
			throw new IllegalArgumentException("Cannot send a private message to an unregistered user");
		}
		return fetch(new GDMessageSendRequest(this, recipientAccountId, subject, body));
	}

	/**
	 * Gets the account ID of this client
	 * 
	 * @return the account ID
	 * @throws IllegalStateException if this client is unauthenticated
	 */
	public long getAccountID() {
		return accountID;
	}

	/**
	 * Gets the player ID of this client
	 * 
	 * @return the player ID
	 * @throws IllegalStateException if this client is unauthenticated
	 */
	public long getPlayerID() {
		return playerID;
	}

	/**
	 * Gets the username of the GD account this client is logged in
	 * 
	 * @return the username
	 * @throws IllegalStateException if this client is unauthenticated
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the GD account password of this client
	 * 
	 * @return the password
	 * @throws IllegalStateException if this client was built with no password
	 *                               specified
	 */
	public String getPassword() {
		return password;
	}
}
