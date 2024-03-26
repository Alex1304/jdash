package jdash.graphics.internal;

import java.awt.*;
import java.util.Map;

public final class SpriteElement {

    private final String name;
    private final Point spriteOffset;
    private final Point spriteSourceSize;
    private final Rectangle textureRect;
    private final boolean textureRotated;
    private final boolean duplicate;

    private SpriteElement(String name, Point spriteOffset,
                          Point spriteSourceSize,
                          Rectangle textureRect, boolean textureRotated,
                          boolean duplicate) {
        this.name = name;
        this.spriteOffset = spriteOffset;
        this.spriteSourceSize = spriteSourceSize;
        this.textureRect = textureRect;
        this.textureRotated = textureRotated;
        this.duplicate = duplicate;
    }

    public static SpriteElement from(String name, Map<String, String> fields) {
        final var spriteOffset = GraphicsUtils.parsePoint(fields.get("spriteOffset"));
        final var spriteSourceSize = GraphicsUtils.parsePoint(fields.get("spriteSourceSize"));
        final var textureRect = GraphicsUtils.parseRectangle(fields.get("textureRect"));
        final var textureRotated = Boolean.parseBoolean(fields.get("textureRotated"));
        return new SpriteElement(name, spriteOffset, spriteSourceSize, textureRect, textureRotated, false);
    }


    public SpriteElement duplicate() {
        return new SpriteElement(name, spriteOffset, spriteSourceSize, textureRect,
                textureRotated, true);
    }

    public String getName() {
        return name;
    }

    public Point getSpriteOffset() {
        return spriteOffset;
    }

    public Point getSpriteSourceSize() {
        return spriteSourceSize;
    }

    public Rectangle getSourceRectangle() {
        //noinspection SuspiciousNameCombination
        return textureRotated ?
                new Rectangle(textureRect.x, textureRect.y, textureRect.height, textureRect.width) :
                textureRect;
    }

    public boolean isTextureRotated() {
        return textureRotated;
    }

    public boolean isDuplicate() {
        return duplicate;
    }

    @Override
    public String toString() {
        return "SpriteElement{" +
                "name='" + name + '\'' +
                ", spriteOffset=" + spriteOffset +
                ", spriteSourceSize=" + spriteSourceSize +
                ", textureRect=" + textureRect +
                ", textureRotated=" + textureRotated +
                '}';
    }
}
