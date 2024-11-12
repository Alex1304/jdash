package jdash.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class SecretRewardChkGeneratorTest {

    @Test
    public void testFixedChkGenerator() {
        assertEquals("ALEX1BAsCDAcD", SecretRewardChkGenerator.fixed("ALEX1", "123456").get());
    }
}