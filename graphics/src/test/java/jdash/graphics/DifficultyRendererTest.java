package jdash.graphics;

import jdash.common.DemonDifficulty;
import jdash.common.Difficulty;
import jdash.common.Length;
import jdash.common.QualityRating;
import jdash.common.entity.ImmutableGDLevel;
import jdash.common.entity.ImmutableGDSong;
import jdash.common.internal.InternalUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static jdash.graphics.test.ImageTestUtils.*;

public final class DifficultyRendererTest {

    @Test
    public void shouldRenderNA() throws IOException {
        final var image = DifficultyRenderer.create(Difficulty.NA).render();
        assertImageEquals(loadTestImage("/tests/na.png"), image);
    }

    @Test
    public void shouldRenderHard5Stars() throws IOException {
        final var image = DifficultyRenderer.create(Difficulty.HARD)
                .withStars(5)
                .render();
        assertImageEquals(loadTestImage("/tests/hard-5-stars.png"), image);
    }

    @Test
    public void shouldRenderHarder7StarsFeatured() throws IOException {
        final var image = DifficultyRenderer.create(Difficulty.HARDER)
                .withStars(7)
                .withQualityRating(QualityRating.FEATURED)
                .render();
        assertImageEquals(loadTestImage("/tests/harder-7-stars-featured.png"), image);
    }

    @Test
    public void shouldRenderInsane8MoonsEpic() throws IOException {
        final var image = DifficultyRenderer.create(Difficulty.INSANE)
                .withMoons(8)
                .withQualityRating(QualityRating.EPIC)
                .render();
        assertImageEquals(loadTestImage("/tests/insane-8-moons-epic.png"), image);
    }

    @Test
    public void shouldRenderEasyDemon10MoonsLegendary() throws IOException {
        final var image = DifficultyRenderer.create(DemonDifficulty.EASY)
                .withMoons(10)
                .withQualityRating(QualityRating.LEGENDARY)
                .render();
        assertImageEquals(loadTestImage("/tests/demon-easy-10-stars-legendary.png"), image);
    }

    @Test
    public void shouldRenderAuto1StarMythicStarsLegendary() throws IOException {
        final var image = DifficultyRenderer.create(Difficulty.AUTO)
                .withStars(1)
                .withQualityRating(QualityRating.MYTHIC)
                .render();
        assertImageEquals(loadTestImage("/tests/auto-1-star-mythic.png"), image);
    }

    @Test
    public void shouldRenderExtremeDemonMythic() throws IOException {
        final var image = DifficultyRenderer.create(DemonDifficulty.EXTREME)
                .withMoons(10)
                .withQualityRating(QualityRating.MYTHIC)
                .withoutStarsOrMoons()
                .render();
        assertImageEquals(loadTestImage("/tests/demon-extreme-mythic.png"), image);
    }

    @Test
    public void shouldRenderForLevel() throws IOException {
        final var level = ImmutableGDLevel.builder()
                .coinCount(0)
                .creatorPlayerId(503085)
                .creatorName("Riot")
                .demonDifficulty(DemonDifficulty.EXTREME)
                .description("Whose blood will be spilt in the Bloodbath? Who will the victors be? How many will " +
                        "survive? Good luck...")
                .votedDifficulty(Difficulty.INSANE)
                .downloads(26672952)
                .likes(1505455)
                .featuredScore(10330)
                .gameVersion(21)
                .hasCoinsVerified(false)
                .id(10565740)
                .isAuto(false)
                .isDemon(true)
                .qualityRating(QualityRating.FEATURED)
                .length(Length.LONG)
                .levelVersion(3)
                .name("Bloodbath")
                .objectCount(24746)
                .originalLevelId(7679228)
                .requestedStars(0)
                .songId(467339)
                .song(ImmutableGDSong.builder()
                        .artist("Dimrain47")
                        .title("At the Speed of Light")
                        .size("9.56")
                        .downloadUrl(InternalUtils.urlDecode("http%3A%2F%2Faudio.ngfiles" +
                                ".com%2F467000%2F467339_At_the_Speed_of_Light_FINA.mp3"))
                        .id(467339)
                        .build())
                .rewards(10)
                .build();
        final var image = DifficultyRenderer.forLevel(level).render();
        assertImageEquals(loadTestImage("/tests/level.png"), image);
    }
}