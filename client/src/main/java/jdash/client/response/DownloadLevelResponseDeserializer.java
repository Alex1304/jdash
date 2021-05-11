package jdash.client.response;

import jdash.common.RobTopsWeakEncryption;
import jdash.common.entity.GDLevel;
import jdash.common.entity.GDSong;
import jdash.common.internal.InternalUtils;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static jdash.common.internal.Indexes.*;
import static jdash.common.internal.InternalUtils.b64DecodeToBytes;
import static jdash.common.internal.InternalUtils.splitToMap;

public class DownloadLevelResponseDeserializer implements Function<String, GDLevel> {

    @Override
    public GDLevel apply(String response) {
        String[] split1 = response.split("#");
        String levelData = split1[0];
        String creatorName = null;
        if (split1.length > 3 && split1[3].split(":").length >= 2) {
            creatorName = split1[3].split(":")[1];
        }
        Map<Integer, String> dataMap = splitToMap(levelData, ":");
        int pass;
        String strPass = dataMap.getOrDefault(LEVEL_PASS, "");
        if (strPass.equals("Aw==")) {
            pass = -2; // free to copy
        } else if (strPass.length() < 5) {
            pass = -1; // not copyable
        } else {
            pass = Integer.parseInt(RobTopsWeakEncryption.decodeLevelPass(strPass).substring(1));
        }
        long songID = Long.parseLong(dataMap.get(LEVEL_SONG_ID));
        GDSong song = songID > 0 ? GDSong.unknownSong(songID) : GDSong.unknownSong(0);
        return InternalUtils.initLevelBuilder(creatorName, dataMap)
                .song(song)
                .isCopyable(pass != -1)
                .copyPasscode(Optional.of(pass).filter(x -> x >= 0))
                .uploadedAgo(dataMap.getOrDefault(LEVEL_UPLOADED_AGO, "NA"))
                .updatedAgo(dataMap.getOrDefault(LEVEL_UPDATED_AGO, "NA"))
                .data(ByteBuffer.wrap(b64DecodeToBytes(dataMap.getOrDefault(LEVEL_DATA, ""))))
                .build();
    }
}
