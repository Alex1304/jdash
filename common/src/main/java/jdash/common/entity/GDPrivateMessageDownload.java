package jdash.common.entity;

/**
 * Represents full data on a private message, which includes the message body.
 *
 * @param message The {@link GDPrivateMessage} containing the base information about the private message.
 * @param body    The body of the private message.
 */
public record GDPrivateMessageDownload(
        GDPrivateMessage message,
        String body
) {}
