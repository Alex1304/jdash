package jdash.common.entity;

import org.immutables.value.Value;

import java.util.Map;
import java.util.Optional;

@Value.Immutable
public interface GDSong {

    Map<Integer, GDSong> AUDIO_TRACKS = initAudioTracks();

    private static Map<Integer, GDSong> initAudioTracks() {
        return Map.ofEntries(
                Map.entry(0, buildAudioTrack("ForeverBound", "Stereo Madness")),
                Map.entry(1, buildAudioTrack("DJVI", "Back On Track")),
                Map.entry(2, buildAudioTrack("Step", "Polargeist")),
                Map.entry(3, buildAudioTrack("DJVI", "Dry Out")),
                Map.entry(4, buildAudioTrack("DJVI", "Base After Base")),
                Map.entry(5, buildAudioTrack("DJVI", "Cant Let Go")),
                Map.entry(6, buildAudioTrack("Waterflame", "Jumper")),
                Map.entry(7, buildAudioTrack("Waterflame", "Time Machine")),
                Map.entry(8, buildAudioTrack("DJVI", "Cycles")),
                Map.entry(9, buildAudioTrack("DJVI", "xStep")),
                Map.entry(10, buildAudioTrack("Waterflame", "Clutterfunk")),
                Map.entry(11, buildAudioTrack("DJ-Nate", "Theory of Everything")),
                Map.entry(12, buildAudioTrack("Waterflame", "Electroman Adventures")),
                Map.entry(13, buildAudioTrack("DJ-Nate", "Clubstep")),
                Map.entry(14, buildAudioTrack("DJ-Nate", "Electrodynamix")),
                Map.entry(15, buildAudioTrack("Waterflame", "Hexagon Force")),
                Map.entry(16, buildAudioTrack("Waterflame", "Blast Processing")),
                Map.entry(17, buildAudioTrack("DJ-Nate", "Theory of Everything 2")),
                Map.entry(18, buildAudioTrack("Waterflame", "Geometrical Dominator")),
                Map.entry(19, buildAudioTrack("F-777", "Deadlocked")),
                Map.entry(20, buildAudioTrack("MDK", "Fingerdash")));
    }

    private static GDSong buildAudioTrack(String artist, String title) {
        return ImmutableGDSong.builder()
                .id(0)
                .title(title)
                .artist(artist)
                .build();
    }

    /**
     * Gets an audio track by its ID
     *
     * @param id - the audio track id
     * @return the song, or empty if the id doesn't correspond to any existing song
     */
    static Optional<GDSong> getAudioTrack(int id) {
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
