package jdash.common.internal;

import jdash.common.*;
import jdash.common.entity.*;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static jdash.common.internal.Indexes.*;

public final class InternalUtils {

    private InternalUtils() {
    }

    /**
     * Transforms a string into a map. The string must be in a specific format for this method to work. For example, a
     * string formatted as
     * <code>"1:abc:2:def:3:xyz"</code> will return the following map:
     *
     * <pre>
     * 1 =&gt; abc
     * 2 =&gt; def
     * 3 =&gt; xyz
     * </pre>
     *
     * @param str   the string to convert to map
     * @param regex the regex representing the separator between values in the string. In the example above, it would be
     *              ":"
     * @return a Map of Integer, String
     * @throws NumberFormatException if a key of the map couldn't be converted to int. Example, the string
     *                               <code>1:abc:A:xyz</code> would throw this
     *                               exception.
     */
    public static Map<Integer, String> splitToMap(String str, String regex) {
        Map<Integer, String> map = new HashMap<>();
        String[] splitted = str.split(regex);

        for (int i = 0; i < splitted.length; i += 2)
            map.put(Integer.parseInt(splitted[i]), i < splitted.length - 1 ? splitted[i + 1] : "");

        return map.isEmpty() ? Collections.emptyMap() : map;
    }

    /**
     * Parses the String representing level creators into a Map that associates the creator ID with their name
     *
     * @param creatorsInfoRD the String representing the creators
     * @return a Map of Long, String
     */
    public static Map<Long, String> structureCreatorsInfo(String creatorsInfoRD) {
        if (creatorsInfoRD.isEmpty())
            return Collections.emptyMap();

        String[] arrayCreatorsRD = creatorsInfoRD.split("\\|");
        Map<Long, String> structuredCreatorsInfo = new HashMap<>();

        for (String creatorRD : arrayCreatorsRD) {
            structuredCreatorsInfo.put(Long.parseLong(creatorRD.split(":")[0]), creatorRD.split(":")[1]);
        }

        return structuredCreatorsInfo;
    }

    /**
     * Parses the String representing level songs into a Map that associates the song ID with their title
     *
     * @param songsInfoRD the String representing the songs
     * @return a Map of Long, String
     */
    public static Map<Long, GDSong> structureSongsInfo(String songsInfoRD) {
        if (songsInfoRD.isEmpty())
            return Collections.emptyMap();

        String[] arraySongsRD = songsInfoRD.split("~:~");
        Map<Long, GDSong> result = new HashMap<>();

        for (String songRD : arraySongsRD) {
            Map<Integer, String> data = splitToMap(songRD, "~\\|~");
            requireKeys(data, SONG_ID, SONG_TITLE, SONG_ARTIST, SONG_SIZE, SONG_URL);
            long songID = Long.parseLong(data.get(SONG_ID));
            result.put(songID, ImmutableGDSong.builder()
                    .id(songID)
                    .artist(data.get(SONG_ARTIST))
                    .size(data.get(SONG_SIZE))
                    .title(data.get(SONG_TITLE))
                    .downloadUrl(urlDecode(data.get(SONG_URL)))
                    .build());
        }

        return result;
    }

    public static String urlEncode(String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8);
    }

    public static String urlDecode(String str) {
        return URLDecoder.decode(str, StandardCharsets.UTF_8);
    }

    public static byte[] b64DecodeToBytes(String str) {
        byte[] result = null;
        StringBuilder buf = new StringBuilder(str);
        while (result == null && buf.length() > 0) {
            try {
                result = Base64.getUrlDecoder().decode(buf.toString());
            } catch (IllegalArgumentException e) {
                buf.deleteCharAt(buf.length() - 1);
            }
        }
        return result == null ? new byte[0] : result;
    }

    public static String b64Decode(String str) {
        return new String(b64DecodeToBytes(str));
    }

    public static String b64Encode(String str) {
        return Base64.getUrlEncoder().encodeToString(str.getBytes());
    }
    
    public static <T> T parseIndex(String str, T[] array) {
        var value = Integer.parseInt(str);
        return array[value >= array.length ? 0 : value];
    }
    
    public static GDLevel buildLevel(Map<Integer, String> data, Map<Long, String> structuredCreatorsInfo,
                                     Map<Long, GDSong> structuredSongsInfo) {
        requireKeys(data, LEVEL_ID, LEVEL_NAME, LEVEL_CREATOR_ID, LEVEL_DESCRIPTION, LEVEL_DIFFICULTY,
                LEVEL_DEMON_DIFFICULTY, LEVEL_STARS, LEVEL_FEATURED_SCORE, LEVEL_IS_EPIC, LEVEL_DOWNLOADS,
                LEVEL_LIKES, LEVEL_LENGTH, LEVEL_COIN_COUNT, LEVEL_COIN_VERIFIED, LEVEL_VERSION, LEVEL_GAME_VERSION,
                LEVEL_OBJECT_COUNT, LEVEL_IS_DEMON, LEVEL_IS_AUTO, LEVEL_ORIGINAL, LEVEL_REQUESTED_STARS,
                LEVEL_SONG_ID, LEVEL_AUDIO_TRACK);
        long songId = Long.parseLong(data.get(LEVEL_SONG_ID));
        var song = songId > 0 ? Optional.ofNullable(structuredSongsInfo.get(songId))
                : GDSong.getAudioTrack(Integer.parseInt(data.get(LEVEL_AUDIO_TRACK)));
        var creatorName = structuredCreatorsInfo.get(Long.parseLong(data.get(LEVEL_CREATOR_ID)));
        return ImmutableGDLevel.builder()
                .id(Long.parseLong(data.get(LEVEL_ID)))
                .name(data.get(LEVEL_NAME))
                .creatorId(Long.parseLong(data.get(LEVEL_CREATOR_ID)))
                .description(b64Decode(data.get(LEVEL_DESCRIPTION)))
                .difficulty(Difficulty.parse(data.get(LEVEL_DIFFICULTY)))
                .demonDifficulty(DemonDifficulty.parse(data.get(LEVEL_DEMON_DIFFICULTY)))
                .stars(Integer.parseInt(data.get(LEVEL_STARS)))
                .featuredScore(Integer.parseInt(data.get(LEVEL_FEATURED_SCORE)))
                .isEpic(!data.get(LEVEL_IS_EPIC).equals("0"))
                .downloads(Integer.parseInt(data.get(LEVEL_DOWNLOADS)))
                .likes(Integer.parseInt(data.get(LEVEL_LIKES)))
                .length(Length.parse(data.get(LEVEL_LENGTH)))
                .coinCount(Integer.parseInt(data.get(LEVEL_COIN_COUNT)))
                .hasCoinsVerified(data.get(LEVEL_COIN_VERIFIED).equals("1"))
                .levelVersion(Integer.parseInt(data.get(LEVEL_VERSION)))
                .gameVersion(Integer.parseInt(data.get(LEVEL_GAME_VERSION)))
                .objectCount(Integer.parseInt(data.get(LEVEL_OBJECT_COUNT)))
                .isDemon(data.get(LEVEL_IS_DEMON).equals("1"))
                .isAuto(data.get(LEVEL_IS_AUTO).equals("1"))
                .originalLevelId(Long.parseLong(data.get(LEVEL_ORIGINAL)))
                .requestedStars(Integer.parseInt(data.get(LEVEL_REQUESTED_STARS)))
                .creatorName(Optional.ofNullable(creatorName))
                .song(song)
                .songId(songId)
                .build();
    }

    public static GDUser buildUser(Map<Integer, String> data) {
        if (data.containsKey(USER_GLOW_OUTLINE_2)) {
            data.put(USER_GLOW_OUTLINE, data.get(USER_GLOW_OUTLINE_2));
        }
        requireKeys(data, USER_PLAYER_ID, USER_NAME, USER_COLOR_1, USER_COLOR_2, USER_GLOW_OUTLINE);
        return ImmutableGDUser.builder()
                .playerId(Long.parseLong(data.get(USER_PLAYER_ID)))
                .accountId(Long.parseLong(data.getOrDefault(USER_ACCOUNT_ID, "0")))
                .name(data.get(USER_NAME))
                .color1Id(Integer.parseInt(data.get(USER_COLOR_1)))
                .color2Id(Integer.parseInt(data.get(USER_COLOR_2)))
                .hasGlowOutline(!data.get(USER_GLOW_OUTLINE).matches("0?"))
                .mainIconId(Optional.ofNullable(data.get(USER_ICON)).map(Integer::parseInt))
                .mainIconType(Optional.ofNullable(data.get(USER_ICON_TYPE)).map(IconType::parse))
                .role(Optional.ofNullable(data.get(USER_ROLE)).map(Role::parse))
                .build();
    }

    public static GDUserStats buildUserStats(Map<Integer, String> data) {
        requireKeys(data, USER_STARS, USER_SECRET_COINS, USER_USER_COINS, USER_DEMONS,
                USER_CREATOR_POINTS);
        return ImmutableGDUserStats.builder()
                .from(buildUser(data))
                .stars(Integer.parseInt(data.get(USER_STARS)))
                .diamonds(Integer.parseInt(data.getOrDefault(USER_DIAMONDS, "0")))
                .secretCoins(Integer.parseInt(data.get(USER_SECRET_COINS)))
                .userCoins(Integer.parseInt(data.get(USER_USER_COINS)))
                .demons(Integer.parseInt(data.get(USER_DEMONS)))
                .creatorPoints(Integer.parseInt(data.get(USER_CREATOR_POINTS)))
                .build();
    }

    public static GDUserProfile buildUserProfile(Map<Integer, String> data) {
        requireKeys(data, USER_GLOBAL_RANK, USER_ICON_CUBE, USER_ICON_SHIP, USER_ICON_UFO, USER_ICON_BALL,
                USER_ICON_WAVE, USER_ICON_ROBOT, USER_ICON_SPIDER, USER_YOUTUBE, USER_TWITTER,
                USER_TWITCH, USER_FRIEND_REQUEST_POLICY, USER_PRIVATE_MESSAGE_POLICY, USER_COMMENT_HISTORY_POLICY);
        return ImmutableGDUserProfile.builder()
                .from(buildUserStats(data))
                .globalRank(Integer.parseInt(data.get(USER_GLOBAL_RANK)))
                .cubeIconId(Integer.parseInt(data.get(USER_ICON_CUBE)))
                .shipIconId(Integer.parseInt(data.get(USER_ICON_SHIP)))
                .ufoIconId(Integer.parseInt(data.get(USER_ICON_UFO)))
                .ballIconId(Integer.parseInt(data.get(USER_ICON_BALL)))
                .waveIconId(Integer.parseInt(data.get(USER_ICON_WAVE)))
                .robotIconId(Integer.parseInt(data.get(USER_ICON_ROBOT)))
                .spiderIconId(Integer.parseInt(data.get(USER_ICON_SPIDER)))
                .youtube(data.get(USER_YOUTUBE))
                .twitter(data.get(USER_TWITTER))
                .twitch(data.get(USER_TWITCH))
                .hasFriendRequestsEnabled(data.get(USER_FRIEND_REQUEST_POLICY).equals("0"))
                .privateMessagePolicy(PrivacySetting.parse(data.get(USER_PRIVATE_MESSAGE_POLICY)))
                .commentHistoryPolicy(PrivacySetting.parse(data.get(USER_COMMENT_HISTORY_POLICY)))
                .build();
    }

    public static void requireKeys(Map<Integer, String> data, int... keys) {
        for (var key : keys) {
            if (!data.containsKey(key)) {
                throw new IllegalStateException("Missing required key: " + key);
            }
        }
    }
}
