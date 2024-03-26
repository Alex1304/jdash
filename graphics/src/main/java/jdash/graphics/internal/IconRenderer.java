package jdash.graphics.internal;

import jdash.common.IconType;

import java.awt.image.BufferedImage;
import java.util.*;

public final class IconRenderer {

    private final List<SpriteElement> elements;
    private final Map<Integer, PlayerColor> colors;
    private final BufferedImage gameSheet;

    private IconRenderer(List<SpriteElement> elements, Map<Integer, PlayerColor> colors,
                         BufferedImage gameSheet) {
        this.elements = elements;
        this.colors = colors;
        this.gameSheet = gameSheet;
    }

    public static IconRenderer load(IconType type, int id) {
        final var iconId = IconIdentifier.of(type, id);
        try {
            final var parser = GameSheetParser.parse(iconId.toSpriteResourceName(), iconId.toPlistResourceName());
            final var colors = GraphicsUtils.loadColors();
            return new IconRenderer(parser.getSpriteElements(), colors, parser.getImage());
        } catch (MissingResourceException e) {
            throw new IllegalArgumentException("Icon ID=" + id + " for type " + type.name() + " does not exist", e);
        }
    }

    public BufferedImage render(int color1Id, int color2Id, boolean withGlowOutline) {
        if (!colors.containsKey(color1Id)) {
            throw new IllegalArgumentException("Color1 ID=" + color1Id + " does not exist");
        }
        if (!colors.containsKey(color2Id)) {
            throw new IllegalArgumentException("Color2 ID=" + color2Id + " does not exist");
        }
        final var elements = new ArrayList<>(this.elements);
        if (!withGlowOutline) {
            elements.removeIf(el -> el.getName().contains("_glow_"));
        }
        Collections.reverse(elements);
        final var image = new BufferedImage(250, 250, BufferedImage.TYPE_INT_ARGB);
        final var g = image.createGraphics();
        final var initialTransform = g.getTransform();
        for (final var element : elements) {
            final var rect = element.getSourceRectangle();
            final var subImage = gameSheet.getSubimage(rect.x, rect.y, rect.width, rect.height);
            final var offset = element.getSpriteOffset();
            g.translate(125 - rect.width / 2 + offset.x, 125 - rect.height / 2 - offset.y);
            if (element.isTextureRotated()) {
                g.rotate(Math.toRadians(-90), rect.width / 2.0, rect.height / 2.0);
            }
            g.drawImage(subImage, 0, 0, null);
            g.setTransform(initialTransform);
        }
        return image;
    }
}
