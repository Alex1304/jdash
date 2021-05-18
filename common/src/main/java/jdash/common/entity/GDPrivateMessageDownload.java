package jdash.common.entity;

import org.immutables.value.Value;

/**
 * Represents full data on a private message, which includes the message body.
 */
@Value.Immutable
public interface GDPrivateMessageDownload extends GDPrivateMessage {

    /**
     * The body of the private message.
     *
     * @return a string
     */
    String body();
}
