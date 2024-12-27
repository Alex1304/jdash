package jdash.common.entity;

/**
 * Represents a single entry in a Geometry Dash platformer level leaderboard.
 *
 * @param user            The user
 * @param timeMs          The time (in milliseconds)
 * @param leaderboardRank The rank in the leaderboard, from 1 to +infinity
 * @param postedAgo       The entry age
 */
public record GDPlatformerLeaderboardEntry(
        GDUser user,
        long timeMs,
        int leaderboardRank,
        String postedAgo
) {
}
