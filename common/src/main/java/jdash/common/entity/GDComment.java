package jdash.common.entity;

import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
public interface GDComment {

    long id();

    Optional<GDUser> author();

    String content();

    int likes();

    String postedAgo();

    Optional<Integer> percentage();

    Optional<Integer> color();
}
