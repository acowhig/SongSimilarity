public class Rating {
    private final String song;
    private final String user;
    private final int rating;

    /**
     * Rating constructor
     * 
     * @param song The song name
     * @param user The user name
     * @param value The rating value
     */
    public Rating(String song, String user, int rating) {
        this.song = song;
        this.user = user;
        this.rating = rating;
    }

    /**
     * Song getter method
     * 
     * @return Song name
     */
    public String getSong() {
        return song;
    }

    /**
     * User getter method
     * 
     * @return User name
     */
    public String getUser() {
        return user;
    }

    /**
     * Rating value getter
     * 
     * @return Rating value
     */
    public int getValue() {
        return rating;
    }
}
