package jdash.client.response;

import jdash.client.exception.ActionFailedException;
import jdash.common.entity.GDLeaderboardEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static jdash.common.internal.InternalUtils.buildLeaderboardEntry;
import static jdash.common.internal.InternalUtils.splitToMap;

public class LeaderboardResponseDeserializer implements Function<String, List<GDLeaderboardEntry>> {

    @Override
    public List<GDLeaderboardEntry> apply(String response) {
        ActionFailedException.throwIfEquals(response, "-1", "Failed to load level leaderboard");
        final var list = new ArrayList<GDLeaderboardEntry>();
        final var leaderboardEntries = response.split("#")[0].split("\\|");
        for (String lbEntry : leaderboardEntries) {
            list.add(buildLeaderboardEntry(splitToMap(lbEntry, ":")));
        }
        return list;
    }
}
