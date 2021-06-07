package jdash.client.cache;

import jdash.client.request.GDRequest;

import java.util.Optional;

class WriteOnlyCache implements GDCache {

    private final GDCache delegate;

    WriteOnlyCache(GDCache delegate) {
        this.delegate = delegate;
    }

    @Override
    public Optional<Object> retrieve(GDRequest request) {
        return Optional.empty();
    }

    @Override
    public void put(GDRequest request, Object cached) {
        delegate.put(request, cached);
    }

    @Override
    public void clear() {
        delegate.clear();
    }
}
