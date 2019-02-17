package com.github.alex1304.jdash.entity;

/**
 * Represents a song used in GD levels
 * 
 * @author Alex1304
 *
 */
public class GDSong implements GDEntity {

	private long id;
	private String songAuthorName;
	private String songSize;
	private String songTitle;
	private String downloadURL;
	private boolean isCustom;
	
	public GDSong() {
		
	}

	/**
	 * @param id
	 *            - the song ID
	 * @param songAuthorName
	 *            - the song author name
	 * @param songSize
	 *            - the song author size
	 * @param songTitle
	 *            - the song title
	 * @param downloadURL
	 *            - link to the song audio file
	 * @param isCustom
	 *            - whether the song is custom
	 */
	public GDSong(long id, String songAuthorName, String songSize, String songTitle, String downloadURL, boolean isCustom) {
		this.id = id;
		this.songAuthorName = songAuthorName;
		this.songSize = songSize;
		this.songTitle = songTitle;
		this.downloadURL = downloadURL;
		this.isCustom = isCustom;
	}
	
	/**
	 * Quick constructor for non-custom songs
	 * 
	 * @param songAuthorName
	 *            - the song author name
	 * @param songTitle
	 *            - the song title
	 */
	public GDSong(String songAuthorName, String songTitle) {
		this(0, songAuthorName, "", songTitle, "", false);
	}
	
	public static GDSong unknownSong(long songID) {
		return new GDSong(songID, "-", "", "Unknown", "", songID > 0);
	}

	@Override
	public long getId() {
		return id;
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

	/**
	 * Gets the downloadURL
	 *
	 * @return String
	 */
	public String getDownloadURL() {
		return downloadURL;
	}

	/**
	 * Gets whether the song is custom
	 *
	 * @return boolean
	 */
	public boolean isCustom() {
		return isCustom;
	}

	/**
	 * Sets the songID
	 *
	 * @param songID - long
	 */
	public void setSongID(long songID) {
		this.id = songID;
	}

	/**
	 * Sets the songAuthorName
	 *
	 * @param songAuthorName - String
	 */
	public void setSongAuthorName(String songAuthorName) {
		this.songAuthorName = songAuthorName;
	}

	/**
	 * Sets the songSize
	 *
	 * @param songSize - String
	 */
	public void setSongSize(String songSize) {
		this.songSize = songSize;
	}

	/**
	 * Sets the songTitle
	 *
	 * @param songTitle - String
	 */
	public void setSongTitle(String songTitle) {
		this.songTitle = songTitle;
	}

	/**
	 * Sets the downloadURL
	 *
	 * @param downloadURL - String
	 */
	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}

	/**
	 * Sets the isCustom
	 *
	 * @param isCustom - boolean
	 */
	public void setCustom(boolean isCustom) {
		this.isCustom = isCustom;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GDUser)) {
			return false;
		}
		GDSong song = (GDSong) obj;
		return song.id == id;
	}
	
	@Override
	public int hashCode() {
		return Long.hashCode(id);
	}

	@Override
	public String toString() {
		return "GDSong [songID=" + id + ", songAuthorName=" + songAuthorName + ", songSize=" + songSize
				+ ", songTitle=" + songTitle + ", downloadURL=" + downloadURL + ", isCustom=" + isCustom + "]";
	}
}
