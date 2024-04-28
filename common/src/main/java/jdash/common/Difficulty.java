package jdash.common;

/**
 * Represents the difficulty of a level.
 */
public enum Difficulty {

    /**
     * N/A difficulty, it means the difficulty for a level has not been determined.
     */
    NA(-1),

    /**
     * Auto difficulty, it means the level can be completed automatically without the player needing to jump.
     */
    AUTO(-2),

    /**
     * Easy difficulty.
     */
    EASY(1),

    /**
     * Normal difficulty.
     */
    NORMAL(2),

    /**
     * Hard difficulty.
     */
    HARD(3),

    /**
     * Harder difficulty.
     */
    HARDER(4),

    /**
     * Insane difficulty.
     */
    INSANE(5),

    /**
     * Demon difficulty.
     */
    DEMON(-3);

    private final int value;

    Difficulty(int value) {
        this.value = value;
    }

    /**
     * Convenience method to get a {@link Difficulty} based on the in-game encoding of a difficulty.
     *
     * @param str the encoded string
     * @return a {@link Difficulty}
     */
    public static Difficulty parse(String str) {
        return switch (str) {
            case "10" -> Difficulty.EASY;
            case "20" -> Difficulty.NORMAL;
            case "30" -> Difficulty.HARD;
            case "40" -> Difficulty.HARDER;
            case "50" -> Difficulty.INSANE;
            default -> Difficulty.NA;
        };
    }

    /**
     * The raw value of the difficulty, as used for example in search filters.
     *
     * @return an int
     */
    public int getValue() {
        return value;
    }
}