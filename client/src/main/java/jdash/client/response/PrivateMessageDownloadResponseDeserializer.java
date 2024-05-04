package jdash.client.response;

import jdash.client.exception.ActionFailedException;
import jdash.common.entity.GDPrivateMessageDownload;

import java.util.function.Function;

import static jdash.common.RobTopsWeakEncryption.decodePrivateMessageBody;
import static jdash.common.internal.Indexes.MESSAGE_BODY;
import static jdash.common.internal.InternalUtils.*;

class PrivateMessageDownloadResponseDeserializer implements Function<String, GDPrivateMessageDownload> {

    @Override
    public GDPrivateMessageDownload apply(String response) {
        ActionFailedException.throwIfEquals(response, "-1", "Failed to download message");
        final var data = splitToMap(response, ":");
        requireKeys(data, MESSAGE_BODY);
        return new GDPrivateMessageDownload(buildMessage(data), decodePrivateMessageBody(data.get(MESSAGE_BODY)));
    }
}
