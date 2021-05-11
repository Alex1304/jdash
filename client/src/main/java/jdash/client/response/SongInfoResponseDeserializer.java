package jdash.client.response;

import jdash.client.exception.SongNotAllowedForUseException;
import jdash.common.entity.GDSong;
import jdash.common.internal.InternalUtils;

import java.util.function.Function;

public class SongInfoResponseDeserializer implements Function<String, GDSong> {

    @Override
    public GDSong apply(String response) {
        if (response.equals("-2")) {
            throw new SongNotAllowedForUseException();
        }
        return InternalUtils.structureSongsInfo(response).values().stream().findFirst().orElseThrow();
    }
}
