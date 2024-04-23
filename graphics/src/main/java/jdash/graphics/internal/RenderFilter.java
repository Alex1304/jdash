package jdash.graphics.internal;

import jdash.graphics.ColorSelection;

import java.awt.*;
import java.awt.image.BufferedImage;

import static jdash.graphics.IconRenderer.COLORS;
import static jdash.graphics.internal.GraphicsUtils.applyColor;

public interface RenderFilter {

    RenderFilter NONE = new RenderFilter() {

        @Override
        public boolean shouldRender(Renderable renderable) {
            return true;
        }

        @Override
        public Image filter(Renderable renderable, BufferedImage image) {
            return image;
        }
    };

    boolean shouldRender(Renderable renderable);

    Image filter(Renderable renderable, BufferedImage image);

    static RenderFilter applyingIconColors(ColorSelection colorSelection) {
        return new IconRenderFilter(colorSelection);
    }

    final class IconRenderFilter implements RenderFilter {

        private final ColorSelection colorSelection;

        IconRenderFilter(ColorSelection colorSelection) {
            this.colorSelection = colorSelection;
        }

        @Override
        public boolean shouldRender(Renderable renderable) {
            return !renderable.getName().contains("_glow_") || colorSelection.getGlowColorId().isPresent();
        }

        @Override
        public Image filter(Renderable renderable, BufferedImage image) {
            final var name = renderable.getName();
            Color colorToApply = null;
            if (name.contains("_glow_")) {
                colorToApply = COLORS.get(colorSelection.getGlowColorId().orElseThrow());
            } else if (name.contains("_2_00")) {
                colorToApply = COLORS.get(colorSelection.getSecondaryColorId());
            } else if (!name.contains("extra") && !name.contains("_3_00")) {
                colorToApply = COLORS.get(colorSelection.getPrimaryColorId());
            }
            return applyColor(image, colorToApply);
        }
    }
}
