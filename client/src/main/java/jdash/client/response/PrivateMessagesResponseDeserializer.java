package jdash.client.response;

import jdash.common.entity.GDPrivateMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static jdash.common.internal.InternalUtils.buildMessage;
import static jdash.common.internal.InternalUtils.splitToMap;

public class PrivateMessagesResponseDeserializer implements Function<String, List<GDPrivateMessage>> {

    @Override
    public List<GDPrivateMessage> apply(String response) {
        var list = new ArrayList<GDPrivateMessage>();
        var messages = response.split("#")[0].split("\\|");
        for (var message : messages) {
            var data = splitToMap(message, ":");
            list.add(buildMessage(data));
        }
        return list;
    }
}
