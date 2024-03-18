package jdash.graphics;

import jdash.common.IconType;
import jdash.graphics.internal.IconSpriteFactory;

import java.awt.image.BufferedImage;

/**
 * Responsible for loading game assets and generate the images corresponding to the different icons in the game.
 * @deprecated use {@link IconSpriteFactory} instead.
 */
@Deprecated
public interface SpriteFactory {
    /**
     * Makes an image corresponding to the sprite of the desired icon.
     *
     * @param type            the icon type
     * @param id              the icon ID
     * @param color1Id        the primary color ID
     * @param color2Id        the secondary color ID
     * @param withGlowOutline whether to include the glow outline
     * @return a {@link BufferedImage}
     */
    BufferedImage makeSprite(IconType type, int id, int color1Id, int color2Id, boolean withGlowOutline);
}
