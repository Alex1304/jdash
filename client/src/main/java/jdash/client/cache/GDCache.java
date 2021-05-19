package jdash.client.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import jdash.client.request.GDRequest;
import jdash.client.request.GDRouter;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;

/**
 * A cache allows to retrieve the response associated to a request that was already made previously without needing to
 * pass the request to the {@link GDRouter}.
 */
public interface GDCache {

    /**
     * Creates a {@link GDCache} based on {@link Caffeine} implementation, allowing to customize things such as
     * expiration times or maximum size.
     *
     * @param caffeineBuilder a function that allows to configure the caffeine cache
     * @return a new {@link GDCache}
     */
    static GDCache caffeine(UnaryOperator<Caffeine<Object, Object>> caffeineBuilder) {
        return new CaffeineCache(caffeineBuilder);
    }

    /**
     * Creates a {@link GDCache} that does nothing.
     *
     * @return a new {@link GDCache}
     */
    static GDCache disabled() {
        return NoOpCache.INSTANCE;
    }

    /**
     * Creates a {@link GDCache} that is backed by a simple {@link ConcurrentHashMap}, storing everything indefinitely
     * in main memory. It is recommended to clear it regularly to avoid memory leaks when used in production.
     *
     * @return a new {@link GDCache}
     */
    static GDCache inMemory() {
        return new InMemoryCache();
    }

    /**
     * Attempts to retrieve the object associated to the given request.
     *
     * @param request the request
     * @return the object associated to the request, or an empty {@link Optional} if not found.
     */
    Optional<Object> retrieve(GDRequest request);

    /**
     * Puts a new object in the cache, associated with the given request.
     *
     * @param request the request
     * @param cached  the object to cache
     */
    void put(GDRequest request, Object cached);

    /**
     * Clears the cache completely, which will effectively remove all objects currently stored.
     */
    void clear();
}
