package jdash.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Contains assets taken from the game (colors and sprite sheets).
 */
public final class GameResourceContainer {

    private final Map<Integer, Color> colors;
    private final BufferedImage spriteSheet;

    GameResourceContainer(Map<Integer, Color> colors, BufferedImage spriteSheet) {
        this.colors = colors;
        this.spriteSheet = spriteSheet;
    }

    /**
     * Checks whether the given color ID is a valid color.
     *
     * @param colorId the color ID
     * @return true if the color exists
     */
    public boolean colorExists(int colorId) {
        return colors.containsKey(colorId);
    }

    /**
     * Gets the {@link Color} corresponding to the given ID.
     *
     * @param colorId the color ID
     * @return the {@link Color}
     */
    public Color getColor(int colorId) {
        if (!colors.containsKey(colorId)) {
            throw new IllegalArgumentException("Color ID=" + colorId + " does not exist");
        }
        return colors.get(colorId);
    }

    /**
     * Gets the original sprite sheet asset from the game.
     *
     * @return a {@link BufferedImage} representing the sheet
     */
    public BufferedImage getSpriteSheet() {
        return spriteSheet;
    }

    @Override
    public String toString() {
        return "GameResourceContainer{" +
                "colors=" + colors +
                ", spriteSheet=" + spriteSheet +
                '}';
    }
}
