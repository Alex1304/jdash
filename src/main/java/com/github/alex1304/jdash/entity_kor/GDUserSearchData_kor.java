package com.github.alex1304.jdash.entity;

import java.util.Objects;

public final class GDUserSearchData extends AbstractGDUser {
	
	private final String name;
	private final boolean hasGlowOutline;
	private final int mainIconId;
	private final IconType mainIconType;

	public GDUserSearchData(long id, int secretCoins, int userCoins, int color1Id, int color2Id, long accountId, int stars,
			int creatorPoints, int demons, String name, boolean hasGlowOutline, int mainIconId, IconType mainIconType) {
		super(id, secretCoins, userCoins, color1Id, color2Id, accountId, stars, creatorPoints, demons);
		this.name = Objects.requireNonNull(name);
		this.hasGlowOutline = hasGlowOutline;
		this.mainIconId = mainIconId;
		this.mainIconType = Objects.requireNonNull(mainIconType);
	}

	public String getName() {
		return name;
	}

	public boolean hasGlowOutline() {
		return hasGlowOutline;
	}

	public int getMainIconId() {
		return mainIconId;
	}
	
	public IconType getMainIconType() {
		return mainIconType;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof GDUserSearchData && super.equals(obj);
	}

	@Override
	
	public String toString() {
		return "GDUserPart2 [name=" + name + ", hasGlowOutline=" + hasGlowOutline + ", mainIconId=" + mainIconId
				+ ", mainIconType=" + mainIconType + ", secretCoins=" + secretCoins + ", userCoins=" + userCoins
				+ ", color1Id=" + color1Id + ", color2Id=" + color2Id + ", accountId=" + accountId + ", stars=" + stars
				+ ", creatorPoints=" + creatorPoints + ", demons=" + demons + ", id=" + id + "]";
	}
}
