package com.github.alex1304.jdash.entity;

import java.util.Objects;

/**
 * Represents a song used in GD levels (GD �������� ���Ǵ� �뷡�� ��Ÿ���ϴ�.)
 * 
 * @author Alex1304 (���� Alex1304)
 *
 */
public final class GDSong implements GDEntity {

	private final long id;
	private final String songAuthorName;
	private final String songSize;
	private final String songTitle;
	private final String downloadURL;
	private final boolean isCustom;

	/**
	 * @param id - the song ID
	 * (@�Ű����� id - �뷡 ID)
	 * 
	 * @param songAuthorName - the song author name
	 * (@�Ű����� songAuthorName - �뷡 ���� �̸�)
	 * 
	 * @param songSize - the song author size
	 * (@�Ű����� songSize - �뷡�� ũ��)
	 * 
	 * 
	 * @param songTitle - the song title
	 * (@�Ű����� songTitle - �뷡 ����)
	 * 
	 * @param downloadURL - link to the song audio file
	 * (@�Ű����� downloadURL - �뷡 ����� ���Ͽ� ���� ��ũ)
	 * 
	 * @param isCustom - whether the song is custom
	 * (@�Ű����� isCustom - �� �뷡�� ����� �������� ����)
	 */
	public GDSong(long id, String songAuthorName, String songSize, String songTitle, String downloadURL,
			boolean isCustom) {
		this.id = id;
		this.songAuthorName = Objects.requireNonNull(songAuthorName);
		this.songSize = Objects.requireNonNull(songSize);
		this.songTitle = Objects.requireNonNull(songTitle);
		this.downloadURL = Objects.requireNonNull(downloadURL);
		this.isCustom = Objects.requireNonNull(isCustom);
	}

	/**
	 * Quick constructor for non-custom songs (����� �������� ���� ���� ���� ���� ������)
	 * 
	 * @param songAuthorName - the song author name
	 * (@�Ű����� songAuthorName - �뷡 ���� �̸�)
	 * 
	 * @param songTitle - the song title
	 * (@�Ű����� songTitle - �뷡 ����)
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
	 * Gets the song author name (�뷡 �ۼ��� �̸� ��������)
	 * 
	 * @return String (String���� ��ȯ�Ѵ�.)
	 */
	public String getSongAuthorName() {
		return songAuthorName;
	}

	/**
	 * Gets the song size (�뷡 ũ�� ��������)
	 * 
	 * @return String (String���� ��ȯ�Ѵ�.)
	 */
	public String getSongSize() {
		return songSize;
	}

	/**
	 * Gets the song title (�뷡 ���� ��������)
	 * 
	 * @return String (String���� ��ȯ�Ѵ�.)
	 */
	public String getSongTitle() {
		return songTitle;
	}

	/**
	 * Gets the downloadURL (�ٿ�ε�URL ��������)
	 * 
	 * @return String (String���� ��ȯ�Ѵ�.)
	 */
	public String getDownloadURL() {
		return downloadURL;
	}

	/**
	 * Gets whether the song is custom (�뷡�� ����� �������� ���� ��������)
	 *
	 * @return boolean (boolean���� ��ȯ�Ѵ�.)
	 */
	public boolean isCustom() {
		return isCustom;
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
		return "GDSong [songID=" + id + ", songAuthorName=" + songAuthorName + ", songSize=" + songSize + ", songTitle="
				+ songTitle + ", downloadURL=" + downloadURL + ", isCustom=" + isCustom + "]";
	}
}
