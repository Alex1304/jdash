package jdash.common;


import jdash.common.entity.GDUserProfile;
import jdash.common.internal.InternalUtils;

import java.util.Objects;
import java.util.function.ToIntFunction;

/**
 * Represents a type of icon, typically corresponding to a different game mode.
 */
public enum IconType {

    /**
     * A cube icon.
     */
    CUBE(GDUserProfile::cubeIconId),

    /**
     * A ship icon.
     */
    SHIP(GDUserProfile::shipIconId),

    /**
     * A ball icon.
     */
    BALL(GDUserProfile::ballIconId),

    /**
     * A UFO icon.
     */
    UFO(GDUserProfile::ufoIconId),

    /**
     * A wave icon.
     */
    WAVE(GDUserProfile::waveIconId),

    /**
     * A robot icon.
     */
    ROBOT(GDUserProfile::robotIconId),

    /**
     * A spider icon.
     */
    SPIDER(GDUserProfile::spiderIconId);

    private final ToIntFunction<GDUserProfile> idGetter;

    IconType(ToIntFunction<GDUserProfile> idGetter) {
        this.idGetter = idGetter;
    }

    /**
     * Convenience method to get a {@link IconType} based on the in-game encoding of an icon type.
     *
     * @param str the encoded string
     * @return a {@link IconType}
     */
    public static IconType parse(String str) {
        return InternalUtils.parseIndex(str, IconType.values());
    }

    /**
     * Convenience method to extract the icon ID used by the given user corresponding to this icon type.
     *
     * @param user the user
     * @return an int
     */
    public int idForUser(GDUserProfile user) {
        return idGetter.applyAsInt(Objects.requireNonNull(user));
    }
}
