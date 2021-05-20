package jdash.common.entity;

import org.immutables.value.Value;

import java.util.Optional;

/**
 * Represents the player stats of a user. It inherits all information from a simple {@link GDUser}, plus some
 * information regarding the player stats (stars, demons...). It does not contain detailed information on the user's
 * profile such as their icon set, their social links or their privacy settings. This information is provided by {@link
 * GDUserProfile} which extends this interface.
 */
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
