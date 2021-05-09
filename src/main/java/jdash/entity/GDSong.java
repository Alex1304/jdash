package jdash.entity;

import org.immutables.value.Value;

@Value.Immutable
public interface GDSong {

    static GDSong unknownSong(long songId) {
        return ImmutableGDSong.builder()
                .id(songId)
                .songAuthorName("-")
                .songSize("0")
                .songTitle("Unknown")
                .downloadUrl("")
                .isCustom(songId > 0)
                .build();
    }

    static GDSong nativeSong(String songAuthorName, String songTitle) {
        return ImmutableGDSong.builder()
                .id(0)
                .songAuthorName(songAuthorName)
                .songSize("0")
                .songTitle(songTitle)
                .downloadUrl("")
                .isCustom(false)
                .build();
    }

    long id();

    String songAuthorName();

    String songSize();

    String songTitle();

    String downloadUrl();

    boolean isCustom();
}
