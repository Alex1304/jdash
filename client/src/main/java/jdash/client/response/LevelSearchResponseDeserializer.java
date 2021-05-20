package jdash.client.response;

import jdash.client.exception.ActionFailedException;
import jdash.common.entity.GDLevel;
import jdash.common.entity.GDSong;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static jdash.common.internal.InternalUtils.*;

class LevelSearchResponseDeserializer implements Function<String, List<GDLevel>> {

    @Override
    public List<GDLevel> apply(String response) {
        ActionFailedException.throwIfEquals(response, "-1", "No results found");
        ArrayList<GDLevel> list = new ArrayList<>();
        String[] split1 = response.split("#");
        String levels = split1[0];
        String creators = split1[1];
        String songs = split1[2];
        Map<Long, String> structuredCreatorsInfo = structureCreatorsInfo(creators);
        Map<Long, GDSong> structuredSongsInfo = structureSongsInfo(songs);
        String[] levelArray = levels.split("\\|");
        for (String l : levelArray) {
            Map<Integer, String> data = splitToMap(l, ":");
            list.add(buildLevel(data, structuredCreatorsInfo, structuredSongsInfo));
        }
        return list;
    }
}
