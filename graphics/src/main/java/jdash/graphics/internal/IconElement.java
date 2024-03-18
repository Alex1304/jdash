package jdash.graphics.internal;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Map;

public final class IconElement {

    private final String type;
    private final String id;
    private final String part;
    private final Point2D.Double spriteOffset;
    private final Point2D.Double spriteSourceSize;
    private final Point2D.Double textureRect1;
    private final Point2D.Double textureRect2;
    private final boolean textureRotated;

    private IconElement(String type, String id, String part, Point2D.Double spriteOffset,
                        Point2D.Double spriteSourceSize,
                        Point2D.Double textureRect1, Point2D.Double textureRect2, boolean textureRotated) {
        this.type = type;
        this.id = id;
        this.part = part;
        this.spriteOffset = spriteOffset;
        this.spriteSourceSize = spriteSourceSize;
        this.textureRect1 = textureRect1;
        this.textureRect2 = textureRect2;
        this.textureRotated = textureRotated;
    }

    public static IconElement from(String baseName, Map<String, String> fields) {
        final var typeIdPart = baseName.split("_", 3);
        final var spriteOffset = GraphicsUtils.parsePoint(fields.get("spriteOffset"));
        final var spriteSourceSize = GraphicsUtils.parsePoint(fields.get("spriteSourceSize"));
        final var textureRect = GraphicsUtils.parsePointPair(fields.get("textureRect"));
        final var textureRotated = Boolean.parseBoolean(fields.get("textureRotated"));
        return new IconElement(typeIdPart[0], typeIdPart[1], typeIdPart.length < 3 ? "" : typeIdPart[2], spriteOffset,
                spriteSourceSize, textureRect[0], textureRect[1], textureRotated);
    }

    @Override
    public String toString() {
        return "IconElement{" +
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
