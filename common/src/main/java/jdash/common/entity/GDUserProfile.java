package jdash.common.entity;

import jdash.common.AccessPolicy;
import org.immutables.value.Value;

/**
 * Represents a user profile. It inherits all information from {@link GDUser} and {@link GDUserStats}, plus some
 * additional information such as the user's icon set, their social links and their privacy settings.
 */
@Value.Immutable
public interface GDUserProfile extends GDUserStats {

    /**
     * The global rank of the user, as shown in profile.
     *
     * @return an int
     */
    int globalRank();

    /**
     * The ID of the cube icon of the user.
     *
     * @return an int
     */
    int cubeIconId();

    /**
     * The ID of the ship icon of the user.
     *
     * @return an int
     */
    int shipIconId();

    /**
     * The ID of the UFO icon of the user.
     *
     * @return an int
     */
    int ufoIconId();

    /**
     * The ID of the ball icon of the user.
     *
     * @return an int
     */
    int ballIconId();

    /**
     * The ID of the wave icon of the user.
     *
     * @return an int
     */
    int waveIconId();

    /**
     * The ID of the robot icon of the user.
     *
     * @return an int
     */
    int robotIconId();

    /**
     * The ID of the spider icon of the user.
     *
     * @return an int
     */
    int spiderIconId();

    /**
     * The ID of the swing icon of the user.
     *
     * @return an int
     */
    int swingIconId();

    /**
     * The ID of the jetpack icon of the user.
     *
     * @return an int
     */
    int jetpackIconId();

    /**
     * The ID of the glow color of the user.
     *
     * @return an int
     */
    int glowColorId();

    /**
     * The YouTube channel ID of the user.
     *
     * @return a string
     */
    String youtube();

    /**
     * The Twitter handle of the user
     *
     * @return a string
     */
    String twitter();

    /**
     * The Twitch username of the user
     *
     * @return a string
     */
    String twitch();

    /**
     * Whether the user enabled incoming friend requests.
     *
     * @return a boolean
     */
    boolean hasFriendRequestsEnabled();

    /**
     * The policy of the user regarding private messages.
     *
     * @return a {@link AccessPolicy}
     */
    AccessPolicy privateMessagePolicy();

    /**
     * The policy of the user regarding their comment history.
     *
     * @return a {@link AccessPolicy}
     */
    AccessPolicy commentHistoryPolicy();
}
