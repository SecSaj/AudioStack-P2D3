package audiostack;

import java.util.NoSuchElementException;

/**
 * Shows the AudioStack menu and carries out user choices.
 * 
 * @author AudioStack Team
 */
public class PlaylistMenu
{
    private Playlist playlist;
    private InputHelper input;

    /**
     * Makes a menu for a playlist.
     * 
     * @param playlist
     *            playlist to change
     * @param input
     *            input helper
     * @throws IllegalArgumentException
     *             if either is null
     */
    public PlaylistMenu(Playlist playlist, InputHelper input)
    {
        if (playlist == null || input == null)
        {
            throw new IllegalArgumentException(
                "Playlist and input cannot be null.");
        }
        this.playlist = playlist;
        this.input = input;
    }


    /** Runs the menu until quitting or reaching the end of input. */
    public void run()
    {
        System.out.println("AudioStack — " + playlist.getName());
        try
        {
            while (true)
            {
                System.out.println(
                    "0 Quit | 1 Add song | 2 Add podcast | 3 View all"
                        + " | 4 Remove at position | 5 Search title");
                int choice = input.readInt("Option: ");
                try
                {
                    if (choice == 0)
                    {
                        System.out.println("Goodbye.");
                        return;
                    }
                    else if (choice == 1)
                    {
                        String title = input.readNonBlank("Title: ");
                        String artist = input.readNonBlank("Artist: ");
                        String album = input.readLine("Album (optional): ");
                        playlist.addItem(new Song(title, artist, album));
                        System.out.println("Added.");
                    }
                    else if (choice == 2)
                    {
                        String title = input.readNonBlank("Title: ");
                        String show = input.readNonBlank("Show: ");
                        int number = input.readInt("Episode number: ");
                        playlist.addItem(new Podcast(title, show, number));
                        System.out.println("Added.");
                    }
                    else if (choice == 3)
                    {
                        System.out.println(playlist.toDisplayString());
                    }
                    else if (choice == 4)
                    {
                        if (playlist.isEmpty())
                        {
                            System.out.println("Nothing to remove.");
                        }
                        else
                        {
                            int position = input.readInt("Remove position: ");
                            System.out.println(
                                "Removed: " + playlist.removeItemAt(position));
                        }
                    }
                    else if (choice == 5)
                    {
                        String text = input.readNonBlank("Search title: ");
                        System.out.println(playlist.searchTitle(text));
                    }
                    else
                    {
                        System.out.println("Unknown option.");
                    }
                }
                catch (IllegalArgumentException error)
                {
                    System.out.println(error.getMessage());
                }
            }
        }
        catch (NoSuchElementException error)
        {
            System.out.println("Goodbye.");
        }
    }
}
