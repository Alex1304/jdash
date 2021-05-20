package jdash.client.exception;

import java.util.Objects;

/**
 * Emitted when the Geometry Dash server signals that the attempted action could not be executed. It can happen if the
 * request has invalid parameters, refers to a resource that does not exist or is not accessible, or does not have the
 * permissions to execute it.
 */
public class ActionFailedException extends GDResponseException {

    public ActionFailedException(String response, String message) {
        super(response, message);
    }

    /**
     * Throws {@link ActionFailedException} with the given message if both strings are equal.
     *
     * @param a       the first string
     * @param b       the second string
     * @param message the message to include in the exception in case a equals b
     */
    public static void throwIfEquals(String a, String b, String message) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);
        Objects.requireNonNull(message);
        if (a.equals(b)) {
            throw new ActionFailedException(a, message);
        }
    }
}