package jdash.graphics.internal;

import java.awt.geom.Point2D;
import java.util.Map;

public final class SpriteElement {

    private final String name;
    private final Point2D.Double spriteOffset;
    private final Point2D.Double spriteSourceSize;
    private final Point2D.Double textureRect1;
    private final Point2D.Double textureRect2;
    private final boolean textureRotated;
    private final boolean duplicate;

    private SpriteElement(String name, Point2D.Double spriteOffset,
                          Point2D.Double spriteSourceSize,
                          Point2D.Double textureRect1, Point2D.Double textureRect2, boolean textureRotated,
                          boolean duplicate) {
        this.name = name;
        this.spriteOffset = spriteOffset;
        this.spriteSourceSize = spriteSourceSize;
        this.textureRect1 = textureRect1;
        this.textureRect2 = textureRect2;
        this.textureRotated = textureRotated;
        this.duplicate = duplicate;
    }

    public static SpriteElement from(String name, Map<String, String> fields) {
        final var spriteOffset = GraphicsUtils.parsePoint(fields.get("spriteOffset"));
        final var spriteSourceSize = GraphicsUtils.parsePoint(fields.get("spriteSourceSize"));
        final var textureRect = GraphicsUtils.parsePointPair(fields.get("textureRect"));
        final var textureRotated = Boolean.parseBoolean(fields.get("textureRotated"));
        return new SpriteElement(name, spriteOffset,
                spriteSourceSize, textureRect[0], textureRect[1], textureRotated, false);
    }


    public SpriteElement duplicate() {
        return new SpriteElement(name, spriteOffset, spriteSourceSize, textureRect1, textureRect2,
                textureRotated, true);
    }

    public String getName() {
        return name;
    }

    public Point2D.Double getSpriteOffset() {
        return spriteOffset;
    }

    public Point2D.Double getSpriteSourceSize() {
        return spriteSourceSize;
    }

    public Point2D.Double getTextureRect1() {
        return textureRect1;
    }

    public Point2D.Double getTextureRect2() {
        return textureRect2;
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
                ", textureRect1=" + textureRect1 +
                ", textureRect2=" + textureRect2 +
                ", textureRotated=" + textureRotated +
                '}';
    }
}
