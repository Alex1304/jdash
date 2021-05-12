package jdash.client.response;

import jdash.common.RobTopsWeakEncryption;
import jdash.common.entity.GDLevelDownload;
import jdash.common.entity.ImmutableGDLevelDownload;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static jdash.common.internal.Indexes.*;
import static jdash.common.internal.InternalUtils.*;

public class LevelDownloadResponseDeserializer implements Function<String, GDLevelDownload> {

    @Override
    public GDLevelDownload apply(String response) {
        var parts = response.split("#");
        var levelData = parts[0];
        var creatorInfo = parts.length > 3 ? structureCreatorsInfo(parts[3]) : Map.<Long, String>of();
        var data = splitToMap(levelData, ":");
        requireKeys(data, LEVEL_PASS, LEVEL_SONG_ID, LEVEL_UPLOADED_AGO, LEVEL_UPDATED_AGO, LEVEL_DATA);
        int pass;
        var strPass = data.get(LEVEL_PASS);
        if (strPass.equals("Aw==")) {
            pass = -2; // free to copy
        } else if (strPass.length() < 5) {
            pass = -1; // not copyable
        } else {
            pass = Integer.parseInt(RobTopsWeakEncryption.decodeLevelPass(strPass).substring(1));
        }
        return ImmutableGDLevelDownload.builder()
                .from(buildLevel(data, creatorInfo, Map.of()))
                .isCopyable(pass != -1)
                .copyPasscode(Optional.of(pass).filter(x -> x >= 0))
                .uploadedAgo(data.get(LEVEL_UPLOADED_AGO))
                .updatedAgo(data.get(LEVEL_UPDATED_AGO))
                .data(ByteBuffer.wrap(b64DecodeToBytes(data.get(LEVEL_DATA))))
                .build();
    }
}
