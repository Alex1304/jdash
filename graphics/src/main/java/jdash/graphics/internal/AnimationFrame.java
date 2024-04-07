package jdash.graphics.internal;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;

final class AnimationFrame {

    private final String name;
    private final List<Texture> textures;

    AnimationFrame(String name, List<Texture> textures) {
        this.name = name;
        this.textures = textures;
    }

    static final class Texture implements Drawable {

        final SpriteElement spriteElement;
        final Point2D.Double position;
        final Point2D.Double scale;
        final double rotation;
        final int zValue;

        private Texture(SpriteElement spriteElement, Point2D.Double position, Point2D.Double scale, double rotation,
                        int zValue) {
            this.spriteElement = spriteElement;
            this.position = position;
            this.scale = scale;
            this.rotation = rotation;
            this.zValue = zValue;
        }

        static Texture from(SpriteElement spriteElement, Map<String, String> fields) {
            final var position = GraphicsUtils.parsePoint(fields.get("position"));
            final var scale = GraphicsUtils.parsePoint(fields.get("scale"));
            final var rotation = Double.parseDouble(fields.get("rotation"));
            final var flipped = Tuple.parse(fields.get("flipped"), Boolean::parseBoolean);
            final var zValue = Integer.parseInt(fields.get("zValue"));
            final var actualScale = new Point2D.Double(
                    flipped.getA() ? -scale.x : scale.x,
                    flipped.getB() ? -scale.y : scale.y
            );
            return new Texture(spriteElement, position, actualScale, rotation, zValue);
        }

        @Override
        public AffineTransform getTransform() {
            final var transform = spriteElement.getTransform();
            return transform;
        }

        @Override
        public void draw(Graphics2D g, GameResourceContainer resources, ColorSelection colorSelection) {

        }

        @Override
        public int drawOrder() {
            return zValue;
        }
    }
}
