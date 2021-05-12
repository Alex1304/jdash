package jdash.common;

import jdash.common.internal.InternalUtils;

public enum Role {
    USER,
    MODERATOR,
    ELDER_MODERATOR;

    public static Role parse(String str) {
        return InternalUtils.parseIndex(str, Role.values());
    }
}