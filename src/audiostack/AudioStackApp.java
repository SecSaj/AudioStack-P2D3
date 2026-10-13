package audiostack;

import java.util.Scanner;

/**
 * Starts the AudioStack playlist application. Creates the playlist, input
 * helper, and menu.
 *
 * @author AudioStack Team
 * @version 2026.10.13
 */
public class AudioStackApp
{

    /**
     * Starts the AudioStack program.
     *
     * @param args
     *            command-line arguments (not used)
     */
    public static void main(String[] args)
    {

        Scanner scanner = new Scanner(System.in);

        Playlist playlist = new Playlist("My Playlist");

        InputHelper input = new InputHelper(scanner);

        PlaylistMenu menu = new PlaylistMenu(playlist, input);

        menu.run();
    }
}
