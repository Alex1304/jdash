package jdash.client.cache;

import jdash.client.request.GDRequest;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCache implements GDCache {

    private final ConcurrentHashMap<GDRequest, Object> map = new ConcurrentHashMap<>();

    @Override
    public Optional<Object> retrieve(GDRequest request) {
        Objects.requireNonNull(request);
        return Optional.ofNullable(map.get(request));
    }

    @Override
    public void put(GDRequest request, Object cached) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(cached);
        map.put(request, cached);
    }

    @Override
    public void clear() {
        map.clear();
    }
}
