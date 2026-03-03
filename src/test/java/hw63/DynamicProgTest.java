package hw63;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DynamicProgTest {

    @Test
    void testSimpleCase() {
        assertEquals("ABC", DynamicProg.getMaxSameString("ABC", "ABC"));
    }

    @Test
    void testNoCommonSubsequence() {
        assertEquals("", DynamicProg.getMaxSameString("ABC", "DEF"));
    }

    @Test
    void testPartialMatch() {
        String result = DynamicProg.getMaxSameString("ABCBDAB", "BDCABA");
        assertEquals(4, result.length());
    }

    @Test
    void testOneEmptyString() {
        assertEquals("", DynamicProg.getMaxSameString("", "ABC"));
        assertEquals("", DynamicProg.getMaxSameString("ABC", ""));
    }

    @Test
    void testSingleCharacter() {
        assertEquals("A", DynamicProg.getMaxSameString("A", "A"));
        assertEquals("", DynamicProg.getMaxSameString("A", "B"));
    }
}
