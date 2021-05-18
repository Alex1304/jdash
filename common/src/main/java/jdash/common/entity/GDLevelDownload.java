package jdash.common.entity;

import org.immutables.value.Value;

import java.nio.ByteBuffer;
import java.util.Optional;

/**
 * Represents the full data of a Geometry Dash level.
 */
@Value.Immutable
public interface GDLevelDownload extends GDLevel {

    /**
     * Whether the creator of the level allowed this level to be copied.
     *
     * @return a boolean
     */
    boolean isCopyable();

    /**
     * The passcode to copy this level, if one is required.
     *
     * @return an int representing the passcode, or empty if the level is not copyable or if no passcode is required.
     */
    Optional<Integer> copyPasscode();

    /**
     * The string indicating when the level was uploaded. The structure of the string is not guaranteed.
     *
     * @return a string
     */
    String uploadedAgo();

    /**
     * The string indicating when the level was last updated. The structure of the string is not guaranteed.
     *
     * @return a string
     */
    String updatedAgo();

    /**
     * The data of the level. It is provided as a sequence of bytes as returned by the server. In some cases, the
     * data might be GZIP-compressed.
     *
     * @return a ByteBuffer
     */
    ByteBuffer data();
}
