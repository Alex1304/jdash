package jdash.graphics.internal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public final class GameResourceContainer {

    private final Map<Integer, ? extends PlayerColor> colors;
    private final BufferedImage gameSheet;

    private GameResourceContainer(Map<Integer, ? extends PlayerColor> colors, BufferedImage gameSheet) {
        this.colors = colors;
        this.gameSheet = gameSheet;
    }

    public static GameResourceContainer of(Map<Integer, ? extends PlayerColor> colors, BufferedImage gameSheet) {
        return new GameResourceContainer(colors, gameSheet);
    }

    public boolean colorExists(int colorId) {
        return colors.containsKey(colorId);
    }

    public Color getColor(int colorId) {
        if (!colors.containsKey(colorId)) {
            throw new IllegalArgumentException("Color ID=" + colorId + " does not exist");
        }
        return colors.get(colorId).toColor();
    }

    public BufferedImage getGameSheet() {
        return gameSheet;
    }

    @Override
    public String toString() {
        return "GameResourceContainer{" +
                "colors=" + colors +
                ", gameSheet=" + gameSheet +
                '}';
    }
}
