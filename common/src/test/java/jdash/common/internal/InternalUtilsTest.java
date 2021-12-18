package jdash.common.internal;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static jdash.common.internal.InternalUtils.structureCreatorsInfo;
import static org.junit.jupiter.api.Assertions.*;

public final class InternalUtilsTest {

    @Test
    public void structureCreatorsInfoTest() {
        // Invalid cases, should always return empty maps
        assertEquals(Map.of(), structureCreatorsInfo(""), "empty string");
        assertEquals(Map.of(), structureCreatorsInfo("quizgf5410qz!!???"), "invalid string");
        assertEquals(Map.of(), structureCreatorsInfo("123:abc"), "missing part");
        assertEquals(Map.of(), structureCreatorsInfo("WWWW:lololol:QAA&1&&"), "invalid parts");
        assertEquals(Map.of(), structureCreatorsInfo("::"), "empty parts");
        assertEquals(Map.of(), structureCreatorsInfo("123::123"), "empty name");
        assertEquals(Map.of(), structureCreatorsInfo("123:abc:123|AAA:AAA:AAA"), "one correct and one incorrect");
        assertEquals(Map.of(), structureCreatorsInfo("123:abc:123:abc"), "extra part");

        // Valid cases
        assertEquals(Map.of(123L, "abc"), structureCreatorsInfo("123:abc:789"), "regular case");
        assertEquals(Map.of(123L, "a b c"), structureCreatorsInfo("123:a b c:123"), "contains whitespaces");
        assertEquals(Map.of(123L, "  "), structureCreatorsInfo("123:  :123"), "only whitespaces");
        assertEquals(Map.of(123L, "a!b&c"), structureCreatorsInfo("123:a!b&c:123"), "contains symbols");
        assertEquals(Map.of(123L, "456"), structureCreatorsInfo("123:456:789"), "only numbers");
    }
}