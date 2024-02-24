package jdash.graphics.internal;

import jdash.common.IconType;
import jdash.graphics.LegacySpriteFactory;
import jdash.graphics.SpriteFactory;
import jdash.graphics.exception.SpriteLoadException;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.plist.XMLPropertyListConfiguration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteFactoryImpl22 implements SpriteFactory {


    /**
     * Creates a new sprite factory.
     *
     * @return the sprite factory
     * @throws SpriteLoadException if something goes wrong when loading the sprites.
     */
    public static SpriteFactory create() {
        try {
            var url = LegacySpriteFactory.class.getResource("/GJ_GameSheet03-uhd.png");
            if (url == null) {
                throw new AssertionError("Game sheet URL is null");
            }
            var spriteImg = ImageIO.read(url);
            var spritePlist = new Configurations()
                    .fileBased(XMLPropertyListConfiguration.class, SpriteFactoryImpl22.class
                            .getResource("/GJ_GameSheet03-uhd.plist"));
            
            return new SpriteFactoryImpl22();
        } catch (IOException | ConfigurationException e) {
            throw new SpriteLoadException(e);
        }
    }
    @Override
    public BufferedImage makeSprite(IconType type, int id, int color1Id, int color2Id, boolean withGlowOutline) {
        return null;
    }
}
