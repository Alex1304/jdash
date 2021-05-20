package jdash.common.entity;

import org.immutables.value.Value;

import java.util.Map;
import java.util.Optional;

/**
 * Represents a song that can be used in-game. It can be a custom song from Newgrounds or one of the official game
 * songs.
 */
@Value.Immutable
public interface GDSong {

    /**
     * An unmodifiable map containing all official songs. It is generally recommended to use {@link
     * #getOfficialSong(int)} that provides safe access to individual songs, but it can be used to iterate through all
     * official songs for example.
     */
    Map<Integer, GDSong> OFFICIAL_SONGS = initOfficialSongs();

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
                officialSongEntry(20, "MDK", "Fingerdash"));
    }

    private static Map.Entry<Integer, GDSong> officialSongEntry(int id, String artist, String title) {
        return Map.entry(id, ImmutableGDSong.builder()
                .id(id)
                .title(title)
                .artist(artist)
                .build());
    }

    /**
     * Gets an official song by its in-game ID (not Newgrounds ID)
     *
     * @param id the official song ID
     * @return the song, or empty if the id doesn't correspond to any existing song
     */
    static Optional<GDSong> getOfficialSong(int id) {
        return Optional.ofNullable(OFFICIAL_SONGS.get(id));
    }

    /**
     * The ID of the song. For a custom song, this is the Newgrounds ID. For an official song, this is its in-game ID.
     *
     * @return a long
     */
    long id();

    /**
     * The title of the song.
     *
     * @return a string
     */
    String title();

    /**
     * The display name of the artist of the song.
     *
     * @return a string
     */
    String artist();

    /**
     * A string that indicates the size of the song. The structure of the string is not guaranteed. Only applicable for
     * custom songs.
     *
     * @return an {@link Optional} containing a string if present
     */
    Optional<String> size();

    /**
     * The download URL of the song. Only applicable for custom songs.
     *
     * @return an {@link Optional} containing a string if present
     */
    Optional<String> downloadUrl();

    /**
     * Convenience method to check whether this song represents a custom song on Newgrounds or an official in-game
     * song.
     *
     * @return a boolean
     */
    default boolean isCustom() {
        return !OFFICIAL_SONGS.containsKey((int) id()) || size().isPresent() || downloadUrl().isPresent();
    }
}
