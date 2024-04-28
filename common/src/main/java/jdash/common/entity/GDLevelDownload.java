package jdash.common.entity;

import java.nio.ByteBuffer;
import java.util.Optional;

/**
 * Represents the full data of a Geometry Dash level after it has been downloaded.
 *
 * @param level        The {@link GDLevel} containing the base information about the level.
 * @param isCopyable   Whether the creator of the level allowed this level to be copied.
 * @param copyPasscode The passcode to copy this level, if one is required.
 * @param uploadedAgo  The string indicating when the level was uploaded. The structure of the string is not
 *                     guaranteed.
 * @param updatedAgo   The string indicating when the level was last updated. The structure of the string is not
 *                     guaranteed.
 * @param data         The data of the level. It is provided as a sequence of bytes as returned by the server. In some
 *                     cases, the data might be GZIP-compressed.
 */
public record GDLevelDownload(
        GDLevel level,
        boolean isCopyable,
        Optional<Integer> copyPasscode,
        String uploadedAgo,
        String updatedAgo,
        ByteBuffer data
) {}
