package jdash.events.object;

import jdash.common.entity.GDLevel;
import org.immutables.value.Value;

@Value.Immutable
public interface AwardedUpdate {

    @Value.Parameter
    GDLevel oldData();

    @Value.Parameter
    GDLevel newData();
}
