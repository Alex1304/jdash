package com.github.alex1304.jdash.entity;

import java.util.Objects;

/**
 * Represents a song used in GD levels (GD 레벨에서 사용되는 노래를 나타냅니다.)
 * 
 * @author Alex1304 (저자 Alex1304)
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
	 * (@매개변수 id - 노래 ID)
	 * 
	 * @param songAuthorName - the song author name
	 * (@매개변수 songAuthorName - 노래 저자 이름)
	 * 
	 * @param songSize - the song author size
	 * (@매개변수 songSize - 노래의 크기)
	 * 
	 * 
	 * @param songTitle - the song title
	 * (@매개변수 songTitle - 노래 제목)
	 * 
	 * @param downloadURL - link to the song audio file
	 * (@매개변수 downloadURL - 노래 오디오 파일에 대한 링크)
	 * 
	 * @param isCustom - whether the song is custom
	 * (@매개변수 isCustom - 그 노래가 사용자 정의인지 여부)
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
	 * Quick constructor for non-custom songs (사용자 지정되지 않은 곡을 위한 빠른 생성자)
	 * 
	 * @param songAuthorName - the song author name
	 * (@매개변수 songAuthorName - 노래 저자 이름)
	 * 
	 * @param songTitle - the song title
	 * (@매개변수 songTitle - 노래 제목)
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
	 * Gets the song author name (노래 작성자 이름 가져오기)
	 * 
	 * @return String (String으로 반환한다.)
	 */
	public String getSongAuthorName() {
		return songAuthorName;
	}

	/**
	 * Gets the song size (노래 크기 가져오기)
	 * 
	 * @return String (String으로 반환한다.)
	 */
	public String getSongSize() {
		return songSize;
	}

	/**
	 * Gets the song title (노래 제목 가져오기)
	 * 
	 * @return String (String으로 반환한다.)
	 */
	public String getSongTitle() {
		return songTitle;
	}

	/**
	 * Gets the downloadURL (다운로드URL 가져오기)
	 * 
	 * @return String (String으로 반환한다.)
	 */
	public String getDownloadURL() {
		return downloadURL;
	}

	/**
	 * Gets whether the song is custom (노래가 사용자 정의인지 여부 가져오기)
	 *
	 * @return boolean (boolean으로 반환한다.)
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
