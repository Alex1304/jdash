package jdash.graphics.exception;

import jdash.graphics.LegacySpriteFactory;

/**
 * Thrown when the construction of a {@link LegacySpriteFactory} fails because of the inability to load the game assets in
 * memory.
 */
public class SpriteLoadException extends RuntimeException {

    public SpriteLoadException(Throwable cause) {
        super(cause);
    }
}