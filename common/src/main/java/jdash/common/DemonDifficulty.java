package jdash.common;

/**
 * Represents the demon difficulty of a demon level.
 */
public enum DemonDifficulty {

    /**
     * Easy demon.
     */
    EASY,

    /**
     * Medium demon.
     */
    MEDIUM,

    /**
     * Hard demon.
     */
    HARD,

    /**
     * Insane demon.
     */
    INSANE,

    /**
     * Extreme demon.
     */
    EXTREME;

    /**
     * Convenience method to get a {@link DemonDifficulty} based on the in-game encoding of a demon difficulty.
     *
     * @param str the encoded string
     * @return a {@link DemonDifficulty}
     */
    public static DemonDifficulty parse(String str) {
        return switch (str) {
            case "3" -> DemonDifficulty.EASY;
            case "4" -> DemonDifficulty.MEDIUM;
            case "5" -> DemonDifficulty.INSANE;
            case "6" -> DemonDifficulty.EXTREME;
            default -> DemonDifficulty.HARD;
        };
    }
}