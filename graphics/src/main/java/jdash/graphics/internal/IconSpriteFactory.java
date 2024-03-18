package jdash.graphics.internal;

import jdash.common.IconType;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class IconSpriteFactory {

    private final Map<Integer, List<SpriteElement>> elements;

    private IconSpriteFactory(Map<Integer, List<SpriteElement>> elements) {
        this.elements = elements;
    }

    public static IconSpriteFactory create() {
        final var parser = GameSheetParser.parse("/GJ_GameSheetIcons-hd.png", "/GJ_GameSheetIcons-hd.plist");
        final var elements = parser.getSpriteElements()
                .stream()
                .collect(Collectors.groupingBy(element -> Integer.parseInt(element.getId())));
        for (Integer key : elements.keySet()) {
            elements.computeIfPresent(key, (k, oldV) -> GraphicsUtils.orderIconLayers(oldV));
        }
        System.out.println(elements);
        return new IconSpriteFactory(elements);
    }

    public BufferedImage makeIcon(IconType type, int id, int color1Id, int color2Id, boolean withGlowOutline) {
        return null;
    }
}
