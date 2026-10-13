package audiostack;

/**
 * Stores media in a LinkedChain and applies playlist rules.
 * 
 * @author AudioStack Team
 */
public class Playlist
{
    private String name;
    private LinkedChain<MediaItem> items;

    /**
     * Makes an empty playlist.
     * 
     * @param name
     *            playlist name
     * @throws IllegalArgumentException
     *             if name is blank or null
     */
    public Playlist(String name)
    {
        this.name = MediaItem.requireText(name, "Playlist name");
        items = new LinkedChain<MediaItem>();
    }


    /** @return playlist name */
    public String getName()
    {
        return name;
    }


    /** @return number of items */
    public int size()
    {
        return items.size();
    }


    /** @return true if the playlist is empty */
    public boolean isEmpty()
    {
        return items.isEmpty();
    }


    /**
     * Adds an item to the end of the playlist.
     * 
     * @param item
     *            song or podcast to add
     * @throws IllegalArgumentException
     *             if item is null
     */
    public void addItem(MediaItem item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        items.addToEnd(item);
    }


    /**
     * Removes and returns an item at a specific position.
     *
     * @param position
     *            the 1-based playlist position
     * @return the removed media item
     * @throws IllegalArgumentException
     *             if position is invalid
     */
    public MediaItem removeItemAt(int position)
    {
        if (position < 1 || position > items.size())
        {
            throw new IllegalArgumentException(
                "Position must be between 1 and " + items.size() + ".");
        }

        return items.removeAt(position - 1);
    }


    /**
     * Returns a numbered list of playlist items.
     *
     * @return the formatted playlist
     */
    public String toDisplayString() {
        String result = "";

        for (int i = 0; i < items.size(); i++) {
            if (i > 0) {
                result += System.lineSeparator();
            }

            result += (i + 1) + ". " + items.get(i).toString();
        }

        if (items.isEmpty()) {
            return "(empty)";
        }

        return result;
    }

    /**
     * Searches the playlist by title, ignoring capitalization.
     *
     * @param query the title to search for
     * @return the matching playlist items
     */
    public String searchTitle(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Search title cannot be blank.");
        }

        String result = "";
        String search = query.trim().toLowerCase();

        for (int i = 0; i < items.size(); i++) {
            MediaItem item = items.get(i);

            if (item.getTitle().toLowerCase().contains(search)) {
                if (!result.isEmpty()) {
                    result += System.lineSeparator();
                }

                result += (i + 1) + ". " + item.toString();
            }
        }

        if (result.isEmpty()) {
            return "No matches.";
        }

        return result;
    }
 }
