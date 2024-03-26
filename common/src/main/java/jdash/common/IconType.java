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
    CUBE(GDUserProfile::cubeIconId, "player"),

    /**
     * A ship icon.
     */
    SHIP(GDUserProfile::shipIconId, "ship"),

    /**
     * A ball icon.
     */
    BALL(GDUserProfile::ballIconId, "player_ball"),

    /**
     * A UFO icon.
     */
    UFO(GDUserProfile::ufoIconId, "bird"),

    /**
     * A wave icon.
     */
    WAVE(GDUserProfile::waveIconId, "dart"),

    /**
     * A robot icon.
     */
    ROBOT(GDUserProfile::robotIconId, "robot"),

    /**
     * A spider icon.
     */
    SPIDER(GDUserProfile::spiderIconId, "spider"),

    /**
     * A swing icon.
     */
    SWING(GDUserProfile::swingIconId, "swing"),

    /**
     * A jetpack icon.
     */
    JETPACK(GDUserProfile::jetpackIconId, "jetpack");

    private final ToIntFunction<GDUserProfile> idGetter;
    private final String internalName;

    IconType(ToIntFunction<GDUserProfile> idGetter, String internalName) {
        this.idGetter = idGetter;
        this.internalName = internalName;
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

    /**
     * Get the name of the icon type as used internally (for example in sprites).
     *
     * @return a {@link String}
     */
    public String getInternalName() {
        return internalName;
    }
}
