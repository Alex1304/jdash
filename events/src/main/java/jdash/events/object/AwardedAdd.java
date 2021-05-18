package jdash.events.object;

import jdash.common.entity.GDLevel;
import org.immutables.value.Value;

@Value.Immutable
public interface AwardedAdd {

    @Value.Parameter
    GDLevel addedLevel();
}
