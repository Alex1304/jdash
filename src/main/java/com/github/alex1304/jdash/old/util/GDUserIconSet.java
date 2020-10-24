package com.github.alex1304.jdash.old.util;

import java.awt.image.BufferedImage;
import java.util.Objects;

import com.github.alex1304.jdash.graphics.SpriteFactory;
import com.github.alex1304.jdash.old.entity.GDUser;
import com.github.alex1304.jdash.old.entity.IconType;

/**
 * Allows to generate user icons.
 */
public class GDUserIconSet {
	
	private final GDUser user;
	private final SpriteFactory factory;

	public GDUserIconSet(GDUser user, SpriteFactory factory) {
		this.user = Objects.requireNonNull(user);
		this.factory = Objects.requireNonNull(factory);
	}
	
	/**
	 * Generates the icon of the specified type for the underlying user.
	 * 
	 * @param iconType the typeof icon to generate
	 * @return the requested icon as a {@link BufferedImage}
	 */
	public BufferedImage generateIcon(IconType iconType) {
		return factory.makeSprite(iconType, Math.max(1, iconType.idForUser(user)), user.getColor1Id(), user.getColor2Id(), user.hasGlowOutline());
	}
	
	/**
	 * Two icon sets are equal if:
	 * <ul>
	 * <li>For each icon type, both users have the same icon ID and the same
	 * color.</li>
	 * <li>Both users either have the glow outline on their icons or do not have
	 * it.</li>
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
		return user.getColor1Id() == o.user.getColor1Id() && user.getColor2Id() == o.user.getColor2Id()
				&& user.hasGlowOutline() == o.user.hasGlowOutline();
	}
	
	@Override
	public int hashCode() {
		int hash = user.getColor1Id() ^ user.getColor2Id() ^ Boolean.hashCode(user.hasGlowOutline());
		for (IconType t : IconType.values()) {
			hash ^= t.idForUser(user);
		}
		return hash;
	}
}
