package audiostack;

/**
 * A playable item with a title and creator. Songs and podcasts both extend this
 * class.
 * 
 * @author AudioStack Team
 */
public abstract class MediaItem
{
    private final String title;
    private final String creator;

    /**
     * Creates a media item.
     * 
     * @param title
     *            item title
     * @param creator
     *            artist or show name
     * @throws IllegalArgumentException
     *             if either is blank or null
     */
    protected MediaItem(String title, String creator)
    {
        this.title = requireText(title, "Title");
        this.creator = requireText(creator, "Creator");
    }


    /**
     * Checks required text and removes spaces at the ends.
     * 
     * @param text
     *            value to check
     * @param field
     *            field name used in errors
     * @return cleaned text
     * @throws IllegalArgumentException
     *             if text is blank or null
     */
    public static String requireText(String text, String field)
    {
        if (text == null || text.trim().length() == 0)
        {
            throw new IllegalArgumentException(field + " cannot be blank.");
        }
        return text.trim();
    }


    /** @return item title */
    public String getTitle()
    {
        return title;
    }


    /** @return artist or show name */
    public String getCreator()
    {
        return creator;
    }


    /** @return the media type */
    public abstract String getType();


    /**
     * Tests whether the item has the same type, title, and creator.
     * 
     * @param other
     *            object to compare
     * @return true when the values match ignoring letter case
     */
    @Override
    public boolean equals(Object other)
    {
        if (other == null || getClass() != other.getClass())
        {
            return false;
        }
        MediaItem item = (MediaItem)other;
        return title.equalsIgnoreCase(item.title)
            && creator.equalsIgnoreCase(item.creator);
    }


    /** @return a hash code matching the equals rule */
    @Override
    public int hashCode()
    {
        return getClass().getName().hashCode()
            + title.toLowerCase().hashCode() * 31
            + creator.toLowerCase().hashCode() * 17;
    }


    /**
     * Returns the media item's display text.
     *
     * @return the formatted media item
     */
    @Override
    public String toString() {
        return "[" + getType() + "] " + title + " — " + creator;
    }


    /**
     * Returns the full details for viewing one item. By default this is the
     * same as toString(); subclasses with extra fields that the short format
     * leaves out (like a song's album) override it.
     *
     * @return the detailed display text
     */
    public String toDetailString()
    {
        return toString();
    }
    
}