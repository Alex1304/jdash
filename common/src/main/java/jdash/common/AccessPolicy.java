package jdash.common;

import jdash.common.internal.InternalUtils;

/**
 * Access policy refers to the profile settings where a user can choose to restrict incoming private messages or
 * visibility of comment history.
 */
public enum AccessPolicy {

    /**
     * Policy that grants access to all.
     */
    ALL,

    /**
     * Policy that grants access to friends only.
     */
    FRIENDS_ONLY,

    /**
     * Policy that grants access to nobody.
     */
    NONE;

    /**
     * Convenience method to get a {@link AccessPolicy} based on the in-game encoding of an access policy.
     *
     * @param str the encoded string
     * @return a {@link AccessPolicy}
     */
    public static AccessPolicy parse(String str) {
        return InternalUtils.parseIndex(str, AccessPolicy.values());
    }
}
