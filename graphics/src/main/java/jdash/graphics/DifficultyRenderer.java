package jdash.graphics;

import jdash.common.DemonDifficulty;
import jdash.common.Difficulty;
import jdash.common.QualityRating;
import jdash.common.entity.GDLevel;
import jdash.graphics.internal.GameSheetParser;
import jdash.graphics.internal.RenderFilter;
import jdash.graphics.internal.SpriteElement;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Objects;

public final class DifficultyRenderer {

    private static final Map<Difficulty, String> DIFFICULTY_ASSET_NAMES = Map.ofEntries(
            Map.entry(Difficulty.NA, "difficulty_00_btn_001"),
            Map.entry(Difficulty.AUTO, "difficulty_auto_btn_001"),
            Map.entry(Difficulty.EASY, "difficulty_01_btn_001"),
            Map.entry(Difficulty.NORMAL, "difficulty_02_btn_001"),
            Map.entry(Difficulty.HARD, "difficulty_03_btn_001"),
            Map.entry(Difficulty.HARDER, "difficulty_04_btn_001"),
            Map.entry(Difficulty.INSANE, "difficulty_05_btn_001")
    );

    private static final Map<DemonDifficulty, String> DEMON_DIFFICULTY_ASSET_NAMES = Map.ofEntries(
            Map.entry(DemonDifficulty.EASY, "difficulty_06_btn2_001"),
            Map.entry(DemonDifficulty.MEDIUM, "difficulty_07_btn2_001"),
            Map.entry(DemonDifficulty.HARD, "difficulty_08_btn2_001"),
            Map.entry(DemonDifficulty.INSANE, "difficulty_09_btn2_001"),
            Map.entry(DemonDifficulty.EXTREME, "difficulty_10_btn2_001")
    );

    private static final Map<QualityRating, String> QUALITY_RATING_ASSET_NAMES = Map.ofEntries(
            Map.entry(QualityRating.FEATURED, "GJ_featuredCoin_001"),
            Map.entry(QualityRating.EPIC, "GJ_epicCoin_001"),
            Map.entry(QualityRating.LEGENDARY, "GJ_epicCoin2_001"),
            Map.entry(QualityRating.MYTHIC, "GJ_epicCoin3_001")
    );

    private DifficultyRenderer() {
        throw new AssertionError();
    }

    private static BufferedImage render(String difficultyAssetName, int starsOrMoons, String qualityAssetName,
                                        boolean isPlatformer) {
        final var parser = GameSheetParser.parse("/GJ_GameSheet03-uhd.png", "/GJ_GameSheet03-uhd.plist");
        final var elements = parser.getSpriteElements();
        SpriteElement difficultyElement = null, qualityElement = null;
        for (final var element : elements) {
            if (element.getName().equals(difficultyAssetName)) {
                difficultyElement = element;
            } else if (element.getName().equals(qualityAssetName)) {
                qualityElement = element;
            }
        }
        Objects.requireNonNull(difficultyElement);
        Objects.requireNonNull(qualityElement);
        final var difficultySprite = difficultyElement.render(parser.getImage(), RenderFilter.NONE);
        final var qualitySprite = qualityElement.render(parser.getImage(), RenderFilter.NONE);
        final var image = new BufferedImage(280, 400, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        final var g = image.createGraphics();
        g.translate(0, -50);
        g.drawImage(qualitySprite, (280 - qualityElement.getWidth()) / 2, (400 - qualityElement.getHeight()) / 2, null);
        g.drawImage(difficultySprite, (280 - difficultyElement.getWidth()) / 2,
                (400 - difficultyElement.getHeight()) / 2, null);
        g.dispose();
        return image;
    }

    public static BufferedImage render(Difficulty difficulty, int starsOrMoons, QualityRating qualityRating,
                                       boolean isPlatformer) {
        return render(DIFFICULTY_ASSET_NAMES.get(difficulty), starsOrMoons,
                QUALITY_RATING_ASSET_NAMES.get(qualityRating), isPlatformer);
    }

    public static BufferedImage render(DemonDifficulty demonDifficulty, int starsOrMoons, QualityRating qualityRating,
                                       boolean isPlatformer) {
        return render(DEMON_DIFFICULTY_ASSET_NAMES.get(demonDifficulty), starsOrMoons,
                QUALITY_RATING_ASSET_NAMES.get(qualityRating), isPlatformer);
    }

    public static BufferedImage render(GDLevel level) {
        if (level.isDemon()) {
            return render(level.demonDifficulty(), level.stars(), level.qualityRating(), level.isPlatformer());
        }
        return render(level.actualDifficulty(), level.stars(), level.qualityRating(), level.isPlatformer());
    }
}
