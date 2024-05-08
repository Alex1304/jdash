package jdash.common.entity;

import jdash.common.DemonDifficulty;
import jdash.common.Difficulty;
import jdash.common.Length;
import jdash.common.QualityRating;

import java.util.Optional;

/**
 * Represents a Geometry Dash level.
 *
 * @param id               The ID of the level.
 * @param name             The name of the level.
 * @param creatorPlayerId  The player ID of the creator of the level.
 * @param description      The description of the level.
 * @param votedDifficulty  The difficulty of the level based on community votes. Use {@link #difficulty()} to get the
 *                         actual difficulty as shown in-game.
 * @param demonDifficulty  The demon difficulty of the level based on community votes. Generally defaults to
 *                         {@link DemonDifficulty#HARD} if not specified or is not a demon level.
 * @param rewards          The rewards of the level (stars or moons).
 * @param featuredScore    The featured score of the level. It is generally used to position levels in the featured
 *                         section of the game.
 * @param qualityRating    The quality rating of the level. It corresponds to the decoration of the difficulty face as
 *                         shown in-game (featured, epic, legendary, mythic).
 * @param downloads        The number of downloads of the level.
 * @param likes            The number of likes of the level.
 * @param length           The length of the level.
 * @param coinCount        The number of user coins the level has.
 * @param hasCoinsVerified Whether the user coins are verified (silver) or unverified (bronze). <code>true</code> means
 *                         verified.
 * @param levelVersion     The version of the level.
 * @param gameVersion      The minimum version of the game that is required to play the level.
 * @param objectCount      The number of objects in the level.
 * @param isDemon          Whether the level is demon.
 * @param isAuto           Whether the level is auto.
 * @param originalLevelId  The ID of the original level if it was copied from an existing level.
 * @param requestedStars   The number of stars the creator requested for this level.
 * @param songId           The ID of the song used in this level. Always present if it is a song from Newgrounds, always
 *                         absent if it is an official song. As such, <code>songId().isPresent()</code> can be used to
 *                         test whether this level uses a custom song or an official song.
 * @param song             The song used in this level. This information is always present if it is a valid official
 *                         song, but might not always be present if it is an invalid official song or a song from
 *                         Newgrounds. In the latter case, the song ID will always be present via {@link #songId()} so
 *                         you can fetch song info separately later on if needed.
 * @param creatorName      The name of the creator of this level. This information is not always provided. The creator's
 *                         player ID will always be present via {@link #creatorPlayerId()} so you can fetch creator info
 *                         separately later on if needed.
 * @param creatorAccountId The account ID of the creator of the level.
 */
public record GDLevel(
        long id,
        String name,
        long creatorPlayerId,
        String description,
        Difficulty votedDifficulty,
        DemonDifficulty demonDifficulty,
        int rewards,
        int featuredScore,
        QualityRating qualityRating,
        int downloads,
        int likes,
        Length length,
        int coinCount,
        boolean hasCoinsVerified,
        int levelVersion,
        int gameVersion,
        int objectCount,
        boolean isDemon,
        boolean isAuto,
        Optional<Long> originalLevelId,
        int requestedStars,
        Optional<Long> songId,
        Optional<GDSong> song,
        Optional<String> creatorName,
        Optional<Long> creatorAccountId
) {
    /**
     * The actual difficulty of the level as shown in-game. It is computed based on the results of
     * {@link #votedDifficulty()}, {@link #isDemon()} and {@link #isAuto()}.
     *
     * @return a {@link Difficulty}
     */
    public Difficulty difficulty() {
        if (isDemon) {
            return Difficulty.DEMON;
        }
        if (isAuto) {
            return Difficulty.AUTO;
        }
        return votedDifficulty;
    }

    /**
     * Whether this level is in Platformer mode. Shorthand for {@code length() == Length.PLATFORMER}.
     *
     * @return a boolean
     */
    public boolean isPlatformer() {
        return length == Length.PLATFORMER;
    }
}
