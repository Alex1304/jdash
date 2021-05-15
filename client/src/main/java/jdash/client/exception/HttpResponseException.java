package jdash.client.exception;

import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.Objects;

/**
 * Emitted when the Geometry Dash server returns an HTTP error.
 */
public class HttpResponseException extends RuntimeException {

    private final HttpResponseStatus responseStatus;

    public HttpResponseException(HttpResponseStatus responseStatus) {
        super(Objects.requireNonNull(responseStatus).toString());
        this.responseStatus = responseStatus;
    }

    /**
     * Gets the HTTP response status.
     *
     * @return the {@link HttpResponseStatus}
     */
    public HttpResponseStatus getResponseStatus() {
        return responseStatus;
    }
}