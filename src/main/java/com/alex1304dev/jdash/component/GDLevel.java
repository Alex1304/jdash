package com.alex1304dev.jdash.component;

import com.alex1304dev.jdash.component.property.GDLevelDemonDifficulty;
import com.alex1304dev.jdash.component.property.GDLevelDifficulty;
import com.alex1304dev.jdash.component.property.GDLevelLength;

/**
 * Represents a level in the game.
 * 
 * @author Alex1304
 *
 */
public class GDLevel implements GDComponent {

	private long id;
	private String name;
	private long creatorID;
	private String description;
	private GDLevelDifficulty difficulty;
	private GDLevelDemonDifficulty demonDifficulty;
	private int stars;
	private int featuredScore;
	private boolean epic;
	private int downloads;
	private int likes;
	private GDLevelLength length;
	private int pass;
	
	/**
	 * @param id
	 *            - the level ID
	 * @param name
	 *            - the level name
	 * @param creatorID
	 *            - the account ID of the creator of this level
	 * @param description
	 *            - the description of the level
	 * @param difficulty
	 *            - the level difficulty
	 * @param demonDifficulty
	 *            - the level demon difficulty
	 * @param stars
	 *            - the number of stars assigned to this level
	 * @param featuredScore
	 *            - the featured score of the level. This score is mainly used
	 *            to sort levels in the Featured section.
	 * @param epic
	 *            - whether the level is marked as Epic
	 * @param downloads
	 *            - the number of downloads of the level
	 * @param likes
	 *            - the number of likes the level received
	 * @param length
	 *            - the level length
	 * @param pass
	 *            - the passcode to copy the level into the editor. -1 if the
	 *            level is not copyable, -2 if the level is free to copy.
	 */
	public GDLevel(long id, String name, long creatorID, String description, GDLevelDifficulty difficulty,
			GDLevelDemonDifficulty demonDifficulty, int stars, int featuredScore, boolean epic, int downloads,
			int likes, GDLevelLength length, int pass) {
		this.id = id;
		this.name = name;
		this.creatorID = creatorID;
		this.description = description;
		this.difficulty = difficulty;
		this.demonDifficulty = demonDifficulty;
		this.stars = stars;
		this.featuredScore = featuredScore;
		this.epic = epic;
		this.downloads = downloads;
		this.likes = likes;
		this.length = length;
		this.pass = pass;
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
	 * Gets the account ID of the creator of this level
	 * 
	 * @return long
	 */
	public long getCreatorID() {
		return creatorID;
	}
	
	/**
	 * Gets the description of the level
	 * 
	 * @return String
	 */
	public String getDescription() {
		return description;
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
		return epic;
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
	 * Gets the passcode to copy the level into the editor.
	 * -1 if the level is not copyable, -2 if the level is free to copy.
	 * 
	 * @return int
	 */
	public int getPass() {
		return pass;
	}
}
