package jdash.graphics.internal;

import ij.IJ;
import ij.ImagePlus;
import jdash.common.IconType;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public final class IconSpriteFactory {

    private final Map<IconIdentifier, List<SpriteElement>> elements;
    private final Map<Integer, PlayerColor> colors;
    private final ImagePlus gameSheet;

    private IconSpriteFactory(Map<IconIdentifier, List<SpriteElement>> elements, Map<Integer, PlayerColor> colors,
                              ImagePlus gameSheet) {
        this.elements = elements;
        this.colors = colors;
        this.gameSheet = gameSheet;
    }

    public static IconSpriteFactory create() {
        final var parser = GameSheetParser.parse("/GJ_GameSheetIcons-hd.png", "/GJ_GameSheetIcons-hd.plist");
        final var colors = GraphicsUtils.loadColors();
        final var elements = parser.getSpriteElements()
                .stream()
                .map(element -> Map.entry(IconIdentifier.fromName(element.getName()), element))
                .collect(groupingBy(Map.Entry::getKey, mapping(Map.Entry::getValue, toUnmodifiableList())));
        for (final var key : elements.keySet()) {
            elements.computeIfPresent(key, (k, oldV) -> GraphicsUtils.orderIconLayers(oldV));
        }
        System.out.println(elements);
        return new IconSpriteFactory(elements, colors, parser.getImage());
    }

    public BufferedImage makeIcon(IconType type, int id, int color1Id, int color2Id, boolean withGlowOutline) {
        if (!colors.containsKey(color1Id)) {
            throw new IllegalArgumentException("Color1 ID=" + color1Id + " does not exist");
        }
        if (!colors.containsKey(color2Id)) {
            throw new IllegalArgumentException("Color2 ID=" + color2Id + " does not exist");
        }
        final var iconElements = elements.get(IconIdentifier.of(type, id));
        if (iconElements == null) {
            throw new IllegalArgumentException("Icon ID=" + id + " for type " + type.name() + " does not exist");
        }
        if (!withGlowOutline) {
            iconElements.removeIf(el -> el.getName().contains("_glow_"));
        }
        final var image = IJ.createImage("icon-" + type.name() + "-" + id, 250, 250, 1, 32);
        for (final var element : iconElements) {
            gameSheet.setRoi(
                    (int) element.getPosition().x,
                    (int) element.getPosition().y,
                    (int) element.getWidth(),
                    (int) element.getHeight());
        }
        return null;
    }
}
