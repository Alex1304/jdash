package jdash.entity;

import jdash.common.IconType;
import jdash.common.PrivacySetting;
import jdash.common.Role;

import java.util.Optional;

public interface GDUser {

    long playerId();

    long accountId();

    String name();

    int secretCoins();

    int userCoins();

    int color1Id();

    int color2Id();

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

    Optional<Boolean> hasGlowOutline();

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
