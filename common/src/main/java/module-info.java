module jdash.common {

    requires jsr305;

    exports jdash.common;
    exports jdash.common.entity;
    exports jdash.common.internal to jdash.client, jdash.events, jdash.graphics;
}