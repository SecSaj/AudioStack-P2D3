package audiostack;

/**
 * An audiobook chapter in an AudioStack playlist.
 * Audiobooks share MediaItem storage with songs and podcasts.
 *
 * @author AudioStack Team (GenAI-assisted draft)
 */
public class Audiobook extends MediaItem
{
    private final int chapterNumber;

    /**
     * Creates an audiobook chapter.
     *
     * @param title audiobook title
     * @param author book author
     * @param chapterNumber chapter number, at least 1
     * @throws IllegalArgumentException if title/author is blank or chapter is invalid
     */
    public Audiobook(String title, String author, int chapterNumber)
    {
        super(title, author);
        if (chapterNumber < 1)
        {
            throw new IllegalArgumentException("Chapter number must be at least 1.");
        }
        this.chapterNumber = chapterNumber;
    }

    /** @return audiobook author */
    public String getAuthor()
    {
        return getCreator();
    }

    /** @return the chapter number */
    public int getChapterNumber()
    {
        return chapterNumber;
    }

    /** @return the media type name */
    @Override
    public String getType()
    {
        return "Audiobook";
    }

    /**
     * Compares title, author, and chapter number, ignoring case for text.
     *
     * @param other object to compare
     * @return true if both represent the same audiobook chapter
     */
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof Audiobook))
        {
            return false;
        }
        Audiobook book = (Audiobook)other;
        return super.equals(other) && chapterNumber == book.chapterNumber;
    }

    /** @return hash code consistent with equals */
    @Override
    public int hashCode()
    {
        return super.hashCode() * 31 + chapterNumber;
    }

    /** @return formatted audiobook title, author, and chapter */
    @Override
    public String toString()
    {
        return super.toString() + " #" + chapterNumber;
    }
}
