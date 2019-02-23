package com.github.alex1304.jdash.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.github.alex1304.jdash.entity.GDSong;

public class ParseUtils {
	private ParseUtils() {
	}

	/**
	 * Transforms a string into a map. The string must be in a specific format for
	 * this method to work. For example, a string formatted as
	 * <code>"1:abc:2:def:3:xyz"</code> will return the following map:
	 * 
	 * <pre>
	 * 1 =&gt; abc
	 * 2 =&gt; def
	 * 3 =&gt; xyz
	 * </pre>
	 * 
	 * @param str   the string to convert to map
	 * @param regex the regex representing the separator between values in the
	 *              string. In the example above, it would be ":"
	 * @return a Map of Integer, String
	 * @throws NumberFormatException if a key of the map couldn't be converted to
	 *                               int. Example, the string
	 *                               <code>1:abc:A:xyz</code> would throw this
	 *                               exception.
	 */
	public static Map<Integer, String> splitToMap(String str, String regex) {
		Map<Integer, String> map = new HashMap<>();
		String[] splitted = str.split(regex);

		for (int i = 0; i < splitted.length - 1; i += 2)
			map.put(Integer.parseInt(splitted[i]), splitted[i + 1]);

		return map;
	}

	/**
	 * Parses the String representing level creators into a Map that associates the
	 * creator ID with their name
	 * 
	 * @param creatorsInfoRD the String representing the creators
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
	 * Parses the String representing level songs into a Map that associates the
	 * song ID with their title
	 * 
	 * @param songsInfoRD the String representing the songs
	 * @return a Map of Long, String
	 */
	public static Map<Long, GDSong> structureSongsInfo(String songsInfoRD) {
		if (songsInfoRD.isEmpty())
			return new HashMap<>();

		String[] arraySongsRD = songsInfoRD.split("~:~");
		Map<Long, GDSong> result = new HashMap<>();

		for (String songRD : arraySongsRD) {
			Map<Integer, String> songMap = splitToMap(songRD, "~\\|~");
			long songID = Long.parseLong(songMap.get(Indexes.SONG_ID));
			String songTitle = songMap.get(Indexes.SONG_TITLE);
			String songAuthor = songMap.get(Indexes.SONG_AUTHOR);
			String songSize = songMap.get(Indexes.SONG_SIZE);
			String songURL = songMap.get(Indexes.SONG_URL);
			try {
				songURL = URLDecoder.decode(songURL, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			result.put(songID, new GDSong(songID, songAuthor, songSize, songTitle, songURL, true));
		}

		return result;
	}

	/**
	 * Extracts the page info from the given raw data string. The string is expected
	 * to match the regex:
	 * 
	 * <pre>
	 * [0-9]+:[0-9]+:[0-9]+
	 * </pre>
	 * 
	 * @param pageInfoRD the String containing the info on the pagination.
	 * @return an int array of length 3 which [0] is the total element count, [1] is
	 *         the page number and [2] is the number of elements per page.
	 */
	public static int[] extractPageInfo(String pageInfoRD) {
		if (!pageInfoRD.matches("[0-9]+:[0-9]+:[0-9]+")) {
			throw new IllegalArgumentException("Malformed page info string");
		}
		String[] pageInfo = pageInfoRD.split(":");
		return new int[] { 
			Integer.parseInt(pageInfo[0]),
			Integer.parseInt(pageInfo[1]),
			Integer.parseInt(pageInfo[2])
		};
	}
}
