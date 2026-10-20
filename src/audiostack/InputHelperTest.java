package audiostack;

import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/** JUnit 5 regression tests for InputHelper. */
class InputHelperTest
{
    /**
     * Tests constructor and read line including the relevant normal or edge
     * case.
     */
    @Test
    void constructorAndReadLine()
    {
        InputHelper i = new InputHelper(new Scanner(" hi \n"));
        assertEquals("hi", i.readLine("Prompt: "));
        assertThrows(
            NoSuchElementException.class,
            () -> i.readLine("Prompt: "));
        assertThrows(
            IllegalArgumentException.class,
            () -> new InputHelper(null));
    }


    /**
     * Tests re prompt integers and blank text including the relevant normal or
     * edge case.
     */
    @Test
    void rePromptIntegersAndBlankText()
    {
        InputHelper i = new InputHelper(new Scanner("abc\n\n7\n \n\nword\n"));
        assertEquals(7, i.readInt("Number: "));
        assertEquals("word", i.readNonBlank("Text: "));
    }


    /**
     * Tests correct input and eof in retry including the relevant normal or
     * edge case.
     */
    @Test
    void correctInputAndEofInRetry()
    {
        assertEquals(
            -4,
            new InputHelper(new Scanner("-4\n")).readInt("Number: "));
        assertEquals(
            "ok",
            new InputHelper(new Scanner("ok\n")).readNonBlank("Text: "));
        assertThrows(
            NoSuchElementException.class,
            () -> new InputHelper(new Scanner("oops\n")).readInt("Number: "));
        assertThrows(
            NoSuchElementException.class,
            () -> new InputHelper(new Scanner(" \n")).readNonBlank("Text: "));
    }
}
