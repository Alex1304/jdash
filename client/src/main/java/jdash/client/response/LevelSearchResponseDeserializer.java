package jdash.client.response;

import jdash.common.entity.GDLevel;
import jdash.common.entity.GDSong;
import jdash.common.internal.InternalUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static jdash.common.internal.Indexes.*;
import static jdash.common.internal.InternalUtils.*;

class LevelSearchResponseDeserializer implements Function<String, List<GDLevel>> {

    @Override
    public List<GDLevel> apply(String response) {
        ArrayList<GDLevel> list = new ArrayList<>();
        String[] split1 = response.split("#");
        String levels = split1[0];
        String creators = split1[1];
        String songs = split1[2];
        Map<Long, String> structuredCreatorsInfo = structureCreatorsInfo(creators);
        Map<Long, GDSong> structuredSongsInfo = structureSongsInfo(songs);
        String[] levelArray = levels.split("\\|");
        for (String l : levelArray) {
            Map<Integer, String> lmap = splitToMap(l, ":");
            long songID = Long.parseLong(lmap.getOrDefault(LEVEL_SONG_ID, "0"));
            GDSong song = songID <= 0
                    ? getAudioTrack(Integer.parseInt(lmap.getOrDefault(LEVEL_AUDIO_TRACK, "0")))
                    : structuredSongsInfo.getOrDefault(songID, GDSong.unknownSong(songID));
            var creatorName = structuredCreatorsInfo.get(Long.parseLong(
                    lmap.getOrDefault(LEVEL_CREATOR_ID, "0")));
            list.add(InternalUtils.initLevelBuilder(creatorName, lmap)
                    .song(song)
                    .build());
        }
        return list;
    }
}
