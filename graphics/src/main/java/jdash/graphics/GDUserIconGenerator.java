package jdash.graphics;

import jdash.common.IconType;
import jdash.common.entity.GDUserProfile;
import jdash.graphics.internal.ColorSelection;
import jdash.graphics.internal.IconRenderer;

import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * Allows to generate user icons.
 */
public final class GDUserIconGenerator {

    private static final int ICON_SLOT_SIZE = 200;

    private final GDUserProfile user;

    private GDUserIconGenerator(GDUserProfile user) {
        this.user = user;
    }

    /**
     * Creates a new {@link GDUserIconGenerator} for the given user.
     *
     * @param user    the user to generate icons for
     * @return a new {@link GDUserIconGenerator}
     */
    public static GDUserIconGenerator create(GDUserProfile user) {
        Objects.requireNonNull(user);
        return new GDUserIconGenerator(user);
    }

    /**
     * Generates the icon of the specified type for the underlying user.
     *
     * @param iconType the typeof icon to generate
     * @return the requested icon as a {@link BufferedImage}
     */
    public BufferedImage generateIcon(IconType iconType) {
        final var renderer = IconRenderer.load(iconType, Math.max(1, iconType.idForUser(user)));
        final var colors = user.hasGlowOutline() ? ColorSelection.of(user.color1Id(), user.color2Id(),
                user.color2Id()) : ColorSelection.of(user.color1Id(), user.color2Id());
        return renderer.render(colors);
    }

    /**
     * Generates the full icon set for the underlying user.
     *
     * @return all user icons packed into a single {@link BufferedImage}
     */
    public BufferedImage generateIconSet() {
        final var types = IconType.values();
        final var result = new BufferedImage(ICON_SLOT_SIZE * types.length, ICON_SLOT_SIZE, BufferedImage.TYPE_INT_ARGB);
        final var g = result.createGraphics();
        for (final var type : types) {
            final var image = generateIcon(type);
            final var x = (int) (ICON_SLOT_SIZE * (type.ordinal() + 0.5) - image.getWidth() / 2);
            final var y = (ICON_SLOT_SIZE - image.getHeight()) / 2;
            g.drawImage(image, x, y, null);
        }
        g.dispose();
        return result;
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
        if (!(obj instanceof GDUserIconGenerator)) {
            return false;
        }
        GDUserIconGenerator o = (GDUserIconGenerator) obj;
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
        int hash = Objects.hash(user.color1Id(), user.color2Id(), user.hasGlowOutline());
        for (IconType t : IconType.values()) {
            hash = Objects.hash(hash, t.idForUser(user));
        }
        return hash;
    }
}