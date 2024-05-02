package jdash.client.response;

import jdash.common.entity.*;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.function.Function;

/**
 * Contains a set of predefined deserializer functions that can be directly passed to {@link
 * GDResponse#deserialize(Function)}.
 */
public final class GDResponseDeserializers {

    private static final LevelSearchResponseDeserializer LEVEL_SEARCH_RESPONSE = new LevelSearchResponseDeserializer();
    private static final LoginResponseDeserializer LOGIN_RESPONSE = new LoginResponseDeserializer();
    private static final UserProfileResponseDeserializer USER_PROFILE_RESPONSE = new UserProfileResponseDeserializer();
    private static final UserStatsListResponseDeserializer USER_STATS_LIST_RESPONSE =
            new UserStatsListResponseDeserializer();
    private static final UserListResponseDeserializer USER_LIST_RESPONSE = new UserListResponseDeserializer();
    private static final SongInfoResponseDeserializer SONG_INFO_RESPONSE = new SongInfoResponseDeserializer();
    private static final LevelDownloadResponseDeserializer LEVEL_DOWNLOAD_RESPONSE =
            new LevelDownloadResponseDeserializer();
    private static final DailyInfoResponseDeserializer DAILY_INFO_RESPONSE = new DailyInfoResponseDeserializer();
    private static final CommentsResponseDeserializer COMMENTS_RESPONSE = new CommentsResponseDeserializer();
    private static final PrivateMessagesResponseDeserializer PRIVATE_MESSAGES_RESPONSE =
            new PrivateMessagesResponseDeserializer();
    private static final PrivateMessageDownloadResponseDeserializer PRIVATE_MESSAGE_DOWNLOAD_RESPONSE =
            new PrivateMessageDownloadResponseDeserializer();
    private static final ListSearchResponseDeserializer LIST_SEARCH_RESPONSE = new ListSearchResponseDeserializer();

    private GDResponseDeserializers() {
        throw new AssertionError();
    }

    /**
     * A function that can deserialize a list of searched levels.
     *
     * @return a deserializer function
     */
    public static Function<String, List<GDLevel>> levelSearchResponse() {
        return LEVEL_SEARCH_RESPONSE;
    }

    /**
     * A function that can deserialize the response received upon successful login.
     *
     * @return a deserializer function
     */
    public static Function<String, Tuple2<Long, Long>> loginResponse() {
        return LOGIN_RESPONSE;
    }

    /**
     * A function that can deserialize a user profile.
     *
     * @return a deserializer function
     */
    public static Function<String, GDUserProfile> userProfileResponse() {
        return USER_PROFILE_RESPONSE;
    }

    /**
     * A function that can deserialize a list of user stats.
     *
     * @return a deserializer function
     */
    public static Function<String, List<GDUserStats>> userStatsListResponse() {
        return USER_STATS_LIST_RESPONSE;
    }

    /**
     * A function that can deserialize a list of users.
     *
     * @return a deserializer function
     */
    public static Function<String, List<GDUser>> userListResponse() {
        return USER_LIST_RESPONSE;
    }

    /**
     * A function that can deserialize information on a song.
     *
     * @return a deserializer function
     */
    public static Function<String, GDSong> songInfoResponse() {
        return SONG_INFO_RESPONSE;
    }

    /**
     * A function that can deserialize a downloaded leve.
     *
     * @return a deserializer function
     */
    public static Function<String, GDLevelDownload> levelDownloadResponse() {
        return LEVEL_DOWNLOAD_RESPONSE;
    }

    /**
     * A function that can deserialize the information on a daily level or weekly demon.
     *
     * @return a deserializer function
     */
    public static Function<String, GDDailyInfo> dailyInfoResponse() {
        return DAILY_INFO_RESPONSE;
    }

    /**
     * A function that can deserialize a list of comments.
     *
     * @return a deserializer function
     */
    public static Function<String, List<GDComment>> commentsResponse() {
        return COMMENTS_RESPONSE;
    }

    /**
     * A function that can deserialize a list of private messages.
     *
     * @return a deserializer function
     */
    public static Function<String, List<GDPrivateMessage>> privateMessagesResponse() {
        return PRIVATE_MESSAGES_RESPONSE;
    }

    /**
     * A function that can deserialize the content of a private message.
     *
     * @return a deserializer function
     */
    public static Function<String, GDPrivateMessageDownload> privateMessageDownloadResponse() {
        return PRIVATE_MESSAGE_DOWNLOAD_RESPONSE;
    }

    /**
     * A function that can deserialize a list of searched lists.
     *
     * @return a deserializer function
     */
    public static Function<String, List<GDList>> listSearchResponse() {
        return LIST_SEARCH_RESPONSE;
    }
}
