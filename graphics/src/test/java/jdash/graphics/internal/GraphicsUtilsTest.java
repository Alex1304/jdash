package jdash.graphics.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GraphicsUtilsTest {

    @Test
    void shouldLoadColors() {
        final var colorMap = GraphicsUtils.loadColors();
        assertEquals(107, colorMap.size());
        final var color = colorMap.get(0);
        assertEquals(125, color.getRed());
        assertEquals(255, color.getGreen());
        assertEquals(0, color.getBlue());
    }
}