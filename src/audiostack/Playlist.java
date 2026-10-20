package audiostack;

import java.util.Random;

/**
 * Stores media in a LinkedChain and applies playlist rules.
 * 
 * @author AudioStack Team
 */
public class Playlist
{
    private String name;
    private final LinkedChain<MediaItem> items;

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


    /**
     * Changes the playlist name. (Stretch goal: multiple playlists.)
     *
     * @param newName
     *            the new playlist name
     * @throws IllegalArgumentException
     *             if newName is blank or null; the old name is kept
     */
    public void rename(String newName)
    {
        name = MediaItem.requireText(newName, "Playlist name");
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
     * Inserts an item at a 1-based position. Position size() + 1 adds it to
     * the end. Items at or after the position move back one spot.
     *
     * @param position
     *            the 1-based position, 1 through size() + 1
     * @param item
     *            song or podcast to insert
     * @throws IllegalArgumentException
     *             if item is null or position is invalid
     */
    public void insertItem(int position, MediaItem item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        if (position < 1 || position > items.size() + 1)
        {
            throw new IllegalArgumentException(
                "Position must be between 1 and " + (items.size() + 1) + ".");
        }

        // convert the user's 1-based position to the chain's 0-based index
        items.insert(position - 1, item);
    }


    /**
     * Gets the item at a 1-based position without removing it.
     *
     * @param position
     *            the 1-based position, 1 through size()
     * @return the media item at that position
     * @throws IllegalArgumentException
     *             if position is invalid, including on an empty playlist
     */
    public MediaItem getItem(int position)
    {
        if (position < 1 || position > items.size())
        {
            throw new IllegalArgumentException(
                "Position must be between 1 and " + items.size() + ".");
        }

        return items.get(position - 1);
    }


    /**
     * Removes the first item equal to the given one. Equality follows
     * MediaItem.equals, so case does not matter.
     *
     * @param item
     *            song or podcast to remove
     * @return true if an item was removed; false if absent or empty
     * @throws IllegalArgumentException
     *             if item is null
     */
    public boolean removeItem(MediaItem item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException("Item cannot be null.");
        }

        return items.removeEntry(item);
    }


    /**
     * Counts how many items in the playlist equal the given one.
     *
     * @param item
     *            song or podcast to count
     * @return number of matches; 0 if absent or empty
     * @throws IllegalArgumentException
     *             if item is null
     */
    public int countItem(MediaItem item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException("Item cannot be null.");
        }

        return items.getFrequencyOf(item);
    }


    /**
     * Moves an existing item from one 1-based position to another without
     * deleting or remaking it. After the move, the item sits at toPosition and
     * the items between the two positions shift by one. (Stretch goal:
     * move/reorder.)
     *
     * @param fromPosition
     *            the item's current 1-based position, 1 through size()
     * @param toPosition
     *            the 1-based position it should end up at, 1 through size()
     * @throws IllegalArgumentException
     *             if either position is invalid; the playlist is unchanged
     */
    public void moveItem(int fromPosition, int toPosition)
    {
        // check both positions before changing anything
        if (fromPosition < 1 || fromPosition > items.size()
            || toPosition < 1 || toPosition > items.size())
        {
            throw new IllegalArgumentException(
                "Position must be between 1 and " + items.size() + ".");
        }

        // take the same object out and put it back at the new spot
        MediaItem item = items.removeAt(fromPosition - 1);
        items.insert(toPosition - 1, item);
    }


    /**
     * Searches the playlist by artist or show name, ignoring capitalization.
     * (Extra stretch goal: search by artist/creator.)
     *
     * @param query
     *            the artist or show name to search for
     * @return the matching playlist items with their positions, or
     *         "No matches."
     * @throws IllegalArgumentException
     *             if query is blank or null
     */
    public String searchCreator(String query)
    {
        if (query == null || query.trim().isEmpty())
        {
            throw new IllegalArgumentException(
                "Search creator cannot be blank.");
        }

        String search = query.trim().toLowerCase();
        Object[] entries = items.toArray();
        String result = "";
        for (int i = 0; i < entries.length; i++)
        {
            MediaItem item = (MediaItem)entries[i];
            if (item.getCreator().toLowerCase().contains(search))
            {
                if (!result.isEmpty())
                {
                    result += System.lineSeparator();
                }
                result += (i + 1) + ". " + item;
            }
        }
        return result.isEmpty() ? "No matches." : result;
    }

    /**
     * Builds a random listening order without changing the stored playlist
     * order. Each line also shows the item's real playlist position. (Stretch
     * goal: shuffle.)
     *
     * @param random
     *            random number source; pass a seeded Random for repeatable
     *            tests
     * @return the shuffled play order, or "(empty)" if there are no items
     * @throws IllegalArgumentException
     *             if random is null
     */
    public String shufflePlayOrder(Random random)
    {
        if (random == null)
        {
            throw new IllegalArgumentException("Random cannot be null.");
        }
        if (items.isEmpty())
        {
            return "(empty)";
        }

        // copy the items so the chain itself is never reordered
        Object[] entries = items.toArray();

        // order[i] holds the 0-based playlist index played i-th
        int[] order = new int[entries.length];
        for (int i = 0; i < order.length; i++)
        {
            order[i] = i;
        }

        // Fisher-Yates shuffle: swap each spot with a random earlier one
        for (int i = order.length - 1; i > 0; i--)
        {
            int j = random.nextInt(i + 1);
            int temp = order[i];
            order[i] = order[j];
            order[j] = temp;
        }

        String result = "";
        for (int i = 0; i < order.length; i++)
        {
            if (i > 0)
            {
                result += System.lineSeparator();
            }
            result += (i + 1) + ". " + entries[order[i]]
                + " (position " + (order[i] + 1) + ")";
        }
        return result;
    }


    /**
     * Returns a numbered list of playlist items.
     *
     * @return the formatted playlist
     */
    public String toDisplayString() {
        if (items.isEmpty()) {
            return "(empty)";
        }

        // one O(n) pass instead of calling get(i), which walks each time
        Object[] entries = items.toArray();
        String result = "";

        for (int i = 0; i < entries.length; i++) {
            if (i > 0) {
                result += System.lineSeparator();
            }

            result += (i + 1) + ". " + entries[i].toString();
        }

        return result;
    }

    /**
     * Searches the playlist by title, ignoring capitalization.
     *
     * @param query the title to search for
     * @return the matching playlist items
     */
    public String searchTitle(String query)
    {
        if (query == null || query.trim().isEmpty())
        {
            throw new IllegalArgumentException(
                "Search title cannot be blank.");
        }

        String search = query.trim().toLowerCase();
        Object[] entries = items.toArray();
        String result = "";
        for (int i = 0; i < entries.length; i++)
        {
            MediaItem item = (MediaItem)entries[i];
            if (item.getTitle().toLowerCase().contains(search))
            {
                if (!result.isEmpty())
                {
                    result += System.lineSeparator();
                }
                result += (i + 1) + ". " + item;
            }
        }
        return result.isEmpty() ? "No matches." : result;
    }
}
