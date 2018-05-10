package com.github.alex1304.jdash.component;

import com.github.alex1304.jdash.component.property.GDUserRole;

/**
 * Represents a user in Geometry Dash.
 * 
 * @author Alex1304
 */
public class GDUser implements GDComponent {
	
	private long accountID;
	private long playerID;
	private String name;
	private int stars;
	private int diamonds;
	private int secretCoins;
	private int userCoins;
	private int demons;
	private int creatorPoints;
	private String youtube;
	private String twitter;
	private String twitch;
	private long globalRank;
	private GDUserRole role;

	/**
	 * @param name
	 *            - user's nickname
	 * @param playerID
	 *            - user's player ID
	 * @param secretCoins
	 *            - user's secret coins
	 * @param userCoins
	 *            - user's user coins
	 * @param stars
	 *            - user's stars
	 * @param diamonds
	 *            - user's diamonds
	 * @param demons
	 *            - user's demons
	 * @param creatorPoints
	 *            - user's creator points
	 * @param youtube
	 *            - user's YouTube channel link (only the part after
	 *            https://www.youtube.com/channel/...)
	 * @param globalRank
	 *            - global rank of user
	 * @param accountID
	 *            - user's account ID
	 * @param role
	 *            - user's role in-game
	 * @param twitter
	 *            - user's Twitter account tag
	 * @param twitch
	 *            - user's Twitch username.
	 */
	public GDUser(String name, long playerID, int secretCoins, int userCoins, int stars, int diamonds, int demons,
			int creatorPoints, String youtube, long globalRank, long accountID, GDUserRole role, String twitter,
			String twitch) {
		this.name = name;
		this.playerID = playerID;
		this.secretCoins = secretCoins;
		this.userCoins = userCoins;
		this.stars = stars;
		this.diamonds = diamonds;
		this.demons = demons;
		this.creatorPoints = creatorPoints;
		this.youtube = youtube;
		this.globalRank = globalRank;
		this.accountID = accountID;
		this.role = role;
		this.twitter = twitter;
		this.twitch = twitch;
	}
	

	/**
	 * Gets the user's nickname
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the user's player ID
	 * 
	 * @return long
	 */
	public long getPlayerID() {
		return playerID;
	}

	/**
	 * Gets the user's secret coins
	 * 
	 * @return int
	 */
	public int getSecretCoins() {
		return secretCoins;
	}

	/**
	 * Gets the user's user coins
	 * 
	 * @return int
	 */
	public int getUserCoins() {
		return userCoins;
	}

	/**
	 * Gets the user's stars
	 * 
	 * @return int
	 */
	public int getStars() {
		return stars;
	}

	/**
	 * Gets the user's diamonds
	 * 
	 * @return int
	 */
	public int getDiamonds() {
		return diamonds;
	}

	/**
	 * Gets the user's demons
	 * 
	 * @return int
	 */
	public int getDemons() {
		return demons;
	}

	/**
	 * Gets the user's creator points
	 * 
	 * @return int
	 */
	public int getCreatorPoints() {
		return creatorPoints;
	}

	/**
	 * Gets the user's YouTube channel link (only the part after
	 * https://www.youtube.com/channel/...)
	 * 
	 * @return String
	 */
	public String getYoutube() {
		return youtube;
	}

	/**
	 * Gets the global rank of user
	 * 
	 * @return long
	 */
	public long getGlobalRank() {
		return globalRank;
	}

	/**
	 * Gets the user's account ID
	 * 
	 * @return long
	 */
	public long getAccountID() {
		return accountID;
	}

	/**
	 * Gets the user's role in-game
	 * 
	 * @return GDUserRole
	 */
	public GDUserRole getRole() {
		return role;
	}

	/**
	 * Gets the user's Twitter account tag
	 * 
	 * @return String
	 */
	public String getTwitter() {
		return twitter;
	}

	/**
	 * Gets the user's Twitch username
	 * 
	 * @return String
	 */
	public String getTwitch() {
		return twitch;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (accountID ^ (accountID >>> 32));
		return result;
	}
	
	/**
	 * Two users are equal if and only if they have the same accountID.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof GDUser))
			return false;
		GDUser other = (GDUser) obj;
		if (accountID != other.accountID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GDUser [accountID=" + accountID + ", playerID=" + playerID + ", name=" + name + ", stars=" + stars
				+ ", diamonds=" + diamonds + ", secretCoins=" + secretCoins + ", userCoins=" + userCoins + ", demons="
				+ demons + ", creatorPoints=" + creatorPoints + ", youtube=" + youtube + ", twitter=" + twitter
				+ ", twitch=" + twitch + ", globalRank=" + globalRank + ", role=" + role + "]";
	}
}
