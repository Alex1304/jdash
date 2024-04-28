package jdash.graphics;

import jdash.common.DemonDifficulty;
import jdash.common.Difficulty;
import jdash.common.QualityRating;
import jdash.common.entity.GDLevel;
import jdash.graphics.internal.SpriteElement;
import jdash.graphics.internal.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;

/**
 * Allows to generate images representing the difficulty of a level. It supports all difficulties as well as demon
 * difficulties as defined by the {@link Difficulty} and the {@link DemonDifficulty} enums respectively. You may as well
 * choose to display stars or moons below the difficulty icon, as well as a {@link QualityRating} that will add the
 * corresponding glowing effect around the difficulty icon.
 * <p>
 * You may use {@link #create(Difficulty)} or {@link #create(DemonDifficulty)} to generate an arbitrary combination of
 * difficulty, quality rating and stars/moons, or you may use {@link #forLevel(GDLevel)} for convenience if you are
 * manipulating level data.
 * <p>
 * This class is immutable: methods that enrich rendering specifications (prefixed by {@code with*}) always create a new
 * instance of this class.
 */
public final class DifficultyRenderer {

    private static final Map<Difficulty, String> DIFFICULTY_ASSET_NAMES = Map.ofEntries(
            Map.entry(Difficulty.NA, "difficulty_00_btn_001"),
            Map.entry(Difficulty.AUTO, "difficulty_auto_btn_001"),
            Map.entry(Difficulty.EASY, "difficulty_01_btn_001"),
            Map.entry(Difficulty.NORMAL, "difficulty_02_btn_001"),
            Map.entry(Difficulty.HARD, "difficulty_03_btn_001"),
            Map.entry(Difficulty.HARDER, "difficulty_04_btn_001"),
            Map.entry(Difficulty.INSANE, "difficulty_05_btn_001"),
            Map.entry(Difficulty.DEMON, "difficulty_06_btn_001")
    );

    private static final Map<DemonDifficulty, String> DEMON_DIFFICULTY_ASSET_NAMES = Map.ofEntries(
            Map.entry(DemonDifficulty.EASY, "difficulty_07_btn2_001"),
            Map.entry(DemonDifficulty.MEDIUM, "difficulty_08_btn2_001"),
            Map.entry(DemonDifficulty.HARD, "difficulty_06_btn2_001"),
            Map.entry(DemonDifficulty.INSANE, "difficulty_09_btn2_001"),
            Map.entry(DemonDifficulty.EXTREME, "difficulty_10_btn2_001")
    );

    private static final Map<QualityRating, String> QUALITY_RATING_ASSET_NAMES = Map.ofEntries(
            Map.entry(QualityRating.FEATURED, "GJ_featuredCoin_001"),
            Map.entry(QualityRating.EPIC, "GJ_epicCoin_001"),
            Map.entry(QualityRating.LEGENDARY, "GJ_epicCoin2_001"),
            Map.entry(QualityRating.MYTHIC, "GJ_epicCoin3_001")
    );

    private static final SpriteSheet SPRITE_SHEET = SpriteSheet.parse("/GJ_GameSheet03-uhd.png",
            "/GJ_GameSheet03-uhd.plist");

    /**
     * The width of the images rendered by this class.
     */
    public static final int WIDTH = 250;

    /**
     * The height of the images rendered by this class.
     */
    public static final int HEIGHT = 350;

    static {
        try (final var ttf = DifficultyRenderer.class.getResourceAsStream("/pusab.ttf")) {
            if (ttf == null) {
                throw new MissingResourceException("Font not found", DifficultyRenderer.class.getName(), "pusab.ttf");
            }
            final var ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, ttf));
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    private final Difficulty difficulty;
    private final DemonDifficulty demonDifficulty;
    private final QualityRating qualityRating;
    private final int rate;
    private final RewardType rewardType;

    private enum RewardType {
        NONE(null),
        STARS("star"),
        MOONS("moon"),
        DIAMONDS("diamond");

        private final String assetName;

        RewardType(String assetName) {this.assetName = assetName;}
    }

    private DifficultyRenderer(Difficulty difficulty, DemonDifficulty demonDifficulty, QualityRating qualityRating,
                               int rate, RewardType rewardType) {
        this.difficulty = difficulty;
        this.demonDifficulty = demonDifficulty;
        this.qualityRating = qualityRating;
        this.rate = rate;
        this.rewardType = rewardType;
    }

    /**
     * Creates a new {@link DifficultyRenderer} with the given difficulty. The resulting image will only show the
     * difficulty icon with no other information. Use {@code with*} methods to enrich the definition of this renderer.
     *
     * @param difficulty the difficulty to render
     * @return a new {@link DifficultyRenderer}
     */
    public static DifficultyRenderer create(Difficulty difficulty) {
        Objects.requireNonNull(difficulty);
        return new DifficultyRenderer(difficulty, null, QualityRating.NONE, 0, RewardType.NONE);
    }

    /**
     * Creates a new {@link DifficultyRenderer} with the given demon difficulty. The resulting image will only show the
     * demon difficulty icon with no other information. Use {@code with*} methods to enrich the definition of this
     * renderer.
     *
     * @param demonDifficulty the demon difficulty to render
     * @return a new {@link DifficultyRenderer}
     */
    public static DifficultyRenderer create(DemonDifficulty demonDifficulty) {
        Objects.requireNonNull(demonDifficulty);
        return new DifficultyRenderer(null, demonDifficulty, QualityRating.NONE, 0, RewardType.NONE);
    }

    /**
     * Creates a new {@link DifficultyRenderer} that is directly able to render the difficulty of the given level,
     * including quality rating, stars or moons when applicable.
     *
     * @param level the level providing all the information to render the difficulty
     * @return a new {@link DifficultyRenderer}
     */
    public static DifficultyRenderer forLevel(GDLevel level) {
        Objects.requireNonNull(level);
        RewardType rewardType;
        if (level.rewards() == 0) {
            rewardType = RewardType.NONE;
        } else if (level.isPlatformer()) {
            rewardType = RewardType.MOONS;
        } else {
            rewardType = RewardType.STARS;
        }
        return new DifficultyRenderer(
                level.isDemon() ? null : level.difficulty(),
                level.isDemon() ? level.demonDifficulty() : null,
                level.qualityRating(),
                level.rewards(),
                rewardType);
    }

    /**
     * Creates a new {@link DifficultyRenderer} identical to the current one but enriched with the given quality
     * rating.
     *
     * @param qualityRating the quality rating
     * @return a new {@link DifficultyRenderer}
     */
    public DifficultyRenderer withQualityRating(QualityRating qualityRating) {
        Objects.requireNonNull(qualityRating);
        return new DifficultyRenderer(difficulty, demonDifficulty, qualityRating, rate, rewardType);
    }

    /**
     * Creates a new {@link DifficultyRenderer} identical to the current one but enriched with the given stars.
     *
     * @param stars the stars
     * @return a new {@link DifficultyRenderer}
     */
    public DifficultyRenderer withStars(int stars) {
        return new DifficultyRenderer(difficulty, demonDifficulty, qualityRating, stars, RewardType.STARS);
    }

    /**
     * Creates a new {@link DifficultyRenderer} identical to the current one but enriched with the given moons.
     *
     * @param moons the moons
     * @return a new {@link DifficultyRenderer}
     */
    public DifficultyRenderer withMoons(int moons) {
        return new DifficultyRenderer(difficulty, demonDifficulty, qualityRating, moons, RewardType.MOONS);
    }

    /**
     * Creates a new {@link DifficultyRenderer} identical to the current one but enriched with the given diamonds.
     *
     * @param diamonds the diamonds
     * @return a new {@link DifficultyRenderer}
     */
    public DifficultyRenderer withDiamonds(int diamonds) {
        return new DifficultyRenderer(difficulty, demonDifficulty, qualityRating, diamonds, RewardType.DIAMONDS);
    }

    /**
     * Creates a new {@link DifficultyRenderer} identical to the current one, but without any star or moon value.
     * Typically used to cancel a previous use of {@link #withStars(int)} or {@link #withMoons(int)}.
     *
     * @return a new {@link DifficultyRenderer}
     */
    public DifficultyRenderer withoutStarsOrMoons() {
        return new DifficultyRenderer(difficulty, demonDifficulty, qualityRating, rate, RewardType.NONE);
    }

    /**
     * Renders an image of the difficulty using all the current information. All images have an identical size of
     * {@link #WIDTH}x{@link #HEIGHT}, and centered on a best-effort basis.
     *
     * @return the rendered image as a {@link BufferedImage}
     */
    public BufferedImage render() {
        if (difficulty != null) {
            return render(DIFFICULTY_ASSET_NAMES.get(difficulty), rate,
                    QUALITY_RATING_ASSET_NAMES.get(qualityRating), rewardType.assetName, false);
        } else if (demonDifficulty != null) {
            return render(DEMON_DIFFICULTY_ASSET_NAMES.get(demonDifficulty), rate,
                    QUALITY_RATING_ASSET_NAMES.get(qualityRating), rewardType.assetName, true);
        }
        throw new AssertionError();
    }

    private static BufferedImage render(String difficultyAssetName, int rewards, String qualityAssetName,
                                        String rewardType, boolean twoLines) {
        final var elements = SPRITE_SHEET.getSpriteElements();
        SpriteElement difficultyElement = null, qualityElement = null, starOrMoonElement = null;
        final var showRate = rewardType != null;
        for (final var element : elements) {
            if (element.getName().equals(difficultyAssetName)) {
                difficultyElement = element;
            } else if (element.getName().equals(qualityAssetName)) {
                qualityElement = element;
            } else if (showRate && element.getName().equals(rewardType + "_small01_001")) {
                starOrMoonElement = element;
            }
        }
        Objects.requireNonNull(difficultyElement);
        final var image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        final var g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.translate(0, (showRate ? -25 : 0) + (twoLines ? -15 : 0));
        if (qualityElement != null) {
            g.drawImage(qualityElement.render(SPRITE_SHEET.getImage()),
                    (WIDTH - qualityElement.getWidth()) / 2,
                    (HEIGHT - qualityElement.getHeight()) / 2, null);
        }
        final var difficultyX = (WIDTH - difficultyElement.getWidth()) / 2;
        final var difficultyY = (HEIGHT - difficultyElement.getHeight()) / 2;
        g.drawImage(difficultyElement.render(SPRITE_SHEET.getImage()), difficultyX, difficultyY, null);
        if (showRate) {
            Objects.requireNonNull(starOrMoonElement);
            final var starX = (WIDTH - starOrMoonElement.getWidth()) / 2 + 35;
            final var starY = starOrMoonElement.getHeight() / 2 + difficultyY + difficultyElement.getHeight() - 10;
            g.drawImage(starOrMoonElement.render(SPRITE_SHEET.getImage()), starX, starY, null);
            drawStarCount(g, "" + rewards, starX - 20, starY + 44);
        }
        g.dispose();
        return image;
    }

    private static void drawStarCount(Graphics2D g, String count, int x, int y) {
        final var originalColor = g.getColor();
        final var originalStroke = g.getStroke();
        final var originalTransform = g.getTransform();

        final var glyphVector = new Font("Pusab", Font.PLAIN, 55)
                .createGlyphVector(g.getFontRenderContext(), count);
        final var textShape = glyphVector.getOutline();
        g.translate(x - textShape.getBounds().width, y);
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(8));
        g.draw(textShape);
        g.setColor(Color.WHITE);
        g.fill(textShape);

        g.setColor(originalColor);
        g.setStroke(originalStroke);
        g.setTransform(originalTransform);
    }
}
