package jdash.client.response;

import jdash.common.entity.GDMessage;
import jdash.common.entity.ImmutableGDMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static jdash.common.internal.Indexes.*;
import static jdash.common.internal.InternalUtils.*;

public class PrivateMessagesResponseDeserializer implements Function<String, List<GDMessage>> {

    @Override
    public List<GDMessage> apply(String response) {
        var list = new ArrayList<GDMessage>();
        var messages = response.split("#")[0].split("\\|");
        for (var message : messages) {
            var data = splitToMap(message, ":");
            requireKeys(data, MESSAGE_ID, MESSAGE_AUTHOR_ID, MESSAGE_AUTHOR_NAME, MESSAGE_SUBJECT,
                    MESSAGE_IS_READ, MESSAGE_SENT_AGO);
            list.add(ImmutableGDMessage.builder()
                    .id(Long.parseLong(data.get(MESSAGE_ID)))
                    .authorAccountId(Long.parseLong(data.get(MESSAGE_AUTHOR_ID)))
                    .authorName(data.get(MESSAGE_AUTHOR_NAME))
                    .subject(b64Decode(data.get(MESSAGE_SUBJECT)))
                    .isRead("1".equals(data.get(MESSAGE_IS_READ)))
                    .sentAgo(data.get(MESSAGE_SENT_AGO))
                    .build());
        }
        return list;
    }
}
