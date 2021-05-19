package jdash.client.request;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.annotation.Nullable;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

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
        private Scheduler scheduler;

        private Builder() {
        }

        /**
         * Sets a limiter that will limit the number of requests that can be processed within a certain timeframe.
         * Requests exceeding the limit may be delayed.
         *
         * @param limiter the limiter to apply
         * @return this builder
         */
        public Builder setRequestLimiter(@Nullable RequestLimiter limiter) {
            this.limiter = limiter;
            return this;
        }

        /**
         * Sets a timeout for each request. Any request exceeding the duration will emit {@link TimeoutException}. By
         * default, does not apply a timeout and lets requests run indefinitely.
         *
         * @param timeout the duration of the timeout to set, or <code>null</code> to apply none
         * @return this builder
         */
        public Builder setRequestTimeout(@Nullable Duration timeout) {
            this.timeout = timeout;
            return this;
        }

        /**
         * Sets a custom base URL to redirect requests to. Useful to configure the client for a Geometry Dash private
         * server (GDPS). By default, uses {@link GDRequests#BASE_URL}.
         *
         * @param baseUrl the base URL, or <code>null</code> to use default
         * @return this builder
         */
        public Builder setBaseUrl(@Nullable String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * Sets a scheduler that will determine which thread the requests will be executed on. By default, runs on
         * {@link Schedulers#boundedElastic()} in order to allow for blocking calls.
         *
         * @param scheduler a scheduler, or <code>null</code> to use default
         * @return this builder
         */
        public Builder setScheduler(@Nullable Scheduler scheduler) {
            this.scheduler = scheduler;
            return this;
        }

        /**
         * Builds a new {@link GDRouter} with the current state of this builder.
         *
         * @return a new {@link GDRouter}
         */
        public GDRouter build() {
            var limiter = Objects.requireNonNullElse(this.limiter, RequestLimiter.none());
            var baseUrl = Objects.requireNonNullElse(this.baseUrl, GDRequests.BASE_URL);
            var scheduler = Objects.requireNonNullElse(this.scheduler, Schedulers.boundedElastic());
            return new GDRouterImpl(limiter, timeout, baseUrl, scheduler);
        }
    }
}
