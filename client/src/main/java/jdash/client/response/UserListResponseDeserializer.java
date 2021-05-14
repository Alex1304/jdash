package jdash.client.response;

import jdash.common.entity.GDUser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static jdash.common.internal.InternalUtils.buildUser;
import static jdash.common.internal.InternalUtils.splitToMap;

public class UserListResponseDeserializer implements Function<String, List<GDUser>> {

    @Override
    public List<GDUser> apply(String response) {
        var list = new ArrayList<GDUser>();
        var users = response.split("\\|");
        for (String user : users) {
            list.add(buildUser(splitToMap(user, ":")));
        }
        return list;
    }
}
