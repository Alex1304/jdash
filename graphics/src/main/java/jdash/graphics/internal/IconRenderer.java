package jdash.graphics.internal;

import jdash.common.IconType;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.MissingResourceException;

public final class IconRenderer {

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
        final var image = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
        elements.forEach(element -> {
            final var g = image.createGraphics();
            g.setTransform(element.getTransform());
            element.draw(g, resources, colorSelection);
            g.dispose();
        });
        return image;
    }
}
