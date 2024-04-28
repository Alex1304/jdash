package jdash.client.response;

import jdash.client.exception.ActionFailedException;
import jdash.common.entity.GDUser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static jdash.common.internal.InternalUtils.buildUser;
import static jdash.common.internal.InternalUtils.splitToMap;

class UserListResponseDeserializer implements Function<String, List<GDUser>> {

    @Override
    public List<GDUser> apply(String response) {
        ActionFailedException.throwIfEquals(response, "-1", "Failed to load user(s)");
        var list = new ArrayList<GDUser>();
        var users = response.split("\\|");
        for (String user : users) {
            list.add(buildUser(splitToMap(user, ":")));
        }
        return list;
    }
}
