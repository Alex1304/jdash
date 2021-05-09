module jdash {
    exports jdash.client.cache;
    exports jdash.client;
    exports jdash.common;
    exports jdash.entity;
    exports jdash.exception;
    exports jdash.graphics;
    exports jdash.client.request;

    requires org.apache.commons.lang3;
    requires org.apache.commons.configuration2;
    requires io.netty.codec.http;
    requires transitive com.github.benmanes.caffeine;
    requires transitive org.reactivestreams;
    requires transitive reactor.core;
    requires transitive reactor.netty.http;
    requires transitive java.desktop;
    requires static com.google.errorprone.annotations;
    requires static org.immutables.value;
}