package jdash.common.entity;

import org.immutables.value.Value;

import java.time.Duration;

@Value.Immutable
public interface GDTimelyInfo {

    long number();

    Duration nextIn();
}
