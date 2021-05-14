package jdash.client.response;

import jdash.common.entity.GDPrivateMessageDownload;
import jdash.common.entity.ImmutableGDPrivateMessageDownload;

import java.util.function.Function;

import static jdash.common.RobTopsWeakEncryption.decodePrivateMessageBody;
import static jdash.common.internal.Indexes.MESSAGE_BODY;
import static jdash.common.internal.InternalUtils.*;

public class PrivateMessageDownloadResponseDeserializer implements Function<String, GDPrivateMessageDownload> {

    @Override
    public GDPrivateMessageDownload apply(String response) {
        var data = splitToMap(response, ":");
        requireKeys(data, MESSAGE_BODY);
        return ImmutableGDPrivateMessageDownload.builder()
                .from(buildMessage(data))
                .body(decodePrivateMessageBody(data.get(MESSAGE_BODY)))
                .build();
    }
}
