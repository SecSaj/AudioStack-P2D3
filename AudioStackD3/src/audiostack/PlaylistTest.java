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


    /**
     * Tests insertItem at the front and end, plus bad positions and null
     * without changing the playlist.
     */
    @Test
    void insertItemNormalAndBadPositions()
    {
        Playlist p = new Playlist("Mix");
        Song x = new Song("X", "Artist", "");
        p.addItem(song);
        p.addItem(podcast);
        p.insertItem(1, x);
        assertEquals(x, p.getItem(1));
        p.insertItem(4, x);
        assertEquals(x, p.getItem(4));
        assertEquals(4, p.size());
        assertThrows(IllegalArgumentException.class, () -> p.insertItem(0, x));
        assertThrows(IllegalArgumentException.class, () -> p.insertItem(6, x));
        assertThrows(
            IllegalArgumentException.class,
            () -> p.insertItem(1, null));
        assertEquals(4, p.size());
    }


    /**
     * Tests getItem normally, at bad positions, and on an empty playlist.
     */
    @Test
    void getItemNormalAndBadPositions()
    {
        Playlist p = new Playlist("Mix");
        assertThrows(IllegalArgumentException.class, () -> p.getItem(1));
        p.addItem(song);
        p.addItem(podcast);
        assertEquals(podcast, p.getItem(2));
        assertThrows(IllegalArgumentException.class, () -> p.getItem(0));
        assertThrows(IllegalArgumentException.class, () -> p.getItem(3));
    }


    /**
     * Tests removeItem on the first match, a missing item, an empty playlist,
     * and null.
     */
    @Test
    void removeItemNormalMissingEmptyAndNull()
    {
        Playlist p = new Playlist("Mix");
        assertFalse(p.removeItem(song));
        p.addItem(song);
        p.addItem(podcast);
        p.addItem(song);
        assertTrue(p.removeItem(new Song("HEY JUDE", "beatles", "")));
        assertEquals(podcast, p.getItem(1));
        assertEquals(2, p.size());
        assertFalse(p.removeItem(new Song("X", "Y", "")));
        assertThrows(IllegalArgumentException.class, () -> p.removeItem(null));
        assertEquals(2, p.size());
    }


    /**
     * Tests countItem with duplicates, a missing item, an empty playlist, and
     * null.
     */
    @Test
    void countItemNormalMissingEmptyAndNull()
    {
        Playlist p = new Playlist("Mix");
        assertEquals(0, p.countItem(song));
        p.addItem(song);
        p.addItem(podcast);
        p.addItem(song);
        assertEquals(2, p.countItem(song));
        assertEquals(0, p.countItem(new Podcast("Pilot", "Serial", 2)));
        assertThrows(IllegalArgumentException.class, () -> p.countItem(null));
    }


    /**
     * Tests rename normally and with a blank or null name that keeps the old
     * name.
     */
    @Test
    void stretchRenameNormalAndBad()
    {
        Playlist p = new Playlist("Mix");
        p.rename(" Road Trip ");
        assertEquals("Road Trip", p.getName());
        assertThrows(IllegalArgumentException.class, () -> p.rename(" "));
        assertThrows(IllegalArgumentException.class, () -> p.rename(null));
        assertEquals("Road Trip", p.getName());
    }


    /**
     * Tests moveItem forward, backward, onto itself, and with bad positions
     * that leave the playlist unchanged.
     */
    @Test
    void stretchMoveItemNormalAndBadPositions()
    {
        Playlist p = new Playlist("Mix");
        assertThrows(IllegalArgumentException.class, () -> p.moveItem(1, 1));
        Song a = new Song("A", "X", "");
        Song b = new Song("B", "X", "");
        Song c = new Song("C", "X", "");
        p.addItem(a);
        p.addItem(b);
        p.addItem(c);

        // move first to last: [B, C, A]
        p.moveItem(1, 3);
        assertSame(b, p.getItem(1));
        assertSame(c, p.getItem(2));
        assertSame(a, p.getItem(3));

        // move last to first: back to [A, B, C]
        p.moveItem(3, 1);
        assertSame(a, p.getItem(1));
        assertSame(b, p.getItem(2));
        assertSame(c, p.getItem(3));

        // same spot does nothing
        p.moveItem(2, 2);
        assertSame(b, p.getItem(2));

        assertThrows(IllegalArgumentException.class, () -> p.moveItem(0, 1));
        assertThrows(IllegalArgumentException.class, () -> p.moveItem(1, 4));
        assertSame(a, p.getItem(1));
        assertEquals(3, p.size());
    }


    /**
     * Tests searchCreator for songs and podcasts, no match, duplicates, and
     * blank or null queries.
     */
    @Test
    void stretchSearchCreatorNormalNoMatchAndBlank()
    {
        Playlist p = new Playlist("Mix");
        assertEquals("No matches.", p.searchCreator("beat"));
        p.addItem(song);
        p.addItem(podcast);
        p.addItem(song);
        assertEquals(
            "1. [Song] Hey Jude — Beatles" + System.lineSeparator()
                + "3. [Song] Hey Jude — Beatles",
            p.searchCreator(" BEAT "));
        assertEquals(
            "2. [Podcast] Pilot — Serial #1",
            p.searchCreator("serial"));
        assertEquals("No matches.", p.searchCreator("missing"));
        assertThrows(
            IllegalArgumentException.class,
            () -> p.searchCreator(" "));
        assertThrows(
            IllegalArgumentException.class,
            () -> p.searchCreator(null));
    }


    /**
     * Tests shufflePlayOrder lists every item once and never changes the
     * stored order; also tests empty and a null Random.
     */
    @Test
    void stretchShufflePlayOrderKeepsStoredOrder()
    {
        Playlist p = new Playlist("Mix");
        assertEquals("(empty)", p.shufflePlayOrder(new java.util.Random(1)));
        assertThrows(
            IllegalArgumentException.class,
            () -> p.shufflePlayOrder(null));
        p.addItem(new Song("A", "X", ""));
        p.addItem(new Song("B", "X", ""));
        p.addItem(new Song("C", "X", ""));
        String before = p.toDisplayString();

        String order = p.shufflePlayOrder(new java.util.Random(42));
        String[] lines = order.split(System.lineSeparator());
        assertEquals(3, lines.length);
        assertTrue(order.contains("[Song] A — X (position 1)"));
        assertTrue(order.contains("[Song] B — X (position 2)"));
        assertTrue(order.contains("[Song] C — X (position 3)"));
        assertTrue(lines[0].startsWith("1. "));
        assertEquals(before, p.toDisplayString());
    }
}
