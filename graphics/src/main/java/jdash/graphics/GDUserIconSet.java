package jdash.graphics;

import jdash.common.IconType;
import jdash.common.entity.GDUserProfile;

import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * Allows to generate user icons.
 */
public final class GDUserIconSet {

    private final GDUserProfile user;
    private final SpriteFactory factory;

    private GDUserIconSet(GDUserProfile user, SpriteFactory factory) {
        this.user = Objects.requireNonNull(user);
        this.factory = Objects.requireNonNull(factory);
    }

    /**
     * Creates a new {@link GDUserIconSet} for the given user.
     *
     * @param user    the user to generate the icon set for
     * @param factory the factory that will handle the creation of icons
     * @return a new {@link GDUserIconSet}
     */
    public static GDUserIconSet create(GDUserProfile user, SpriteFactory factory) {
        return new GDUserIconSet(user, factory);
    }

    /**
     * Generates the icon of the specified type for the underlying user.
     *
     * @param iconType the typeof icon to generate
     * @return the requested icon as a {@link BufferedImage}
     */
    public BufferedImage generateIcon(IconType iconType) {
        return factory.makeSprite(iconType, Math.max(1, iconType.idForUser(user)), user.color1Id(),
                user.color2Id(), user.hasGlowOutline());
    }

    /**
     * Two icon sets are equal if:
     * <ul>
     * <li>For each icon type, both users have the same icon ID and the same color.</li>
     * <li>Both users either have the glow outline on their icons or do not have it.</li>
     * </ul>
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GDUserIconSet)) {
            return false;
        }
        GDUserIconSet o = (GDUserIconSet) obj;
        if (user.equals(o.user)) {
            return true;
        }
        for (IconType t : IconType.values()) {
            if (t.idForUser(user) != t.idForUser(o.user)) {
                return false;
            }
        }
        return user.color1Id() == o.user.color1Id() && user.color2Id() == o.user.color2Id()
                && user.hasGlowOutline() == o.user.hasGlowOutline();
    }

    @Override
    public int hashCode() {
        int hash = user.color1Id() ^ user.color2Id() ^ Boolean.hashCode(user.hasGlowOutline());
        for (IconType t : IconType.values()) {
            hash ^= t.idForUser(user);
        }
        return hash;
    }
}