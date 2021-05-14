package jdash.common.entity;

import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
public interface GDUserStats extends GDUser {

    int stars();

    int diamonds();

    int secretCoins();

    int userCoins();

    int demons();

    int creatorPoints();

    Optional<Integer> leaderboardRank();
}
