package jdash.common.entity;

import jdash.common.IconType;
import jdash.common.Role;
import org.immutables.value.Value;

import java.util.Optional;

/**
 * Represents a user in Geometry Dash. This class only contains basic information on the user such as their ID, their
 * name, their main icon and their role. It does not contain player stats (stars, demons...), nor detailed information
 * on their profile (icon set, social links, privacy settings...). See {@link GDUserStats} and {@link GDUserProfile}
 * respectively to get this extra information (both of these interfaces extend this one).
 */
@Value.Immutable
public interface GDUser {

    /**
     * The player ID of the user.
     *
     * @return a long
     */
    long playerId();

    /**
     * The account ID of the user. In rare cases, it might be 0 if the user doesn't have an account.
     *
     * @return a long
     */
    long accountId();

    /**
     * The in-game name of the user.
     *
     * @return a string
     */
    String name();

    /**
     * The ID of the primary color of the user.
     *
     * @return an int
     */
    int color1Id();

    /**
     * The ID of the secondary color of the user.
     *
     * @return an int
     */
    int color2Id();

    /**
     * Whether the user enabled glow outline on his icons.
     *
     * @return a boolean
     */
    boolean hasGlowOutline();

    /**
     * The ID of the main icon of the user. The main icon is the one shown in comments or in friends list. This
     * information is not always provided.
     *
     * @return an {@link Optional} containing an integer if present
     */
    Optional<Integer> mainIconId();

    /**
     * The type of the main icon of the user. The main icon is the one shown in comments or in friends list. This
     * information is not always provided.
     *
     * @return an {@link Optional} containing an {@link IconType} if present
     */
    Optional<IconType> mainIconType();

    /**
     * The role of a user, that is whether the user is moderator, elder moderator or just a regular user. This
     * information is not always provided.
     *
     * @return an {@link Optional} containing a {@link Role} if present
     */
    Optional<Role> role();
}
