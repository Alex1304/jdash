package jdash.common;

import jdash.common.internal.InternalUtils;

/**
 * Represents the length of a level.
 */
public enum Length {

    /**
     * A level that is less than 10 seconds long.
     */
    TINY,

    /**
     * A level that is 10 to 29 seconds long.
     */
    SHORT,

    /**
     * A level that is 30 to 59 seconds long.
     */
    MEDIUM,

    /**
     * A level that is 60 to 119 seconds long.
     */
    LONG,

    /**
     * A level that is more than 120 seconds long.
     */
    XL,

    /**
     * A level that is in Platformer mode.
     */
    PLATFORMER;

    /**
     * Convenience method to get a {@link Length} based on the in-game encoding of a length.
     *
     * @param str the encoded string
     * @return a {@link Length}
     */
    public static Length parse(String str) {
        return InternalUtils.parseIndex(str, Length.values());
    }
}