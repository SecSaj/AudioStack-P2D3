package audiostack;

import java.util.Scanner;
import java.util.NoSuchElementException;

/**
 * Reads keyboard input and asks again after bad input.
 * 
 * @author AudioStack Team
 */
public class InputHelper
{
    private final Scanner scanner;

    /**
     * Creates an input helper.
     * 
     * @param scanner
     *            input source
     * @throws IllegalArgumentException
     *             if scanner is null
     */
    public InputHelper(Scanner scanner)
    {
        if (scanner == null)
        {
            throw new IllegalArgumentException("Scanner cannot be null.");
        }
        this.scanner = scanner;
    }


    /**
     * Reads one trimmed line.
     * 
     * @param prompt
     *            question to display
     * @return typed text without outer spaces
     * @throws NoSuchElementException
     *             if no input remains
     */
    public String readLine(String prompt)
    {
        System.out.print(prompt);
        if (!scanner.hasNextLine())
        {
            throw new NoSuchElementException("End of input");
        }
        return scanner.nextLine().trim();
    }


    /**
     * Keeps asking until a whole number is entered.
     * 
     * @param prompt
     *            question to display
     * @return entered integer
     * @throws NoSuchElementException
     *             if no input remains
     */
    public int readInt(String prompt)
    {
        while (true)
        {
            String text = readLine(prompt);
            try
            {
                return Integer.parseInt(text);
            }
            catch (NumberFormatException error)
            {
                System.out.println("Please enter a whole number.");
            }
        }
    }


    /**
     * Keeps asking until nonblank text is entered.
     * 
     * @param prompt
     *            question to display
     * @return trimmed nonblank text
     * @throws NoSuchElementException
     *             if no input remains
     */
    public String readNonBlank(String prompt)
    {
        while (true)
        {
            String text = readLine(prompt);
            if (text.length() > 0)
            {
                return text;
            }
            System.out.println("This cannot be blank.");
        }
    }
}
