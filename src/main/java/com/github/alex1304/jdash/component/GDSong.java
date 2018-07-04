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
	private String downloadURL;
	private boolean isCustom;

	/**
	 * @param songID
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
	public GDSong(long songID, String songAuthorName, String songSize, String songTitle, String downloadURL, boolean isCustom) {
		this.songID = songID;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (songID ^ (songID >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "GDSong [songID=" + songID + ", songAuthorName=" + songAuthorName + ", songSize=" + songSize
				+ ", songTitle=" + songTitle + ", downloadURL=" + downloadURL + ", isCustom=" + isCustom + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof GDSong))
			return false;
		GDSong other = (GDSong) obj;
		if (songID != other.songID)
			return false;
		return true;
	}
}
