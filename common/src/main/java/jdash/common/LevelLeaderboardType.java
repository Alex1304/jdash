package jdash.common;

/**
 * Represent a level leaderboard type.
 */
public enum LevelLeaderboardType {

    /**
     * Friends leaderboard.
     */
    FRIENDS(0),

    /**
     * All players.
     */
    ALL(1),

    /**
     * Weekly leaderboard.
     */
    WEEKLY(2),

    ;

    private final int typeId;

    LevelLeaderboardType(int typeId) {
        this.typeId = typeId;
    }

    /**
     * The ID of the {@link LevelLeaderboardType}, to be used in requests
     *
     * @return an integer representing the type
     */
    public int getTypeId() {
        return typeId;
    }
}
