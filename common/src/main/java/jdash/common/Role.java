package jdash.common;

import jdash.common.internal.InternalUtils;

/**
 * Represents the role of a user, that is whether the user is moderator, elder moderator or just a regular user.
 */
public enum Role {

    /**
     * A normal user.
     */
    USER,

    /**
     * A user with Moderator rights.
     */
    MODERATOR,

    /**
     * A user with Elder Moderator rights.
     */
    ELDER_MODERATOR,

    /**
     * A user with Leaderboard Moderator rights.
     */
    LEADERBOARD_MODERATOR;

    /**
     * Convenience method to get a {@link Role} based on the in-game encoding of a role.
     *
     * @param str the encoded string
     * @return a {@link Role}
     */
    public static Role parse(String str) {
        return InternalUtils.parseIndex(str, Role.values());
    }
}