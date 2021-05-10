package jdash.common.internal;

/**
 * Index values used by the parser or the client to retrieve info on various things in Geometry Dash.
 */
public final class Indexes {
    public static final int LEVEL_ID = 1;

    /* GD Level data */
    public static final int LEVEL_NAME = 2;
    public static final int LEVEL_DESCRIPTION = 3;
    public static final int LEVEL_DATA = 4;
    public static final int LEVEL_VERSION = 5;
    public static final int LEVEL_CREATOR_ID = 6;
    public static final int LEVEL_DIFFICULTY = 9;
    public static final int LEVEL_DOWNLOADS = 10;
    public static final int LEVEL_AUDIO_TRACK = 12;
    public static final int LEVEL_GAME_VERSION = 13;
    public static final int LEVEL_LIKES = 14;
    public static final int LEVEL_LENGTH = 15;
    public static final int LEVEL_IS_DEMON = 17;
    public static final int LEVEL_STARS = 18;
    public static final int LEVEL_FEATURED_SCORE = 19;
    public static final int LEVEL_IS_AUTO = 25;
    public static final int LEVEL_PASS = 27;
    public static final int LEVEL_UPLOADED_TIMESTAMP = 28;
    public static final int LEVEL_LAST_UPDATED_TIMESTAMP = 29;
    public static final int LEVEL_ORIGINAL = 30;
    public static final int LEVEL_SONG_ID = 35;
    public static final int LEVEL_COIN_COUNT = 37;
    public static final int LEVEL_COIN_VERIFIED = 38;
    public static final int LEVEL_REQUESTED_STARS = 39;
    public static final int LEVEL_IS_EPIC = 42;
    public static final int LEVEL_DEMON_DIFFICULTY = 43;
    public static final int LEVEL_OBJECT_COUNT = 45;
    public static final int SONG_ID = 1;

    /* Song data */
    public static final int SONG_TITLE = 2;
    public static final int SONG_AUTHOR = 4;
    public static final int SONG_SIZE = 5;
    public static final int SONG_URL = 10;
    public static final int USER_NAME = 1;

    /* User data */
    public static final int USER_PLAYER_ID = 2;
    public static final int USER_STARS = 3;
    public static final int USER_DEMONS = 4;
    public static final int USER_CREATOR_POINTS = 8;
    public static final int USER_ICON = 9;
    public static final int USER_COLOR_1 = 10;
    public static final int USER_COLOR_2 = 11;
    public static final int USER_SECRET_COINS = 13;
    public static final int USER_ICON_TYPE = 14;
    public static final int USER_GLOW_OUTLINE = 15;
    public static final int USER_ACCOUNT_ID = 16;
    public static final int USER_USER_COINS = 17;
    public static final int USER_PRIVATE_MESSAGE_POLICY = 18;
    public static final int USER_FRIEND_REQUEST_POLICY = 19;
    public static final int USER_YOUTUBE = 20;
    public static final int USER_ICON_CUBE = 21;
    public static final int USER_ICON_SHIP = 22;
    public static final int USER_ICON_BALL = 23;
    public static final int USER_ICON_UFO = 24;
    public static final int USER_ICON_WAVE = 25;
    public static final int USER_ICON_ROBOT = 26;
    public static final int USER_GLOW_OUTLINE_2 = 28;
    public static final int USER_GLOBAL_RANK = 30;
    public static final int USER_ICON_SPIDER = 43;
    public static final int USER_TWITTER = 44;
    public static final int USER_TWITCH = 45;
    public static final int USER_DIAMONDS = 46;
    public static final int USER_DEATH_EFFECT = 47;
    public static final int USER_ROLE = 49;
    public static final int USER_COMMENT_HISTORY_POLICY = 50;
    public static final int MESSAGE_ID = 1;

    /* Message data */
    public static final int MESSAGE_SENDER_ID = 2;
    public static final int MESSAGE_SENDER_NAME = 6;
    public static final int MESSAGE_SUBJECT = 4;
    public static final int MESSAGE_BODY = 5;
    public static final int MESSAGE_TIMESTAMP = 7;
    public static final int MESSAGE_IS_READ = 8;
    public static final int COMMENT_BODY = 2;

    /* Comment data */
    public static final int COMMENT_SENDER_ID = 3;
    public static final int COMMENT_LIKES = 4;
    public static final int COMMENT_ID = 6;
    public static final int COMMENT_UPLOADED_TIMESTAMP = 9;
    public static final int COMMENT_PERCENTAGE = 10;
    public static final int COMMENT_SENDER_ROLE = 11;
    private Indexes() {
    }
}