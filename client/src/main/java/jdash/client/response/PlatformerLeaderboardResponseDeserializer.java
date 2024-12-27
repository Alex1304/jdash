package jdash.client.response;

import jdash.client.exception.ActionFailedException;
import jdash.common.entity.GDLeaderboardEntry;
import jdash.common.entity.GDPlatformerLeaderboardEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static jdash.common.internal.InternalUtils.*;

public class PlatformerLeaderboardResponseDeserializer implements Function<String, List<GDPlatformerLeaderboardEntry>> {

    @Override
    public List<GDPlatformerLeaderboardEntry> apply(String response) {
        ActionFailedException.throwIfEquals(response, "-1", "Failed to load level leaderboard");
        final var list = new ArrayList<GDPlatformerLeaderboardEntry>();
        final var leaderboardEntries = response.split("#")[0].split("\\|");
        for (String lbEntry : leaderboardEntries) {
            list.add(buildPlatformerLeaderboardEntry(splitToMap(lbEntry, ":")));
        }
        return list;
    }
}
