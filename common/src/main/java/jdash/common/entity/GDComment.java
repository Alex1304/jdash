package jdash.common.entity;

import java.util.Optional;

/**
 * Represents a comment posted in Geometry Dash.
 *
 * @param id         The ID of the comment.
 * @param author     The author of the comment, if known.
 * @param content    The content of the comment.
 * @param likes      The number of likes this comment has.
 * @param postedAgo  A string indicating when the comment was posted. The structure of the string is not guaranteed.
 * @param percentage The percentage the user has made on the level this comment was posted on. May not be present if the
 *                   user chose not to share percentage or if the comment doesn't relate to a level.
 * @param color      The color of the comment. It is encoded as an hexadecimal integer, for example
 *                   <code>0xFF0000</code> would be red, <code>0x00FF00</code> would be green, <code>0x0000FF</code>
 *                   would be blue, <code>0</code> would be black,
 *                   <code>0xFFFFFF</code> would be white, etc. It may not be present if the comment doesn't have a
 *                   particular color.
 */
public record GDComment(
        long id,
        Optional<GDUser> author,
        String content,
        int likes,
        String postedAgo,
        Optional<Integer> percentage,
        Optional<Integer> color
) {}
