package audiostack;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** JUnit 5 regression tests for LinkedChain. */
class LinkedChainTest
{
    /** Tests reuse three types including the relevant normal or edge case. */
    @Test
    void reuseThreeTypes()
    {
        LinkedChain<String> strings = new LinkedChain<>();
        LinkedChain<Integer> numbers = new LinkedChain<>();
        LinkedChain<MediaItem> media = new LinkedChain<>();
        strings.addToEnd("a");
        numbers.addToEnd(42);
        media.addToEnd(new Song("A", "B", ""));
        assertEquals("a", strings.get(0));
        assertEquals(42, numbers.get(0));
        assertEquals("A", media.get(0).getTitle());
    }


    /**
     * Tests original public api normal and bad cases including the relevant
     * normal or edge case.
     */
    @Test
    void originalPublicApiNormalAndBadCases()
    {
        LinkedChain<String> c = new LinkedChain<>();
        assertTrue(c.isEmpty());
        assertEquals(0, c.size());
        assertNull(c.remove());
        assertArrayEquals(new String[0], c.toArray(new String[0]));
        assertThrows(IndexOutOfBoundsException.class, () -> c.get(0));
        assertThrows(IllegalArgumentException.class, () -> c.add(null));
        assertThrows(IllegalArgumentException.class, () -> c.addToEnd(null));
        assertThrows(IllegalArgumentException.class, () -> c.toArray(null));
        c.addToEnd("A");
        c.addToEnd("B");
        c.addToEnd("A");
        assertEquals(3, c.size());
        assertEquals("A", c.get(0));
        assertEquals(2, c.count("A"));
        assertTrue(c.contains("B"));
        assertEquals(0, c.count(null));
        assertFalse(c.contains("X"));
        assertThrows(
            IllegalArgumentException.class,
            () -> c.toArray(new String[1]));
        assertArrayEquals(
            new String[] { "A", "B", "A" },
            c.toArray(new String[3]));
        assertEquals("[A, B, A]", c.toString());
        assertFalse(c.remove("Z"));
        assertFalse(c.remove(null));
        assertTrue(c.remove("A"));
        assertEquals("B", c.remove());
        c.add("front");
        assertEquals("front", c.get(0));
        c.clear();
        c.clear();
        assertTrue(c.isEmpty());
    }


    /**
     * Tests added position removal and duplicate safety including the relevant
     * normal or edge case.
     */
    @Test
    void addedPositionRemovalAndDuplicateSafety()
    {
        LinkedChain<String> c = new LinkedChain<>();
        assertThrows(IndexOutOfBoundsException.class, () -> c.removeAt(0));
        c.addToEnd("A");
        c.addToEnd("B");
        c.addToEnd("A");
        assertEquals("A", c.removeAt(2));
        assertArrayEquals(new String[] { "A", "B" }, c.toArray(new String[2]));
        assertThrows(IndexOutOfBoundsException.class, () -> c.removeAt(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> c.removeAt(2));
        assertEquals("B", c.removeAt(1));
        assertEquals("A", c.removeAt(0));
        assertTrue(c.isEmpty());
    }
}
