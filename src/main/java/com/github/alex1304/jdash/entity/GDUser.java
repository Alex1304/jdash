package com.github.alex1304.jdash.entity;

import java.util.Objects;

public final class GDUser extends AbstractGDUser {
	private final String name;
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
	private final boolean hasIconGlowOutline;
	private final int mainIconId;
	private final String youtube;
	private final String twitter;
	private final String twitch;
	private final Role role;
	private final boolean hasFriendRequestsEnabled;
	private final PrivacySetting privateMessagePolicy;
	private final PrivacySetting commmentHistoryPolicy;
	private final IconType mainIconType;

	private GDUser(long id, long accountId, String name, int stars, int demons, int diamonds, int secretCoins,
			int userCoins, int creatorPoints, int globalRank, int cubeIconId, int shipIconId, int ufoIconId,
			int ballIconId, int waveIconId, int robotIconId, int spiderIconId, int trailId, int deathEffectId,
			boolean hasIconGlowOutline, int color1Id, int color2Id, int mainIconId, String youtube, String twitter,
			String twitch, Role role, boolean hasFriendRequestsEnabled, PrivacySetting privateMessagePolicy,
			PrivacySetting commmentHistoryPolicy, IconType mainIconType) {
		super(id, secretCoins, userCoins, color1Id, color2Id, accountId, stars, creatorPoints, demons);
		this.name = name;
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
		this.hasIconGlowOutline = hasIconGlowOutline;
		this.mainIconId = mainIconId;
		this.youtube = Objects.requireNonNull(youtube);
		this.twitter = Objects.requireNonNull(twitter);
		this.twitch = Objects.requireNonNull(twitch);
		this.role = Objects.requireNonNull(role);
		this.hasFriendRequestsEnabled = hasFriendRequestsEnabled;
		this.privateMessagePolicy = Objects.requireNonNull(privateMessagePolicy);
		this.commmentHistoryPolicy = Objects.requireNonNull(commmentHistoryPolicy);
		this.mainIconType = Objects.requireNonNull(mainIconType);
	}
	
	public static GDUser aggregate(GDUserPart1 part1, GDUserPart2 part2) {
		if (part1.id != part2.id) {
			throw new IllegalArgumentException("Parts don't match");
		}
		return new GDUser(part1.id, part1.accountId, part2.getName(), part1.stars, part1.demons, part1.getDiamonds(),
				part1.secretCoins, part1.userCoins, part1.creatorPoints, part1.getGlobalRank(), part1.getCubeIconId(),
				part1.getShipIconId(), part1.getUfoIconId(), part1.getBallIconId(), part1.getWaveIconId(),
				part1.getRobotIconId(), part1.getSpiderIconId(), part1.getTrailId(), part1.getDeathEffectId(),
				part2.hasGlowOutline(), part1.color1Id, part1.color2Id, part2.getMainIconId(), part1.getYoutube(),
				part1.getTwitter(), part1.getTwitch(), part1.getRole(), part1.hasFriendRequestsEnabled(),
				part1.getPrivateMessagePolicy(), part1.getCommmentHistoryPolicy(), part2.getMainIconType());
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
	
	public boolean hasFriendRequestsEnabled() {
		return hasFriendRequestsEnabled;
	}

	public PrivacySetting getPrivateMessagePolicy() {
		return privateMessagePolicy;
	}

	public PrivacySetting getCommmentHistoryPolicy() {
		return commmentHistoryPolicy;
	}

	public IconType getMainIconType() {
		return mainIconType;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof GDUser && super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return Long.hashCode(id);
	}

	@Override
	public String toString() {
		return "GDUser [name=" + name + ", diamonds=" + diamonds + ", globalRank=" + globalRank + ", cubeIconId="
				+ cubeIconId + ", shipIconId=" + shipIconId + ", ufoIconId=" + ufoIconId + ", ballIconId=" + ballIconId
				+ ", waveIconId=" + waveIconId + ", robotIconId=" + robotIconId + ", spiderIconId=" + spiderIconId
				+ ", trailId=" + trailId + ", deathEffectId=" + deathEffectId + ", hasIconGlowOutline="
				+ hasIconGlowOutline + ", mainIconId=" + mainIconId + ", youtube=" + youtube + ", twitter=" + twitter
				+ ", twitch=" + twitch + ", role=" + role + ", hasFriendRequestsEnabled=" + hasFriendRequestsEnabled
				+ ", privateMessagePolicy=" + privateMessagePolicy + ", commmentHistoryPolicy=" + commmentHistoryPolicy
				+ ", mainIconType=" + mainIconType + ", secretCoins=" + secretCoins + ", userCoins=" + userCoins
				+ ", color1Id=" + color1Id + ", color2Id=" + color2Id + ", accountId=" + accountId + ", stars=" + stars
				+ ", creatorPoints=" + creatorPoints + ", demons=" + demons + ", id=" + id + "]";
	}
}
