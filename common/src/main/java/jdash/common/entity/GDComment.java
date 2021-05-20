package jdash.common.entity;

import org.immutables.value.Value;

import java.util.Optional;

/**
 * Represents a comment posted in Geometry Dash.
 */
@Value.Immutable
public interface GDComment {

    /**
     * The ID of the comment.
     *
     * @return a long
     */
    long id();

    /**
     * The author of the comment, if known.
     *
     * @return an {@link Optional} containing a {@link GDUser} if present
     */
    Optional<GDUser> author();

    /**
     * The content of the comment.
     *
     * @return a string
     */
    String content();

    /**
     * The number of likes this comment has.
     *
     * @return an int
     */
    int likes();

    /**
     * A string indicating when the comment was posted. The structure of the string is not guaranteed.
     *
     * @return a string
     */
    String postedAgo();

    /**
     * The percentage the user has made on the level this comment was posted on. May not be present if the user chose
     * not to share percentage or if the comment doesn't relate to a level.
     *
     * @return an {@link Optional} containing an int if present
     */
    Optional<Integer> percentage();

    /**
     * The color of the comment. It is encoded as an hexadecimal integer, for example <code>0xFF0000</code> would be
     * red, <code>0x00FF00</code> would be green, <code>0x0000FF</code> would be blue, <code>0</code> would be black,
     * <code>0xFFFFFF</code> would be white, etc. It may not be present if the comment doesn't have a particular color.
     *
     * @return an {@link Optional} containing an int if present
     */
    Optional<Integer> color();
}
