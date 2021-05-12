package jdash.common.entity;

import jdash.common.IconType;
import jdash.common.Role;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
public interface GDUser {

    long playerId();

    long accountId();

    String name();

    int color1Id();

    int color2Id();

    boolean hasGlowOutline();

    Optional<Integer> mainIconId();

    Optional<IconType> mainIconType();

    Optional<Role> role();
}
