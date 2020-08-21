package com.github.alex1304.jdash.util;

/**
 * Constants corresponding to API routes for Geometry Dash
 */
public final class Routes {
	private Routes() {
	}
	
	public static final String BASE_URL = "www.boomlings.com/database";
	
	public static final String GET_USER_INFO = "/getGJUserInfo20.php";
	public static final String USER_SEARCH = "/getGJUsers20.php";
	public static final String GET_USER_LIST = "/getGJUserList20.php";
	public static final String USER_SCORES = "/getGJScores20.php";
	public static final String GET_USER_COMMENTS = "/getGJAccountComments20.php";
	public static final String DOWNLOAD_LEVEL = "/downloadGJLevel22.php";
	public static final String GET_LEVEL_COMMENTS = "/getGJComments21.php";
	public static final String LEVEL_SEARCH = "/getGJLevels21.php";
	public static final String RATE_LEVEL_STARS = "/rateGJStars211.php";
	public static final String RATE_LEVEL_DEMON = "/rateGJDemon21.php";
	public static final String GET_SONG_INFO = "/getGJSongInfo.php";
	public static final String GET_PRIVATE_MESSAGES = "/getGJMessages20.php";
	public static final String READ_PRIVATE_MESSAGE = "/downloadGJMessage20.php";
	public static final String SEND_PRIVATE_MESSAGE = "/uploadGJMessage20.php";
	public static final String GET_TIMELY = "/getGJDailyLevel.php";
	public static final String BLOCK_USER = "/blockGJUser20.php";
	public static final String UNBLOCK_USER = "/unblockGJUser20.php";
	public static final String LOGIN = "/accounts/loginGJAccount.php";
}
