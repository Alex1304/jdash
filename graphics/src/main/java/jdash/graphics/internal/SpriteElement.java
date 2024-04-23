package jdash.graphics.internal;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Map;

public final class SpriteElement implements Renderable {

    private final String name;
    private final Point2D.Double spriteOffset;
    private final Dimension spriteSourceSize;
    private final Rectangle2D.Double textureRect;
    private final boolean textureRotated;


    private SpriteElement(String name, Point2D.Double spriteOffset, Dimension spriteSourceSize,
                          Rectangle2D.Double textureRect, boolean textureRotated) {
        this.name = name;
        this.spriteOffset = spriteOffset;
        this.spriteSourceSize = spriteSourceSize;
        this.textureRect = textureRect;
        this.textureRotated = textureRotated;
    }

    public static SpriteElement from(String name, Map<String, String> fields) {
        final var spriteOffset = GraphicsUtils.parsePoint(fields.get("spriteOffset"));
        final var spriteSourceSize = GraphicsUtils.parseDimension(fields.get("spriteSourceSize"));
        final var textureRect = GraphicsUtils.parseRectangle(fields.get("textureRect"));
        final var textureRotated = Boolean.parseBoolean(fields.get("textureRotated"));
        return new SpriteElement(name, spriteOffset, spriteSourceSize, textureRect, textureRotated);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getWidth() {
        return spriteSourceSize.width;
    }

    @Override
    public int getHeight() {
        return spriteSourceSize.height;
    }

    @Override
    public BufferedImage render(BufferedImage spriteSheet, RenderFilter filter) {
        final var width = spriteSourceSize.width;
        final var height = spriteSourceSize.height;
        final var image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        if (!filter.shouldRender(this)) {
            return image;
        }
        final var rect = new Rectangle2D.Double(textureRect.x, textureRect.y,
                textureRotated ? textureRect.height : textureRect.width,
                textureRotated ? textureRect.width : textureRect.height);
        final var bounds = rect.getBounds();
        final var subImage = spriteSheet.getSubimage(bounds.x, bounds.y, bounds.width, bounds.height);
        final var toRender = filter.filter(this, subImage);
        final var g = image.createGraphics();
        g.translate(width / 2.0 - rect.width / 2.0 + spriteOffset.x,
                height / 2.0 - rect.height / 2.0 - spriteOffset.y);
        if (textureRotated) {
            g.rotate(Math.toRadians(-90), rect.width / 2.0, rect.height / 2.0);
        }
        g.drawImage(toRender, 0, 0, null);
        g.dispose();
        return image;
    }

    @Override
    public int getZIndex() {
        if (name.contains("_glow_")) {
            return -999;
        }
        if (name.contains("_2_00") || name.contains("_3_00")) {
            return 0;
        }
        if (name.contains("extra")) {
            return 2;
        }
        return 1;
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
