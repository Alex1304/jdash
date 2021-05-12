package jdash.common;

import jdash.common.internal.InternalUtils;

public enum PrivacySetting {
    OPENED_TO_ALL,
    OPENED_TO_FRIENDS_ONLY,
    CLOSED;

    public static PrivacySetting parse(String str) {
        return InternalUtils.parseIndex(str, PrivacySetting.values());
    }
}
