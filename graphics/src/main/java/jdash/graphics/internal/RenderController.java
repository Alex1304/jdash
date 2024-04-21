package jdash.graphics.internal;

import jdash.graphics.ColorSelection;

import java.awt.*;
import java.awt.image.BufferedImage;

import static jdash.graphics.IconRenderer.COLORS;
import static jdash.graphics.internal.GraphicsUtils.applyColor;

public interface RenderController {

    RenderController NONE = new RenderController() {

        @Override
        public boolean shouldRender(Renderable renderable) {
            return true;
        }

        @Override
        public Image postprocess(Renderable renderable, BufferedImage image) {
            return image;
        }
    };

    boolean shouldRender(Renderable renderable);

    Image postprocess(Renderable renderable, BufferedImage image);

    static RenderController icon(ColorSelection colorSelection) {
        return new IconRenderController(colorSelection);
    }

    final class IconRenderController implements RenderController {

        private final ColorSelection colorSelection;

        IconRenderController(ColorSelection colorSelection) {
            this.colorSelection = colorSelection;
        }

        @Override
        public boolean shouldRender(Renderable renderable) {
            return !renderable.getName().contains("_glow_") || colorSelection.getGlowColorId().isPresent();
        }

        @Override
        public Image postprocess(Renderable renderable, BufferedImage image) {
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
