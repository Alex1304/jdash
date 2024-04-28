package jdash.graphics;

import jdash.common.IconType;
import jdash.common.entity.GDUserProfile;

import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.OptionalInt;

/**
 * Allows to generate icons for a specific user profile.
 */
public final class IconSetFactory {

    private static final int ICON_SLOT_SIZE = 200;

    private final GDUserProfile profile;

    private IconSetFactory(GDUserProfile profile) {
        this.profile = profile;
    }

    /**
     * Creates a new {@link IconSetFactory} for the given user profile.
     *
     * @param profile the user profile to generate icons for
     * @return a new {@link IconSetFactory}
     */
    public static IconSetFactory forUser(GDUserProfile profile) {
        Objects.requireNonNull(profile);
        return new IconSetFactory(profile);
    }

    /**
     * Creates the icon of the specified type for the underlying user.
     *
     * @param iconType the typeof icon to generate
     * @return the requested icon as a {@link BufferedImage}
     */
    public BufferedImage createIcon(IconType iconType) {
        Objects.requireNonNull(iconType);
        final var renderer = IconRenderer.load(iconType, Math.max(1, iconType.idForUser(profile)));
        final var colors = new ColorSelection(profile.user().color1Id(), profile.user().color2Id(),
                profile.user().hasGlowOutline() ? OptionalInt.of(profile.glowColorId()) : OptionalInt.empty());
        return renderer.render(colors);
    }

    /**
     * Creates the full icon set for the underlying user.
     *
     * @return all user icons packed into a 1800x200 {@link BufferedImage}
     */
    public BufferedImage createIconSet() {
        final var types = IconType.values();
        final var result = new BufferedImage(ICON_SLOT_SIZE * types.length, ICON_SLOT_SIZE,
                BufferedImage.TYPE_INT_ARGB);
        final var g = result.createGraphics();
        for (final var type : types) {
            final var image = createIcon(type);
            final var x = (int) (ICON_SLOT_SIZE * (type.ordinal() + 0.5) - image.getWidth() / 2);
            final var y = (ICON_SLOT_SIZE - image.getHeight()) / 2;
            g.drawImage(image, x, y, null);
        }
        g.dispose();
        return result;
    }

    /**
     * Two icon sets are equal if both users have the same icon ID and the same color.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IconSetFactory o)) {
            return false;
        }
        if (profile.equals(o.profile)) {
            return true;
        }
        for (IconType t : IconType.values()) {
            if (t.idForUser(profile) != t.idForUser(o.profile)) {
                return false;
            }
        }
        return profile.user().color1Id() == o.profile.user().color1Id()
                && profile.user().color2Id() == o.profile.user().color2Id()
                && profile.glowColorId() == o.profile.glowColorId();
    }

    @Override
    public int hashCode() {
        int hash = Objects.hash(profile.user().color1Id(), profile.user().color2Id(), profile.glowColorId());
        for (IconType t : IconType.values()) {
            hash = Objects.hash(hash, t.idForUser(profile));
        }
        return hash;
    }
}