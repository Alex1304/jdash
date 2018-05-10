package com.github.alex1304.jdash.component;

/**
 * Represents a song used in GD levels
 * 
 * @author Alex1304
 *
 */
public class GDSong implements GDComponent {

	private long songID;
	private String songAuthorName;
	private String songSize;
	private String songTitle;

	/**
	 * @param songID
	 *            - the song ID
	 * @param songAuthorName
	 *            - the song author name
	 * @param songSize
	 *            - the song author size
	 * @param songTitle
	 *            - the song title
	 */
	public GDSong(long songID, String songAuthorName, String songSize, String songTitle) {
		this.songID = songID;
		this.songAuthorName = songAuthorName;
		this.songSize = songSize;
		this.songTitle = songTitle;
	}

	/**
	 * Gets the song ID
	 * 
	 * @return long
	 */
	public long getSongID() {
		return songID;
	}

	/**
	 * Gets the song author name
	 * 
	 * @return String
	 */
	public String getSongAuthorName() {
		return songAuthorName;
	}

	/**
	 * Gets the song size
	 * 
	 * @return String
	 */
	public String getSongSize() {
		return songSize;
	}

	/**
	 * Gets the song title
	 * 
	 * @return String
	 */
	public String getSongTitle() {
		return songTitle;
	}
}
