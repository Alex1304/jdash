package com.github.alex1304.jdash.component;

import com.github.alex1304.jdash.component.property.GDUserRole;

/**
 * Represents a user in Geometry Dash.
 * 
 * @author Alex1304
 */
public class GDUser extends GDUserPreview {
	
	private String youtube;
	private String twitter;
	private String twitch;
	private long globalRank;
	private GDUserRole role;
	
	public GDUser() {
	}
	
	public GDUser(GDUserPreview up, String youtube, String twitter, String twitch, long globalRank, GDUserRole role) {
		this.setAccountID(up.getAccountID());
		this.setPlayerID(up.getPlayerID());
		this.setName(up.getName());
		this.setStars(up.getStars());
		this.setSecretCoins(up.getSecretCoins());
		this.setUserCoins(up.getUserCoins());
		this.setDiamonds(up.getDiamonds());
		this.setDemons(up.getDemons());
		this.setCreatorPoints(up.getCreatorPoints());
		this.youtube = youtube;
		this.twitter = twitter;
		this.twitch = twitch;
		this.globalRank = globalRank;
		this.role = role;
	}

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
		this(new GDUserPreview(name, playerID, secretCoins, userCoins, stars, diamonds, demons, creatorPoints,
				accountID), youtube, twitter, twitch, globalRank, role);
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

	/**
	 * Sets the youtube
	 *
	 * @param youtube - String
	 */
	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}

	/**
	 * Sets the twitter
	 *
	 * @param twitter - String
	 */
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	/**
	 * Sets the twitch
	 *
	 * @param twitch - String
	 */
	public void setTwitch(String twitch) {
		this.twitch = twitch;
	}

	/**
	 * Sets the globalRank
	 *
	 * @param globalRank - long
	 */
	public void setGlobalRank(long globalRank) {
		this.globalRank = globalRank;
	}

	/**
	 * Sets the role
	 *
	 * @param role - GDUserRole
	 */
	public void setRole(GDUserRole role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "GDUser [youtube=" + youtube + ", twitter=" + twitter + ", twitch=" + twitch + ", globalRank="
				+ globalRank + ", role=" + role + ", getName()=" + getName() + ", getPlayerID()=" + getPlayerID()
				+ ", getSecretCoins()=" + getSecretCoins() + ", getUserCoins()=" + getUserCoins() + ", getStars()="
				+ getStars() + ", getDiamonds()=" + getDiamonds() + ", getDemons()=" + getDemons()
				+ ", getCreatorPoints()=" + getCreatorPoints() + ", getAccountID()=" + getAccountID() + "]";
	}

}
