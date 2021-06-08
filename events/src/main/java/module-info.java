module jdash.events {

    exports jdash.events;
    exports jdash.events.object;
    exports jdash.events.producer;

    requires reactor.extra;
    requires transitive jdash.client;
    requires static org.immutables.value;
}