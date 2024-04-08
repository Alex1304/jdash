package jdash.graphics.internal;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Map;

public final class AnimationFrame implements Drawable {

    final SpriteElement spriteElement;
    final Point2D.Double position;
    final Point2D.Double scale;
    final double rotation;
    final int zValue;

    private AnimationFrame(SpriteElement spriteElement, Point2D.Double position, Point2D.Double scale, double rotation,
                           int zValue) {
        this.spriteElement = spriteElement;
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
        this.zValue = zValue;
    }

    public static AnimationFrame from(SpriteElement spriteElement, Map<String, String> fields) {
        final var position = GraphicsUtils.parsePoint(fields.get("position"));
        final var scale = GraphicsUtils.parsePoint(fields.get("scale"));
        final var rotation = Double.parseDouble(fields.get("rotation"));
        final var flipped = Tuple.parse(fields.get("flipped"), Boolean::parseBoolean);
        final var zValue = Integer.parseInt(fields.get("zValue"));
        final var actualScale = new Point2D.Double(
                flipped.getA() ? -scale.x : scale.x,
                flipped.getB() ? -scale.y : scale.y
        );
        return new AnimationFrame(spriteElement, position, actualScale, rotation, zValue);
    }

    @Override
    public AffineTransform getTransform() {
        final var transform = new AffineTransform();
        final var rect = spriteElement.getSourceRectangle();
        final var factor = 4;
        transform.translate(
                150 - rect.width / 2.0 + spriteElement.getSpriteOffset().x + position.x * factor,
                150 - rect.height / 2.0 - spriteElement.getSpriteOffset().y - position.y * factor);
        transform.rotate(Math.toRadians(rotation - (spriteElement.isTextureRotated() ? 90 : 0)), rect.width / 2.0,
                rect.height / 2.0);
        transform.scale(scale.x, scale.y);
        return transform;
    }

    @Override
    public void draw(Graphics2D g, GameResourceContainer resources, ColorSelection colorSelection) {
        spriteElement.draw(g, resources, colorSelection, zValue <= 2 && !spriteElement.getName().contains("_glow_"));
    }

    @Override
    public int drawOrder() {
        return spriteElement.drawOrder() == 0 ? zValue : spriteElement.drawOrder();
    }
}
