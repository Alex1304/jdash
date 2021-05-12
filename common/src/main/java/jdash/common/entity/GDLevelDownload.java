package jdash.common.entity;

import org.immutables.value.Value;

import java.nio.ByteBuffer;
import java.util.Optional;

@Value.Immutable
public interface GDLevelDownload extends GDLevel {

    boolean isCopyable();

    Optional<Integer> copyPasscode();

    String uploadedAgo();

    String updatedAgo();

    ByteBuffer data();
}
