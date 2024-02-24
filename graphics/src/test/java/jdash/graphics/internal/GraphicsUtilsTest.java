package jdash.graphics.internal;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GraphicsUtilsTest {

    @Test
    void loadColors() throws IOException {
        final var colorMap = GraphicsUtils.loadColors();
        assertEquals(colorMap.size(), 107);
        final var color = colorMap.get(0);
        assertEquals(color.r(), 125);
        assertEquals(color.g(), 255);
        assertEquals(color.b(), 0);
    }
}