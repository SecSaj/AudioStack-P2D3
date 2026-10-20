package audiostack;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** Tests the audiobook media type and invalid chapter values. */
public class AudiobookTest
{
    /** Checks fields and the printable format. */
    @Test
    public void constructsAudiobook()
    {
        Audiobook book = new Audiobook(" Dune ", " Frank Herbert ", 3);
        assertEquals("Dune", book.getTitle());
        assertEquals("Frank Herbert", book.getAuthor());
        assertEquals(3, book.getChapterNumber());
        assertEquals("Audiobook", book.getType());
        assertEquals("[Audiobook] Dune — Frank Herbert #3", book.toString());
        assertEquals(book.toString(), book.toDetailString());
    }

    /** Checks blank fields and invalid chapter numbers. */
    @Test
    public void rejectsBadAudiobook()
    {
        assertThrows(IllegalArgumentException.class, () -> new Audiobook("", "Author", 1));
        assertThrows(IllegalArgumentException.class, () -> new Audiobook("Book", null, 1));
        assertThrows(IllegalArgumentException.class, () -> new Audiobook("Book", "Author", 0));
        assertThrows(IllegalArgumentException.class, () -> new Audiobook("Book", "Author", -2));
    }

    /** Equal audiobook chapters have equal hashes; different chapters differ. */
    @Test
    public void equalityAndHashCode()
    {
        Audiobook a = new Audiobook("Dune", "Frank Herbert", 1);
        Audiobook b = new Audiobook("dune", "FRANK HERBERT", 1);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, new Audiobook("Dune", "Frank Herbert", 2));
        assertNotEquals(a, null);
        assertNotEquals(a, new Song("Dune", "Frank Herbert", ""));
    }

    /** Stores and retrieves all three actual media types through one chain. */
    @Test
    public void threeMediaTypesShareOneChain()
    {
        LinkedChain<MediaItem> chain = new LinkedChain<MediaItem>();
        chain.addToEnd(new Song("Song", "Artist", ""));
        chain.addToEnd(new Podcast("Episode", "Show", 1));
        chain.addToEnd(new Audiobook("Book", "Author", 2));
        assertEquals("Song", chain.get(0).getType());
        assertEquals("Podcast", chain.get(1).getType());
        assertEquals("Audiobook", chain.get(2).getType());
        assertEquals(3, chain.size());
    }

    /** Verifies audiobook uses normal playlist insertion, search, removal and counting. */
    @Test
    public void audiobookPlaylistOperations()
    {
        Playlist playlist = new Playlist("Mixed");
        Audiobook book = new Audiobook("Dune", "Frank Herbert", 1);
        playlist.insertItem(1, book);
        playlist.addItem(new Song("Track", "Artist", ""));
        playlist.addItem(new Podcast("Talk", "Show", 2));
        assertEquals(book, playlist.getItem(1));
        assertEquals(1, playlist.countItem(new Audiobook("DUNE", "FRANK HERBERT", 1)));
        assertTrue(playlist.searchTitle("dune").contains("Audiobook"));
        assertTrue(playlist.searchCreator("herbert").contains("Audiobook"));
        assertTrue(playlist.removeItem(book));
        assertEquals(2, playlist.size());
        assertEquals(0, playlist.countItem(book));
    }
}
