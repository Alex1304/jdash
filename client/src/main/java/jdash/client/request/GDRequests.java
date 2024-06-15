package jdash.client.request;

import java.util.Map;

/**
 * Constants and static methods useful when building GD requests.
 */
public final class GDRequests {

    /* Routes */
    public static final String BASE_URL = "https://www.boomlings.com/database";
    public static final String GET_GJ_USER_INFO_20 = "/getGJUserInfo20.php";
    public static final String GET_GJ_USERS_20 = "/getGJUsers20.php";
    public static final String GET_GJ_USER_LIST_20 = "/getGJUserList20.php";
    public static final String GET_GJ_SCORES_20 = "/getGJScores20.php";
    public static final String GET_GJ_ACCOUNT_COMMENTS_20 = "/getGJAccountComments20.php";
    public static final String DOWNLOAD_GJ_LEVEL_22 = "/downloadGJLevel22.php";
    public static final String GET_GJ_COMMENTS_21 = "/getGJComments21.php";
    public static final String GET_GJ_LEVELS_21 = "/getGJLevels21.php";
    public static final String GET_GJ_LEVEL_LISTS = "/getGJLevelLists.php";
    public static final String RATE_GJ_STARS_211 = "/rateGJStars211.php";
    public static final String RATE_GJ_DEMON_21 = "/rateGJDemon21.php";
    public static final String GET_GJ_SONG_INFO = "/getGJSongInfo.php";
    public static final String GET_GJ_MESSAGES_20 = "/getGJMessages20.php";
    public static final String DOWNLOAD_GJ_MESSAGE_20 = "/downloadGJMessage20.php";
    public static final String UPLOAD_GJ_MESSAGE_20 = "/uploadGJMessage20.php";
    public static final String GET_GJ_DAILY_LEVEL = "/getGJDailyLevel.php";
    public static final String BLOCK_GJ_USER_20 = "/blockGJUser20.php";
    public static final String UNBLOCK_GJ_USER_20 = "/unblockGJUser20.php";
    public static final String LOGIN_GJ_ACCOUNT = "/accounts/loginGJAccount.php";

    /* Params */
    public static final String GAME_VERSION = "22";
    public static final String BINARY_VERSION = "42";
    public static final String SECRET = "Wmfd2893gb7";

    private GDRequests() {
        throw new AssertionError();
    }

    /**
     * Gets the common parameters found in most requests.
     *
     * @return a map of parameters
     */
    public static Map<String, String> commonParams() {
        return Map.ofEntries(
                Map.entry("gameVersion", GAME_VERSION),
                Map.entry("binaryVersion", BINARY_VERSION),
                Map.entry("gdw", "0"),
                Map.entry("secret", SECRET)
        );
    }
}
