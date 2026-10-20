package audiostack;

/**
 * A music track with an optional album.
 * 
 * @author AudioStack Team
 */
public final class Song
    extends MediaItem
{
    private final String album;

    /**
     * Creates a song.
     * 
     * @param title
     *            nonblank song title
     * @param artist
     *            nonblank artist name
     * @param album
     *            optional album; null becomes an empty string
     * @throws IllegalArgumentException
     *             if title or artist is blank
     */
    public Song(String title, String artist, String album)
    {
        super(title, artist);
        this.album = album == null ? "" : album.trim();
    }


    /**
     * Gets the artist name.
     * 
     * @return artist
     */
    public String getArtist()
    {
        return getCreator();
    }


    /**
     * Gets the optional album.
     * 
     * @return album, possibly empty
     */
    public String getAlbum()
    {
        return album;
    }


    /** @return the text Song */
    @Override
    public String getType()
    {
        return "Song";
    }


    /**
     * Returns the short format plus the album, when there is one. The list
     * view keeps the short format; this is used when viewing one song.
     *
     * @return the song's full details
     */
    @Override
    public String toDetailString()
    {
        if (album.isEmpty())
        {
            return toString();
        }
        return toString() + " | Album: " + album;
    }
}
