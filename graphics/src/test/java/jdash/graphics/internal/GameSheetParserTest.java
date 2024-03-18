package jdash.graphics.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameSheetParserTest {

    @Test
    void parse() {
        final var parser = assertDoesNotThrow(() -> GameSheetParser.parse("/GJ_GameSheetIcons-hd.png",
                "/GJ_GameSheetIcons-hd.plist"));
        assertEquals(parser.getImage().getWidth(), 2048);
        assertEquals(parser.getImage().getHeight(), 2048);
        assertEquals(parser.getPlist().getString("frames.bird_01_001..png.spriteOffset"), "{0,0}");
        assertEquals(parser.getSpriteElements().size(), 1698);
    }
}