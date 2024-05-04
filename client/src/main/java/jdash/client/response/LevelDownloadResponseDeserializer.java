package jdash.client.response;

import jdash.client.exception.ActionFailedException;
import jdash.common.RobTopsWeakEncryption;
import jdash.common.entity.GDLevelDownload;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static jdash.common.internal.Indexes.*;
import static jdash.common.internal.InternalUtils.*;

class LevelDownloadResponseDeserializer implements Function<String, GDLevelDownload> {

    @Override
    public GDLevelDownload apply(String response) {
        ActionFailedException.throwIfEquals(response, "-1", "Failed to download level");
        final var parts = response.split("#");
        final var levelData = parts[0];
        final var creatorInfo = parts.length > 3 ? structureCreatorsInfo(parts[3]) : Map.<Long, String>of();
        final var data = splitToMap(levelData, ":");
        requireKeys(data, LEVEL_PASS, LEVEL_SONG_ID, LEVEL_UPLOADED_AGO, LEVEL_UPDATED_AGO, LEVEL_DATA);
        int pass;
        final var strPass = data.get(LEVEL_PASS);
        if (strPass.equals("Aw==")) {
            pass = -2; // free to copy
        } else if (strPass.length() < 5) {
            pass = -1; // not copyable
        } else {
            pass = Integer.parseInt(RobTopsWeakEncryption.decodeLevelPasscode(strPass).substring(1));
        }
        return new GDLevelDownload(
                buildLevel(data, creatorInfo, Map.of()),
                pass != -1,
                Optional.of(pass).filter(x -> x >= 0),
                data.get(LEVEL_UPLOADED_AGO),
                data.get(LEVEL_UPDATED_AGO),
                ByteBuffer.wrap(b64DecodeToBytes(data.get(LEVEL_DATA)))
        );
    }
}
