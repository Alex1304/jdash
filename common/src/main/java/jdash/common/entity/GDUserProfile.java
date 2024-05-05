package jdash.common.entity;

import jdash.common.PrivacySetting;

import java.util.Optional;

/**
 * Represents a user profile. It exposes information from {@link GDUser} and {@link GDUserStats}, plus some additional
 * information such as the user's icon set, their social links and their privacy settings.
 *
 * @param user                      The {@link GDUser} containing the base information about the user.
 * @param stats                     The {@link GDUserStats} containing information about the user's stats.
 * @param globalRank                The global rank of the user, as shown in profile.
 * @param cubeIconId                The ID of the cube icon of the user.
 * @param shipIconId                The ID of the ship icon of the user.
 * @param ufoIconId                 The ID of the UFO icon of the user.
 * @param ballIconId                The ID of the ball icon of the user.
 * @param waveIconId                The ID of the wave icon of the user.
 * @param robotIconId               The ID of the robot icon of the user.
 * @param spiderIconId              The ID of the spider icon of the user.
 * @param swingIconId               The ID of the swing icon of the user.
 * @param jetpackIconId             The ID of the jetpack icon of the user.
 * @param glowColorId               The ID of the glow color of the user.
 * @param youtube                   The YouTube channel ID of the user.
 * @param twitter                   The Twitter handle of the user
 * @param twitch                    The Twitch username of the user
 * @param hasFriendRequestsEnabled  Whether the user enabled incoming friend requests.
 * @param privateMessagePrivacy     The privacy setting for private messages.
 * @param commentHistoryPrivacy     The privacy setting for comment history.
 * @param completedClassicLevels    The breakdown of completed classic levels. May not be available.
 * @param completedPlatformerLevels The breakdown of completed platformer levels. May not be available.
 * @param completedDemons           The breakdown of completed demons. May not be available.
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
        PrivacySetting commentHistoryPrivacy,
        Optional<CompletedClassicLevels> completedClassicLevels,
        Optional<CompletedPlatformerLevels> completedPlatformerLevels,
        Optional<CompletedDemons> completedDemons
) {

    /**
     * Stores the breakdown of completed classic levels by difficulty.
     *
     * @param auto     The number of completed auto levels.
     * @param easy     The number of completed easy levels.
     * @param normal   The number of completed normal levels.
     * @param hard     The number of completed hard levels.
     * @param harder   The number of completed harder levels.
     * @param insane   The number of completed insane levels.
     * @param daily    The number of completed daily levels.
     * @param gauntlet The number of completed levels from gauntlets.
     */
    public record CompletedClassicLevels(
            int auto,
            int easy,
            int normal,
            int hard,
            int harder,
            int insane,
            int daily,
            int gauntlet
    ) {
        /**
         * Sums all values of this {@link CompletedClassicLevels}, excluding {@link #daily()} and {@link #gauntlet()}.
         *
         * @return the total
         */
        public int total() {
            return auto + easy + normal + hard + harder + insane;
        }
    }

    /**
     * Stores the breakdown of completed platformer levels by difficulty.
     *
     * @param auto     The number of completed auto levels.
     * @param easy     The number of completed easy levels.
     * @param normal   The number of completed normal levels.
     * @param hard     The number of completed hard levels.
     * @param harder   The number of completed harder levels.
     * @param insane   The number of completed insane levels.
     * @param gauntlet The number of completed levels from gauntlets.
     */
    public record CompletedPlatformerLevels(
            int auto,
            int easy,
            int normal,
            int hard,
            int harder,
            int insane,
            int gauntlet
    ) {
        /**
         * Sums all values of this {@link CompletedPlatformerLevels}, excluding {@link #gauntlet()}.
         *
         * @return the total
         */
        public int total() {
            return auto + easy + normal + hard + harder + insane;
        }
    }

    /**
     * Stores the breakdown of completed Demon levels by difficulty, both classic and platformer.
     *
     * @param easyClassic       The number of completed classic easy demons.
     * @param mediumClassic     The number of completed classic medium demons.
     * @param hardClassic       The number of completed classic hard demons.
     * @param insaneClassic     The number of completed classic insane demons.
     * @param extremeClassic    The number of completed classic extreme demons.
     * @param easyPlatformer    The number of completed platformer easy demons.
     * @param mediumPlatformer  The number of completed platformer medium demons.
     * @param hardPlatformer    The number of completed platformer hard demons.
     * @param insanePlatformer  The number of completed platformer insane demons.
     * @param extremePlatformer The number of completed platformer extreme demons.
     * @param weekly            The number of completed weekly demons.
     * @param gauntlet          The number of completed demons from gauntlets.
     */
    public record CompletedDemons(
            int easyClassic,
            int mediumClassic,
            int hardClassic,
            int insaneClassic,
            int extremeClassic,
            int easyPlatformer,
            int mediumPlatformer,
            int hardPlatformer,
            int insanePlatformer,
            int extremePlatformer,
            int weekly,
            int gauntlet
    ) {
        /**
         * Sums all values of classic completed demons. It does not include {@link #weekly()} or {@link #gauntlet()}.
         *
         * @return the total for classic demons
         */
        public int totalClassic() {
            return easyClassic + mediumClassic + hardClassic + insaneClassic + extremeClassic;
        }

        /**
         * Sums all values of platformer completed demons. It does not include {@link #weekly()} or
         * {@link #gauntlet()}.
         *
         * @return the total for platformer demons
         */
        public int totalPlatformer() {
            return easyPlatformer + mediumPlatformer + hardPlatformer + insanePlatformer + extremePlatformer;
        }
    }
}
