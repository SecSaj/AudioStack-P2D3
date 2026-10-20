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


    /**
     * Tests getCurrentSize normally and after a rejected insert.
     */
    @Test
    void getCurrentSizeNormalAndAfterRejectedInsert()
    {
        LinkedChain<String> c = new LinkedChain<>();
        assertEquals(0, c.getCurrentSize());
        c.addToEnd("A");
        c.addToEnd("B");
        assertEquals(2, c.getCurrentSize());
        assertThrows(IndexOutOfBoundsException.class, () -> c.insert(5, "X"));
        assertEquals(2, c.getCurrentSize());
    }


    /**
     * Tests insert at the front, middle, end, and into an empty chain, plus
     * bad indexes and null.
     */
    @Test
    void insertFrontMiddleEndAndBadCases()
    {
        LinkedChain<String> c = new LinkedChain<>();
        c.insert(0, "B");
        assertEquals("[B]", c.toString());
        c.insert(0, "A");
        c.insert(2, "D");
        c.insert(2, "C");
        assertEquals("[A, B, C, D]", c.toString());
        assertEquals(4, c.size());
        assertThrows(IndexOutOfBoundsException.class, () -> c.insert(5, "X"));
        assertThrows(IndexOutOfBoundsException.class, () -> c.insert(-1, "X"));
        assertThrows(IllegalArgumentException.class, () -> c.insert(0, null));
        assertEquals("[A, B, C, D]", c.toString());
    }


    /**
     * Tests removeEntry on the first match, a missing value, null, and an
     * empty chain.
     */
    @Test
    void removeEntryNormalMissingNullAndEmpty()
    {
        LinkedChain<String> c = new LinkedChain<>();
        assertFalse(c.removeEntry("A"));
        c.addToEnd("A");
        c.addToEnd("B");
        c.addToEnd("A");
        assertTrue(c.removeEntry("A"));
        assertEquals("[B, A]", c.toString());
        assertFalse(c.removeEntry("X"));
        assertFalse(c.removeEntry(null));
        assertEquals(2, c.size());
        assertTrue(c.removeEntry("A"));
        assertTrue(c.removeEntry("B"));
        assertTrue(c.isEmpty());
    }


    /**
     * Tests getFrequencyOf with duplicates, a missing value, null, and an
     * empty chain.
     */
    @Test
    void getFrequencyOfNormalMissingNullAndEmpty()
    {
        LinkedChain<String> c = new LinkedChain<>();
        assertEquals(0, c.getFrequencyOf("A"));
        c.addToEnd("A");
        c.addToEnd("B");
        c.addToEnd("A");
        assertEquals(2, c.getFrequencyOf("A"));
        assertEquals(0, c.getFrequencyOf("X"));
        assertEquals(0, c.getFrequencyOf(null));
    }


    /**
     * Tests the no-argument toArray in order and on an empty chain.
     */
    @Test
    void toArrayObjectNormalAndEmpty()
    {
        LinkedChain<Integer> c = new LinkedChain<>();
        assertEquals(0, c.toArray().length);
        c.addToEnd(1);
        c.addToEnd(2);
        assertArrayEquals(new Object[] { 1, 2 }, c.toArray());
    }


    /**
     * Tests that appending still works after every kind of removal, which
     * only happens if lastNode is kept correct.
     */
    @Test
    void tailStaysCorrectAfterEveryChange()
    {
        LinkedChain<String> c = new LinkedChain<>();
        c.add("A");
        c.addToEnd("B");
        assertEquals("[A, B]", c.toString());
        c.removeAt(1);
        c.addToEnd("C");
        assertEquals("[A, C]", c.toString());
        c.remove("C");
        c.addToEnd("D");
        assertEquals("[A, D]", c.toString());
        c.remove();
        c.remove();
        c.addToEnd("E");
        assertEquals("[E]", c.toString());
        c.insert(1, "F");
        c.removeEntry("F");
        c.addToEnd("G");
        assertEquals("[E, G]", c.toString());
        c.clear();
        c.addToEnd("H");
        assertEquals("[H]", c.toString());
    }
}
