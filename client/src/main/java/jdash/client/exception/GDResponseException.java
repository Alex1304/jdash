package jdash.client.exception;

import java.util.Objects;

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

    public String getResponse() {
        return response;
    }
}