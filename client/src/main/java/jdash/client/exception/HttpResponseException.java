package jdash.client.exception;

import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.Objects;

/**
 * Thrown when the Geometry Dash server returns an HTTP error
 */
public class HttpResponseException extends RuntimeException {

    private final HttpResponseStatus responseStatus;

    public HttpResponseException(HttpResponseStatus responseStatus) {
        super(Objects.requireNonNull(responseStatus).toString());
        this.responseStatus = responseStatus;
    }

    public HttpResponseStatus getResponseStatus() {
        return responseStatus;
    }
}