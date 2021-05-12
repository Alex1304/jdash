package jdash.common.entity;

import org.immutables.value.Value;

@Value.Immutable
public interface GDPrivateMessageDownload extends GDPrivateMessage {

    String body();
}
