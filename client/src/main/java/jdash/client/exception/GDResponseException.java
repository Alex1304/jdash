package jdash.client.exception;

import java.util.Objects;

/**
 * Superclass representing an exception that occurs when reading the response of the request.
 */
public class GDResponseException extends RuntimeException {

    private final String response;

    public GDResponseException(String response, String message) {
        super(message);
        this.response = Objects.requireNonNull(response);
    }

    public GDResponseException(String response, String message, Throwable cause) {
        super(message, cause);
        this.response = Objects.requireNonNull(response);
    }

    /**
     * Gets the raw response that caused the exception.
     *
     * @return the string response
     */
    public String getResponse() {
        return response;
    }
}