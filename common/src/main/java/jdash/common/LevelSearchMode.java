package jdash.common;

/**
 * Indicates the category in which to search levels.
 * <p>
 * Although the word "level" is used here, it may also apply to level lists.
 */
public enum LevelSearchMode {

    /**
     * Allows to search for any level by ID or name.
     */
    SEARCH(0),

    /**
     * Allows to search most downloaded levels.
     */
    MOST_DOWNLOADED(1),

    /**
     * Allows to search most liked levels.
     */
    MOST_LIKED(2),

    /**
     * Allows to search trending levels.
     */
    TRENDING(3),

    /**
     * Allows to search recent levels.
     */
    RECENT(4),

    /**
     * Allows to search levels created by a specific user.
     */
    BY_USER(5),

    /**
     * Allows to search featured levels.
     */
    FEATURED(6),

    /**
     * Allows to search magic levels.
     */
    MAGIC(7),

    /**
     * Allows to search levels contained in map packs.
     */
    MAP_PACKS(10),

    /**
     * Allows to search awarded levels.
     */
    AWARDED(11),

    /**
     * Allows to search levels by followed users.
     */
    FOLLOWED(12),

    /**
     * Allows to search levels uploaded by friends. Requires authentication.
     */
    FRIENDS(13),

    /**
     * Allows to search levels in the Hall of Fame.
     *
     * @deprecated Hall of Fame has been removed from the game
     * since update 2.2, although this mode might still exist server-side.
     */
    @Deprecated
    HALL_OF_FAME(16),

    /**
     * Allows to search featured levels in Geometry Dash World.
     */
    GD_WORLD_FEATURED(17),

    /**
     * Allows to search history of past Daily levels.
     */
    DAILY_HISTORY(21),

    /**
     * Allows to search history of past Weekly demons.
     */
    WEEKLY_HISTORY(22),

    /**
     * Allows to search content of a list.
     */
    LIST_CONTENT(25),

    /**
     * Allows to search levels sent by moderators.
     */
    SENT(27);

    private final int type;

    LevelSearchMode(int type) {
        this.type = type;
    }

    /**
     * The raw value of this {@link LevelSearchMode}.
     *
     * @return an int
     */
    public int getType() {
        return type;
    }
}