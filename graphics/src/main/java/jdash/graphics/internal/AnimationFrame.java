package jdash.graphics.internal;

import jdash.graphics.IconRenderer;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;

import static jdash.graphics.internal.GraphicsUtils.reduceBrightness;
import static jdash.graphics.internal.GraphicsUtils.renderLayers;

public final class AnimationFrame implements Renderable {

    public static final int ICON_WIDTH = 300;
    public static final int ICON_HEIGHT = 300;

    private final List<? extends Renderable> elements;
    private final Point2D.Double position;
    private final Point2D.Double scale;
    private final double rotation;
    private final int zValue;
    private final boolean isGlow;

    private AnimationFrame(List<? extends Renderable> elements, Point2D.Double position, Point2D.Double scale,
                           double rotation,
                           int zValue, boolean isGlow) {
        this.elements = elements;
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
        this.zValue = zValue;
        this.isGlow = isGlow;
    }

    public static AnimationFrame from(List<? extends Renderable> elements, Map<String, String> fields, boolean isGlow) {
        final var position = GraphicsUtils.parsePoint(fields.get("position"));
        final var scale = GraphicsUtils.parsePoint(fields.get("scale"));
        final var rotation = Double.parseDouble(fields.get("rotation"));
        final var flipped = Tuple.parse(fields.get("flipped"), s -> Objects.equals(s, "1"));
        final var zValue = Integer.parseInt(fields.get("zValue"));
        final var actualScale = new Point2D.Double(
                flipped.a() ? -scale.x : scale.x,
                flipped.b() ? -scale.y : scale.y
        );
        final var sorted = new ArrayList<>(elements);
        Collections.sort(sorted);
        return new AnimationFrame(sorted, position, actualScale, rotation, zValue, isGlow);
    }

    @Override
    public String getName() {
        return StringUtils.getCommonPrefix(elements.stream().map(Renderable::getName).toArray(String[]::new));
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
    public BufferedImage render(BufferedImage spriteSheet, RenderFilter filter) {
        Image frame = renderLayers(elements, spriteSheet, filter);
        if (!isGlow && zValue <= 2) {
            frame = reduceBrightness(frame);
        }
        final var result = new BufferedImage(ICON_WIDTH, ICON_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        final var g = result.createGraphics();
        if (IconRenderer.isAntialiasingEnabled()) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        }
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
    public int getZIndex() {
        return isGlow ? -999 : zValue;
    }

    @Override
    public String toString() {
        return "AnimationFrame{" +
                "elements=" + elements +
                ", position=" + position +
                ", scale=" + scale +
                ", rotation=" + rotation +
                ", zValue=" + zValue +
                ", isGlow=" + isGlow +
                '}';
    }
}
