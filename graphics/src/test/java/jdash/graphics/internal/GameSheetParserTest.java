package jdash.graphics.internal;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static jdash.graphics.test.ImageTestUtils.*;
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

    @Test
    public void shouldLoadAsset() throws IOException {
        //ncs_large_001
        final var parser = SpriteSheet.parse("/GJ_GameSheet03-uhd.png", "/GJ_GameSheet03-uhd.plist");
        final var asset = parser.getSpriteElements().stream()
                .filter(se -> se.getName().equals("ncs_large_001"))
                .findAny()
                .orElseThrow()
                .render(parser.getImage());
        assertImageEquals(loadTestImage("/tests/ncs.png"), asset);
    }
}