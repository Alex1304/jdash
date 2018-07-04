package com.github.alex1304.jdash.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.github.alex1304.jdash.component.GDSong;

/**
 * Contains utility static methods 
 *
 * @author Alex1304
 *
 */
public abstract class Utils {
	
	private static final Map<Integer, GDSong> AUDIO_TRACKS = initAudioTracks();
	
	private static Map<Integer, GDSong> initAudioTracks() {
		Map<Integer, GDSong> map = new HashMap<>();
		map.put(0, new GDSong("ForeverBound", "Stereo Madness"));
		map.put(1, new GDSong("DJVI", "Back On Track"));
		map.put(2, new GDSong("Step", "Polargeist"));
		map.put(3, new GDSong("DJVI", "Dry Out"));
		map.put(4, new GDSong("DJVI", "Base After Base"));
		map.put(5, new GDSong("DJVI", "Cant Let Go"));
		map.put(6, new GDSong("Waterflame", "Jumper"));
		map.put(7, new GDSong("Waterflame", "Time Machine"));
		map.put(8, new GDSong("DJVI", "Cycles"));
		map.put(9, new GDSong("DJVI", "xStep"));
		map.put(10, new GDSong("Waterflame", "Clutterfunk"));
		map.put(11, new GDSong("DJ-Nate", "Theory of Everything"));
		map.put(12, new GDSong("Waterflame", "Electroman Adventures"));
		map.put(13, new GDSong("DJ-Nate", "Clubstep"));
		map.put(14, new GDSong("DJ-Nate", "Electrodynamix"));
		map.put(15, new GDSong("Waterflame", "Hexagon Force"));
		map.put(16, new GDSong("Waterflame", "Blast Processing"));
		map.put(17, new GDSong("DJ-Nate", "Theory of Everything 2"));
		map.put(18, new GDSong("Waterflame", "Geometrical Dominator"));
		map.put(19, new GDSong("F-777", "Deadlocked"));
		map.put(20, new GDSong("MDK", "Fingerdash"));
		return map;
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
}
