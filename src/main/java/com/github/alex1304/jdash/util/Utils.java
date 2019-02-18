package com.github.alex1304.jdash.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.github.alex1304.jdash.entity.DemonDifficulty;
import com.github.alex1304.jdash.entity.Difficulty;
import com.github.alex1304.jdash.entity.GDSong;

/**
 * Contains utility static methods 
 *
 * @author Alex1304
 *
 */
public class Utils {
	private Utils() {
	}
	
	private static final Map<Integer, GDSong> AUDIO_TRACKS;
	
	static {
		AUDIO_TRACKS = new HashMap<>();
		AUDIO_TRACKS.put(0, new GDSong("ForeverBound", "Stereo Madness"));
		AUDIO_TRACKS.put(1, new GDSong("DJVI", "Back On Track"));
		AUDIO_TRACKS.put(2, new GDSong("Step", "Polargeist"));
		AUDIO_TRACKS.put(3, new GDSong("DJVI", "Dry Out"));
		AUDIO_TRACKS.put(4, new GDSong("DJVI", "Base After Base"));
		AUDIO_TRACKS.put(5, new GDSong("DJVI", "Cant Let Go"));
		AUDIO_TRACKS.put(6, new GDSong("Waterflame", "Jumper"));
		AUDIO_TRACKS.put(7, new GDSong("Waterflame", "Time Machine"));
		AUDIO_TRACKS.put(8, new GDSong("DJVI", "Cycles"));
		AUDIO_TRACKS.put(9, new GDSong("DJVI", "xStep"));
		AUDIO_TRACKS.put(10, new GDSong("Waterflame", "Clutterfunk"));
		AUDIO_TRACKS.put(11, new GDSong("DJ-Nate", "Theory of Everything"));
		AUDIO_TRACKS.put(12, new GDSong("Waterflame", "Electroman Adventures"));
		AUDIO_TRACKS.put(13, new GDSong("DJ-Nate", "Clubstep"));
		AUDIO_TRACKS.put(14, new GDSong("DJ-Nate", "Electrodynamix"));
		AUDIO_TRACKS.put(15, new GDSong("Waterflame", "Hexagon Force"));
		AUDIO_TRACKS.put(16, new GDSong("Waterflame", "Blast Processing"));
		AUDIO_TRACKS.put(17, new GDSong("DJ-Nate", "Theory of Everything 2"));
		AUDIO_TRACKS.put(18, new GDSong("Waterflame", "Geometrical Dominator"));
		AUDIO_TRACKS.put(19, new GDSong("F-777", "Deadlocked"));
		AUDIO_TRACKS.put(20, new GDSong("MDK", "Fingerdash"));
	}
	
	/**
	 * Transforms a string into a map. The string must be in a specific format for this
	 * method to work. For example, a string formatted as <code>"1:abc:2:def:3:xyz"</code>
	 * will return the following map:
	 * <pre>
	 * 1 =&gt; abc
	 * 2 =&gt; def
	 * 3 =&gt; xyz
	 * </pre>
	 * 
	 * @param str - the string to convert to map
	 * @param regex - the regex representing the separator between values in the string. In the example above,
	 * it would be ":"
	 * @return a Map of Integer, String
	 * @throws NumberFormatException - if a key of the map couldn't be converted to int. Example, the string
	 * <code>1:abc:A:xyz</code> would throw this exception.
	 */
	public static Map<Integer, String> splitToMap(String str, String regex) {
		Map<Integer, String> map = new HashMap<>();
		String[] splitted = str.split(regex);
		
		for (int i = 0 ; i < splitted.length - 1 ; i += 2)
			map.put(Integer.parseInt(splitted[i]), splitted[i + 1]);
		
		return map;
	}
	
	/**
	 * Converts a set of integers to a string with the following format:
	 * <code>val1,val2,val3,val4</code>
	 * 
	 * @param intSet
	 *            - the set of integers to convert
	 * 
	 * @return String
	 */
	public static String setOfIntToString(Set<Integer> intSet) {
		StringBuffer sb = new StringBuffer();
		
		for (int val : intSet) {
			sb.append(val);
			sb.append(",");
		}
		
		if (!intSet.isEmpty())
			sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	/**
	 * Gets an audio track by its ID
	 * 
	 * @param id - the audio track id
	 * @return GDSong
	 */
	public static GDSong getAudioTrack(int id) {
		return AUDIO_TRACKS.containsKey(id) ?  AUDIO_TRACKS.get(id) : new GDSong("-", "Unknown");
	}

	/**
	 * Parses the String representing level creators into a Map that associates
	 * the creator ID with their name
	 * 
	 * @param creatorsInfoRD
	 *            - the String representing the creators
	 * @return a Map of Long, String
	 */
	public static Map<Long, String> structureCreatorsInfo(String creatorsInfoRD) {
		if (creatorsInfoRD.isEmpty())
			return new HashMap<>();
		
		String[] arrayCreatorsRD = creatorsInfoRD.split("\\|");
		Map<Long, String> structuredCreatorsInfo = new HashMap<>();
		
		for (String creatorRD : arrayCreatorsRD) {
			structuredCreatorsInfo.put(Long.parseLong(creatorRD.split(":")[0]), creatorRD.split(":")[1]);
		}
		
		return structuredCreatorsInfo;
	}
	
	/**
	 * Parses the String representing level songs into a Map that associates
	 * the song ID with their title
	 * 
	 * @param songsInfoRD
	 *            - the String representing the songs
	 * @return a Map of Long, String
	 */
	public static Map<Long, GDSong> structureSongsInfo(String songsInfoRD) {
		if (songsInfoRD.isEmpty())
			return new HashMap<>();

		String[] arraySongsRD = songsInfoRD.split("~:~");
		Map<Long, GDSong> result = new HashMap<>();

		for (String songRD : arraySongsRD) {
			Map<Integer, String> songMap = Utils.splitToMap(songRD, "~\\|~");
			long songID = Long.parseLong(songMap.get(Constants.INDEX_SONG_ID));
			String songTitle = songMap.get(Constants.INDEX_SONG_TITLE);
			String songAuthor = songMap.get(Constants.INDEX_SONG_AUTHOR);
			String songSize = songMap.get(Constants.INDEX_SONG_SIZE);
			String songURL = songMap.get(Constants.INDEX_SONG_URL);
			try {
				songURL = URLDecoder.decode(songURL, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			result.put(songID, new GDSong(songID, songAuthor, songSize, songTitle, songURL, true));
		}

		return result;
	}
	
	public static Difficulty valueToDifficulty(int value) {
		switch (value) {
			case 10:
				return Difficulty.EASY; 
			case 20:
				return Difficulty.NORMAL; 
			case 30:
				return Difficulty.HARD; 
			case 40:
				return Difficulty.HARDER;
			case 50:
				return Difficulty.INSANE;  
			default:
				return Difficulty.NA;
		}
	}
	
	public static DemonDifficulty valueToDemonDifficulty(int value) {
		switch (value) {
			case 3:
				return DemonDifficulty.EASY; 
			case 4:
				return DemonDifficulty.MEDIUM; 
			case 5:
				return DemonDifficulty.INSANE;
			case 6:
				return DemonDifficulty.EXTREME;  
			default:
				return DemonDifficulty.HARD;
		}
	}
}
