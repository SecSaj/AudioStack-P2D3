package audiostack;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** JUnit 5 regression tests for PlaylistLibrary (stretch goal). */
class PlaylistLibraryTest
{
    /**
     * Tests the constructor sets the starting playlist as current and
     * rejects null.
     */
    @Test
    void constructorNormalAndNull()
    {
        Playlist start = new Playlist("Mix");
        PlaylistLibrary lib = new PlaylistLibrary(start);
        assertEquals(1, lib.size());
        assertSame(start, lib.getCurrent());
        assertThrows(
            IllegalArgumentException.class,
            () -> new PlaylistLibrary(null));
    }


    /**
     * Tests createPlaylist adds to the end without switching, and rejects
     * blank and duplicate names.
     */
    @Test
    void createNormalBlankAndDuplicate()
    {
        PlaylistLibrary lib = new PlaylistLibrary(new Playlist("Mix"));
        Playlist gym = lib.createPlaylist(" Gym ");
        assertEquals("Gym", gym.getName());
        assertEquals(2, lib.size());
        assertEquals("Mix", lib.getCurrent().getName());
        assertThrows(
            IllegalArgumentException.class,
            () -> lib.createPlaylist(" "));
        assertThrows(
            IllegalArgumentException.class,
            () -> lib.createPlaylist("gym"));
        assertEquals(2, lib.size());
    }


    /**
     * Tests switchTo normally and with bad positions.
     */
    @Test
    void switchNormalAndBadPositions()
    {
        PlaylistLibrary lib = new PlaylistLibrary(new Playlist("Mix"));
        Playlist gym = lib.createPlaylist("Gym");
        lib.switchTo(2);
        assertSame(gym, lib.getCurrent());
        assertThrows(IllegalArgumentException.class, () -> lib.switchTo(0));
        assertThrows(IllegalArgumentException.class, () -> lib.switchTo(3));
        assertSame(gym, lib.getCurrent());
    }


    /**
     * Tests renameCurrent normally, to its own name in a new case, and with
     * blank or duplicate names.
     */
    @Test
    void renameNormalSameNameAndBad()
    {
        PlaylistLibrary lib = new PlaylistLibrary(new Playlist("Mix"));
        lib.createPlaylist("Gym");
        lib.renameCurrent("Chill");
        assertEquals("Chill", lib.getCurrent().getName());
        lib.renameCurrent("CHILL");
        assertEquals("CHILL", lib.getCurrent().getName());
        assertThrows(
            IllegalArgumentException.class,
            () -> lib.renameCurrent("gym"));
        assertThrows(
            IllegalArgumentException.class,
            () -> lib.renameCurrent(null));
        assertEquals("CHILL", lib.getCurrent().getName());
    }


    /**
     * Tests deletePlaylist before, at, and after the current playlist, plus
     * the last-playlist and bad-position cases.
     */
    @Test
    void deleteKeepsCurrentValidAndRejectsBadCases()
    {
        Playlist a = new Playlist("A");
        PlaylistLibrary lib = new PlaylistLibrary(a);
        assertThrows(
            IllegalArgumentException.class,
            () -> lib.deletePlaylist(1));
        Playlist b = lib.createPlaylist("B");
        Playlist c = lib.createPlaylist("C");
        Playlist d = lib.createPlaylist("D");
        lib.switchTo(3);

        // delete before current: current stays C
        assertSame(a, lib.deletePlaylist(1));
        assertSame(c, lib.getCurrent());

        // delete after current: current stays C
        assertSame(d, lib.deletePlaylist(3));
        assertSame(c, lib.getCurrent());

        // delete current when it is last: new last (B) becomes current
        assertSame(c, lib.deletePlaylist(2));
        assertSame(b, lib.getCurrent());
        assertEquals(1, lib.size());

        lib.createPlaylist("E");
        assertThrows(
            IllegalArgumentException.class,
            () -> lib.deletePlaylist(0));
        assertThrows(
            IllegalArgumentException.class,
            () -> lib.deletePlaylist(3));
        assertEquals(2, lib.size());
    }


    /**
     * Tests the display marks the current playlist and shows item counts.
     */
    @Test
    void displayMarksCurrent()
    {
        Playlist mix = new Playlist("Mix");
        mix.addItem(new Song("A", "B", ""));
        PlaylistLibrary lib = new PlaylistLibrary(mix);
        lib.createPlaylist("Gym");
        assertEquals(
            "1. Mix [1 items] (current)" + System.lineSeparator()
                + "2. Gym [0 items]",
            lib.toDisplayString());
        lib.switchTo(2);
        assertTrue(
            lib.toDisplayString().endsWith("2. Gym [0 items] (current)"));
    }
}
