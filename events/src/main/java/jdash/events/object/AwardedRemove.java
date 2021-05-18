package jdash.events.object;

import jdash.common.entity.GDLevel;
import org.immutables.value.Value;

@Value.Immutable
public interface AwardedRemove {

    @Value.Parameter
    GDLevel removedLevel();
}
