package jdash.graphics.internal;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;

import static jdash.graphics.internal.GraphicsUtils.applyColor;
import static jdash.graphics.internal.GraphicsUtils.reduceBrightness;

public final class SpriteElement implements Drawable {

    private final String name;
    private final Point2D.Double spriteOffset;
    private final Rectangle2D.Double textureRect;
    private final boolean textureRotated;


    private SpriteElement(String name, Point2D.Double spriteOffset,Rectangle2D.Double textureRect,
                          boolean textureRotated) {
        this.name = name;
        this.spriteOffset = spriteOffset;
        this.textureRect = textureRect;
        this.textureRotated = textureRotated;
    }

    public static SpriteElement from(String name, Map<String, String> fields) {
        final var spriteOffset = GraphicsUtils.parsePoint(fields.get("spriteOffset"));
        final var textureRect = GraphicsUtils.parseRectangle(fields.get("textureRect"));
        final var textureRotated = Boolean.parseBoolean(fields.get("textureRotated"));
        return new SpriteElement(name, spriteOffset, textureRect, textureRotated);
    }

    @Override
    public AffineTransform getTransform() {
        final var rect = getTextureRectangle();
        final var transform = new AffineTransform();
        transform.translate(150 - rect.width / 2.0 + spriteOffset.x, 150 - rect.height / 2.0 - spriteOffset.y);
        if (textureRotated) {
            transform.rotate(Math.toRadians(-90), rect.width / 2.0, rect.height / 2.0);
        }
        return transform;
    }

    @Override
    public void draw(Graphics2D g, GameResourceContainer resources, ColorSelection colorSelection) {
        draw(g, resources, colorSelection, false);
    }

    void draw(Graphics2D g, GameResourceContainer resources, ColorSelection colorSelection, boolean dim) {
        if (name.contains("_glow_") && colorSelection.getGlowColorId().isEmpty()) {
            return;
        }
        final var bounds = getTextureRectangle().getBounds();
        final var gameSheet = resources.getGameSheet();
        final var subImage = gameSheet.getSubimage(bounds.x, bounds.y, bounds.width, bounds.height);
        Color colorToApply = null;
        if (name.contains("_glow_")) {
            colorToApply = resources.getColor(colorSelection.getGlowColorId().orElseThrow());
        } else if (name.contains("_2_00")) {
            colorToApply = resources.getColor(colorSelection.getSecondaryColorId());
        } else if (!name.contains("extra") && !name.contains("_3_00")) {
            colorToApply = resources.getColor(colorSelection.getPrimaryColorId());
        }
        var subImageColored = applyColor(subImage, colorToApply);
        if (dim) {
            subImageColored = reduceBrightness(subImageColored);
        }
        g.drawImage(subImageColored, 0, 0, null);
    }

    @Override
    public int drawOrder() {
        return name.contains("_glow_") ? -99 : 0;
    }

    public String getName() {
        return name;
    }

    public Point2D.Double getSpriteOffset() {
        return spriteOffset;
    }

    public boolean isTextureRotated() {
        return textureRotated;
    }

    public Rectangle2D.Double getTextureRectangle() {
        return new Rectangle2D.Double(textureRect.x, textureRect.y, textureRotated ? textureRect.height :
                textureRect.width, textureRotated ? textureRect.width : textureRect.height);
    }

    @Override
    public String toString() {
        return "SpriteElement{" +
                "name='" + name + '\'' +
                ", spriteOffset=" + spriteOffset +
                ", textureRect=" + textureRect +
                ", textureRotated=" + textureRotated +
                '}';
    }
}
