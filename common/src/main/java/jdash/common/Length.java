package jdash.common;

import jdash.common.internal.InternalUtils;

public enum Length {
    TINY,
    SHORT,
    MEDIUM,
    LONG,
    XL;

    public static Length parse(String str) {
        return InternalUtils.parseIndex(str, Length.values());
    }
}