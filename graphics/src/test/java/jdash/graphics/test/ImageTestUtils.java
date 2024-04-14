package jdash.graphics.test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.MissingResourceException;

import static org.junit.jupiter.api.Assertions.fail;

public final class ImageTestUtils {

    private ImageTestUtils() {
        throw new AssertionError();
    }

    public static void assertImageEquals(BufferedImage expected, BufferedImage actual) {
        if (expected.getWidth() != actual.getWidth() || expected.getHeight() != actual.getHeight()) {
            fail("Image size mismatch");
        }
        for (int x = 0; x < expected.getWidth(); x++) {
            for (int y = 0; y < expected.getHeight(); y++) {
                final var expectedPixel = expected.getRGB(x, y);
                final var actualPixel = actual.getRGB(x, y);
                if (expectedPixel != actualPixel) {
                    fail("Pixel mismatch at (" + x + ", " + y + "). Expected: 0x" + Integer.toHexString(expectedPixel) +
                            " Actual: 0x" + Integer.toHexString(actualPixel));
                }
            }
        }
    }

    public static BufferedImage loadTestImage(String name) throws IOException {
        try (final var resource = ImageTestUtils.class.getResourceAsStream(name)) {
            if (resource == null) {
                throw new MissingResourceException("Test image not found", ImageTestUtils.class.getName(), name);
            }
            return ImageIO.read(resource);
        }
    }
}
