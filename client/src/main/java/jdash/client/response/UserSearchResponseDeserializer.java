package jdash.client.response;

import jdash.common.entity.GDUserStats;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static jdash.common.internal.InternalUtils.buildUserStats;
import static jdash.common.internal.InternalUtils.splitToMap;

public class UserSearchResponseDeserializer implements Function<String, List<GDUserStats>> {

    @Override
    public List<GDUserStats> apply(String response) {
        var list = new ArrayList<GDUserStats>();
        var users = response.split("#")[0].split("\\|");
        for (String user : users) {
            list.add(buildUserStats(splitToMap(user, ":")));
        }
        return list;
    }
}
