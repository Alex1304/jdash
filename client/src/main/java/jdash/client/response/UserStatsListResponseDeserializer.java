package jdash.client.response;

import jdash.client.exception.ActionFailedException;
import jdash.common.entity.GDUserStats;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static jdash.common.internal.InternalUtils.buildUserStats;
import static jdash.common.internal.InternalUtils.splitToMap;

public class UserStatsListResponseDeserializer implements Function<String, List<GDUserStats>> {

    @Override
    public List<GDUserStats> apply(String response) {
        ActionFailedException.throwIfEquals(response, "-1", "No users could be loaded");
        var list = new ArrayList<GDUserStats>();
        var users = response.split("#")[0].split("\\|");
        for (String user : users) {
            list.add(buildUserStats(splitToMap(user, ":")));
        }
        return list;
    }
}
