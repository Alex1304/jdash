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
        switch (str) {
            case "3":
                return DemonDifficulty.EASY;
            case "4":
                return DemonDifficulty.MEDIUM;
            case "5":
                return DemonDifficulty.INSANE;
            case "6":
                return DemonDifficulty.EXTREME;
            default:
                return DemonDifficulty.HARD;
        }
    }
}