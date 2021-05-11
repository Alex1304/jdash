package jdash.common;

public enum LevelBrowseMode {
    REGULAR(0),
    MOST_DOWNLOADED(1),
    MOST_LIKED(2),
    TRENDING(3),
    RECENT(4),
    BY_USER(5),
    FEATURED(6),
    MAGIC(7),
    AWARDED(11),
    FOLLOWED(12),
    HALL_OF_FAME(16);

    private final int type;

    LevelBrowseMode(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}