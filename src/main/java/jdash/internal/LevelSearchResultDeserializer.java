package jdash.internal;

import jdash.common.Length;
import jdash.entity.GDLevel;
import jdash.entity.GDSong;
import jdash.entity.ImmutableGDLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static jdash.internal.Indexes.*;
import static jdash.internal.InternalUtils.*;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;

public class LevelSearchResultDeserializer implements Function<String, List<GDLevel>> {

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
            long songID = Long.parseLong(defaultIfEmpty(lmap.get(LEVEL_SONG_ID), "0"));
            GDSong song = songID <= 0
                    ? getAudioTrack(Integer.parseInt(defaultIfEmpty(lmap.get(LEVEL_AUDIO_TRACK), "0")))
                    : structuredSongsInfo.getOrDefault(songID, GDSong.unknownSong(songID));
            String creatorName = structuredCreatorsInfo.getOrDefault(Long.parseLong(
                    defaultIfEmpty(lmap.get(LEVEL_CREATOR_ID), "0")), "-");
            int length = Integer.parseInt(defaultIfEmpty(lmap.get(LEVEL_LENGTH), "0"));
            long levelId = Long.parseLong(defaultIfEmpty(lmap.get(LEVEL_ID), "0"));
            list.add(ImmutableGDLevel.builder()
                    .id(levelId)
                    .name(defaultIfEmpty(lmap.get(LEVEL_NAME), "-"))
                    .creatorId(Long.parseLong(defaultIfEmpty(lmap.get(LEVEL_CREATOR_ID), "0")))
                    .description(b64Decode(defaultIfEmpty(lmap.get(LEVEL_DESCRIPTION), "")))
                    .difficulty(valueToDifficulty(Integer.parseInt(defaultIfEmpty(lmap.get(LEVEL_DIFFICULTY), "0"))))
                    .demonDifficulty(valueToDemonDifficulty(
                            Integer.parseInt(defaultIfEmpty(lmap.get(LEVEL_DEMON_DIFFICULTY), "0"))))
                    .stars(Integer.parseInt(defaultIfEmpty(lmap.get(LEVEL_STARS), "0")))
                    .featuredScore(Integer.parseInt(defaultIfEmpty(lmap.get(LEVEL_FEATURED_SCORE), "0")))
                    .isEpic(!defaultIfEmpty(lmap.get(LEVEL_IS_EPIC), "0").equals("0"))
                    .downloads(Integer.parseInt(defaultIfEmpty(lmap.get(LEVEL_DOWNLOADS), "0")))
                    .likes(Integer.parseInt(defaultIfEmpty(lmap.get(LEVEL_LIKES), "0")))
                    .length(Length.values()[length >= Length.values().length ? 0 : length])
                    .coinCount(Integer.parseInt(defaultIfEmpty(lmap.get(LEVEL_COIN_COUNT), "0")))
                    .hasCoinsVerified(defaultIfEmpty(lmap.get(LEVEL_COIN_VERIFIED), "0").equals("1"))
                    .levelVersion(Integer.parseInt(defaultIfEmpty(lmap.get(LEVEL_VERSION), "0")))
                    .gameVersion(Integer.parseInt(defaultIfEmpty(lmap.get(LEVEL_GAME_VERSION), "0")))
                    .objectCount(Integer.parseInt(defaultIfEmpty(lmap.get(LEVEL_OBJECT_COUNT), "0")))
                    .isDemon(defaultIfEmpty(lmap.get(LEVEL_IS_DEMON), "0").equals("1"))
                    .isAuto(defaultIfEmpty(lmap.get(LEVEL_IS_AUTO), "0").equals("1"))
                    .originalLevelId(Long.parseLong(defaultIfEmpty(lmap.get(LEVEL_ORIGINAL), "0")))
                    .requestedStars(Integer.parseInt(defaultIfEmpty(lmap.get(LEVEL_REQUESTED_STARS), "0")))
                    .creatorName(creatorName)
                    .song(song)
                    .build());
        }
        return list;
    }
}
