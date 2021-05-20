package jdash.common;

/**
 * Indicates the category in which to browse levels.
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
     * Allows to browse awarded levels.
     */
    AWARDED(11),

    /**
     * Allows to browse levels by followed users.
     */
    FOLLOWED(12),

    /**
     * Allows to browse levels in the Hall of Fame.
     */
    HALL_OF_FAME(16);

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