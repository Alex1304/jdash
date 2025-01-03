package jdash.graphics;

import jdash.common.IconType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.OptionalInt;

import static jdash.graphics.test.ImageTestUtils.assertImageEquals;
import static jdash.graphics.test.ImageTestUtils.loadTestImage;

public final class IconRendererTest {

    @BeforeAll
    public static void beforeAll() {
        IconRenderer.enableAntialiasing(false);
    }

    @Test
    public void shouldReturnSpriteSheet() throws IOException {
        final var renderer = IconRenderer.load(IconType.CUBE, 1);
        assertImageEquals(loadTestImage("/icons/player_01-uhd.png"), renderer.getSpriteSheet());
    }

    @Test
    public void shouldRenderCube1() throws IOException {
        final var renderer = IconRenderer.load(IconType.CUBE, 1);
        final var output = renderer.render(ColorSelection.defaultColors(false));
        // writeToTempDir(output, "tests/cube-1.png");
        assertImageEquals(loadTestImage("/tests/cube-1.png"), output);
    }

    @Test
    public void shouldRenderShip10WithGlow() throws IOException {
        final var renderer = IconRenderer.load(IconType.SHIP, 10);
        final var output = renderer.render(ColorSelection.defaultColors(true));
        // writeToTempDir(output, "tests/ship-10.png");
        assertImageEquals(loadTestImage("/tests/ship-10.png"), output);
    }

    @Test
    public void shouldRenderRobot1WithCustomColors() throws IOException {
        final var renderer = IconRenderer.load(IconType.ROBOT, 1);
        final var output = renderer.render(new ColorSelection(16, 11, OptionalInt.of(14)));
        // writeToTempDir(output, "tests/robot-1.png");
        assertImageEquals(loadTestImage("/tests/robot-1.png"), output);
    }

    @Test
    public void shouldRenderSpider7() throws IOException {
        final var renderer = IconRenderer.load(IconType.SPIDER, 7);
        final var output = renderer.render(ColorSelection.defaultColors(false));
        // writeToTempDir(output, "tests/spider-7.png");
        assertImageEquals(loadTestImage("/tests/spider-7.png"), output);
    }

    @Test
    public void shouldRenderSpider15WithCustomColors() throws IOException {
        final var renderer = IconRenderer.load(IconType.SPIDER, 15);
        final var output = renderer.render(new ColorSelection(12, 9, OptionalInt.of(9)));
        // writeToTempDir(output, "tests/spider-15.png");
        assertImageEquals(loadTestImage("/tests/spider-15.png"), output);
    }
}