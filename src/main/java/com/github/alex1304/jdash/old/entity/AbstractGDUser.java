package com.github.alex1304.jdash.old.entity;

import java.util.Objects;

abstract class AbstractGDUser extends AbstractGDEntity {

	final String name;
	final int secretCoins;
	final int userCoins;
	final int color1Id;
	final int color2Id;
	final long accountId;
	final int stars;
	final int creatorPoints;
	final int demons;
	
	public AbstractGDUser(long id, String name, int secretCoins, int userCoins, int color1Id, int color2Id, long accountId, int stars,
			int creatorPoints, int demons) {
		super(id);
		this.name = Objects.requireNonNull(name);
		this.secretCoins = secretCoins;
		this.userCoins = userCoins;
		this.color1Id = color1Id;
		this.color2Id = color2Id;
		this.accountId = accountId;
		this.stars = stars;
		this.creatorPoints = creatorPoints;
		this.demons = demons;
	}

	public String getName() {
		return name;
	}
	
	public int getSecretCoins() {
		return secretCoins;
	}

	public int getUserCoins() {
		return userCoins;
	}

	public int getColor1Id() {
		return color1Id;
	}

	public int getColor2Id() {
		return color2Id;
	}

	public long getAccountId() {
		return accountId;
	}

	public int getStars() {
		return stars;
	}

	public int getCreatorPoints() {
		return creatorPoints;
	}

	public int getDemons() {
		return demons;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof AbstractGDUser && super.equals(obj);
	}
}
