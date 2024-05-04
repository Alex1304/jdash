package jdash.client.response;

import jdash.client.exception.ActionFailedException;
import jdash.common.entity.GDPrivateMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static jdash.common.internal.InternalUtils.buildMessage;
import static jdash.common.internal.InternalUtils.splitToMap;

class PrivateMessagesResponseDeserializer implements Function<String, List<GDPrivateMessage>> {

    @Override
    public List<GDPrivateMessage> apply(String response) {
        ActionFailedException.throwIfEquals(response, "-1", "Failed to load messages");
        final var list = new ArrayList<GDPrivateMessage>();
        final var messages = response.split("#")[0].split("\\|");
        for (var message : messages) {
            final var data = splitToMap(message, ":");
            list.add(buildMessage(data));
        }
        return list;
    }
}
