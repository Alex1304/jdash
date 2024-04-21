package jdash.graphics;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdash.common.IconType;
import jdash.graphics.internal.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.*;
import java.util.List;

import static jdash.graphics.internal.GraphicsUtils.renderLayers;
import static jdash.graphics.internal.GraphicsUtils.trim;

/**
 * Allows to generate arbitrary icons given an ID and an {@link IconType}.
 */
public final class IconRenderer {

    public static final Map<Integer, Color> COLORS = loadColors();
    private final List<? extends Renderable> elements;
    private final BufferedImage spriteSheet;

    private IconRenderer(List<? extends Renderable> elements, BufferedImage spriteSheet) {
        this.elements = elements;
        this.spriteSheet = spriteSheet;
    }

    private static Map<Integer, Color> loadColors() {
        try {
            final var colorsFile = GraphicsUtils.class.getResource("/colors.json");
            if (colorsFile == null) {
                throw new AssertionError("/colors.json not found");
            }
            final var objectMapper = new ObjectMapper();
            final var object = objectMapper.readTree(colorsFile);
            final var fields = object.fields();
            final var colorMap = new HashMap<Integer, Color>();
            while (fields.hasNext()) {
                final var field = fields.next();
                final var name = field.getKey();
                final var element = field.getValue();
                final var colorValue = objectMapper.treeToValue(element, PlayerColor.class);
                colorMap.put(Integer.parseInt(name), colorValue.toColor());
            }
            return colorMap;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
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
            return new IconRenderer(elements, parser.getImage());
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
        return trim(renderLayers(elements, spriteSheet, RenderController.icon(colorSelection)));
    }

    /**
     * Gets the original sprite sheet of the icon from the game.
     *
     * @return a {@link BufferedImage}
     */
    public BufferedImage getSpriteSheet() {
        return spriteSheet;
    }
}
