package jdash.graphics.internal;

import jdash.common.IconType;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public final class IconSpriteFactory {

    private final Map<IconIdentifier, List<SpriteElement>> elements;
    private final Map<Integer, PlayerColor> colors;

    private IconSpriteFactory(Map<IconIdentifier, List<SpriteElement>> elements, Map<Integer, PlayerColor> colors) {
        this.elements = elements;
        this.colors = colors;
    }

    public static IconSpriteFactory create() {
        final var parser = GameSheetParser.parse("/GJ_GameSheetIcons-hd.png", "/GJ_GameSheetIcons-hd.plist");
        final var colors = GraphicsUtils.loadColors();
        final var elements = parser.getSpriteElements()
                .stream()
                .map(element -> Map.entry(IconIdentifier.from(element.getName()), element))
                .collect(groupingBy(Map.Entry::getKey, mapping(Map.Entry::getValue, toUnmodifiableList())));
        for (final var key : elements.keySet()) {
            elements.computeIfPresent(key, (k, oldV) -> GraphicsUtils.orderIconLayers(oldV));
        }
        System.out.println(elements);
        return new IconSpriteFactory(elements, colors);
    }

    public BufferedImage makeIcon(IconType type, int id, int color1Id, int color2Id, boolean withGlowOutline) {
        return null;
    }
}
