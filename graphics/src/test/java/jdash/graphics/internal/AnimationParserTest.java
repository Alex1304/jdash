package jdash.graphics.internal;

import org.junit.jupiter.api.Test;

import java.util.List;

import static jdash.graphics.IconRenderer.ICON_HEIGHT;
import static jdash.graphics.IconRenderer.ICON_WIDTH;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AnimationParserTest {

    @Test
    void shouldParseFrames() {
        final var frames = AnimationParser.parseFrames("/Robot_AnimDesc.plist",
                "animationContainer.Robot_idle_001..png", List.of());
        assertEquals(14, frames.size());
        assertEquals(ICON_WIDTH, frames.get(0).getWidth());
        assertEquals(ICON_HEIGHT, frames.get(0).getHeight());
        assertEquals(1, frames.get(0).getZIndex());
        assertEquals(-999, frames.get(1).getZIndex());
    }

}