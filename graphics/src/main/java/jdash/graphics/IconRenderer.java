package jdash.graphics;

import jdash.common.IconType;
import jdash.graphics.internal.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.MissingResourceException;

import static jdash.graphics.internal.GraphicsUtils.renderLayers;
import static jdash.graphics.internal.GraphicsUtils.trim;

/**
 * Allows to generate arbitrary icons given an ID and an {@link IconType}.
 */
public final class IconRenderer {

    public static final int ICON_WIDTH = 300;
    public static final int ICON_HEIGHT = 300;

    private final List<? extends Renderable> elements;
    private final GameResourceContainer gameResources;

    private IconRenderer(List<? extends Renderable> elements, GameResourceContainer gameResources) {
        this.elements = elements;
        this.gameResources = gameResources;
    }

    /**
     * Loads the assets for the specified icon, so it is ready to be rendered later.
     *
     * @param type the icon type
     * @param id   the icon ID
     * @return an {@link IconRenderer} with assets loaded internally. Use {@link #render(ColorSelection)} to create an
     * image of it with the desired colors.
     */
    public static IconRenderer load(IconType type, int id) {
        final var iconId = IconIdentifier.of(type, id);
        try {
            final var parser = GameSheetParser.parse(iconId.toSpriteResourceName(), iconId.toPlistResourceName());
            final var colors = GraphicsUtils.loadColors();
            List<? extends Renderable> elements;
            if (type == IconType.ROBOT) {
                elements = new ArrayList<>(AnimationParser.parseFrames("/Robot_AnimDesc.plist",
                        "animationContainer.Robot_idle_001..png", parser.getSpriteElements()));
            } else if (type == IconType.SPIDER) {
                elements = new ArrayList<>(AnimationParser.parseFrames("/Spider_AnimDesc.plist",
                        "animationContainer.Spider_idle_001..png", parser.getSpriteElements()));
            } else {
                elements = new ArrayList<>(parser.getSpriteElements());
                Collections.reverse(elements);
            }
            Collections.sort(elements);
            return new IconRenderer(elements, new GameResourceContainer(colors, parser.getImage()));
        } catch (MissingResourceException e) {
            throw new IllegalArgumentException("Icon ID=" + id + " for type " + type.name() + " does not exist", e);
        }
    }

    /**
     * Renders the icon from the assets loaded previously via {@link #load(IconType, int)} using the specified colors.
     *
     * @param colorSelection the colors used to tint the icon assets
     * @return a {@link BufferedImage} of the rendered icon
     */
    public BufferedImage render(ColorSelection colorSelection) {
        return trim(renderLayers(elements, gameResources, colorSelection));
    }

    /**
     * Gets the game resources object that gives you access to colors and the raw sprite sheet data.
     *
     * @return the {@link GameResourceContainer}
     */
    public GameResourceContainer getGameResources() {
        return gameResources;
    }
}
