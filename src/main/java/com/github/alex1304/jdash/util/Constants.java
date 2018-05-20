package com.github.alex1304.jdash.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.github.alex1304.jdash.component.property.GDLevelDemonDifficulty;
import com.github.alex1304.jdash.component.property.GDLevelDifficulty;
import com.github.alex1304.jdash.util.robtopsweakcrypto.RobTopsWeakCrypto;

/**
 * Useful constants to be uses anywhere in the project.
 * 
 * @author Alex1304
 *
 */
public interface Constants {

	/**
	 * The API URL of Geometry Dash
	 */
	public static final String GD_API_URL = "http://www.boomlings.com/database";

	/**
	 * Global params to be put in each request that doesn't require
	 * authentication
	 * 
	 * @return a Map of String, String, representing the global params.
	 */
	static Map<String, String> globalHttpRequestParams() {
		Map<String, String> params = new HashMap<>();
		params.put("gameVersion", "21");
		params.put("binaryVersion", "34");
		params.put("gdw", "0");
		params.put("secret", "Wmfd2893gb7");

		return params;
	}

	/**
	 * Global params to be put in each request where authentication is required
	 * 
	 * @param accountID
	 *            - the client account ID
	 * @param password
	 *            - the client account password
	 * @return a Map of String, String, representing the global params with
	 *         authentication info
	 */
	static Map<String, String> globalHttpRequestParamsWithAuthentication(long accountID, String password) {
		Map<String, String> params = globalHttpRequestParams();
		params.put("accountID", "" + accountID);
		params.put("gjp", RobTopsWeakCrypto.encodeGDAccountPassword(password));

		return params;
	}
	
	/* GD Level data */
	
	public static final int INDEX_LEVEL_ID = 1;
	public static final int INDEX_LEVEL_NAME = 2;
	public static final int INDEX_LEVEL_VERSION = 5;
	public static final int INDEX_LEVEL_GAME_VERSION = 13;
	public static final int INDEX_LEVEL_CREATOR_ID = 6;
	public static final int INDEX_LEVEL_DESCRIPTION = 3;
	public static final int INDEX_LEVEL_DIFFICULTY = 9;
	public static final int INDEX_LEVEL_DEMON_DIFFICULTY = 43;
	public static final int INDEX_LEVEL_STARS = 18;
	public static final int INDEX_LEVEL_FEATURED_SCORE = 19;
	public static final int INDEX_LEVEL_OBJECT_COUNT = 45;
	public static final int INDEX_LEVEL_IS_EPIC = 42;
	public static final int INDEX_LEVEL_DOWNLOADS = 10;
	public static final int INDEX_LEVEL_IS_DEMON = 17;
	public static final int INDEX_LEVEL_IS_AUTO = 25;
	public static final int INDEX_LEVEL_LIKES = 14;
	public static final int INDEX_LEVEL_LENGTH = 15;
	public static final int INDEX_LEVEL_ORIGINAL = 30;
	public static final int INDEX_LEVEL_PASS = 27;
	public static final int INDEX_LEVEL_AUDIO_TRACK = 12;
	public static final int INDEX_LEVEL_SONG_ID = 35;
	public static final int INDEX_LEVEL_COIN_COUNT = 37;
	public static final int INDEX_LEVEL_COIN_VERIFIED = 38;
	public static final int INDEX_LEVEL_REQUESTED_STARS = 39;
	public static final int INDEX_LEVEL_UPLOADED_TIMESTAMP = 28;
	public static final int INDEX_LEVEL_LAST_UPDATED_TIMESTAMP = 29;
	

	/**
	 * Associates the integer value in the raw data with the corresponding difficulty
	 */
	public static final Function<Integer, GDLevelDifficulty> VALUE_TO_DIFFICULTY = value -> {
		switch (value) {
			case 10:
				return GDLevelDifficulty.EASY; 
			case 20:
				return GDLevelDifficulty.NORMAL; 
			case 30:
				return GDLevelDifficulty.HARD; 
			case 40:
				return GDLevelDifficulty.HARDER;
			case 50:
				return GDLevelDifficulty.INSANE;  
			default:
				return GDLevelDifficulty.NA;
		}
	};
	
	/**
	 * Associates the integer value in the raw data with the corresponding Demon difficulty
	 */
	public static final Function<Integer, GDLevelDemonDifficulty> VALUE_TO_DEMON_DIFFICULTY = value -> {
		switch (value) {
			case 3:
				return GDLevelDemonDifficulty.EASY; 
			case 4:
				return GDLevelDemonDifficulty.MEDIUM; 
			case 5:
				return GDLevelDemonDifficulty.INSANE;
			case 6:
				return GDLevelDemonDifficulty.EXTREME;  
			default:
				return GDLevelDemonDifficulty.HARD;
		}
	};
	
	/* Level search type property */
	
	public static final int LEVEL_SEARCH_TYPE_REGULAR = 0;
	public static final int LEVEL_SEARCH_TYPE_RECENT = 4;
	public static final int LEVEL_SEARCH_TYPE_TRENDING = 3;
	public static final int LEVEL_SEARCH_TYPE_MOST_DOWNLOADED = 1;
	public static final int LEVEL_SEARCH_TYPE_MOST_LIKED = 2;
	public static final int LEVEL_SEARCH_TYPE_FEATURED = 6;
	public static final int LEVEL_SEARCH_TYPE_MAGIC = 6;
	public static final int LEVEL_SEARCH_TYPE_AWARDED = 11;
	public static final int LEVEL_SEARCH_TYPE_HALL_OF_FAME = 16;
	
	/* Level search diff property */

	public static final int LEVEL_SEARCH_DIFF_ALL = 0;
	public static final int LEVEL_SEARCH_DIFF_NA = -1;
	public static final int LEVEL_SEARCH_DIFF_EASY = 1;
	public static final int LEVEL_SEARCH_DIFF_NORMAL = 2;
	public static final int LEVEL_SEARCH_DIFF_HARD = 3;
	public static final int LEVEL_SEARCH_DIFF_HARDER = 4;
	public static final int LEVEL_SEARCH_DIFF_INSANE = 5;
	public static final int LEVEL_SEARCH_DIFF_DEMON = -2;
	public static final int LEVEL_SEARCH_DIFF_EASY_DEMON = 1;
	public static final int LEVEL_SEARCH_DIFF_MEDIUM_DEMON = 2;
	public static final int LEVEL_SEARCH_DIFF_HARD_DEMON = 3;
	public static final int LEVEL_SEARCH_DIFF_INSANE_DEMON = 4;
	public static final int LEVEL_SEARCH_DIFF_EXTREME_DEMON = 5;
	
	/* Song data */

	public static final int INDEX_SONG_ID = 1;
	public static final int INDEX_SONG_TITLE = 2;
	public static final int INDEX_SONG_AUTHOR = 4;
	public static final int INDEX_SONG_SIZE = 5;
	
	/* User data */
	
	public static final int INDEX_USER_NAME = 1;
	public static final int INDEX_USER_PLAYER_ID = 2;
	public static final int INDEX_USER_SECRET_COINS = 13;
	public static final int INDEX_USER_USER_COINS = 17;
	public static final int INDEX_USER_STARS = 3;
	public static final int INDEX_USER_DIAMONDS = 46;
	public static final int INDEX_USER_DEMONS = 4;
	public static final int INDEX_USER_CREATOR_POINTS = 8;
	public static final int INDEX_USER_YOUTUBE = 20;
	public static final int INDEX_USER_GLOBAL_RANK = 30;
	public static final int INDEX_USER_ACCOUNT_ID = 16;
	public static final int INDEX_USER_ROLE = 49;
	public static final int INDEX_USER_TWITTER = 44;
	public static final int INDEX_USER_TWITCH = 45;
	
	/* Message data */
	
	public static final int INDEX_MESSAGE_ID = 1;
	public static final int INDEX_MESSAGE_SENDER_ID = 2;
	public static final int INDEX_MESSAGE_SENDER_NAME = 6;
	public static final int INDEX_MESSAGE_SUBJECT = 4;
	public static final int INDEX_MESSAGE_BODY = 5;
	public static final int INDEX_MESSAGE_TIMESTAMP = 7;
	public static final int INDEX_MESSAGE_IS_READ = 8;
	
}
