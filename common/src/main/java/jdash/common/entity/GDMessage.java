package jdash.common.entity;

import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
public interface GDMessage {

    long id();

    long authorAccountId();

    String authorName();

    String subject();

    Optional<String> body();

    boolean isRead();

    String sentAgo();
}
