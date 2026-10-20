package audiostack;

/**
 * Holds every playlist the user has made and remembers which one is in use.
 * Playlists are stored in a LinkedChain, so the same generic chain that holds
 * MediaItem objects is reused here for Playlist objects. (Stretch goal:
 * multiple playlists.)
 *
 * @author AudioStack Team
 */
public class PlaylistLibrary
{
    private LinkedChain<Playlist> playlists;
    private int currentIndex;

    /**
     * Makes a library that starts with one playlist, which becomes the
     * current playlist.
     *
     * @param first
     *            the starting playlist
     * @throws IllegalArgumentException
     *             if first is null
     */
    public PlaylistLibrary(Playlist first)
    {
        if (first == null)
        {
            throw new IllegalArgumentException("Playlist cannot be null.");
        }
        playlists = new LinkedChain<Playlist>();
        playlists.addToEnd(first);
        currentIndex = 0;
    }


    /** @return number of playlists, always at least 1 */
    public int size()
    {
        return playlists.size();
    }


    /** @return the playlist currently in use */
    public Playlist getCurrent()
    {
        return playlists.get(currentIndex);
    }


    /**
     * Makes a new empty playlist and adds it to the end of the library. The
     * current playlist does not change.
     *
     * @param name
     *            name for the new playlist
     * @return the new playlist
     * @throws IllegalArgumentException
     *             if name is blank, null, or already used (ignoring case)
     */
    public Playlist createPlaylist(String name)
    {
        // the Playlist constructor rejects blank or null names
        Playlist playlist = new Playlist(name);
        requireUnusedName(playlist.getName(), -1);
        playlists.addToEnd(playlist);
        return playlist;
    }


    /**
     * Makes the playlist at a 1-based position the current one.
     *
     * @param position
     *            1-based position, 1 through size()
     * @throws IllegalArgumentException
     *             if position is invalid
     */
    public void switchTo(int position)
    {
        checkPosition(position);
        currentIndex = position - 1;
    }


    /**
     * Renames the current playlist.
     *
     * @param newName
     *            new name for the current playlist
     * @throws IllegalArgumentException
     *             if newName is blank, null, or used by another playlist
     */
    public void renameCurrent(String newName)
    {
        // check blank first so the duplicate check gets a clean name
        String cleaned = MediaItem.requireText(newName, "Playlist name");
        requireUnusedName(cleaned, currentIndex);
        getCurrent().rename(cleaned);
    }


    /**
     * Deletes the playlist at a 1-based position. The last remaining playlist
     * cannot be deleted. If the current playlist is deleted, the one that
     * slides into its spot (or the new last one) becomes current.
     *
     * @param position
     *            1-based position, 1 through size()
     * @return the deleted playlist
     * @throws IllegalArgumentException
     *             if position is invalid or only one playlist is left
     */
    public Playlist deletePlaylist(int position)
    {
        if (playlists.size() == 1)
        {
            throw new IllegalArgumentException(
                "Cannot delete the only playlist.");
        }
        checkPosition(position);

        int index = position - 1;
        Playlist removed = playlists.removeAt(index);

        // keep currentIndex pointing at the same playlist, or a valid one
        if (index < currentIndex)
        {
            currentIndex--;
        }
        else if (currentIndex >= playlists.size())
        {
            currentIndex = playlists.size() - 1;
        }
        return removed;
    }


    /**
     * Returns a numbered list of playlists with their item counts. The
     * current playlist is marked with "(current)".
     *
     * @return the formatted list of playlists
     */
    public String toDisplayString()
    {
        String result = "";
        for (int i = 0; i < playlists.size(); i++)
        {
            Playlist playlist = playlists.get(i);
            if (i > 0)
            {
                result += System.lineSeparator();
            }
            result += (i + 1) + ". " + playlist.getName() + " ["
                + playlist.size() + " items]";
            if (i == currentIndex)
            {
                result += " (current)";
            }
        }
        return result;
    }


    /**
     * Rejects a 1-based position outside 1 through size().
     *
     * @param position
     *            position to check
     * @throws IllegalArgumentException
     *             if position is invalid
     */
    private void checkPosition(int position)
    {
        if (position < 1 || position > playlists.size())
        {
            throw new IllegalArgumentException(
                "Position must be between 1 and " + playlists.size() + ".");
        }
    }


    /**
     * Rejects a name that another playlist already uses, ignoring case.
     *
     * @param name
     *            trimmed name to check
     * @param skipIndex
     *            0-based index to ignore (the playlist being renamed), or -1
     * @throws IllegalArgumentException
     *             if another playlist has the name
     */
    private void requireUnusedName(String name, int skipIndex)
    {
        for (int i = 0; i < playlists.size(); i++)
        {
            if (i != skipIndex
                && playlists.get(i).getName().equalsIgnoreCase(name))
            {
                throw new IllegalArgumentException(
                    "A playlist named " + name + " already exists.");
            }
        }
    }
}
