package jdash.graphics.internal;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public final class SpriteElement {

    private final String type;
    private final String id;
    private final String part;
    private final Point2D.Double spriteOffset;
    private final Point2D.Double spriteSourceSize;
    private final Point2D.Double textureRect1;
    private final Point2D.Double textureRect2;
    private final boolean textureRotated;
    private final boolean duplicate;

    private SpriteElement(String type, String id, String part, Point2D.Double spriteOffset,
                          Point2D.Double spriteSourceSize,
                          Point2D.Double textureRect1, Point2D.Double textureRect2, boolean textureRotated,
                          boolean duplicate) {
        this.type = type;
        this.id = id;
        this.part = part;
        this.spriteOffset = spriteOffset;
        this.spriteSourceSize = spriteSourceSize;
        this.textureRect1 = textureRect1;
        this.textureRect2 = textureRect2;
        this.textureRotated = textureRotated;
        this.duplicate = duplicate;
    }

    public static SpriteElement from(String baseName, Map<String, String> fields) {
        final var typeIdPart = baseName.replace("player_ball", "ball").split("_", 3);
        final var spriteOffset = GraphicsUtils.parsePoint(fields.get("spriteOffset"));
        final var spriteSourceSize = GraphicsUtils.parsePoint(fields.get("spriteSourceSize"));
        final var textureRect = GraphicsUtils.parsePointPair(fields.get("textureRect"));
        final var textureRotated = Boolean.parseBoolean(fields.get("textureRotated"));
        return new SpriteElement(typeIdPart[0], typeIdPart[1], typeIdPart.length < 3 ? "" : typeIdPart[2], spriteOffset,
                spriteSourceSize, textureRect[0], textureRect[1], textureRotated, false);
    }


    public SpriteElement duplicate() {
        return new SpriteElement(type, id, part, spriteOffset, spriteSourceSize, textureRect1, textureRect2,
                textureRotated, true);
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getPart() {
        return part;
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
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", part='" + part + '\'' +
                ", spriteOffset=" + spriteOffset +
                ", spriteSourceSize=" + spriteSourceSize +
                ", textureRect1=" + textureRect1 +
                ", textureRect2=" + textureRect2 +
                ", textureRotated=" + textureRotated +
                '}';
    }
}
