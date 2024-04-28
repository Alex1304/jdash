package jdash.common.entity;

import jdash.common.IconType;
import jdash.common.Role;

import java.util.Optional;

/**
 * Represents a user in Geometry Dash. This class only contains basic information on the user such as their ID, their
 * name, their main icon and their role. It does not contain player stats (stars, demons...), nor detailed information
 * on their profile (icon set, social links, privacy settings...). See {@link GDUserStats} and {@link GDUserProfile}
 * respectively to get this extra information.
 *
 * @param playerId       The player ID of the user.
 * @param accountId      The account ID of the user. In rare cases, it might be 0 if the user doesn't have an account.
 * @param name           The in-game name of the user.
 * @param color1Id       The ID of the primary color of the user.
 * @param color2Id       The ID of the secondary color of the user.
 * @param hasGlowOutline Whether the user enabled glow outline on his icons.
 * @param mainIconId     The ID of the main icon of the user. The main icon is the one shown in comments or in friends
 *                       list. This information is not always provided.
 * @param mainIconType   The type of the main icon of the user. The main icon is the one shown in comments or in friends
 *                       list. This information is not always provided.
 * @param role           The role of a user, that is whether the user is moderator, elder moderator or just a regular
 *                       user. This information is not always provided.
 */
public record GDUser(
        long playerId,
        long accountId,
        String name,
        int color1Id,
        int color2Id,
        boolean hasGlowOutline,
        Optional<Integer> mainIconId,
        Optional<IconType> mainIconType,
        Optional<Role> role
) {}
