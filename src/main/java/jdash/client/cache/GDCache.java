package jdash.client.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import jdash.client.request.GDRequest;

import java.util.Optional;
import java.util.function.UnaryOperator;

public interface GDCache {

    static GDCache caffeine(UnaryOperator<Caffeine<Object, Object>> caffeineBuilder) {
        return new CaffeineCache(caffeineBuilder);
    }

    static GDCache disabled() {
        return NoOpCache.INSTANCE;
    }

    static GDCache inMemory() {
        return new InMemoryCache();
    }

    Optional<Object> retrieve(GDRequest request);

    void put(GDRequest request, Object cached);

    void clear();
}
