
package audiostack;

import java.util.NoSuchElementException;
import java.util.Random;

/** Displays category menus and handles playlist commands. */
public class PlaylistMenu
{
    private Playlist playlist;
    private final InputHelper input;
    private final PlaylistLibrary library;
    private final Random random;

    public PlaylistMenu(Playlist playlist, InputHelper input)
    {
        if (playlist == null || input == null)
        {
            throw new IllegalArgumentException(
                "Playlist and input cannot be null.");
        }
        this.playlist = playlist;
        this.input = input;
        library = new PlaylistLibrary(playlist);
        random = new Random();
    }


    /** Keeps returning to the main menu until the user quits. */
    public void run()
    {
        System.out.println("AudioStack — " + playlist.getName());
        try
        {
            while (true)
            {
                printMenu();
                int choice = input.readInt("Option: ");
                if (choice == 0)
                {
                    System.out.println("Goodbye.");
                    return;
                }
                try
                {
                    switch (choice)
                    {
                        case 1:
                            addMenu();
                            break;
                        case 2:
                            viewMenu();
                            break;
                        case 3:
                            removeMenu();
                            break;
                        case 4:
                            searchMenu();
                            break;
                        case 5:
                            arrangeMenu();
                            break;
                        case 6:
                            otherMenu();
                            break;
                        default:
                            System.out.println("Unknown option.");
                            break;
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


    private void printMenu()
    {
        int count = playlist.size();
        String items = count == 1 ? "1 item" : count + " items";
        System.out.println();
        System.out.println("===== " + playlist.getName()
            + " (" + items + ") =====");
        System.out.println("1  Add");
        System.out.println("2  View");
        System.out.println("3  Remove");
        System.out.println("4  Search");
        System.out.println("5  Arrange");
        System.out.println("6  Other");
        System.out.println("0  Quit");
    }


    private void addMenu()
    {
        System.out.println("\nADD");
        System.out.println("1  Song");
        System.out.println("2  Podcast");
        System.out.println("3  Audiobook");
        System.out.println("4  Insert at position");
        System.out.println("0  Back");
        int choice = input.readInt("Add option: ");
        if (choice == 0)
        {
            return;
        }
        if (choice == 1)
        {
            playlist.addItem(readSong());
            System.out.println("Added.");
        }
        else if (choice == 2)
        {
            playlist.addItem(readPodcast());
            System.out.println("Added.");
        }
        else if (choice == 3)
        {
            playlist.addItem(readAudiobook());
            System.out.println("Added.");
        }
        else if (choice == 4)
        {
            MediaItem item = readMediaItem();
            int position = input.readInt("Insert position: ");
            playlist.insertItem(position, item);
            System.out.println("Inserted.");
        }
        else
        {
            System.out.println("Unknown option.");
        }
    }


    private void viewMenu()
    {
        System.out.println("\nVIEW");
        System.out.println("1  View all");
        System.out.println("2  View at position");
        System.out.println("3  Count matching item");
        System.out.println("0  Back");
        int choice = input.readInt("View option: ");
        if (choice == 1)
        {
            System.out.println(playlist.toDisplayString());
        }
        else if (choice == 2)
        {
            int position = input.readInt("View position: ");
            System.out.println(playlist.getItem(position).toDetailString());
        }
        else if (choice == 3)
        {
            MediaItem item = readMediaItem();
            System.out.println("Count: " + playlist.countItem(item));
        }
        else if (choice != 0)
        {
            System.out.println("Unknown option.");
        }
    }


    private void removeMenu()
    {
        System.out.println("\nREMOVE");
        System.out.println("1  Remove at position");
        System.out.println("2  Remove matching item");
        System.out.println("0  Back");
        int choice = input.readInt("Remove option: ");
        if (choice == 1)
        {
            if (playlist.isEmpty())
            {
                System.out.println("Nothing to remove.");
            }
            else
            {
                int position = input.readInt("Remove position: ");
                System.out.println("Removed: "
                    + playlist.removeItemAt(position));
            }
        }
        else if (choice == 2)
        {
            MediaItem item = readMediaItem();
            if (playlist.removeItem(item))
            {
                System.out.println("Removed: " + item);
            }
            else
            {
                System.out.println("Not in the playlist.");
            }
        }
        else if (choice != 0)
        {
            System.out.println("Unknown option.");
        }
    }


    private void searchMenu()
    {
        System.out.println("\nSEARCH");
        System.out.println("1  By title");
        System.out.println("2  By artist/show/author");
        System.out.println("0  Back");
        int choice = input.readInt("Search option: ");
        if (choice == 1)
        {
            System.out.println(playlist.searchTitle(
                input.readNonBlank("Search title: ")));
        }
        else if (choice == 2)
        {
            System.out.println(playlist.searchCreator(
                input.readNonBlank("Search creator: ")));
        }
        else if (choice != 0)
        {
            System.out.println("Unknown option.");
        }
    }


    private void arrangeMenu()
    {
        System.out.println("\nARRANGE");
        System.out.println("1  Move item");
        System.out.println("2  Shuffle play order");
        System.out.println("0  Back");
        int choice = input.readInt("Arrange option: ");
        if (choice == 1)
        {
            if (playlist.isEmpty())
            {
                System.out.println("Nothing to move.");
            }
            else
            {
                int from = input.readInt("Move from position: ");
                int to = input.readInt("Move to position: ");
                playlist.moveItem(from, to);
                System.out.println("Moved.");
            }
        }
        else if (choice == 2)
        {
            System.out.println("Shuffled play order:");
            System.out.println(playlist.shufflePlayOrder(random));
        }
        else if (choice != 0)
        {
            System.out.println("Unknown option.");
        }
    }


    private void otherMenu()
    {
        System.out.println("\nOTHER");
        System.out.println("1  Manage playlists");
        System.out.println("0  Back");
        int choice = input.readInt("Other option: ");
        if (choice == 1)
        {
            managePlaylists();
        }
        else if (choice != 0)
        {
            System.out.println("Unknown option.");
        }
    }


    private Song readSong()
    {
        String title = input.readNonBlank("Title: ");
        String artist = input.readNonBlank("Artist: ");
        String album = input.readLine("Album (optional): ");
        return new Song(title, artist, album);
    }


    private Podcast readPodcast()
    {
        String title = input.readNonBlank("Title: ");
        String show = input.readNonBlank("Show: ");
        int number = input.readInt("Episode number: ");
        return new Podcast(title, show, number);
    }


    private Audiobook readAudiobook()
    {
        String title = input.readNonBlank("Title: ");
        String author = input.readNonBlank("Author: ");
        int chapter = input.readInt("Chapter number: ");
        return new Audiobook(title, author, chapter);
    }


    /**
     * Shows every playlist and lets the user create, switch, rename, or
     * delete one. After any change the menu's current playlist is updated to
     * match the library. (Stretch goal: multiple playlists.)
     *
     * @throws IllegalArgumentException
     *             if a name or position breaks a library rule; run() prints
     *             the message
     */
    private void managePlaylists()
    {
        System.out.println(library.toDisplayString());
        System.out.println(
            "0 Back | 1 Create | 2 Switch | 3 Rename current | 4 Delete");
        int choice = input.readInt("Playlist option: ");
        if (choice == 0)
        {
            return;
        }
        else if (choice == 1)
        {
            String name = input.readNonBlank("New playlist name: ");
            library.createPlaylist(name);
            System.out.println("Created.");
        }
        else if (choice == 2)
        {
            library.switchTo(input.readInt("Switch to position: "));
        }
        else if (choice == 3)
        {
            library.renameCurrent(input.readNonBlank("New name: "));
            System.out.println("Renamed.");
        }
        else if (choice == 4)
        {
            int position = input.readInt("Delete position: ");
            System.out.println(
                "Deleted: " + library.deletePlaylist(position).getName());
        }
        else
        {
            System.out.println("Unknown option.");
            return;
        }

        // Keep the menu working on the current playlist.
        playlist = library.getCurrent();
        System.out.println("Current playlist: " + playlist.getName());
    }


    /** Reads the type and details of one media item. */
    private MediaItem readMediaItem()
    {
        int type = input.readInt("Type (1 Song, 2 Podcast, 3 Audiobook): ");
        if (type == 1)
        {
            return readSong();
        }
        else if (type == 2)
        {
            return readPodcast();
        }
        else if (type == 3)
        {
            return readAudiobook();
        }
        throw new IllegalArgumentException("Type must be 1, 2, or 3.");
    }
}
