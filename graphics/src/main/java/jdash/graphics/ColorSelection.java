package jdash.graphics;

import java.util.OptionalInt;

/**
 * Represents a selection of colors for icons. It consists of a primary color, a secondary color as well as an optional
 * glow color.
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class ColorSelection {

    private final int primaryColorId;
    private final int secondaryColorId;
    private final OptionalInt glowColorId;

    private ColorSelection(int primaryColorId, int secondaryColorId, OptionalInt glowColorId) {
        this.primaryColorId = primaryColorId;
        this.secondaryColorId = secondaryColorId;
        this.glowColorId = glowColorId;
    }

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

    /**
     * Creates a {@link ColorSelection} with the specified primary and secondary colors, without glow. Use
     * {@link #of(int, int, int)} in order to define glow color.
     *
     * @param primaryColorId   the ID of the primary color
     * @param secondaryColorId the ID of the secondary color
     * @return a new {@link ColorSelection}
     */
    public static ColorSelection of(int primaryColorId, int secondaryColorId) {
        return new ColorSelection(primaryColorId, secondaryColorId, OptionalInt.empty());
    }

    /**
     * Creates a {@link ColorSelection} with the specified primary, secondary and glow colors. Use {@link #of(int, int)}
     * if you don't want a glow color.
     *
     * @param primaryColorId   the ID of the primary color
     * @param secondaryColorId the ID of the secondary color
     * @param glowColorId      the ID of the glow color
     * @return a new {@link ColorSelection}
     */
    public static ColorSelection of(int primaryColorId, int secondaryColorId, int glowColorId) {
        return new ColorSelection(primaryColorId, secondaryColorId, OptionalInt.of(glowColorId));
    }

    /**
     * Gets the primary color ID.
     *
     * @return the ID as int
     */
    public int getPrimaryColorId() {
        return primaryColorId;
    }

    /**
     * Gets the secondary color ID.
     *
     * @return the ID as int
     */
    public int getSecondaryColorId() {
        return secondaryColorId;
    }

    /**
     * Gets the glow color ID, if present.
     *
     * @return the ID as {@link OptionalInt}
     */
    public OptionalInt getGlowColorId() {
        return glowColorId;
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
