package jdash.common.entity;

/**
 * Represents a single entry in a Geometry Dash level leaderboard.
 *
 * @param user            The user
 * @param percentage      The level completion percentage
 * @param leaderboardRank The rank in the leaderboard, from 1 to +infinity
 * @param secretCoins     The amount of secret coins
 * @param postedAgo       The entry age
 */
public record GDLeaderboardEntry(
        GDUser user,
        int percentage,
        int leaderboardRank,
        int secretCoins,
        String postedAgo
) {
}
