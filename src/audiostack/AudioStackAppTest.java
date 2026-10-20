package audiostack;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

/** JUnit 5 regression tests for AudioStackApp. */
class AudioStackAppTest
{
    private String launch(String input, String[] args)
    {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        try
        {
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            System.setOut(new PrintStream(result));
            AudioStackApp.main(args);
        }
        finally
        {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        return result.toString();
    }


    /**
     * Tests normal startup and quit including the relevant normal or edge case.
     */
    @Test
    void normalStartupAndQuit()
    {
        assertTrue(launch("0\n", new String[0]).contains("AudioStack"));
    }


    /**
     * Tests eof exits and args ignored including the relevant normal or edge
     * case.
     */
    @Test
    void eofExitsAndArgsIgnored()
    {
        assertTrue(launch("", null).contains("Goodbye."));
    }
}
