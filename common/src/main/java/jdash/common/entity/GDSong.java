package jdash.common.entity;

import java.util.Map;
import java.util.Optional;

/**
 * Represents a song that can be used in-game. It can be a custom song from Newgrounds, a song from the Music Library,
 * or one of the official game songs.
 *
 * @param id          The ID of the song. For a custom song, this is the Newgrounds ID. For an official song, this is
 *                    its in-game ID.
 * @param title       The title of the song.
 * @param artist      The display name of the artist of the song.
 * @param size        A string that indicates the size of the song. The structure of the string is not guaranteed. Only
 *                    present for Newgrounds and Music Library songs.
 * @param downloadUrl The download URL of the song. Only present for Newgrounds songs.
 */
public record GDSong(
        long id,
        String title,
        String artist,
        Optional<String> size,
        Optional<String> downloadUrl
) {

    private static final Map<Integer, GDSong> OFFICIAL_SONGS = initOfficialSongs();

    private static Map<Integer, GDSong> initOfficialSongs() {
        return Map.ofEntries(
                officialSongEntry(0, "ForeverBound", "Stereo Madness"),
                officialSongEntry(1, "DJVI", "Back On Track"),
                officialSongEntry(2, "Step", "Polargeist"),
                officialSongEntry(3, "DJVI", "Dry Out"),
                officialSongEntry(4, "DJVI", "Base After Base"),
                officialSongEntry(5, "DJVI", "Cant Let Go"),
                officialSongEntry(6, "Waterflame", "Jumper"),
                officialSongEntry(7, "Waterflame", "Time Machine"),
                officialSongEntry(8, "DJVI", "Cycles"),
                officialSongEntry(9, "DJVI", "xStep"),
                officialSongEntry(10, "Waterflame", "Clutterfunk"),
                officialSongEntry(11, "DJ-Nate", "Theory of Everything"),
                officialSongEntry(12, "Waterflame", "Electroman Adventures"),
                officialSongEntry(13, "DJ-Nate", "Clubstep"),
                officialSongEntry(14, "DJ-Nate", "Electrodynamix"),
                officialSongEntry(15, "Waterflame", "Hexagon Force"),
                officialSongEntry(16, "Waterflame", "Blast Processing"),
                officialSongEntry(17, "DJ-Nate", "Theory of Everything 2"),
                officialSongEntry(18, "Waterflame", "Geometrical Dominator"),
                officialSongEntry(19, "F-777", "Deadlocked"),
                officialSongEntry(20, "MDK", "Fingerdash"),
                officialSongEntry(21, "MDK", "Dash"));
    }

    private static Map.Entry<Integer, GDSong> officialSongEntry(int id, String artist, String title) {
        return Map.entry(id, new GDSong(id, title, artist, Optional.empty(), Optional.empty()));
    }

    /**
     * Gets an official song by its in-game ID (not Newgrounds ID)
     *
     * @param id the official song ID
     * @return the song, or empty if the id doesn't correspond to any existing song
     */
    public static Optional<GDSong> getOfficialSong(int id) {
        return Optional.ofNullable(OFFICIAL_SONGS.get(id));
    }

    /**
     * Convenience method to check whether this song represents an official in-game song.
     *
     * @return a boolean
     */
    public boolean isOfficial() {
        return OFFICIAL_SONGS.containsKey((int) id);
    }

    /**
     * Convenience method to check whether this song represents song from Newgrounds.
     *
     * @return a boolean
     */
    public boolean isFromNewgrounds() {
        return !OFFICIAL_SONGS.containsKey((int) id) && id < 10_000_000;
    }

    /**
     * Convenience method to check whether this song represents song from the in-game Music Library.
     *
     * @return a boolean
     */
    public boolean isFromMusicLibrary() {
        return !OFFICIAL_SONGS.containsKey((int) id) && id >= 10_000_000;
    }
}
