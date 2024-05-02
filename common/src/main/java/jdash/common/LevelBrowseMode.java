package jdash.common;

/**
 * Indicates the category in which to browse levels.
 * <p>
 * Although the word "level" is used here, it may also apply to level lists.
 */
public enum LevelBrowseMode {

    /**
     * Allows to search for any level by ID or name.
     */
    SEARCH(0),

    /**
     * Allows to browse most downloaded levels.
     */
    MOST_DOWNLOADED(1),

    /**
     * Allows to browse most liked levels.
     */
    MOST_LIKED(2),

    /**
     * Allows to browse trending levels.
     */
    TRENDING(3),

    /**
     * Allows to browse recent levels.
     */
    RECENT(4),

    /**
     * Allows to browse levels created by a specific user.
     */
    BY_USER(5),

    /**
     * Allows to browse featured levels.
     */
    FEATURED(6),

    /**
     * Allows to browse magic levels.
     */
    MAGIC(7),

    /**
     * Allows to browse levels contained in map packs.
     */
    MAP_PACKS(10),

    /**
     * Allows to browse awarded levels.
     */
    AWARDED(11),

    /**
     * Allows to browse levels by followed users.
     */
    FOLLOWED(12),

    /**
     * Allows to browse levels uploaded by friends. Requires authentication.
     */
    FRIENDS(13),

    /**
     * Allows to browse most liked levels in Geometry Dash World.
     */
    GD_WORLD_MOST_LIKED(15),

    /**
     * Allows to browse levels in the Hall of Fame.
     *
     * @deprecated Hall of Fame has been removed from the game
     * since update 2.2, although this mode might still exist server-side.
     */
    @Deprecated
    HALL_OF_FAME(16),

    /**
     * Allows to browse featured levels in Geometry Dash World.
     */
    GD_WORLD_FEATURED(17),

    /**
     * Allows to browse history of past Daily levels.
     */
    DAILY_HISTORY(21),

    /**
     * Allows to browse history of past Weekly demons.
     */
    WEEKLY_HISTORY(22);

    private final int type;

    LevelBrowseMode(int type) {
        this.type = type;
    }

    /**
     * The raw value of this {@link LevelBrowseMode}.
     *
     * @return an int
     */
    public int getType() {
        return type;
    }
}