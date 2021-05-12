package jdash.common.entity;

import jdash.common.PrivacySetting;
import org.immutables.value.Value;

@Value.Immutable
public interface GDUserProfile extends GDUserStats {
    
    int globalRank();

    int cubeIconId();

    int shipIconId();

    int ufoIconId();

    int ballIconId();

    int waveIconId();

    int robotIconId();

    int spiderIconId();

    String youtube();

    String twitter();

    String twitch();

    boolean hasFriendRequestsEnabled();

    PrivacySetting privateMessagePolicy();

    PrivacySetting commentHistoryPolicy();
}
