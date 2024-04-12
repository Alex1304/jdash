package jdash.graphics.internal;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;

import static jdash.graphics.internal.GraphicsUtils.reduceBrightness;
import static jdash.graphics.internal.GraphicsUtils.renderLayers;
import static jdash.graphics.internal.IconRenderer.ICON_HEIGHT;
import static jdash.graphics.internal.IconRenderer.ICON_WIDTH;

public final class AnimationFrame implements Drawable {

    private final List<Drawable> elements;
    private final Point2D.Double position;
    private final Point2D.Double scale;
    private final double rotation;
    private final int zValue;
    private final boolean isGlow;

    private AnimationFrame(List<Drawable> elements, Point2D.Double position, Point2D.Double scale, double rotation,
                           int zValue, boolean isGlow) {
        this.elements = elements;
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
        this.zValue = zValue;
        this.isGlow = isGlow;
    }

    public static AnimationFrame from(List<Drawable> elements, Map<String, String> fields, boolean isGlow) {
        final var position = GraphicsUtils.parsePoint(fields.get("position"));
        final var scale = GraphicsUtils.parsePoint(fields.get("scale"));
        final var rotation = Double.parseDouble(fields.get("rotation"));
        final var flipped = Tuple.parse(fields.get("flipped"), s -> Objects.equals(s, "1"));
        final var zValue = Integer.parseInt(fields.get("zValue"));
        final var actualScale = new Point2D.Double(
                flipped.getA() ? -scale.x : scale.x,
                flipped.getB() ? -scale.y : scale.y
        );
        final var sorted = new ArrayList<>(elements);
        Collections.sort(sorted);
        return new AnimationFrame(sorted, position, actualScale, rotation, zValue, isGlow);
    }

    @Override
    public int getWidth() {
        return ICON_WIDTH;
    }

    @Override
    public int getHeight() {
        return ICON_HEIGHT;
    }

    @Override
    public BufferedImage render(GameResourceContainer resources, ColorSelection colorSelection) {
        Image frame = renderLayers(elements, resources, colorSelection);
        if (!isGlow && zValue <= 2) {
            frame = reduceBrightness(frame);
        }
        final var result = new BufferedImage(ICON_WIDTH, ICON_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        final var g = result.createGraphics();
        final var halfW = ICON_WIDTH / 2.0;
        final var halfH = ICON_HEIGHT / 2.0;
        g.translate(position.x * 4.0, -position.y * 4.0);
        g.scale(scale.x, scale.y);
        g.translate(ICON_WIDTH * (1 / (2 * scale.x) - 0.5), ICON_HEIGHT * (1 / (2 * scale.y) - 0.5));
        g.rotate(Math.toRadians(rotation), halfW, halfH);
        g.drawImage(frame, 0, 0, null);
        g.dispose();
        return result;
    }

    @Override
    public int drawOrder() {
        return isGlow ? -999 : zValue;
    }
}
