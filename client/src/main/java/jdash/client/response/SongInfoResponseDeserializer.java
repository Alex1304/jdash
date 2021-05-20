package jdash.client.response;

import jdash.client.exception.ActionFailedException;
import jdash.common.entity.GDSong;

import java.util.function.Function;

import static jdash.common.internal.InternalUtils.structureSongsInfo;

class SongInfoResponseDeserializer implements Function<String, GDSong> {

    @Override
    public GDSong apply(String response) {
        ActionFailedException.throwIfEquals(response, "-1", "Failed to fetch song info");
        ActionFailedException.throwIfEquals(response, "-2", "Song is not allowed for use");
        return structureSongsInfo(response).values().stream().findFirst().orElseThrow();
    }
}
