package audiostack;

import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** JUnit 5 regression tests for PlaylistMenu. */
class PlaylistMenuTest
{
    private String run(String script)
    {
        PrintStream before = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try
        {
            System.setOut(new PrintStream(output));
            new PlaylistMenu(
                new Playlist("Demo"),
                new InputHelper(new Scanner(script))).run();
        }
        finally
        {
            System.setOut(before);
        }
        return output.toString();
    }


    /**
     * Tests constructor normal and bad including the relevant normal or edge
     * case.
     */
    @Test
    void constructorNormalAndBad()
    {
        Playlist p = new Playlist("Demo");
        InputHelper input = new InputHelper(new Scanner("0\n"));
        assertDoesNotThrow(() -> new PlaylistMenu(p, input));
        assertThrows(
            IllegalArgumentException.class,
            () -> new PlaylistMenu(null, input));
        assertThrows(
            IllegalArgumentException.class,
            () -> new PlaylistMenu(p, null));
    }


    /**
     * Tests run add both display search remove and quit including the relevant
     * normal or edge case.
     */
    @Test
    void runAddBothDisplaySearchRemoveAndQuit()
    {
        String out = run(
            "1\nHey Jude\nBeatles\n\n2\nPilot\nSerial\n1\n3\n5\nhey\n4\n1\n3\n0\n");
        assertTrue(out.contains("1. [Song] Hey Jude — Beatles"));
        assertTrue(out.contains("2. [Podcast] Pilot — Serial #1"));
        assertTrue(out.contains("Removed: [Song] Hey Jude — Beatles"));
        assertTrue(out.contains("1. [Podcast] Pilot — Serial #1"));
        assertTrue(out.contains("Goodbye."));
    }


    /**
     * Tests run survives bad inputs including the relevant normal or edge case.
     */
    @Test
    void runSurvivesBadInputs()
    {
        String out = run(
            "x\n9\n4\n2\nEp\nShow\n0\n3\n1\n \nOK\nArtist\n\n4\n99\n5\nzzz\n0\n");
        assertTrue(out.contains("Please enter a whole number."));
        assertTrue(out.contains("Unknown option."));
        assertTrue(out.contains("Nothing to remove."));
        assertTrue(out.contains("Episode number must be at least 1."));
        assertTrue(out.contains("This cannot be blank."));
        assertTrue(out.contains("Position must be between 1 and 1."));
        assertTrue(out.contains("No matches."));
        assertTrue(out.contains("Goodbye."));
        assertTrue(run("").contains("Goodbye."));
    }
}
