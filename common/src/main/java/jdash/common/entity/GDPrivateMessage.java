package jdash.common.entity;

import org.immutables.value.Value;

@Value.Immutable
public interface GDPrivateMessage {

    long id();

    long userAccountId();

    long userPlayerId();

    String userName();

    String subject();

    boolean isUnread();

    boolean isSender();

    String sentAgo();
}
