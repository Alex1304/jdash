package jdash.client.exception;

import reactor.netty.http.client.HttpClientResponse;

/**
 * Thrown when the Geometry Dash API returns an HTTP error
 */
public class BadResponseException extends GDClientException {

    private final HttpClientResponse response;

    public BadResponseException(HttpClientResponse response) {
        super(response.status().toString());
        this.response = response;
    }

    public HttpClientResponse getResponse() {
        return response;
    }
}