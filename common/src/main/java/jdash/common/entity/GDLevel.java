package jdash.common.entity;

import jdash.common.DemonDifficulty;
import jdash.common.Difficulty;
import jdash.common.Length;
import org.immutables.value.Value;

import java.util.Optional;

/**
 * Represents a Geometry Dash level.
 */
@Value.Immutable
public interface GDLevel {

    /**
     * The ID of the level.
     *
     * @return a long
     */
    long id();

    /**
     * The name of the level.
     *
     * @return a string
     */
    String name();

    /**
     * The player ID of the creator of the level.
     *
     * @return a long
     */
    long creatorPlayerId();

    /**
     * The description of the level.
     *
     * @return a string
     */
    String description();

    /**
     * The difficulty of the level. This only takes into account community votes and ignores the actual rating as shown
     * in-game. Use {@link #actualDifficulty()} to get a more accurate result.
     *
     * @return a {@link Difficulty}
     */
    Difficulty difficulty();

    /**
     * The actual difficulty of the level as shown in-game. It is computed based on the results of {@link
     * #difficulty()}, {@link #isDemon()} and {@link #isAuto()}.
     *
     * @return a {@link Difficulty}
     */
    default Difficulty actualDifficulty() {
        if (isDemon()) {
            return Difficulty.DEMON;
        }
        if (isAuto()) {
            return Difficulty.AUTO;
        }
        return difficulty();
    }

    /**
     * The demon difficulty of the level based on community votes. Generally defaults to {@link DemonDifficulty#HARD} if
     * not specified or is not a demon level.
     *
     * @return a {@link DemonDifficulty}
     */
    DemonDifficulty demonDifficulty();

    /**
     * The stars of the level.
     *
     * @return an int
     */
    int stars();

    /**
     * The featured score of the level. It is generally used to position levels in the featured section of the game.
     *
     * @return an int
     */
    int featuredScore();

    /**
     * Whether the level is epic.
     *
     * @return a boolean
     */
    boolean isEpic();

    /**
     * The number of downloads of the level.
     *
     * @return an int
     */
    int downloads();

    /**
     * The number of likes of the level.
     *
     * @return an int
     */
    int likes();

    /**
     * The length of the level.
     *
     * @return a {@link Length}
     */
    Length length();

    /**
     * The number of user coins the level has.
     *
     * @return an int
     */
    int coinCount();

    /**
     * Whether the user coins are verified (silver) or unverified (bronze). <code>true</code> means verified.
     *
     * @return a boolean
     */
    boolean hasCoinsVerified();

    /**
     * The version of the level.
     *
     * @return an int
     */
    int levelVersion();

    /**
     * The minimum version of the game that is required to play the level.
     *
     * @return an int
     */
    int gameVersion();

    /**
     * The number of objects in the level.
     *
     * @return an int
     */
    int objectCount();

    /**
     * Whether the level is demon.
     *
     * @return a boolean
     */
    boolean isDemon();

    /**
     * Whether the level is auto.
     *
     * @return a boolean
     */
    boolean isAuto();

    /**
     * The ID of the original level if it was copied from an existing level.
     *
     * @return an {@link Optional} containing a long if present
     */
    Optional<Long> originalLevelId();

    /**
     * The number of stars the creator requested for this level.
     *
     * @return an int
     */
    int requestedStars();

    /**
     * The ID of the song used in this level. Always present if it is a song from Newgrounds, always absent if it is an
     * official song. As such, <code>songId().isPresent()</code> can be used to test whether this level uses a custom
     * song or an official song.
     *
     * @return an {@link Optional} containing a long
     */
    Optional<Long> songId();

    /**
     * The song used in this level. This information is always present if it is an official song, but might not be
     * present if it is a song from Newgrounds. In the latter case, the song ID will always be present via {@link
     * #songId()} so you can fetch song info separately later on if needed.
     *
     * @return an {@link Optional} containing a {@link GDSong} if present
     */
    Optional<GDSong> song();

    /**
     * The name of the creator of this level. This information is not always provided. The creator's player ID will
     * always be present via {@link #creatorPlayerId()} so you can fetch creator info separately later on if needed.
     *
     * @return an {@link Optional} containing a string if present
     */
    Optional<String> creatorName();
}
