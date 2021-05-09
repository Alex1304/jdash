package jdash.common;


import jdash.entity.GDUser;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public enum IconType {
    CUBE(GDUser::cubeIconId),
    SHIP(GDUser::shipIconId),
    BALL(GDUser::ballIconId),
    UFO(GDUser::ufoIconId),
    WAVE(GDUser::waveIconId),
    ROBOT(GDUser::robotIconId),
    SPIDER(GDUser::spiderIconId);

    private final Function<GDUser, Optional<Integer>> idGetter;

    IconType(Function<GDUser, Optional<Integer>> idGetter) {
        this.idGetter = idGetter;
    }

    public int idForUser(GDUser user) {
        return idGetter.apply(Objects.requireNonNull(user)).orElse(0);
    }
}
