package jdash.common;


import jdash.common.entity.GDUserProfile;
import jdash.common.internal.InternalUtils;

import java.util.Objects;
import java.util.function.ToIntFunction;

public enum IconType {
    CUBE(GDUserProfile::cubeIconId),
    SHIP(GDUserProfile::shipIconId),
    BALL(GDUserProfile::ballIconId),
    UFO(GDUserProfile::ufoIconId),
    WAVE(GDUserProfile::waveIconId),
    ROBOT(GDUserProfile::robotIconId),
    SPIDER(GDUserProfile::spiderIconId);

    private final ToIntFunction<GDUserProfile> idGetter;

    IconType(ToIntFunction<GDUserProfile> idGetter) {
        this.idGetter = idGetter;
    }

    public static IconType parse(String str) {
        return InternalUtils.parseIndex(str, IconType.values());
    }

    public int idForUser(GDUserProfile user) {
        return idGetter.applyAsInt(Objects.requireNonNull(user));
    }
}
