package jdash.common.internal;

import jdash.common.DemonDifficulty;
import jdash.common.Difficulty;
import jdash.common.Length;
import jdash.common.entity.GDSong;
import jdash.common.entity.ImmutableGDLevel;
import jdash.common.entity.ImmutableGDSong;
import jdash.common.entity.ImmutableGDUser;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static jdash.common.internal.Indexes.*;

public final class InternalUtils {
    private static final Map<Integer, GDSong> AUDIO_TRACKS;

    static {
        AUDIO_TRACKS = new HashMap<>();
        AUDIO_TRACKS.put(0, GDSong.nativeSong("ForeverBound", "Stereo Madness"));
        AUDIO_TRACKS.put(1, GDSong.nativeSong("DJVI", "Back On Track"));
        AUDIO_TRACKS.put(2, GDSong.nativeSong("Step", "Polargeist"));
        AUDIO_TRACKS.put(3, GDSong.nativeSong("DJVI", "Dry Out"));
        AUDIO_TRACKS.put(4, GDSong.nativeSong("DJVI", "Base After Base"));
        AUDIO_TRACKS.put(5, GDSong.nativeSong("DJVI", "Cant Let Go"));
        AUDIO_TRACKS.put(6, GDSong.nativeSong("Waterflame", "Jumper"));
        AUDIO_TRACKS.put(7, GDSong.nativeSong("Waterflame", "Time Machine"));
        AUDIO_TRACKS.put(8, GDSong.nativeSong("DJVI", "Cycles"));
        AUDIO_TRACKS.put(9, GDSong.nativeSong("DJVI", "xStep"));
        AUDIO_TRACKS.put(10, GDSong.nativeSong("Waterflame", "Clutterfunk"));
        AUDIO_TRACKS.put(11, GDSong.nativeSong("DJ-Nate", "Theory of Everything"));
        AUDIO_TRACKS.put(12, GDSong.nativeSong("Waterflame", "Electroman Adventures"));
        AUDIO_TRACKS.put(13, GDSong.nativeSong("DJ-Nate", "Clubstep"));
        AUDIO_TRACKS.put(14, GDSong.nativeSong("DJ-Nate", "Electrodynamix"));
        AUDIO_TRACKS.put(15, GDSong.nativeSong("Waterflame", "Hexagon Force"));
        AUDIO_TRACKS.put(16, GDSong.nativeSong("Waterflame", "Blast Processing"));
        AUDIO_TRACKS.put(17, GDSong.nativeSong("DJ-Nate", "Theory of Everything 2"));
        AUDIO_TRACKS.put(18, GDSong.nativeSong("Waterflame", "Geometrical Dominator"));
        AUDIO_TRACKS.put(19, GDSong.nativeSong("F-777", "Deadlocked"));
        AUDIO_TRACKS.put(20, GDSong.nativeSong("MDK", "Fingerdash"));
    }

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

        for (int i = 0; i < splitted.length - 1; i += 2)
            map.put(Integer.parseInt(splitted[i]), splitted[i + 1]);

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
            Map<Integer, String> songMap = splitToMap(songRD, "~\\|~");
            long songID = Long.parseLong(songMap.getOrDefault(SONG_ID, "0"));
            String songTitle = songMap.getOrDefault(SONG_TITLE, "");
            String songAuthor = songMap.getOrDefault(SONG_AUTHOR, "");
            String songSize = songMap.getOrDefault(SONG_SIZE, "");
            String songURL = urlDecode(songMap.getOrDefault(SONG_URL, ""));
            result.put(songID, ImmutableGDSong.builder()
                    .id(songID)
                    .songAuthorName(songAuthor)
                    .songSize(songSize)
                    .songTitle(songTitle)
                    .downloadUrl(songURL)
                    .isCustom(true)
                    .build());
        }

        return result;
    }

    /**
     * Gets an audio track by its ID
     *
     * @param id - the audio track id
     * @return GDSong
     */
    public static GDSong getAudioTrack(int id) {
        return AUDIO_TRACKS.containsKey(id) ? AUDIO_TRACKS.get(id) : GDSong.nativeSong("-", "Unknown");
    }

    public static Difficulty valueToDifficulty(int value) {
        switch (value) {
            case 10:
                return Difficulty.EASY;
            case 20:
                return Difficulty.NORMAL;
            case 30:
                return Difficulty.HARD;
            case 40:
                return Difficulty.HARDER;
            case 50:
                return Difficulty.INSANE;
            default:
                return Difficulty.NA;
        }
    }

    public static DemonDifficulty valueToDemonDifficulty(int value) {
        switch (value) {
            case 3:
                return DemonDifficulty.EASY;
            case 4:
                return DemonDifficulty.MEDIUM;
            case 5:
                return DemonDifficulty.INSANE;
            case 6:
                return DemonDifficulty.EXTREME;
            default:
                return DemonDifficulty.HARD;
        }
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
    
    public static ImmutableGDLevel.Builder initLevelBuilder(String creatorName, Map<Integer, String> data) {
        int length = Integer.parseInt(data.getOrDefault(LEVEL_LENGTH, "0"));
        long levelId = Long.parseLong(data.getOrDefault(LEVEL_ID, "0"));
        return ImmutableGDLevel.builder()
                .id(levelId)
                .name(data.getOrDefault(LEVEL_NAME, "-"))
                .creatorId(Long.parseLong(data.getOrDefault(LEVEL_CREATOR_ID, "0")))
                .description(b64Decode(data.getOrDefault(LEVEL_DESCRIPTION, "")))
                .difficulty(valueToDifficulty(Integer.parseInt(data.getOrDefault(LEVEL_DIFFICULTY, "0"))))
                .demonDifficulty(valueToDemonDifficulty(
                        Integer.parseInt(data.getOrDefault(LEVEL_DEMON_DIFFICULTY, "0"))))
                .stars(Integer.parseInt(data.getOrDefault(LEVEL_STARS, "0")))
                .featuredScore(Integer.parseInt(data.getOrDefault(LEVEL_FEATURED_SCORE, "0")))
                .isEpic(!data.getOrDefault(LEVEL_IS_EPIC, "0").equals("0"))
                .downloads(Integer.parseInt(data.getOrDefault(LEVEL_DOWNLOADS, "0")))
                .likes(Integer.parseInt(data.getOrDefault(LEVEL_LIKES, "0")))
                .length(Length.values()[length >= Length.values().length ? 0 : length])
                .coinCount(Integer.parseInt(data.getOrDefault(LEVEL_COIN_COUNT, "0")))
                .hasCoinsVerified(data.getOrDefault(LEVEL_COIN_VERIFIED, "0").equals("1"))
                .levelVersion(Integer.parseInt(data.getOrDefault(LEVEL_VERSION, "0")))
                .gameVersion(Integer.parseInt(data.getOrDefault(LEVEL_GAME_VERSION, "0")))
                .objectCount(Integer.parseInt(data.getOrDefault(LEVEL_OBJECT_COUNT, "0")))
                .isDemon(data.getOrDefault(LEVEL_IS_DEMON, "0").equals("1"))
                .isAuto(data.getOrDefault(LEVEL_IS_AUTO, "0").equals("1"))
                .originalLevelId(Long.parseLong(data.getOrDefault(LEVEL_ORIGINAL, "0")))
                .requestedStars(Integer.parseInt(data.getOrDefault(LEVEL_REQUESTED_STARS, "0")))
                .creatorName(Optional.ofNullable(creatorName));
    }

    public static ImmutableGDUser.Builder initUserBuilder(Map<Integer, String> data) {
        return ImmutableGDUser.builder()
                .playerId(Long.parseLong(data.getOrDefault(USER_PLAYER_ID, "0")))
                .name(data.getOrDefault(USER_NAME, "-"))
                .secretCoins(Integer.parseInt(data.getOrDefault(USER_SECRET_COINS, "0")))
                .userCoins(Integer.parseInt(data.getOrDefault(USER_USER_COINS, "0")))
                .color1Id(Integer.parseInt(data.getOrDefault(USER_COLOR_1, "0")))
                .color2Id(Integer.parseInt(data.getOrDefault(USER_COLOR_2, "0")))
                .accountId(Long.parseLong(data.getOrDefault(USER_ACCOUNT_ID, "0")))
                .stars(Integer.parseInt(data.getOrDefault(USER_STARS, "0")))
                .creatorPoints(Integer.parseInt(data.getOrDefault(USER_CREATOR_POINTS, "0")))
                .demons(Integer.parseInt(data.getOrDefault(USER_DEMONS, "0")));
    }
}
