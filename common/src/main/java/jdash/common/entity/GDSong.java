package jdash.common.entity;

import jdash.common.MusicLibraryProvider;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a song that can be used in-game. It can be a custom song from Newgrounds, a song from the Music Library,
 * or one of the official game songs.
 *
 * @param id                   The ID of the song. For a custom song, this is the Newgrounds ID. For an official song,
 *                             this is its in-game ID.
 * @param title                The title of the song.
 * @param artistId             The ID of the artist of the song.
 * @param artist               The display name of the artist of the song.
 * @param size                 A string that indicates the size of the song. The structure of the string is not
 *                             guaranteed. Not present for official songs.
 * @param youtubeArtist        The YouTube channel ID of the artist. Not always present.
 * @param isArtistScouted      Whether the artist is scouted on Newgrounds. Always false if the song is not from
 *                             Newgrounds.
 * @param downloadUrl          The download URL of the song. Not always present. May be equal to "CUSTOMURL" to indicate
 *                             this information may be fetched elsewhere.
 * @param musicLibraryProvider The song provider if the song is from music library, otherwise always returns
 *                             {@link MusicLibraryProvider#OTHER}.
 * @param otherArtistIds       The list of other artist IDs that collaborated on the song.
 */
public record GDSong(
        long id,
        String title,
        long artistId,
        String artist,
        Optional<String> size,
        Optional<String> youtubeArtist,
        boolean isArtistScouted,
        Optional<String> downloadUrl,
        MusicLibraryProvider musicLibraryProvider,
        List<Long> otherArtistIds
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
        return Map.entry(id, new GDSong(
                id,
                title,
                0L,
                artist,
                Optional.empty(),
                Optional.empty(),
                false,
                Optional.empty(),
                MusicLibraryProvider.OTHER,
                List.of()
        ));
    }

    private static boolean isMusicLibraryId(long id) {
        return id >= 10_000_000;
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
     * Construct a {@link GDSong} with only the data that is relevant for a Newgrounds song.
     *
     * @param id              The song ID. Must be &lt; 10_000_000
     * @param title           The song title
     * @param artistId        The artist ID
     * @param artist          The artist name
     * @param size            The formatted size in MB
     * @param isArtistScouted Whether the artist is scouted on Newgrounds
     * @param downloadUrl     The download URL of the song. May be null or equal to "CUSTOMURL", otherwise a valid URL
     * @return a {@link GDSong}
     * @throws IllegalArgumentException if ID doesn't fit a Newgrounds song (&lt; 10_000_000)
     */
    public static GDSong fromNewgrounds(long id, String title, long artistId, String artist, String size,
                                        boolean isArtistScouted, @Nullable String downloadUrl) {
        if (isMusicLibraryId(id)) {
            throw new IllegalArgumentException("id must be < 10_000_000 for a Newgrounds song");
        }
        Objects.requireNonNull(title);
        Objects.requireNonNull(artist);
        Objects.requireNonNull(size);
        return new GDSong(
                id,
                title,
                artistId,
                artist,
                Optional.of(size),
                Optional.empty(),
                isArtistScouted,
                Optional.ofNullable(downloadUrl),
                MusicLibraryProvider.OTHER,
                List.of()
        );
    }

    /**
     * Construct a {@link GDSong} with only the data that is relevant for a Music Library song.
     *
     * @param id                   The song ID. Must be &gt;= 10_000_000
     * @param title                The song title
     * @param artistId             The artist ID
     * @param artist               The artist name
     * @param size                 The formatted size in MB
     * @param youtubeArtist        The YouTube channel ID of the artist, may be null
     * @param musicLibraryProvider The music library provider
     * @param otherArtistIds       The list of other artist IDs that collaborated on the song.
     * @return a {@link GDSong}
     * @throws IllegalArgumentException if ID doesn't fit a Music Library song (&gt;= 10_000_000)
     */
    public static GDSong fromMusicLibrary(long id, String title, long artistId, String artist, String size,
                                          @Nullable String youtubeArtist, MusicLibraryProvider musicLibraryProvider,
                                          List<Long> otherArtistIds) {
        if (!isMusicLibraryId(id)) {
            throw new IllegalArgumentException("id must be >= 10_000_000 for a Music Library song");
        }
        Objects.requireNonNull(title);
        Objects.requireNonNull(artist);
        Objects.requireNonNull(size);
        Objects.requireNonNull(musicLibraryProvider);
        Objects.requireNonNull(otherArtistIds);
        return new GDSong(
                id,
                title,
                artistId,
                artist,
                Optional.of(size),
                Optional.ofNullable(youtubeArtist),
                false,
                Optional.of("CUSTOMURL"),
                musicLibraryProvider,
                otherArtistIds
        );
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
        return !OFFICIAL_SONGS.containsKey((int) id) && !isMusicLibraryId(id);
    }

    /**
     * Convenience method to check whether this song represents song from the in-game Music Library.
     *
     * @return a boolean
     */
    public boolean isFromMusicLibrary() {
        return !OFFICIAL_SONGS.containsKey((int) id) && isMusicLibraryId(id);
    }

    /**
     * Convenience method to check whether this song is provided by NCS.
     *
     * @return a boolean
     */
    public boolean isNCS() {
        return musicLibraryProvider == MusicLibraryProvider.NCS;
    }
}
