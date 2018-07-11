package com.github.alex1304.jdash.component;

import com.github.alex1304.jdash.component.property.GDLevelDemonDifficulty;
import com.github.alex1304.jdash.component.property.GDLevelDifficulty;
import com.github.alex1304.jdash.component.property.GDLevelLength;

/**
 * Basic info on a GD level
 * 
 * @author Alex1304
 *
 */
public class GDLevelPreview implements GDComponent {
	
	private long id;
	private String name;
	private String creatorName;
	private long creatorID;
	private String description;
	private GDLevelDifficulty difficulty;
	private GDLevelDemonDifficulty demonDifficulty;
	private int stars;
	private int featuredScore;
	private boolean isEpic;
	private int downloads;
	private int likes;
	private GDLevelLength length;
	private GDSong song;
	private int coinCount;
	private boolean hasCoinsVerified;
	private int levelVersion;
	private int gameVersion;
	private int objectCount;
	private boolean isDemon;
	private boolean isAuto;
	private long originalLevelID;
	private int requestedStars;
	
	public GDLevelPreview() {
	}
	
	public GDLevelPreview(long id, String name, String creatorName, long creatorID, String description,
			GDLevelDifficulty difficulty, GDLevelDemonDifficulty demonDifficulty, int stars, int featuredScore,
			boolean isEpic, int downloads, int likes, GDLevelLength length, GDSong song, int coinCount,
			boolean hasCoinsVerified, int levelVersion, int gameVersion, int objectCount, boolean isDemon,
			boolean isAuto, long originalLevelID, int requestedStars) {
		this.id = id;
		this.name = name;
		this.creatorName = creatorName;
		this.creatorID = creatorID;
		this.description = description;
		this.difficulty = difficulty;
		this.demonDifficulty = demonDifficulty;
		this.stars = stars;
		this.featuredScore = featuredScore;
		this.isEpic = isEpic;
		this.downloads = downloads;
		this.likes = likes;
		this.length = length;
		this.song = song;
		this.coinCount = coinCount;
		this.hasCoinsVerified = hasCoinsVerified;
		this.levelVersion = levelVersion;
		this.gameVersion = gameVersion;
		this.objectCount = objectCount;
		this.isDemon = isDemon;
		this.isAuto = isAuto;
		this.originalLevelID = originalLevelID;
		this.requestedStars = requestedStars;
	}

	/**
	 * Gets the id
	 *
	 * @return long
	 */
	public long getId() {
		return id;
	}

	/**
	 * Gets the name
	 *
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the creatorName
	 *
	 * @return String
	 */
	public String getCreatorName() {
		return creatorName;
	}

	/**
	 * Gets the creatorID
	 *
	 * @return long
	 */
	public long getCreatorID() {
		return creatorID;
	}

	/**
	 * Gets the description
	 *
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the difficulty
	 *
	 * @return GDLevelDifficulty
	 */
	public GDLevelDifficulty getDifficulty() {
		return difficulty;
	}

	/**
	 * Gets the demonDifficulty
	 *
	 * @return GDLevelDemonDifficulty
	 */
	public GDLevelDemonDifficulty getDemonDifficulty() {
		return demonDifficulty;
	}

	/**
	 * Gets the stars
	 *
	 * @return int
	 */
	public int getStars() {
		return stars;
	}

	/**
	 * Gets the featuredScore
	 *
	 * @return int
	 */
	public int getFeaturedScore() {
		return featuredScore;
	}

	/**
	 * Gets the isEpic
	 *
	 * @return boolean
	 */
	public boolean isEpic() {
		return isEpic;
	}

	/**
	 * Gets the downloads
	 *
	 * @return int
	 */
	public int getDownloads() {
		return downloads;
	}

	/**
	 * Gets the likes
	 *
	 * @return int
	 */
	public int getLikes() {
		return likes;
	}

	/**
	 * Gets the length
	 *
	 * @return GDLevelLength
	 */
	public GDLevelLength getLength() {
		return length;
	}

	/**
	 * Gets the song
	 *
	 * @return GDSong
	 */
	public GDSong getSong() {
		return song;
	}

	/**
	 * Gets the coinCount
	 *
	 * @return int
	 */
	public int getCoinCount() {
		return coinCount;
	}

	/**
	 * Gets the hasCoinsVerified
	 *
	 * @return boolean
	 */
	public boolean hasCoinsVerified() {
		return hasCoinsVerified;
	}

	/**
	 * Gets the levelVersion
	 *
	 * @return int
	 */
	public int getLevelVersion() {
		return levelVersion;
	}

	/**
	 * Gets the gameVersion
	 *
	 * @return int
	 */
	public int getGameVersion() {
		return gameVersion;
	}

	/**
	 * Gets the objectCount
	 *
	 * @return int
	 */
	public int getObjectCount() {
		return objectCount;
	}

	/**
	 * Gets the isDemon
	 *
	 * @return boolean
	 */
	public boolean isDemon() {
		return isDemon;
	}

	/**
	 * Gets the isAuto
	 *
	 * @return boolean
	 */
	public boolean isAuto() {
		return isAuto;
	}

	/**
	 * Gets the originalLevelID
	 *
	 * @return long
	 */
	public long getOriginalLevelID() {
		return originalLevelID;
	}

	/**
	 * Gets the requestedStars
	 *
	 * @return int
	 */
	public int getRequestedStars() {
		return requestedStars;
	}

	/**
	 * Sets the id
	 *
	 * @param id - long
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Sets the name
	 *
	 * @param name - String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the creatorName
	 *
	 * @param creatorName - String
	 */
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	/**
	 * Sets the creatorID
	 *
	 * @param creatorID - long
	 */
	public void setCreatorID(long creatorID) {
		this.creatorID = creatorID;
	}

	/**
	 * Sets the description
	 *
	 * @param description - String
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets the difficulty
	 *
	 * @param difficulty - GDLevelDifficulty
	 */
	public void setDifficulty(GDLevelDifficulty difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * Sets the demonDifficulty
	 *
	 * @param demonDifficulty - GDLevelDemonDifficulty
	 */
	public void setDemonDifficulty(GDLevelDemonDifficulty demonDifficulty) {
		this.demonDifficulty = demonDifficulty;
	}

	/**
	 * Sets the stars
	 *
	 * @param stars - int
	 */
	public void setStars(int stars) {
		this.stars = stars;
	}

	/**
	 * Sets the featuredScore
	 *
	 * @param featuredScore - int
	 */
	public void setFeaturedScore(int featuredScore) {
		this.featuredScore = featuredScore;
	}

	/**
	 * Sets the isEpic
	 *
	 * @param isEpic - boolean
	 */
	public void setEpic(boolean isEpic) {
		this.isEpic = isEpic;
	}

	/**
	 * Sets the downloads
	 *
	 * @param downloads - int
	 */
	public void setDownloads(int downloads) {
		this.downloads = downloads;
	}

	/**
	 * Sets the likes
	 *
	 * @param likes - int
	 */
	public void setLikes(int likes) {
		this.likes = likes;
	}

	/**
	 * Sets the length
	 *
	 * @param length - GDLevelLength
	 */
	public void setLength(GDLevelLength length) {
		this.length = length;
	}

	/**
	 * Sets the song
	 *
	 * @param song - GDSong
	 */
	public void setSong(GDSong song) {
		this.song = song;
	}

	/**
	 * Sets the coinCount
	 *
	 * @param coinCount - int
	 */
	public void setCoinCount(int coinCount) {
		this.coinCount = coinCount;
	}

	/**
	 * Sets the hasCoinsVerified
	 *
	 * @param hasCoinsVerified - boolean
	 */
	public void setCoinsVerified(boolean hasCoinsVerified) {
		this.hasCoinsVerified = hasCoinsVerified;
	}

	/**
	 * Sets the levelVersion
	 *
	 * @param levelVersion - int
	 */
	public void setLevelVersion(int levelVersion) {
		this.levelVersion = levelVersion;
	}

	/**
	 * Sets the gameVersion
	 *
	 * @param gameVersion - int
	 */
	public void setGameVersion(int gameVersion) {
		this.gameVersion = gameVersion;
	}

	/**
	 * Sets the objectCount
	 *
	 * @param objectCount - int
	 */
	public void setObjectCount(int objectCount) {
		this.objectCount = objectCount;
	}

	/**
	 * Sets the isDemon
	 *
	 * @param isDemon - boolean
	 */
	public void setDemon(boolean isDemon) {
		this.isDemon = isDemon;
	}

	/**
	 * Sets the isAuto
	 *
	 * @param isAuto - boolean
	 */
	public void setAuto(boolean isAuto) {
		this.isAuto = isAuto;
	}

	/**
	 * Sets the originalLevelID
	 *
	 * @param originalLevelID - long
	 */
	public void setOriginalLevelID(long originalLevelID) {
		this.originalLevelID = originalLevelID;
	}

	/**
	 * Sets the requestedStars
	 *
	 * @param requestedStars - int
	 */
	public void setRequestedStars(int requestedStars) {
		this.requestedStars = requestedStars;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof GDLevelPreview))
			return false;
		GDLevelPreview other = (GDLevelPreview) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GDLevelPreview [id=" + id + ", name=" + name + ", creatorName=" + creatorName + ", creatorID="
				+ creatorID + ", description=" + description + ", difficulty=" + difficulty + ", demonDifficulty="
				+ demonDifficulty + ", stars=" + stars + ", featuredScore=" + featuredScore + ", isEpic=" + isEpic
				+ ", downloads=" + downloads + ", likes=" + likes + ", length=" + length + ", song=" + song
				+ ", coinCount=" + coinCount + ", hasCoinsVerified=" + hasCoinsVerified + ", levelVersion="
				+ levelVersion + ", gameVersion=" + gameVersion + ", objectCount=" + objectCount + ", isDemon="
				+ isDemon + ", isAuto=" + isAuto + ", originalLevelID=" + originalLevelID + ", requestedStars="
				+ requestedStars + "]";
	}
}