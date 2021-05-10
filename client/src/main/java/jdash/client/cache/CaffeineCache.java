package jdash.client.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jdash.client.request.GDRequest;

import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

class CaffeineCache implements GDCache {

    private final Cache<GDRequest, Object> cache;

    CaffeineCache(UnaryOperator<Caffeine<Object, Object>> caffeineBuilder) {
        Objects.requireNonNull(caffeineBuilder);
        this.cache = caffeineBuilder.apply(Caffeine.newBuilder()).build();
    }

    @Override
    public Optional<Object> retrieve(GDRequest request) {
        Objects.requireNonNull(request);
        return Optional.ofNullable(cache.getIfPresent(request));
    }

    @Override
    public void put(GDRequest request, Object cached) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(cached);
        cache.put(request, cached);
    }

    @Override
    public void clear() {
        cache.invalidateAll();
    }
}
