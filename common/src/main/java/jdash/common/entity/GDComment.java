package jdash.common.entity;

import jdash.common.Role;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
public interface GDComment {

    long id();

    Optional<Long> authorPlayerId();

    Optional<Long> authorAccountId();

    Optional<String> authorName();

    Optional<Role> authorRole();

    String content();

    int likes();

    String postedAgo();

    Optional<Integer> percentage();

    Optional<Integer> color();
}
