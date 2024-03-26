package jdash.graphics.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameSheetParserTest {

    @Test
    void parse() {
        final var parser = assertDoesNotThrow(() -> GameSheetParser.parse("/icons/bird_01-uhd.png",
                "/icons/bird_01-uhd.plist"));
        assertEquals(231, parser.getImage().getWidth());
        assertEquals(199, parser.getImage().getHeight());
        parser.getPlist().getKeys().forEachRemaining(System.out::println);
        assertEquals("{0,0}", parser.getPlist().getString("frames.bird_01_001..png.spriteOffset"));
        assertEquals(4, parser.getSpriteElements().size());
    }
}