package jdash.graphics.internal;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import static jdash.graphics.internal.GraphicsUtils.applyColor;

public final class SpriteElement implements Drawable {

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
    public int getWidth() {
        return spriteSourceSize.width;
    }

    @Override
    public int getHeight() {
        return spriteSourceSize.height;
    }

    @Override
    public BufferedImage render(GameResourceContainer resources, ColorSelection colorSelection) {
        if (name.contains("_glow_") && colorSelection.getGlowColorId().isEmpty()) {
            return new BufferedImage(0, 0, BufferedImage.TYPE_INT_ARGB);
        }
        final var rect = new Rectangle2D.Double(textureRect.x, textureRect.y,
                textureRotated ? textureRect.height : textureRect.width,
                textureRotated ? textureRect.width : textureRect.height);
        final var bounds = rect.getBounds();
        final var width = spriteSourceSize.width;
        final var height = spriteSourceSize.height;
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
        final var image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final var g = image.createGraphics();
        g.translate(width / 2.0 - rect.width / 2.0 + spriteOffset.x,
                height / 2.0 - rect.height / 2.0 - spriteOffset.y);
        if (textureRotated) {
            g.rotate(Math.toRadians(-90), rect.width / 2.0, rect.height / 2.0);
        }
        g.drawImage(subImageColored, 0, 0, null);
        g.dispose();
        return image;
    }

    @Override
    public int drawOrder() {
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

    public String getName() {
        return name;
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
