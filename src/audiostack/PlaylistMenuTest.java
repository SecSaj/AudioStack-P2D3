
package audiostack;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** Tests the category-based console menus. */
class PlaylistMenuTest
{
    private String run(String script)
    {
        PrintStream original = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try
        {
            System.setOut(new PrintStream(output));
            new PlaylistMenu(new Playlist("Demo"),
                new InputHelper(new Scanner(script))).run();
        }
        finally
        {
            System.setOut(original);
        }
        return output.toString();
    }


    @Test
    void constructorRejectsNull()
    {
        Playlist playlist = new Playlist("Demo");
        InputHelper input = new InputHelper(new Scanner("0\n"));
        assertDoesNotThrow(() -> new PlaylistMenu(playlist, input));
        assertThrows(IllegalArgumentException.class,
            () -> new PlaylistMenu(null, input));
        assertThrows(IllegalArgumentException.class,
            () -> new PlaylistMenu(playlist, null));
    }


    @Test
    void mainMenuShowsOnlyCategories()
    {
        String output = run("0\n");
        assertTrue(output.contains("1  Add"));
        assertTrue(output.contains("2  View"));
        assertTrue(output.contains("3  Remove"));
        assertTrue(output.contains("4  Search"));
        assertTrue(output.contains("5  Arrange"));
        assertTrue(output.contains("6  Other"));
        assertTrue(output.contains("0  Quit"));
        assertFalse(output.contains("14  Manage playlists"));
    }


    @Test
    void addAndViewSong()
    {
        String output = run("1\n1\nHey Jude\nBeatles\n\n2\n1\n0\n");
        assertTrue(output.contains("Added."));
        assertTrue(output.contains("[Song] Hey Jude"));
        assertTrue(output.contains("Goodbye."));
    }


    @Test
    void addPodcastAndAudiobook()
    {
        String output = run("1\n2\nPilot\nSerial\n1\n"
            + "1\n3\nDune\nFrank Herbert\n1\n2\n1\n0\n");
        assertTrue(output.contains("[Podcast] Pilot"));
        assertTrue(output.contains("[Audiobook] Dune"));
    }


    @Test
    void removeByPosition()
    {
        String output = run("1\n1\nA\nArtist\n\n"
            + "3\n1\n1\n2\n1\n0\n");
        assertTrue(output.contains("Removed: [Song] A"));
        assertTrue(output.contains("(empty)"));
    }


    @Test
    void searchTitleAndCreator()
    {
        String output = run("1\n1\nA\nArtist\n\n"
            + "4\n1\nA\n4\n2\nArtist\n0\n");
        assertTrue(output.contains("SEARCH"));
        assertTrue(output.contains("[Song] A"));
    }


    @Test
    void arrangeAndOtherMenus()
    {
        String output = run("5\n1\n5\n2\n6\n0\n0\n");
        assertTrue(output.contains("Nothing to move."));
        assertTrue(output.contains("Shuffled play order:"));
        assertTrue(output.contains("1  Manage playlists"));
    }


    @Test
    void managePlaylists()
    {
        String output = run("6\n1\n1\nGym\n6\n1\n2\n2\n0\n");
        assertTrue(output.contains("Created."));
        assertTrue(output.contains("Current playlist: Gym"));
    }


    @Test
    void backAndBadOptionsDoNotQuit()
    {
        String output = run("99\n1\n0\n2\n0\n3\n0\n"
            + "4\n0\n5\n0\n6\n0\n0\n");
        assertTrue(output.contains("Unknown option."));
        assertTrue(output.contains("Goodbye."));
    }


    @Test
    void endOfInputExits()
    {
        assertTrue(run("").contains("Goodbye."));
    }
}
