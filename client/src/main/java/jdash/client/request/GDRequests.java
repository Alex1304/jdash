package jdash.client.request;

import java.util.Map;

/**
 * Constants and static methods useful when building GD requests.
 */
public final class GDRequests {

    /* Routes */
    public static final String BASE_URL = "www.boomlings.com/database";
    public static final String GET_USER_INFO = "/getGJUserInfo20.php";
    public static final String USER_SEARCH = "/getGJUsers20.php";
    public static final String USER_SCORES = "/getGJScores20.php";
    public static final String GET_USER_COMMENTS = "/getGJAccountComments20.php";
    public static final String DOWNLOAD_LEVEL = "/downloadGJLevel22.php";
    public static final String GET_LEVEL_COMMENTS = "/getGJComments21.php";
    public static final String LEVEL_SEARCH = "/getGJLevels21.php";
    public static final String GET_SONG_INFO = "/getGJSongInfo.php";
    public static final String GET_PRIVATE_MESSAGES = "/getGJMessages20.php";
    public static final String READ_PRIVATE_MESSAGE = "/downloadGJMessage20.php";
    public static final String SEND_PRIVATE_MESSAGE = "/uploadGJMessage20.php";
    public static final String GET_TIMELY = "/getGJDailyLevel.php";
    public static final String LOGIN = "/accounts/loginGJAccount.php";

    /* Params */
    public static final String GAME_VERSION = "21";
    public static final String BINARY_VERSION = "35";
    public static final String SECRET = "Wmfd2893gb7";

    private GDRequests() {
    }

    public static Map<String, String> commonParams() {
        return Map.ofEntries(
                Map.entry("gameVersion", GAME_VERSION),
                Map.entry("binaryVersion", BINARY_VERSION),
                Map.entry("gdw", "0"),
                Map.entry("secret", SECRET)
        );
    }
}
