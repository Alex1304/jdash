package com.github.alex1304.jdash.util;

/**
 * Useful constants to be uses anywhere in the project.
 * 
 * @author Alex1304
 *
 */
public interface Constants {
	/* GD Level data */

	static final int INDEX_LEVEL_ID = 1;
	static final int INDEX_LEVEL_NAME = 2;
	static final int INDEX_LEVEL_VERSION = 5;
	static final int INDEX_LEVEL_GAME_VERSION = 13;
	static final int INDEX_LEVEL_CREATOR_ID = 6;
	static final int INDEX_LEVEL_DESCRIPTION = 3;
	static final int INDEX_LEVEL_DIFFICULTY = 9;
	static final int INDEX_LEVEL_DEMON_DIFFICULTY = 43;
	static final int INDEX_LEVEL_STARS = 18;
	static final int INDEX_LEVEL_FEATURED_SCORE = 19;
	static final int INDEX_LEVEL_OBJECT_COUNT = 45;
	static final int INDEX_LEVEL_IS_EPIC = 42;
	static final int INDEX_LEVEL_DOWNLOADS = 10;
	static final int INDEX_LEVEL_IS_DEMON = 17;
	static final int INDEX_LEVEL_IS_AUTO = 25;
	static final int INDEX_LEVEL_LIKES = 14;
	static final int INDEX_LEVEL_LENGTH = 15;
	static final int INDEX_LEVEL_ORIGINAL = 30;
	static final int INDEX_LEVEL_PASS = 27;
	static final int INDEX_LEVEL_AUDIO_TRACK = 12;
	static final int INDEX_LEVEL_SONG_ID = 35;
	static final int INDEX_LEVEL_COIN_COUNT = 37;
	static final int INDEX_LEVEL_COIN_VERIFIED = 38;
	static final int INDEX_LEVEL_REQUESTED_STARS = 39;
	static final int INDEX_LEVEL_UPLOADED_TIMESTAMP = 28;
	static final int INDEX_LEVEL_LAST_UPDATED_TIMESTAMP = 29;

	/* Level search type property */

	static final int LEVEL_SEARCH_TYPE_REGULAR = 0;
	static final int LEVEL_SEARCH_TYPE_RECENT = 4;
	static final int LEVEL_SEARCH_TYPE_TRENDING = 3;
	static final int LEVEL_SEARCH_TYPE_MOST_DOWNLOADED = 1;
	static final int LEVEL_SEARCH_TYPE_MOST_LIKED = 2;
	static final int LEVEL_SEARCH_TYPE_FEATURED = 6;
	static final int LEVEL_SEARCH_TYPE_MAGIC = 6;
	static final int LEVEL_SEARCH_TYPE_AWARDED = 11;
	static final int LEVEL_SEARCH_TYPE_HALL_OF_FAME = 16;

	/* Level search diff property */

	static final int LEVEL_SEARCH_DIFF_ALL = 0;
	static final int LEVEL_SEARCH_DIFF_NA = -1;
	static final int LEVEL_SEARCH_DIFF_EASY = 1;
	static final int LEVEL_SEARCH_DIFF_NORMAL = 2;
	static final int LEVEL_SEARCH_DIFF_HARD = 3;
	static final int LEVEL_SEARCH_DIFF_HARDER = 4;
	static final int LEVEL_SEARCH_DIFF_INSANE = 5;
	static final int LEVEL_SEARCH_DIFF_DEMON = -2;
	static final int LEVEL_SEARCH_DIFF_EASY_DEMON = 1;
	static final int LEVEL_SEARCH_DIFF_MEDIUM_DEMON = 2;
	static final int LEVEL_SEARCH_DIFF_HARD_DEMON = 3;
	static final int LEVEL_SEARCH_DIFF_INSANE_DEMON = 4;
	static final int LEVEL_SEARCH_DIFF_EXTREME_DEMON = 5;

	/* Song data */

	static final int INDEX_SONG_ID = 1;
	static final int INDEX_SONG_TITLE = 2;
	static final int INDEX_SONG_AUTHOR = 4;
	static final int INDEX_SONG_SIZE = 5;
	static final int INDEX_SONG_URL = 10;

	/* User data */

	static final int INDEX_USER_NAME = 1;
	static final int INDEX_USER_PLAYER_ID = 2;
	static final int INDEX_USER_STARS = 3;
	static final int INDEX_USER_DEMONS = 4;
	static final int INDEX_USER_CREATOR_POINTS = 8;
	static final int INDEX_USER_COLOR_1 = 10;
	static final int INDEX_USER_COLOR_2 = 11;
	static final int INDEX_USER_SECRET_COINS = 13;
	static final int INDEX_USER_ACCOUNT_ID = 16;
	static final int INDEX_USER_USER_COINS = 17;
	static final int INDEX_USER_PRIVATE_MESSAGE_POLICY = 18;
	static final int INDEX_USER_FRIEND_REQUEST_POLICY = 19;
	static final int INDEX_USER_YOUTUBE = 20;
	static final int INDEX_USER_ICON_CUBE = 21;
	static final int INDEX_USER_ICON_SHIP = 22;
	static final int INDEX_USER_ICON_BALL = 23;
	static final int INDEX_USER_ICON_UFO = 24;
	static final int INDEX_USER_ICON_WAVE = 25;
	static final int INDEX_USER_ICON_ROBOT = 26;
	static final int INDEX_USER_GLOW = 28;
	static final int INDEX_USER_GLOBAL_RANK = 30;
	static final int INDEX_USER_ICON_SPIDER = 43;
	static final int INDEX_USER_TWITTER = 44;
	static final int INDEX_USER_TWITCH = 45;
	static final int INDEX_USER_DIAMONDS = 46;
	static final int INDEX_USER_DEATH_EFFECT = 47;
	static final int INDEX_USER_ROLE = 49;
	static final int INDEX_USER_COMMENT_HISTORY_POLICY = 50;

	/* Message data */

	static final int INDEX_MESSAGE_ID = 1;
	static final int INDEX_MESSAGE_SENDER_ID = 2;
	static final int INDEX_MESSAGE_SENDER_NAME = 6;
	static final int INDEX_MESSAGE_SUBJECT = 4;
	static final int INDEX_MESSAGE_BODY = 5;
	static final int INDEX_MESSAGE_TIMESTAMP = 7;
	static final int INDEX_MESSAGE_IS_READ = 8;

	/* Timely level IDs */

	static final int DAILY_LEVEL_ID = -1;
	static final int WEEKLY_DEMON_ID = -2;

}
