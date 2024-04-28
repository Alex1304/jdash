package jdash.common;

/**
 * Represents the quality rating of a level (none, featured, epic, legendary, mythic).
 */
public enum QualityRating {
    /**
     * No quality rating.
     */
    NONE,

    /**
     * Featured rating.
     */
    FEATURED,

    /**
     * Epic rating.
     */
    EPIC,

    /**
     * Legendary rating.
     */
    LEGENDARY,

    /**
     * Mythic rating.
     */
    MYTHIC;

    /**
     * Convenience method to get a {@link QualityRating} based on the in-game encoding of a quality rating.
     *
     * @param str             the encoded string
     * @param defaultFeatured whether the default value should be {@link #FEATURED} or {@link #NONE}
     * @return a {@link Difficulty}
     */
    public static QualityRating parse(String str, boolean defaultFeatured) {
        return switch (str) {
            case "1" -> EPIC;
            case "2" -> LEGENDARY;
            case "3" -> MYTHIC;
            default -> defaultFeatured ? FEATURED : NONE;
        };
    }
}
