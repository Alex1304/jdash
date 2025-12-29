package jdash.client.request;

import jdash.client.cache.GDCache;
import jdash.client.response.GDResponse;
import jdash.client.response.impl.GDCachedObjectResponse;
import jdash.client.response.impl.GDSerializedSourceResponse;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a request to execute an action or access a resource using the client. It is made out of an URI and zero,
 * one or more key/value parameters. {@link GDRequest} instances are mutable and NOT thread-safe.
 */
public final class GDRequest {

    private final String uri;
    private final Map<String, String> params = new HashMap<>();

    private GDRequest(String uri) {
        this.uri = uri;
    }

    /**
     * Creates a new request with the given URI. It is initialized with no parameters.
     *
     * @param uri the URI of the request
     * @return a new {@link GDRequest}
     */
    public static GDRequest of(String uri) {
        return new GDRequest(uri);
    }

    /**
     * Adds a new key/value pair into the parameters of this request. If one already exists for the given key, its value
     * will be replaced.
     *
     * @param key   the parameter key
     * @param value the parameter value, which will be converted to a String according to {@link
     *              String#valueOf(Object)}. May be <code>null</code>.
     * @return this request
     */
    public GDRequest addParameter(String key, @Nullable Object value) {
        Objects.requireNonNull(key);
        params.put(key, String.valueOf(value));
        return this;
    }

    /**
     * Adds all key/value pairs of the given map into the parameters of this request.
     *
     * @param params the map containing all parameters to add
     * @return this request
     */
    public GDRequest addParameters(Map<String, String> params) {
        Objects.requireNonNull(params);
        this.params.putAll(params);
        return this;
    }

    /**
     * Gets the URI of this request.
     *
     * @return the URI
     */
    public String getUri() {
        return uri;
    }

    /**
     * Gets the parameters of this request. If more parameters are added to this request, the returned map will NOT
     * reflect the changes. The returned map is unmodifiable.
     *
     * @return the parameters
     */
    public Map<String, String> getParams() {
        return Map.copyOf(params);
    }

    /**
     * Executes this request using the given cache and router. This method will first check in the cache if an object is
     * not already available for this request. If not, it will delegate the request to the router which will be in
     * charge of delivering a response from an asynchronous source for this request. If a cache miss happens and the
     * router is hit, the response will be added to the cache.
     *
     * @param cache  the cache
     * @param router the router
     * @return a {@link GDResponse} representing the result of the execution of the request.
     */
    public GDResponse execute(GDCache cache, GDRouter router) {
        Objects.requireNonNull(cache);
        Objects.requireNonNull(router);
        return cache.retrieve(this)
                .<GDResponse>map(GDCachedObjectResponse::new)
                .orElseGet(() -> router.send(this)
                        .as(source -> new GDSerializedSourceResponse(this, source, o -> cache.put(this, o))));
    }

    /**
     * Gives a string representation of the parameters of this request, in a format that follows the
     * <code>x-www-form-urlencoded</code> content-type.
     *
     * @return a string
     */
    public String toRequestString() {
        return params.entrySet().stream()
                .map(entry -> entry.getKey() + '=' + entry.getValue())
                .collect(Collectors.joining("&"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GDRequest gdRequest = (GDRequest) o;
        return Objects.equals(uri, gdRequest.uri) && Objects.equals(params, gdRequest.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri, params);
    }

    @Override
    public String toString() {
        return "GDRequest{" +
                "uri='" + uri + '\'' +
                ", params=" + params +
                '}';
    }
}
