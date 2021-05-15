package jdash.client.request;

import reactor.core.publisher.Mono;
import reactor.util.annotation.Nullable;

import java.time.Duration;
import java.util.Objects;

/**
 * The router is responsible for routing GD requests to an appropriate source of responses, typically an HTTP server. In
 * most use cases, requests will be routed to {@link GDRequests#BASE_URL}, but the implementation may customize the way
 * requests are processed.
 */
public interface GDRouter {

    /**
     * Creates a new instance of the default {@link GDRouter} implementation routing requests to the official Geometry
     * Dash HTTP server (as defined by {@link GDRequests#BASE_URL}).
     *
     * @return a new {@link GDRouter}
     */
    static GDRouter defaultRouter() {
        return builder().build();
    }

    /**
     * Creates a new builder for the default {@link GDRouter} implementation.
     *
     * @return a new builder
     */
    static Builder builder() {
        return new Builder();
    }

    /**
     * Routes a request to an appropriate source provider, such as an HTTP server.
     *
     * @param request the request to route
     * @return a Mono emitting the raw response of the source as a String, that can later be deserialized.
     */
    Mono<String> send(GDRequest request);

    final class Builder {

        private RequestLimiter limiter;
        private Duration timeout;
        private String baseUrl;

        private Builder() {
        }

        public Builder setRequestLimiter(@Nullable RequestLimiter limiter) {
            this.limiter = limiter;
            return this;
        }

        public Builder setRequestTimeout(@Nullable Duration timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder setBaseUrl(@Nullable String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public GDRouter build() {
            var limiter = Objects.requireNonNullElse(this.limiter, RequestLimiter.none());
            var baseUrl = Objects.requireNonNullElse(this.baseUrl, GDRequests.BASE_URL);
            return new GDRouterImpl(limiter, timeout, baseUrl);
        }
    }
}
