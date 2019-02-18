package com.github.alex1304.jdash.entity;

import java.util.Objects;

public class GDUserPart2 extends AbstractGDUser {
	
	private final String name;
	private final boolean hasGlowOutline;
	private final int mainIconId;

	public GDUserPart2(long id, int secretCoins, int userCoins, int color1Id, int color2Id, long accountId, int stars,
			int creatorPoints, int demons, String name, boolean hasGlowOutline, int mainIconId) {
		super(id, secretCoins, userCoins, color1Id, color2Id, accountId, stars, creatorPoints, demons);
		this.name = Objects.requireNonNull(name);
		this.hasGlowOutline = hasGlowOutline;
		this.mainIconId = mainIconId;
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
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof GDUserPart2 && super.equals(obj);
	}

	@Override
	public String toString() {
		return "GDUserPart2 [name=" + name + ", hasGlowOutline=" + hasGlowOutline + ", mainIconId=" + mainIconId
				+ ", id=" + id + "]";
	}
}
