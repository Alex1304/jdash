package jdash.common.entity;

/**
 * Represents a private message sent to a user or received from a user.
 *
 * @param id            The ID of the private message.
 * @param userAccountId If this is an incoming message (<code>{@link #isSender()} == false</code>), returns the account
 *                      ID of the sender. If this is an outgoing message (<code>{@link #isSender()} == true</code>),
 *                      returns the account ID of the recipient.
 * @param userPlayerId  If this is an incoming message (<code>{@link #isSender()} == false</code>), returns the player
 *                      ID of the sender. If this is an outgoing message (<code>{@link #isSender()} == true</code>),
 *                      returns the player ID of the recipient.
 * @param userName      If this is an incoming message (<code>{@link #isSender()} == false</code>), returns the name of
 *                      the sender. If this is an outgoing message (<code>{@link #isSender()} == true</code>), returns
 *                      the name of the recipient.
 * @param subject       The subject of the private message.
 * @param isUnread      Whether the message has not been read yet.
 * @param isSender      Whether the current user is the sender of the private message. If <code>false</code>, it means
 *                      the current user is the recipient and not the sender.
 * @param sentAgo       A string indicating when the private message was sent. The structure of the string is not
 *                      guaranteed.
 */
public record GDPrivateMessage(
        long id,
        long userAccountId,
        long userPlayerId,
        String userName,
        String subject,
        boolean isUnread,
        boolean isSender,
        String sentAgo
) {}
