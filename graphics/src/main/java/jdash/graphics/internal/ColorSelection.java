package jdash.graphics.internal;

import java.util.OptionalInt;

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

    public static ColorSelection defaultColors(boolean withGlow) {
        return new ColorSelection(1, 3, withGlow ? OptionalInt.of(3) : OptionalInt.empty());
    }

    public static ColorSelection of(int primaryColorId, int secondaryColorId) {
        return new ColorSelection(primaryColorId, secondaryColorId, OptionalInt.empty());
    }

    public static ColorSelection of(int primaryColorId, int secondaryColorId, int glowColorId) {
        return new ColorSelection(primaryColorId, secondaryColorId, OptionalInt.of(glowColorId));
    }

    public int getPrimaryColorId() {
        return primaryColorId;
    }

    public int getSecondaryColorId() {
        return secondaryColorId;
    }

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
