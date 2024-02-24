package jdash.graphics.internal;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutablePlayerColor.class)
public interface PlayerColor {

    int r();

    int g();

    int b();
}
