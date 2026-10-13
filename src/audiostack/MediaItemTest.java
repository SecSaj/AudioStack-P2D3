package audiostack;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** JUnit 5 regression tests for MediaItem. */
class MediaItemTest
{
    /**
     * Tests song constructor accessors and defaults including the relevant
     * normal or edge case.
     */
    @Test
    void songConstructorAccessorsAndDefaults()
    {
        Song song = new Song(" Hey Jude ", " The Beatles ", " Abbey Road ");
        assertEquals("Hey Jude", song.getTitle());
        assertEquals("The Beatles", song.getCreator());
        assertEquals(song.getCreator(), song.getArtist());
        assertEquals("Abbey Road", song.getAlbum());
        assertEquals("Song", song.getType());
        assertEquals("[Song] Hey Jude — The Beatles", song.toString());
        Song blankAlbum = new Song("A", "B", null);
        assertEquals("", blankAlbum.getAlbum());
        assertEquals("[Song] A — B", blankAlbum.toString());
        assertThrows(
            IllegalArgumentException.class,
            () -> new Song(" ", "B", ""));
        assertThrows(
            IllegalArgumentException.class,
            () -> new Song("A", null, ""));
    }


    /**
     * Tests podcast constructor accessors and invalid episode including the
     * relevant normal or edge case.
     */
    @Test
    void podcastConstructorAccessorsAndInvalidEpisode()
    {
        Podcast p = new Podcast(" Pilot ", " Serial ", 12);
        assertEquals("Pilot", p.getTitle());
        assertEquals("Serial", p.getCreator());
        assertEquals(p.getCreator(), p.getShowName());
        assertEquals(12, p.getEpisodeNumber());
        assertEquals("Podcast", p.getType());
        assertEquals("[Podcast] Pilot — Serial #12", p.toString());
        assertEquals(1, new Podcast("A", "B", 1).getEpisodeNumber());
        assertThrows(
            IllegalArgumentException.class,
            () -> new Podcast("A", "B", 0));
        assertThrows(
            IllegalArgumentException.class,
            () -> new Podcast(null, "B", 1));
    }


    /**
     * Tests equality hash codes and wrong type including the relevant normal or
     * edge case.
     */
    @Test
    void equalityHashCodesAndWrongType()
    {
        Song a = new Song("hey jude", "BEATLES", "A");
        Song b = new Song("Hey Jude", "Beatles", "B");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, null);
        assertNotEquals(a, "song");
        assertNotEquals(a, new Podcast("Hey Jude", "Beatles", 1));
        Podcast p = new Podcast("Episode", "Show", 1);
        Podcast q = new Podcast("episode", "show", 1);
        assertEquals(p, q);
        assertEquals(p.hashCode(), q.hashCode());
        assertNotEquals(p, new Podcast("Episode", "Show", 2));
        assertNotEquals(a, new Song("Other", "Beatles", ""));
    }
}
