package audiostack;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** JUnit 5 regression tests for Playlist. */
class PlaylistTest
{
    private final Song song = new Song("Hey Jude", "Beatles", null);
    private final Podcast podcast = new Podcast("Pilot", "Serial", 1);

    /**
     * Tests constructor and name including the relevant normal or edge case.
     */
    @Test
    void constructorAndName()
    {
        assertEquals("Mix", new Playlist(" Mix ").getName());
        assertThrows(IllegalArgumentException.class, () -> new Playlist(" "));
        assertThrows(IllegalArgumentException.class, () -> new Playlist(null));
    }


    /**
     * Tests size and empty normal and edge including the relevant normal or
     * edge case.
     */
    @Test
    void sizeAndEmptyNormalAndEdge()
    {
        Playlist p = new Playlist("Mix");
        assertEquals(0, p.size());
        assertTrue(p.isEmpty());
        p.addItem(song);
        assertEquals(1, p.size());
        assertFalse(p.isEmpty());
    }


    /**
     * Tests add two types and reject null without mutation including the
     * relevant normal or edge case.
     */
    @Test
    void addTwoTypesAndRejectNullWithoutMutation()
    {
        Playlist p = new Playlist("Mix");
        p.addItem(song);
        p.addItem(podcast);
        assertEquals(2, p.size());
        assertThrows(IllegalArgumentException.class, () -> p.addItem(null));
        assertEquals(2, p.size());
    }


    /**
     * Tests display normal and empty including the relevant normal or edge
     * case.
     */
    @Test
    void displayNormalAndEmpty()
    {
        Playlist p = new Playlist("Mix");
        assertEquals("(empty)", p.toDisplayString());
        p.addItem(song);
        p.addItem(podcast);
        assertEquals(
            "1. [Song] Hey Jude — Beatles" + System.lineSeparator()
                + "2. [Podcast] Pilot — Serial #1",
            p.toDisplayString());
    }


    /**
     * Tests remove normal and bad position including empty including the
     * relevant normal or edge case.
     */
    @Test
    void removeNormalAndBadPositionIncludingEmpty()
    {
        Playlist p = new Playlist("Mix");
        assertThrows(IllegalArgumentException.class, () -> p.removeItemAt(1));
        p.addItem(song);
        p.addItem(podcast);
        assertThrows(IllegalArgumentException.class, () -> p.removeItemAt(0));
        assertThrows(IllegalArgumentException.class, () -> p.removeItemAt(3));
        assertEquals(song, p.removeItemAt(1));
        assertEquals(podcast, p.removeItemAt(1));
        assertTrue(p.isEmpty());
    }


    /**
     * Tests stretch title search normal no match blank and duplicates including
     * the relevant normal or edge case.
     */
    @Test
    void stretchTitleSearchNormalNoMatchBlankAndDuplicates()
    {
        Playlist p = new Playlist("Mix");
        assertEquals("No matches.", p.searchTitle("hey"));
        p.addItem(song);
        p.addItem(podcast);
        p.addItem(song);
        assertEquals(
            "1. [Song] Hey Jude — Beatles" + System.lineSeparator()
                + "3. [Song] Hey Jude — Beatles",
            p.searchTitle(" HEY "));
        assertEquals("No matches.", p.searchTitle("missing"));
        assertThrows(IllegalArgumentException.class, () -> p.searchTitle(" "));
        assertThrows(IllegalArgumentException.class, () -> p.searchTitle(null));
    }


    /**
     * Tests remove position does not remove earlier equal entry including the
     * relevant normal or edge case.
     */
    @Test
    void removePositionDoesNotRemoveEarlierEqualEntry()
    {
        Playlist p = new Playlist("Mix");
        p.addItem(song);
        p.addItem(podcast);
        p.addItem(new Song("hey jude", "beatles", "different"));
        assertEquals(song, p.removeItemAt(3));
        assertEquals(
            "1. [Song] Hey Jude — Beatles" + System.lineSeparator()
                + "2. [Podcast] Pilot — Serial #1",
            p.toDisplayString());
    }
}
