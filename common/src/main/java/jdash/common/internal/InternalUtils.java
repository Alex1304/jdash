package jdash.common.internal;

import jdash.common.*;
import jdash.common.entity.*;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.function.Predicate.not;
import static jdash.common.internal.Indexes.*;

public final class InternalUtils {

    private static final String CHAR_TABLE = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

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
    public static Map<Long, GDCreatorInfo> structureCreatorsInfo(String creatorsInfoRD) {
        if (!creatorsInfoRD.matches("\\d+:[^:|]+:\\d+(\\|\\d+:[^:|]+:\\d+)*")) {
            return Collections.emptyMap();
        }

        String[] arrayCreatorsRD = creatorsInfoRD.split("\\|");
        Map<Long, GDCreatorInfo> structuredCreatorsInfo = new HashMap<>();

        for (String creatorRD : arrayCreatorsRD) {
            structuredCreatorsInfo.put(Long.parseLong(creatorRD.split(":")[0]),
                    new GDCreatorInfo(creatorRD.split(":")[1], Long.parseLong(creatorRD.split(":")[2])));
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
            result.put(songID, new GDSong(
                    songID,
                    data.get(SONG_TITLE),
                    Long.parseLong(data.get(SONG_ARTIST_ID)),
                    data.get(SONG_ARTIST),
                    Optional.ofNullable(data.get(SONG_SIZE)).filter(not(String::isEmpty)),
                    Optional.ofNullable(data.get(SONG_YOUTUBE_ARTIST)).filter(not(String::isEmpty)),
                    Objects.equals(data.get(SONG_NG_SCOUTED), "1"),
                    Optional.ofNullable(urlDecode(data.get(SONG_URL))).filter(not(String::isEmpty)),
                    MusicLibraryProvider.parse(data.get(SONG_PROVIDER_ID)),
                    toList(data.get(SONG_OTHER_ARTIST_IDS), 0, Long::parseLong).orElse(List.of())
            ));
        }

        return result;
    }

    public static String urlEncode(String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8);
    }

    public static String urlDecode(String str) {
        if (str.isEmpty()) {
            return null;
        }
        return URLDecoder.decode(str, StandardCharsets.UTF_8);
    }

    public static byte[] b64DecodeToBytes(String str) {
        byte[] result = null;
        StringBuilder buf = new StringBuilder(str);
        while (result == null && !buf.isEmpty()) {
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
        final var value = Integer.parseInt(str);
        return array[value >= array.length ? 0 : value];
    }

    public static GDLevel buildLevel(Map<Integer, String> data, Map<Long, GDCreatorInfo> structuredCreatorsInfo,
                                     Map<Long, GDSong> structuredSongsInfo) {
        requireKeys(data, LEVEL_ID, LEVEL_NAME, LEVEL_CREATOR_ID, LEVEL_DESCRIPTION, LEVEL_DIFFICULTY,
                LEVEL_DEMON_DIFFICULTY, LEVEL_STARS, LEVEL_FEATURED_SCORE, LEVEL_QUALITY_RATING, LEVEL_DOWNLOADS,
                LEVEL_LIKES, LEVEL_LENGTH, LEVEL_COIN_COUNT, LEVEL_COIN_VERIFIED, LEVEL_VERSION, LEVEL_GAME_VERSION,
                LEVEL_OBJECT_COUNT, LEVEL_IS_DEMON, LEVEL_IS_AUTO, LEVEL_ORIGINAL, LEVEL_REQUESTED_STARS,
                LEVEL_SONG_ID, LEVEL_AUDIO_TRACK, LEVEL_TWO_PLAYER);
        final var songId = Optional.ofNullable(data.get(LEVEL_SONG_ID)).map(Long::parseLong).filter(l -> l > 0);
        @SuppressWarnings("SimplifyOptionalCallChains") // IntelliJ bug
        final var song = songId.map(id -> Optional.ofNullable(structuredSongsInfo.get(id)))
                .orElseGet(() -> GDSong.getOfficialSong(Integer.parseInt(data.get(LEVEL_AUDIO_TRACK))));
        final var creatorInfo = structuredCreatorsInfo.get(Long.parseLong(data.get(LEVEL_CREATOR_ID)));
        final var featuredScore = Integer.parseInt(data.get(LEVEL_FEATURED_SCORE));
        return new GDLevel(
                Long.parseLong(data.get(LEVEL_ID)),
                data.get(LEVEL_NAME),
                Long.parseLong(data.get(LEVEL_CREATOR_ID)),
                b64Decode(data.get(LEVEL_DESCRIPTION)),
                Difficulty.parse(data.get(LEVEL_DIFFICULTY)),
                DemonDifficulty.parse(data.get(LEVEL_DEMON_DIFFICULTY)),
                Integer.parseInt(data.get(LEVEL_STARS)),
                featuredScore,
                QualityRating.parse(data.get(LEVEL_QUALITY_RATING), featuredScore > 0),
                Integer.parseInt(data.get(LEVEL_DOWNLOADS)),
                Integer.parseInt(data.get(LEVEL_LIKES)),
                Length.parse(data.get(LEVEL_LENGTH)),
                Integer.parseInt(data.get(LEVEL_COIN_COUNT)),
                data.get(LEVEL_COIN_VERIFIED).equals("1"),
                Integer.parseInt(data.get(LEVEL_VERSION)),
                Integer.parseInt(data.get(LEVEL_GAME_VERSION)),
                Integer.parseInt(data.get(LEVEL_OBJECT_COUNT)),
                data.get(LEVEL_IS_DEMON).equals("1"),
                data.get(LEVEL_IS_AUTO).equals("1"),
                Optional.ofNullable(data.get(LEVEL_ORIGINAL)).map(Long::parseLong).filter(l -> l > 0),
                Integer.parseInt(data.get(LEVEL_REQUESTED_STARS)),
                songId,
                song,
                Optional.ofNullable(creatorInfo).map(GDCreatorInfo::name),
                Optional.ofNullable(creatorInfo).map(GDCreatorInfo::accountId),
                data.get(LEVEL_TWO_PLAYER).equals("1"),
                Objects.equals(data.get(LEVEL_IS_GAUNTLET), "1")
        );
    }

    public static GDList buildList(Map<Integer, String> data) {
        requireKeys(data, LEVEL_ID, LEVEL_NAME, LEVEL_DESCRIPTION, LEVEL_VERSION, LEVEL_DOWNLOADS, LEVEL_LIKES,
                LEVEL_FEATURED_SCORE, LEVEL_UPLOADED_AGO, LEVEL_UPDATED_AGO, LIST_ICON, LIST_CREATOR_ID,
                LIST_CREATOR_NAME, LIST_ITEMS, LIST_DIAMONDS, LIST_MIN_COMPLETION);
        return new GDList(
                Long.parseLong(data.get(LEVEL_ID)),
                data.get(LEVEL_NAME),
                b64Decode(data.get(LEVEL_DESCRIPTION)),
                Integer.parseInt(data.get(LEVEL_VERSION)),
                Integer.parseInt(data.get(LEVEL_DOWNLOADS)),
                Integer.parseInt(data.get(LEVEL_LIKES)),
                Boolean.parseBoolean(data.get(LEVEL_FEATURED_SCORE)),
                Instant.ofEpochSecond(Long.parseLong(data.get(LEVEL_UPLOADED_AGO))),
                Instant.ofEpochSecond(Long.parseLong(data.get(LEVEL_UPDATED_AGO))),
                Integer.parseInt(data.get(LIST_ICON)),
                Long.parseLong(data.get(LIST_CREATOR_ID)),
                data.get(LIST_CREATOR_NAME),
                Arrays.stream(data.get(LIST_ITEMS).split(",")).map(Long::parseLong).toList(),
                Integer.parseInt(data.get(LIST_DIAMONDS)),
                Integer.parseInt(data.get(LIST_MIN_COMPLETION))
        );
    }

    public static GDUser buildUser(Map<Integer, String> data) {
        if (data.containsKey(USER_GLOW_OUTLINE_2)) {
            data.put(USER_GLOW_OUTLINE, data.get(USER_GLOW_OUTLINE_2));
        }
        requireKeys(data, USER_PLAYER_ID, USER_NAME, USER_COLOR_1, USER_COLOR_2, USER_GLOW_OUTLINE);
        return new GDUser(
                Long.parseLong(data.get(USER_PLAYER_ID)),
                Long.parseLong(data.getOrDefault(USER_ACCOUNT_ID, "0")),
                data.get(USER_NAME),
                Integer.parseInt(data.get(USER_COLOR_1)),
                Integer.parseInt(data.get(USER_COLOR_2)),
                !data.get(USER_GLOW_OUTLINE).matches("0?"),
                Optional.ofNullable(data.get(USER_ICON)).map(Integer::parseInt),
                Optional.ofNullable(data.get(USER_ICON_TYPE)).map(IconType::parse),
                Optional.ofNullable(data.get(USER_ROLE)).map(Role::parse)
        );
    }

    public static GDUserStats buildUserStats(Map<Integer, String> data) {
        requireKeys(data, USER_STARS, USER_MOONS, USER_SECRET_COINS, USER_USER_COINS, USER_DEMONS,
                USER_CREATOR_POINTS);
        return new GDUserStats(
                buildUser(data),
                Integer.parseInt(data.get(USER_STARS)),
                Integer.parseInt(data.get(USER_MOONS)),
                Integer.parseInt(data.getOrDefault(USER_DIAMONDS, "0")),
                Integer.parseInt(data.get(USER_SECRET_COINS)),
                Integer.parseInt(data.get(USER_USER_COINS)),
                Integer.parseInt(data.get(USER_DEMONS)),
                Integer.parseInt(data.get(USER_CREATOR_POINTS)),
                Optional.ofNullable(data.get(USER_LEADERBOARD_RANK))
                        .filter(not(String::isEmpty))
                        .map(Integer::parseInt)
        );
    }

    public static GDUserProfile buildUserProfile(Map<Integer, String> data) {
        requireKeys(data, USER_GLOBAL_RANK, USER_ICON_CUBE, USER_ICON_SHIP, USER_ICON_UFO, USER_ICON_BALL,
                USER_ICON_WAVE, USER_ICON_ROBOT, USER_ICON_SPIDER, USER_ICON_SWING, USER_ICON_JETPACK,
                USER_COLOR_GLOW, USER_YOUTUBE, USER_TWITTER, USER_TWITCH, USER_FRIEND_REQUEST_SETTING,
                USER_PRIVATE_MESSAGE_SETTING, USER_COMMENT_HISTORY_SETTING);
        final var stats = buildUserStats(data);
        return new GDUserProfile(
                stats.user(),
                stats,
                Integer.parseInt(data.get(USER_GLOBAL_RANK)),
                Integer.parseInt(data.get(USER_ICON_CUBE)),
                Integer.parseInt(data.get(USER_ICON_SHIP)),
                Integer.parseInt(data.get(USER_ICON_UFO)),
                Integer.parseInt(data.get(USER_ICON_BALL)),
                Integer.parseInt(data.get(USER_ICON_WAVE)),
                Integer.parseInt(data.get(USER_ICON_ROBOT)),
                Integer.parseInt(data.get(USER_ICON_SPIDER)),
                Integer.parseInt(data.get(USER_ICON_SWING)),
                Integer.parseInt(data.get(USER_ICON_JETPACK)),
                Integer.parseInt(data.get(USER_COLOR_GLOW)),
                data.get(USER_YOUTUBE),
                data.get(USER_TWITTER),
                data.get(USER_TWITCH),
                data.get(USER_FRIEND_REQUEST_SETTING).equals("0"),
                PrivacySetting.parse(data.get(USER_PRIVATE_MESSAGE_SETTING)),
                PrivacySetting.parse(data.get(USER_COMMENT_HISTORY_SETTING)),
                toIntList(data.get(USER_COUNTS_CLASSIC), 8)
                        .map(values -> new GDUserProfile.CompletedClassicLevels(
                                values.get(0),
                                values.get(1),
                                values.get(2),
                                values.get(3),
                                values.get(4),
                                values.get(5),
                                values.get(6),
                                values.get(7)
                        )),
                toIntList(data.get(USER_COUNTS_PLATFORMER), 7)
                        .map(values -> new GDUserProfile.CompletedPlatformerLevels(
                                values.get(0),
                                values.get(1),
                                values.get(2),
                                values.get(3),
                                values.get(4),
                                values.get(5),
                                values.get(6)
                        )),
                toIntList(data.get(USER_COUNTS_DEMONS), 12)
                        .map(values -> new GDUserProfile.CompletedDemons(
                                values.get(0),
                                values.get(1),
                                values.get(2),
                                values.get(3),
                                values.get(4),
                                values.get(5),
                                values.get(6),
                                values.get(7),
                                values.get(8),
                                values.get(9),
                                values.get(10),
                                values.get(11)
                        ))
        );
    }

    private static Optional<List<Integer>> toIntList(String str, int minSize) {
        return toList(str, minSize, Integer::parseInt);
    }

    private static <T> Optional<List<T>> toList(String str, int minSize, Function<String, T> parser) {
        if (str == null || str.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(Arrays.stream(str.split(",")).map(parser).toList())
                .filter(values -> values.size() >= minSize);
    }

    public static GDPrivateMessage buildMessage(Map<Integer, String> data) {
        requireKeys(data, MESSAGE_ID, MESSAGE_USER_ACCOUNT_ID, MESSAGE_USER_NAME, MESSAGE_SUBJECT,
                MESSAGE_IS_UNREAD, MESSAGE_SENT_AGO, MESSAGE_USER_PLAYER_ID, MESSAGE_IS_SENDER);
        return new GDPrivateMessage(
                Long.parseLong(data.get(MESSAGE_ID)),
                Long.parseLong(data.get(MESSAGE_USER_ACCOUNT_ID)),
                Long.parseLong(data.get(MESSAGE_USER_PLAYER_ID)),
                data.get(MESSAGE_USER_NAME),
                b64Decode(data.get(MESSAGE_SUBJECT)),
                !data.get(MESSAGE_IS_UNREAD).equals("1"),
                data.get(MESSAGE_IS_SENDER).equals("1"),
                data.get(MESSAGE_SENT_AGO)
        );
    }

    public static void requireKeys(Map<Integer, String> data, int... keys) {
        for (var key : keys) {
            if (!data.containsKey(key)) {
                throw new IllegalStateException("Missing required key: " + key);
            }
        }
    }

    public static String randomString(int size) {
        final var rand = new Random();
        return IntStream.range(0, size)
                .mapToObj(i -> "" + CHAR_TABLE.charAt(rand.nextInt(CHAR_TABLE.length())))
                .collect(Collectors.joining());
    }

    @SafeVarargs
    public static <T> boolean haveDifferentFields(T el1, T el2, Function<T, ?>... fieldGetters) {
        return Arrays.stream(fieldGetters)
                .anyMatch(fieldGetter -> !fieldGetter.apply(el1).equals(fieldGetter.apply(el2)));
    }
}
