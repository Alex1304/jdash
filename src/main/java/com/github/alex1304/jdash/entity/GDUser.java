package com.github.alex1304.jdash.entity;

import java.util.Objects;

public class GDUser implements GDEntity {
	public static enum Role {
		USER,
		MODERATOR,
		ELDER_MODERATOR;
	}

	private final long id;
	private final long accountId;
	private final String name;
	private final int stars;
	private final int demons;
	private final int diamonds;
	private final int secretCoins;
	private final int userCoins;
	private final int creatorPoints;
	private final int globalRank;
	private final int cubeIconId;
	private final int shipIconId;
	private final int ufoIconId;
	private final int ballIconId;
	private final int waveIconId;
	private final int robotIconId;
	private final int spiderIconId;
	private final int trailId;
	private final int deathEffectId;
	private final boolean hasIconGlowOutline;
	private final int color1Id;
	private final int color2Id;
	private final int mainIconId;
	
	private final String youtube;
	private final String twitter;
	private final String twitch;
	private final Role role;

	public GDUser(long id, long accountId, String name, int stars, int demons, int diamonds, int secretCoins, int userCoins,
			int creatorPoints, int globalRank, int cubeIconId, int shipIconId, int ufoIconId, int ballIconId, int waveIconId,
			int robotIconId, int spiderIconId, int trailId, int deathEffectId, boolean hasIconGlowOutline, int color1Id,
			int color2Id, int mainIconId, String youtube, String twitter, String twitch, Role role) {
		this.id = id;
		this.accountId = accountId;
		this.name = name;
		this.stars = stars;
		this.demons = demons;
		this.diamonds = diamonds;
		this.userCoins = userCoins;
		this.secretCoins = secretCoins;
		this.creatorPoints = creatorPoints;
		this.globalRank = globalRank;
		this.cubeIconId = cubeIconId;
		this.shipIconId = shipIconId;
		this.ufoIconId = ufoIconId;
		this.ballIconId = ballIconId;
		this.waveIconId = waveIconId;
		this.robotIconId = robotIconId;
		this.spiderIconId = spiderIconId;
		this.trailId = trailId;
		this.deathEffectId = deathEffectId;
		this.hasIconGlowOutline = hasIconGlowOutline;
		this.color1Id = color1Id;
		this.color2Id = color2Id;
		this.mainIconId = mainIconId;
		this.youtube = Objects.requireNonNull(youtube);
		this.twitter = Objects.requireNonNull(twitter);
		this.twitch = Objects.requireNonNull(twitch);
		this.role = Objects.requireNonNull(role);
	}

	@Override
	public long getId() {
		return id;
	}

	public long getAccountId() {
		return accountId;
	}

	public String getName() {
		return name;
	}

	public int getStars() {
		return stars;
	}

	public int getDemons() {
		return demons;
	}

	public int getDiamonds() {
		return diamonds;
	}

	public int getSecretCoins() {
		return secretCoins;
	}

	public int getUserCoins() {
		return userCoins;
	}

	public int getCreatorPoints() {
		return creatorPoints;
	}

	public int getGlobalRank() {
		return globalRank;
	}

	public int getCubeIconId() {
		return cubeIconId;
	}

	public int getShipIconId() {
		return shipIconId;
	}

	public int getUfoIconId() {
		return ufoIconId;
	}

	public int getBallIconId() {
		return ballIconId;
	}

	public int getWaveIconId() {
		return waveIconId;
	}

	public int getRobotIconId() {
		return robotIconId;
	}

	public int getSpiderIconId() {
		return spiderIconId;
	}

	public int getTrailId() {
		return trailId;
	}

	public int getDeathEffectId() {
		return deathEffectId;
	}

	public boolean hasIconGlowOutline() {
		return hasIconGlowOutline;
	}

	public int getColor1Id() {
		return color1Id;
	}

	public int getColor2Id() {
		return color2Id;
	}

	public int getMainIconId() {
		return mainIconId;
	}

	public String getYoutube() {
		return youtube;
	}

	public String getTwitter() {
		return twitter;
	}

	public String getTwitch() {
		return twitch;
	}

	public Role getRole() {
		return role;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GDUser)) {
			return false;
		}
		GDUser user = (GDUser) obj;
		return user.id == id;
	}
	
	@Override
	public int hashCode() {
		return Long.hashCode(id);
	}

	@Override
	public String toString() {
		return "GDUser [id=" + id + ", accountId=" + accountId + ", name=" + name + ", stars=" + stars + ", demons="
				+ demons + ", diamonds=" + diamonds + ", secretCoins=" + secretCoins + ", userCoins=" + userCoins
				+ ", creatorPoints=" + creatorPoints + ", globalRank=" + globalRank + ", cubeIconId=" + cubeIconId
				+ ", shipIconId=" + shipIconId + ", ufoIconId=" + ufoIconId + ", ballIconId=" + ballIconId
				+ ", waveIconId=" + waveIconId + ", robotIconId=" + robotIconId + ", spiderIconId=" + spiderIconId
				+ ", trailId=" + trailId + ", deathEffectId=" + deathEffectId + ", hasIconGlowOutline="
				+ hasIconGlowOutline + ", color1Id=" + color1Id + ", color2Id=" + color2Id + ", mainIconId="
				+ mainIconId + ", youtube=" + youtube + ", twitter=" + twitter + ", twitch=" + twitch + ", role=" + role
				+ "]";
	}

}
