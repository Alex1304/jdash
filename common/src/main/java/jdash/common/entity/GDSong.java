package jdash.common.entity;

import org.immutables.value.Value;

import java.util.Map;
import java.util.Optional;

@Value.Immutable
public interface GDSong {

    Map<Integer, GDSong> AUDIO_TRACKS = initAudioTracks();

    private static Map<Integer, GDSong> initAudioTracks() {
        return Map.ofEntries(
                Map.entry(0, buildOfficialSong("ForeverBound", "Stereo Madness")),
                Map.entry(1, buildOfficialSong("DJVI", "Back On Track")),
                Map.entry(2, buildOfficialSong("Step", "Polargeist")),
                Map.entry(3, buildOfficialSong("DJVI", "Dry Out")),
                Map.entry(4, buildOfficialSong("DJVI", "Base After Base")),
                Map.entry(5, buildOfficialSong("DJVI", "Cant Let Go")),
                Map.entry(6, buildOfficialSong("Waterflame", "Jumper")),
                Map.entry(7, buildOfficialSong("Waterflame", "Time Machine")),
                Map.entry(8, buildOfficialSong("DJVI", "Cycles")),
                Map.entry(9, buildOfficialSong("DJVI", "xStep")),
                Map.entry(10, buildOfficialSong("Waterflame", "Clutterfunk")),
                Map.entry(11, buildOfficialSong("DJ-Nate", "Theory of Everything")),
                Map.entry(12, buildOfficialSong("Waterflame", "Electroman Adventures")),
                Map.entry(13, buildOfficialSong("DJ-Nate", "Clubstep")),
                Map.entry(14, buildOfficialSong("DJ-Nate", "Electrodynamix")),
                Map.entry(15, buildOfficialSong("Waterflame", "Hexagon Force")),
                Map.entry(16, buildOfficialSong("Waterflame", "Blast Processing")),
                Map.entry(17, buildOfficialSong("DJ-Nate", "Theory of Everything 2")),
                Map.entry(18, buildOfficialSong("Waterflame", "Geometrical Dominator")),
                Map.entry(19, buildOfficialSong("F-777", "Deadlocked")),
                Map.entry(20, buildOfficialSong("MDK", "Fingerdash")));
    }

    private static GDSong buildOfficialSong(String artist, String title) {
        return ImmutableGDSong.builder()
                .id(0)
                .title(title)
                .artist(artist)
                .build();
    }

    /**
     * Gets an official song by its ID
     *
     * @param id - the official song id
     * @return the song, or empty if the id doesn't correspond to any existing song
     */
    static Optional<GDSong> getOfficialSong(int id) {
        return Optional.ofNullable(AUDIO_TRACKS.get(id));
    }

    long id();

    String title();

    String artist();

    Optional<String> size();

    Optional<String> downloadUrl();

    default boolean isCustom() {
        return id() > 0;
    }
}
