package jdash.client;

import jdash.client.cache.GDCache;
import jdash.client.request.GDRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class GDCacheMockUp implements GDCache {

    private final Map<GDRequest, Object> map = new HashMap<>();

    @Override
    public Optional<Object> retrieve(GDRequest request) {
        return Optional.ofNullable(map.get(request));
    }

    @Override
    public void put(GDRequest request, Object cached) {
        map.put(request, cached);
    }

    @Override
    public void clear() {
        map.clear();
    }

    Map<GDRequest, Object> getMap() {
        return map;
    }
}
