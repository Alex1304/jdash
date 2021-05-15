package jdash.client.request;

import jdash.client.cache.GDCache;
import jdash.client.response.GDResponse;
import jdash.client.response.impl.GDCachedObjectResponse;
import jdash.client.response.impl.GDSerializedSourceResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class GDRequest {

    private final String uri;
    private final Map<String, String> params = new HashMap<>();

    private GDRequest(String uri) {
        this.uri = uri;
    }

    public static GDRequest of(String uri) {
        return new GDRequest(uri);
    }

    public GDRequest addParameter(String key, Object value) {
        Objects.requireNonNull(key);
        params.put(key, String.valueOf(value));
        return this;
    }

    public GDRequest addParameters(Map<String, String> params) {
        Objects.requireNonNull(params);
        this.params.putAll(params);
        return this;
    }

    public String getUri() {
        return uri;
    }

    public Map<String, String> getParams() {
        return Map.copyOf(params);
    }

    public GDResponse execute(GDCache cache, GDRouter router) {
        Objects.requireNonNull(cache);
        Objects.requireNonNull(router);
        return cache.retrieve(this)
                .<GDResponse>map(GDCachedObjectResponse::new)
                .orElseGet(() -> router.send(this)
                        .as(source -> new GDSerializedSourceResponse(this, source, o -> cache.put(this, o))));
    }

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
