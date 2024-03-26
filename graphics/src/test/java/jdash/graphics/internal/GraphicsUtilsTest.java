package jdash.graphics.internal;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GraphicsUtilsTest {

    @Test
    void loadColors() throws IOException {
        final var colorMap = GraphicsUtils.loadColors();
        assertEquals(107, colorMap.size());
        final var color = colorMap.get(0);
        assertEquals(125, color.r());
        assertEquals(255, color.g());
        assertEquals(0, color.b());
    }

    @Test
    void parseGameSheet() throws Exception {

    }
}