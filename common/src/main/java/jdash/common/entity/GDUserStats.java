package jdash.common.entity;

import java.util.Optional;

/**
 * Represents the player stats of a user. It does not contain detailed information on the user's profile such as their
 * icon set, their social links or their privacy settings. This information is provided by {@link GDUserProfile}.
 *
 * @param user            The {@link GDUser} containing the base information about the user.
 * @param stars           The number of stars collected.
 * @param moons           The number of moons collected.
 * @param diamonds        The number of diamonds collected.
 * @param secretCoins     The number of secret coins collected.
 * @param userCoins       The number of user coins collected.
 * @param demons          The number of demons beaten.
 * @param creatorPoints   The number of creator points awarded.
 * @param leaderboardRank The rank on the global leaderboard. This information may not be present.
 */
public record GDUserStats(
        GDUser user,
        int stars,
        int moons,
        int diamonds,
        int secretCoins,
        int userCoins,
        int demons,
        int creatorPoints,
        Optional<Integer> leaderboardRank
) {}
