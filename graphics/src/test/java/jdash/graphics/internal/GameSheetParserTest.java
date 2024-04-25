package jdash.graphics.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class GameSheetParserTest {

    @Test
    public void shouldParseGameSheet() {
        final var parser = assertDoesNotThrow(() -> SpriteSheet.parse("/icons/bird_01-uhd.png",
                "/icons/bird_01-uhd.plist"));
        assertEquals(231, parser.getImage().getWidth());
        assertEquals(199, parser.getImage().getHeight());
        assertEquals("{0,0}", parser.getPlist().getString("frames.bird_01_001..png.spriteOffset"));
        assertEquals(4, parser.getSpriteElements().size());
    }
}