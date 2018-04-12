package com.alex1304dev.jdash.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains utility static methods 
 *
 * @author Alex1304
 *
 */
public abstract class Utils {
	
	/**
	 * transforms a string into a map. The string must be in a specific format for this
	 * method to work. For example, a string formatted as <code>"1:abc:2:def:3:xyz"</code>
	 * will return the following map:
	 * <pre>
	 * 1 => abc
	 * 2 => def
	 * 3 => xyz
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
}
