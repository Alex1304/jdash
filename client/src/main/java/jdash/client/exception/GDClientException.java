package jdash.client.exception;

import jdash.client.GDClient;
import jdash.client.request.GDRequest;

import java.util.Objects;

/**
 * Emitted by {@link GDClient} methods when something goes wrong when executing a request. The cause is always available
 * via {@link GDClientException#getCause()}.
 */
public class GDClientException extends RuntimeException {

    private final GDRequest request;

    public GDClientException(GDRequest request, Throwable cause) {
        super("Error when processing request " + Objects.requireNonNull(request), cause);
        this.request = request;
    }

    /**
     * Gets the original request.
     *
     * @return the {@link GDRequest}
     */
    public GDRequest getRequest() {
        return request;
    }
}
