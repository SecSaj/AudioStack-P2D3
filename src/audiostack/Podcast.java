package audiostack;

/**
 * One episode of a podcast show.
 * 
 * @author AudioStack Team
 */
public class Podcast
    extends MediaItem
{
    private int episodeNumber;

    /**
     * Creates a podcast episode.
     * 
     * @param title
     *            episode title
     * @param show
     *            show name
     * @param episodeNumber
     *            episode number, at least 1
     * @throws IllegalArgumentException
     *             for invalid values
     */
    public Podcast(String title, String show, int episodeNumber)
    {
        super(title, show);
        if (episodeNumber < 1)
        {
            throw new IllegalArgumentException(
                "Episode number must be at least 1.");
        }
        this.episodeNumber = episodeNumber;
    }


    /** @return podcast show name */
    public String getShowName()
    {
        return getCreator();
    }


    /** @return episode number */
    public int getEpisodeNumber()
    {
        return episodeNumber;
    }


    /** @return the text Podcast */
    @Override
    public String getType()
    {
        return "Podcast";
    }


    /**
     * Checks show, title and episode number.
     * 
     * @param other
     *            object to compare
     * @return true when they are the same episode
     */
    @Override
    public boolean equals(Object other)
    {
        if (!super.equals(other))
        {
            return false;
        }
        Podcast podcast = (Podcast)other;
        return episodeNumber == podcast.episodeNumber;
    }


    /** @return hash code that also includes episode number */
    @Override
    public int hashCode()
    {
        return super.hashCode() * 31 + episodeNumber;
    }


    /** @return formatted podcast details */
    @Override
    public String toString()
    {
        return super.toString() + " #" + episodeNumber;
    }
}
