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
	private GDLevelDifficulty difficulty;
	private GDLevelDemonDifficulty demonDifficulty;
	private int stars;
	private String songTitle;
	private int coinCount;
	private boolean hasCoinsVerified;
	private int featuredScore;
	private boolean isEpic;
	private int downloads;
	private int likes;
	private GDLevelLength length;
	private boolean isDemon;
	private boolean isAuto;
	
	/**
	 * @param id
	 *            - the level ID
	 * @param name
	 *            - the level name
	 * @param creatorName
	 *            - the name of the creator of this level
	 * @param difficulty
	 *            - the level difficulty
	 * @param demonDifficulty
	 *            - the level demon difficulty
	 * @param stars
	 *            - the number of stars assigned to this level
	 * @param songTitle
	 *            - the title of the song used in the level
	 * @param featuredScore
	 *            - the featured score of the level. This score is mainly used
	 *            to sort levels in the Featured section.
	 * @param isEpic
	 *            - whether the level is marked as Epic
	 * @param downloads
	 *            - the number of downloads of the level
	 * @param likes
	 *            - the number of likes the level received
	 * @param length
	 *            - the level length
	 * @param coinCount
	 *            - the number of coins the level has
	 * @param hasCoinsVerified
	 *            - whether the coins are verified
	 * @param isDemon
	 *            - whether this level is Demon
	 * @param isAuto
	 *            - whether this level is auto
	 */
	public GDLevelPreview(long id, String name, String creatorName, GDLevelDifficulty difficulty,
			GDLevelDemonDifficulty demonDifficulty, int stars, String songTitle, int featuredScore, boolean isEpic, int downloads,
			int likes, GDLevelLength length, int coinCount, boolean hasCoinsVerified,
			boolean isDemon, boolean isAuto) {
		this.id = id;
		this.name = name;
		this.creatorName = creatorName;
		this.difficulty = difficulty;
		this.demonDifficulty = demonDifficulty;
		this.stars = stars;
		this.songTitle = songTitle;
		this.featuredScore = featuredScore;
		this.isEpic = isEpic;
		this.downloads = downloads;
		this.likes = likes;
		this.length = length;
		this.coinCount = coinCount;
		this.hasCoinsVerified = hasCoinsVerified;
		this.isDemon = isDemon;
		this.isAuto = isAuto;
	}

	/**
	 * Gets the level ID
	 * 
	 * @return long
	 */
	public long getId() {
		return id;
	}

	/**
	 * Gets the level name
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the name of the creator of this level
	 * 
	 * @return String
	 */
	public String getCreatorName() {
		return creatorName;
	}

	/**
	 * Gets the level difficulty
	 * 
	 * @return GDLevelDifficulty
	 */
	public GDLevelDifficulty getDifficulty() {
		return difficulty;
	}

	/**
	 * Gets the level demon difficulty
	 * 
	 * @return GDLevelDemonDifficulty
	 */
	public GDLevelDemonDifficulty getDemonDifficulty() {
		return demonDifficulty;
	}

	/**
	 * Gets the number of stars assigned to this level
	 * 
	 * @return int
	 */
	public int getStars() {
		return stars;
	}

	/**
	 * Gets the title of the song used in the level
	 * 
	 * @return String
	 */
	public String getSongTitle() {
		return songTitle;
	}

	/**
	 * Gets the featured score of the level. This score is mainly used to sort
	 * levels in the Featured section.
	 * 
	 * @return int
	 */
	public int getFeaturedScore() {
		return featuredScore;
	}

	/**
	 * Gets whether the level is marked as Epic
	 * 
	 * @return boolean
	 */
	public boolean isEpic() {
		return isEpic;
	}

	/**
	 * Gets the number of downloads of the level
	 *
	 * @return int
	 */
	public int getDownloads() {
		return downloads;
	}

	/**
	 * Gets the number of likes the level received
	 * 
	 * @return int
	 */
	public int getLikes() {
		return likes;
	}

	/**
	 * Gets the level length
	 * 
	 * @return GDLevelLength
	 */
	public GDLevelLength getLength() {
		return length;
	}

	/**
	 * Gets the number of coins the level has
	 * 
	 * @return int
	 */
	public int getCoinCount() {
		return coinCount;
	}

	/**
	 * Gets whether the coins are verified
	 * 
	 * @return boolean
	 */
	public boolean hasCoinsVerified() {
		return hasCoinsVerified;
	}

	/**
	 * Gets whether this level is Demon
	 * 
	 * @return boolean
	 */
	public boolean isDemon() {
		return isDemon;
	}

	/**
	 * Gets whether this level is auto
	 * 
	 * @return boolean
	 */
	public boolean isAuto() {
		return isAuto;
	}

	@Override
	public String toString() {
		return "GDLevelPreview [id=" + id + ", name=" + name + ", creatorName=" + creatorName + ", difficulty="
				+ difficulty + ", demonDifficulty=" + demonDifficulty + ", stars=" + stars + ", songTitle=" + songTitle
				+ ", coinCount=" + coinCount + ", hasCoinsVerified=" + hasCoinsVerified + ", featuredScore="
				+ featuredScore + ", isEpic=" + isEpic + ", downloads=" + downloads + ", likes=" + likes + ", length="
				+ length + ", isDemon=" + isDemon + ", isAuto=" + isAuto + "]";
	}
}
