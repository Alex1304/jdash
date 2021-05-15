package jdash.client.exception;

import jdash.client.request.GDRequest;

import java.util.Objects;

public class GDClientException extends RuntimeException {

    private final GDRequest request;

    public GDClientException(GDRequest request, Throwable cause) {
        super("Error when processing request " + Objects.requireNonNull(request), cause);
        this.request = request;
    }

    public GDRequest getRequest() {
        return request;
    }
}
