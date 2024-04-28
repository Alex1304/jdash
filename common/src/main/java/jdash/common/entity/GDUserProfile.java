package jdash.common.entity;

import jdash.common.PrivacySetting;

/**
 * Represents a user profile. It exposes information from {@link GDUser} and {@link GDUserStats}, plus some additional
 * information such as the user's icon set, their social links and their privacy settings.
 *
 * @param user                     The {@link GDUser} containing the base information about the user.
 * @param stats                    The {@link GDUserStats} containing information about the user's stats.
 * @param globalRank               The global rank of the user, as shown in profile.
 * @param cubeIconId               The ID of the cube icon of the user.
 * @param shipIconId               The ID of the ship icon of the user.
 * @param ufoIconId                The ID of the UFO icon of the user.
 * @param ballIconId               The ID of the ball icon of the user.
 * @param waveIconId               The ID of the wave icon of the user.
 * @param robotIconId              The ID of the robot icon of the user.
 * @param spiderIconId             The ID of the spider icon of the user.
 * @param swingIconId              The ID of the swing icon of the user.
 * @param jetpackIconId            The ID of the jetpack icon of the user.
 * @param glowColorId              The ID of the glow color of the user.
 * @param youtube                  The YouTube channel ID of the user.
 * @param twitter                  The Twitter handle of the user
 * @param twitch                   The Twitch username of the user
 * @param hasFriendRequestsEnabled Whether the user enabled incoming friend requests.
 * @param privateMessagePrivacy    The privacy setting for private messages.
 * @param commentHistoryPrivacy    The privacy setting for comment history.
 */
public record GDUserProfile(
        GDUser user,
        GDUserStats stats,
        int globalRank,
        int cubeIconId,
        int shipIconId,
        int ufoIconId,
        int ballIconId,
        int waveIconId,
        int robotIconId,
        int spiderIconId,
        int swingIconId,
        int jetpackIconId,
        int glowColorId,
        String youtube,
        String twitter,
        String twitch,
        boolean hasFriendRequestsEnabled,
        PrivacySetting privateMessagePrivacy,
        PrivacySetting commentHistoryPrivacy
) {}
