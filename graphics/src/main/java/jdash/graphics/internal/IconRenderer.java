package jdash.graphics.internal;

import jdash.common.IconType;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.MissingResourceException;

import static jdash.graphics.internal.GraphicsUtils.renderLayers;
import static jdash.graphics.internal.GraphicsUtils.trim;

public final class IconRenderer {

    public static final int ICON_WIDTH = 300;
    public static final int ICON_HEIGHT = 300;

    private final List<? extends Drawable> elements;
    private final GameResourceContainer resources;

    private IconRenderer(List<? extends Drawable> elements, GameResourceContainer resources) {
        this.elements = elements;
        this.resources = resources;
    }

    public static IconRenderer load(IconType type, int id) {
        final var iconId = IconIdentifier.of(type, id);
        try {
            final var parser = GameSheetParser.parse(iconId.toSpriteResourceName(), iconId.toPlistResourceName());
            final var colors = GraphicsUtils.loadColors();
            List<? extends Drawable> elements;
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
            return new IconRenderer(elements, GameResourceContainer.of(colors, parser.getImage()));
        } catch (MissingResourceException e) {
            throw new IllegalArgumentException("Icon ID=" + id + " for type " + type.name() + " does not exist", e);
        }
    }

    public BufferedImage render(ColorSelection colorSelection) {
        return trim(renderLayers(elements, resources, colorSelection));
    }
}
