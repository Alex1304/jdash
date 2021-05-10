package jdash.common.internal;

import jdash.common.DemonDifficulty;
import jdash.common.Difficulty;
import jdash.common.entity.GDSong;
import jdash.common.entity.ImmutableGDSong;
import org.apache.commons.lang3.StringUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
            long songID = Long.parseLong(StringUtils.defaultIfEmpty(songMap.get(Indexes.SONG_ID), "0"));
            String songTitle = StringUtils.defaultIfEmpty(songMap.get(Indexes.SONG_TITLE), "");
            String songAuthor = StringUtils.defaultIfEmpty(songMap.get(Indexes.SONG_AUTHOR), "");
            String songSize = StringUtils.defaultIfEmpty(songMap.get(Indexes.SONG_SIZE), "");
            String songURL = urlDecode(StringUtils.defaultIfEmpty(songMap.get(Indexes.SONG_URL), ""));
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
}
