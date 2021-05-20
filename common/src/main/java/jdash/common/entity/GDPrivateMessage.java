package jdash.common.entity;

import org.immutables.value.Value;

/**
 * Represents a private message sent to a user or received from a user.
 */
@Value.Immutable
public interface GDPrivateMessage {

    /**
     * The ID of the private message.
     *
     * @return a long
     */
    long id();

    /**
     * If this is an incoming message (<code>{@link #isSender()} == false</code>), returns the account ID of the sender.
     * If this is an outgoing message (<code>{@link #isSender()} == true</code>), returns the account ID of the
     * recipient.
     *
     * @return a long
     */
    long userAccountId();

    /**
     * If this is an incoming message (<code>{@link #isSender()} == false</code>), returns the player ID of the sender.
     * If this is an outgoing message (<code>{@link #isSender()} == true</code>), returns the player ID of the
     * recipient.
     *
     * @return a long
     */
    long userPlayerId();

    /**
     * If this is an incoming message (<code>{@link #isSender()} == false</code>), returns the name of the sender. If
     * this is an outgoing message (<code>{@link #isSender()} == true</code>), returns the name of the recipient.
     *
     * @return a string
     */
    String userName();

    /**
     * The subject of the private message.
     *
     * @return a string
     */
    String subject();

    /**
     * Whether the message has not been read yet.
     *
     * @return a boolean
     */
    boolean isUnread();

    /**
     * Whether the current user is the sender of the private message. If <code>false</code>, it means the current user
     * is the recipient and not the sender.
     *
     * @return a boolean
     */
    boolean isSender();

    /**
     * A string indicating when the private message was sent. The structure of the string is not guaranteed.
     *
     * @return a string
     */
    String sentAgo();
}
