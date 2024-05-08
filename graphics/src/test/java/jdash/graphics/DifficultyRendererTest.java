package jdash.graphics;

import jdash.common.DemonDifficulty;
import jdash.common.Difficulty;
import jdash.common.Length;
import jdash.common.QualityRating;
import jdash.common.entity.GDLevel;
import jdash.common.entity.GDSong;
import jdash.common.internal.InternalUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static jdash.graphics.test.ImageTestUtils.*;

public final class DifficultyRendererTest {

    @BeforeAll
    public static void beforeAll() {
        DifficultyRenderer.enableAntialiasing(false);
    }

    @Test
    public void shouldRenderNA() throws IOException {
        final var image = DifficultyRenderer.create(Difficulty.NA).render();
        // writeToTempDir(image, "tests/na.png");
        assertImageEquals(loadTestImage("/tests/na.png"), image);
    }

    @Test
    public void shouldRenderHard5Stars() throws IOException {
        final var image = DifficultyRenderer.create(Difficulty.HARD)
                .withStars(5)
                .render();
        // writeToTempDir(image, "tests/hard-5-stars.png");
        assertImageEquals(loadTestImage("/tests/hard-5-stars.png"), image);
    }

    @Test
    public void shouldRenderHarder7StarsFeatured() throws IOException {
        final var image = DifficultyRenderer.create(Difficulty.HARDER)
                .withStars(7)
                .withQualityRating(QualityRating.FEATURED)
                .render();
        // writeToTempDir(image, "tests/harder-7-stars-featured.png");
        assertImageEquals(loadTestImage("/tests/harder-7-stars-featured.png"), image);
    }

    @Test
    public void shouldRenderInsane8MoonsEpic() throws IOException {
        final var image = DifficultyRenderer.create(Difficulty.INSANE)
                .withMoons(8)
                .withQualityRating(QualityRating.EPIC)
                .render();
        // writeToTempDir(image, "tests/insane-8-moons-epic.png");
        assertImageEquals(loadTestImage("/tests/insane-8-moons-epic.png"), image);
    }

    @Test
    public void shouldRenderEasyDemon10MoonsLegendary() throws IOException {
        final var image = DifficultyRenderer.create(DemonDifficulty.EASY)
                .withMoons(10)
                .withQualityRating(QualityRating.LEGENDARY)
                .render();
        // writeToTempDir(image, "tests/demon-easy-10-stars-legendary.png");
        assertImageEquals(loadTestImage("/tests/demon-easy-10-stars-legendary.png"), image);
    }

    @Test
    public void shouldRenderAuto1StarMythicStarsLegendary() throws IOException {
        final var image = DifficultyRenderer.create(Difficulty.AUTO)
                .withStars(1)
                .withQualityRating(QualityRating.MYTHIC)
                .render();
        // writeToTempDir(image, "tests/auto-1-star-mythic.png");
        assertImageEquals(loadTestImage("/tests/auto-1-star-mythic.png"), image);
    }

    @Test
    public void shouldRenderExtremeDemonMythic() throws IOException {
        final var image = DifficultyRenderer.create(DemonDifficulty.EXTREME)
                .withMoons(10)
                .withQualityRating(QualityRating.MYTHIC)
                .withoutStarsOrMoons()
                .render();
        // writeToTempDir(image, "tests/demon-extreme-mythic.png");
        assertImageEquals(loadTestImage("/tests/demon-extreme-mythic.png"), image);
    }

    @Test
    public void shouldRenderMediumDemon10Diamonds() throws IOException {
        final var image = DifficultyRenderer.create(DemonDifficulty.EASY)
                .withDiamonds(10)
                .render();
        // writeToTempDir(image, "tests/demon-medium-10-diamonds.png");
        assertImageEquals(loadTestImage("/tests/demon-medium-10-diamonds.png"), image);
    }

    @Test
    public void shouldRenderWhateverTheFuckYouWant() throws IOException {
        final var image = DifficultyRenderer.create(Difficulty.NA)
                .withDiamonds(-99)
                .withQualityRating(QualityRating.MYTHIC)
                .render();
        // writeToTempDir(image, "tests/na--99-diamonds-mythic.png");
        assertImageEquals(loadTestImage("/tests/na--99-diamonds-mythic.png"), image);
    }

    @Test
    public void shouldRenderForLevel() throws IOException {
        final var level = new GDLevel(
                10565740,
                "Bloodbath",
                503085,
                "Whose blood will be spilt in the Bloodbath? Who will the victors be? How many will " +
                        "survive? Good luck...",
                Difficulty.INSANE,
                DemonDifficulty.EXTREME,
                10,
                10330,
                QualityRating.FEATURED,
                26672952,
                1505455,
                Length.LONG,
                0,
                false,
                3,
                21,
                24746,
                true,
                false,
                Optional.of(7679228L),
                0,
                Optional.of(467339L),
                Optional.of(new GDSong(
                        467339,
                        "At the Speed of Light",
                        "Dimrain47",
                        Optional.of("9.56"),
                        Optional.ofNullable(InternalUtils.urlDecode("http%3A%2F%2Faudio.ngfiles" +
                                ".com%2F467000%2F467339_At_the_Speed_of_Light_FINA.mp3")))),
                Optional.of("Riot"),
                Optional.of(37415L)
        );
        final var image = DifficultyRenderer.forLevel(level).render();
        // writeToTempDir(image, "tests/level.png");
        assertImageEquals(loadTestImage("/tests/level.png"), image);
    }
}