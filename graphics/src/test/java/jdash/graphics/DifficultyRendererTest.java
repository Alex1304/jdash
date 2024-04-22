package jdash.graphics;

import jdash.common.Difficulty;
import jdash.common.QualityRating;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class DifficultyRendererTest {

    @Test
    public void render() throws IOException {
        final var image = DifficultyRenderer.render(Difficulty.NORMAL, 3, QualityRating.NONE, false);
        try (final var output = Files.newOutputStream(Path.of(System.getProperty("java.io.tmpdir"),
                "fireinthehole.png"))) {
            ImageIO.write(image, "png", output);
        }
    }
}