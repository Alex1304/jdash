package jdash.client.response;

import jdash.client.exception.SongNotAllowedForUseException;
import jdash.common.entity.GDSong;

import java.util.function.Function;

import static jdash.common.internal.InternalUtils.structureSongsInfo;

public class SongInfoResponseDeserializer implements Function<String, GDSong> {

    @Override
    public GDSong apply(String response) {
        if (response.equals("-2")) {
            throw new SongNotAllowedForUseException();
        }
        return structureSongsInfo(response).values().stream().findFirst().orElseThrow();
    }
}
