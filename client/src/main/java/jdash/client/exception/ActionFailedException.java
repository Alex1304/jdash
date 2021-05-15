package jdash.client.exception;

import java.util.Objects;

public class ActionFailedException extends GDResponseException {

    public ActionFailedException(String response, String message) {
        super(response, message);
    }

    public static void throwIfEquals(String a, String b, String message) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);
        Objects.requireNonNull(message);
        if (a.equals(b)) {
            throw new ActionFailedException(a, message);
        }
    }
}