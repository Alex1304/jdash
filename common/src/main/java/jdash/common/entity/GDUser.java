package jdash.common.entity;

import jdash.common.IconType;
import jdash.common.PrivacySetting;
import jdash.common.Role;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
public interface GDUser {

    long playerId();

    long accountId();

    String name();

    int secretCoins();

    int userCoins();

    int color1Id();

    int color2Id();

    boolean hasGlowOutline();

    int stars();

    int creatorPoints();

    int demons();

    Optional<Integer> diamonds();

    Optional<Integer> globalRank();

    Optional<Integer> cubeIconId();

    Optional<Integer> shipIconId();

    Optional<Integer> ufoIconId();

    Optional<Integer> ballIconId();

    Optional<Integer> waveIconId();

    Optional<Integer> robotIconId();

    Optional<Integer> spiderIconId();

    Optional<Integer> deathEffectId();

    Optional<Integer> mainIconId();

    Optional<IconType> mainIconType();

    Optional<String> youtube();

    Optional<String> twitter();

    Optional<String> twitch();

    Optional<Role> role();

    Optional<Boolean> hasFriendRequestsEnabled();

    Optional<PrivacySetting> privateMessagePolicy();

    Optional<PrivacySetting> commentHistoryPolicy();
}
