package jdash.common;

import java.util.Objects;

/**
 * Enumerates the possible song providers for the music library.
 */
public enum MusicLibraryProvider {
    OTHER,
    NCS;

    public static MusicLibraryProvider parse(String value) {
        return Objects.equals(value, "1") ? NCS : OTHER;
    }
}
