package jdash.client.exception;

import jdash.client.request.GDRequest;

import java.util.Objects;

public class CorruptedResponseContentException extends GDClientException {

    private final GDRequest request;
    private final String responseContent;

    public CorruptedResponseContentException(Throwable cause, GDRequest request, String responseContent) {
        super("Error when parsing response for request " + request, cause);
        this.request = Objects.requireNonNull(request);
        this.responseContent = Objects.requireNonNull(responseContent);
    }

    public GDRequest getRequest() {
        return request;
    }

    public String getResponseContent() {
        return responseContent;
    }
}