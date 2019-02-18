package com.github.alex1304.jdash.entity;

import java.util.Objects;

public class GDUserPart1 extends AbstractGDUser {
	private final int diamonds;
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
	private final String youtube;
	private final String twitter;
	private final String twitch;
	private final Role role;

	public GDUserPart1(long id, int secretCoins, int userCoins, int color1Id, int color2Id, long accountId, int stars,
			int creatorPoints, int demons, int diamonds, int globalRank, int cubeIconId, int shipIconId, int ufoIconId, int ballIconId, int waveIconId,
			int robotIconId, int spiderIconId, int trailId, int deathEffectId, String youtube, String twitter, String twitch, Role role) {
		super(id, secretCoins, userCoins, color1Id, color2Id, accountId, stars, creatorPoints, demons);
		this.diamonds = diamonds;
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
		this.youtube = Objects.requireNonNull(youtube);
		this.twitter = Objects.requireNonNull(twitter);
		this.twitch = Objects.requireNonNull(twitch);
		this.role = Objects.requireNonNull(role);
	}
	
	public int getDiamonds() {
		return diamonds;
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
		return obj instanceof GDUserPart1 && super.equals(obj);
	}

	@Override
	public String toString() {
		return "GDUserPart1 [diamonds=" + diamonds + ", globalRank=" + globalRank + ", cubeIconId=" + cubeIconId
				+ ", shipIconId=" + shipIconId + ", ufoIconId=" + ufoIconId + ", ballIconId=" + ballIconId
				+ ", waveIconId=" + waveIconId + ", robotIconId=" + robotIconId + ", spiderIconId=" + spiderIconId
				+ ", trailId=" + trailId + ", deathEffectId=" + deathEffectId + ", youtube=" + youtube + ", twitter="
				+ twitter + ", twitch=" + twitch + ", role=" + role + ", secretCoins=" + secretCoins + ", userCoins="
				+ userCoins + ", color1Id=" + color1Id + ", color2Id=" + color2Id + ", accountId=" + accountId
				+ ", stars=" + stars + ", creatorPoints=" + creatorPoints + ", demons=" + demons + ", id=" + id + "]";
	}
}
