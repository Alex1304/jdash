package jdash.graphics;

import java.util.OptionalInt;

/**
 * Represents a selection of colors for icons. It consists of a primary color, a secondary color as well as an optional
 * glow color.
 *
 * @param primaryColorId   The primary color ID.
 * @param secondaryColorId The secondary color ID.
 * @param glowColorId      The glow color ID, if present.
 */
public record ColorSelection(int primaryColorId, int secondaryColorId, OptionalInt glowColorId) {

    /**
     * Creates a {@link ColorSelection} with default colors as defined in the game, which corresponds to primary = 1 and
     * secondary = 3, glow being equal to secondary.
     *
     * @param withGlow whether to include the glow color
     * @return a new {@link ColorSelection}
     */
    public static ColorSelection defaultColors(boolean withGlow) {
        return new ColorSelection(1, 3, withGlow ? OptionalInt.of(3) : OptionalInt.empty());
    }

    @Override
    public String toString() {
        return "ColorSelection{" +
                "primaryColorId=" + primaryColorId +
                ", secondaryColorId=" + secondaryColorId +
                ", glowColorId=" + glowColorId +
                '}';
    }
}
