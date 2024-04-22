package jdash.graphics;

import jdash.common.DemonDifficulty;
import jdash.common.Difficulty;
import jdash.common.QualityRating;
import jdash.graphics.internal.GameSheetParser;
import jdash.graphics.internal.RenderController;

import java.awt.image.BufferedImage;

public final class DifficultyRenderer {

    private DifficultyRenderer() {
        throw new AssertionError();
    }

    public static BufferedImage render(Difficulty difficulty, int starsOrMoons, QualityRating qualityRating,
                                       boolean isPlatformer) {
        final var parser = GameSheetParser.parse("/GJ_GameSheet03-uhd.png", "/GJ_GameSheet03-uhd.plist");
        final var elements = parser.getSpriteElements();
        final var icon = elements.stream()
                .filter(el -> el.getName().equals("difficulty_02_btn_001"))
                .findAny()
                .orElseThrow();
        return icon.render(parser.getImage(), RenderController.NONE);
    }

    public static BufferedImage render(DemonDifficulty demonDifficulty, int starsOrMoons, QualityRating qualityRating,
                                       boolean isPlatformer) {
        return null;
    }
}
