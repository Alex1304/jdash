package jdash.client.cache;

import jdash.client.request.GDRequest;

import java.util.Optional;

public class NoOpCache implements GDCache {

    static final NoOpCache INSTANCE = new NoOpCache();

    private NoOpCache() {
    }

    @Override
    public Optional<Object> retrieve(GDRequest request) {
        return Optional.empty();
    }

    @Override
    public void put(GDRequest request, Object cached) {
    }

    @Override
    public void clear() {
    }
}
