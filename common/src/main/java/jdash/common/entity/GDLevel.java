package jdash.common.entity;

import jdash.common.DemonDifficulty;
import jdash.common.Difficulty;
import jdash.common.Length;
import org.immutables.value.Value;

import java.nio.ByteBuffer;
import java.util.Optional;

@Value.Immutable
public interface GDLevel {

    long id();

    String name();

    long creatorId();

    String description();

    Difficulty difficulty();

    DemonDifficulty demonDifficulty();

    int stars();

    int featuredScore();

    boolean isEpic();

    int downloads();

    int likes();

    Length length();

    int coinCount();

    boolean hasCoinsVerified();

    int levelVersion();

    int gameVersion();

    int objectCount();

    boolean isDemon();

    boolean isAuto();

    long originalLevelId();

    int requestedStars();

    GDSong song();

    Optional<String> creatorName();

    Optional<Boolean> isCopyable();

    Optional<Integer> copyPasscode();

    Optional<String> uploadedAgo();

    Optional<String> updatedAgo();

    Optional<ByteBuffer> data();
}
