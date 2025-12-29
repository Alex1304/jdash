@SuppressWarnings("requires-transitive-automatic")
module jdash.client {

    exports jdash.client;
    exports jdash.client.cache;
    exports jdash.client.response;
    exports jdash.client.exception;
    exports jdash.client.request;

    requires io.netty.codec.http;
    requires java.logging;
    requires reactor.extra;
    requires reactor.netty.core;
    requires transitive com.github.benmanes.caffeine;
    requires transitive jdash.common;
    requires transitive org.reactivestreams;
    requires transitive reactor.core;
    requires transitive reactor.netty.http;
    requires transitive org.jspecify;
}