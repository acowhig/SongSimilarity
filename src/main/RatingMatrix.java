import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public final class RatingMatrix {
    private final List<String> users;
    private final List<String> songs;

    private final Map<String, Integer> songIndex = new HashMap<>();
    private final Map<String, Integer> userIndex = new HashMap<>();
    private final ArrayList<ArrayList<Double>> normalizedRatings;

    /**
     * Matrix constructor
     * 
     * @param users  Users
     * @param songs  Songs
     * @param normalizedRatings Normalized ratings matrix
     */
    private RatingMatrix(List<String> users, List<String> songs, ArrayList<ArrayList<Double>> normalizedRatings) {
        this.users = users;
        this.songs = songs;
        this.normalizedRatings = normalizedRatings;

        for (int i = 0; i < songs.size(); i++) {
            songIndex.put(songs.get(i), i);
        }
        for (int i = 0; i < users.size(); i++) {
            userIndex.put(users.get(i), i);
        }
    }

    /**
     * Builds a matrix from normalized ratings
     * 
     * @param normalized Normalized ratings
     * @return RatingMatrix
     */
    public static RatingMatrix fromNormalized(Map<String, Map<String, Double>> normalized) {
        List<String> users = new ArrayList<>(new TreeSet<>(normalized.keySet()));
        TreeSet<String> allSongs = new TreeSet<>();

        for (Map<String, Double> userRatings : normalized.values()) {
            allSongs.addAll(userRatings.keySet());
        }

        List<String> songs = new ArrayList<>(allSongs);
        ArrayList<ArrayList<Double>> normalizedRatings = new ArrayList<>();

        for (String user : users) {
            Map<String, Double> userRatings = normalized.get(user);
            ArrayList<Double> row = new ArrayList<>();

            for (String song : songs) {
                Double rating = userRatings.get(song);
                row.add(rating != null ? rating : Double.NaN);
            }
            normalizedRatings.add(row);
        }
        return new RatingMatrix(users, songs, normalizedRatings);
    }

    /**
     * Gets all users
     * 
     * @return users
     */
    public List<String> getUsers() {
        return users;
    }

    /**
     * Gets all songs
     * 
     * @return songs
     */
    public List<String> getSongs() {
        return songs;
    }

    /**
     * Number of users
     * 
     * @return user count
     */
    public int getUserCount() {
        return users.size();
    }

    /**
     * Number of songs
     * 
     * @return song count
     */
    public int getSongCount() {
        return songs.size();
    }

    /**
     * Song index lookup
     * 
     * @param song Song name
     * @throws IllegalArgumentException If the index is null
     * @return song index
     */
    public int getSongIndex(String song) {
        Integer index = songIndex.get(song);

        if (index == null) {
            throw new IllegalArgumentException("Error: Unknown song " + song);
        }

        return index;
    }

    /**
     * User index lookup
     * 
     * @param user User name
     * @throws If the index is null
     * @return user index
     */
    public int getUserIndex(String user) {
        Integer index = userIndex.get(user);

        if (index == null) {
            throw new IllegalArgumentException("Error: Unknown user " + user);
        }

        return index;
    }

    /**
     * Creates a SongProfile from a column
     * 
     * @param song Song name
     * @return SongProfile
     */
    public SongProfile getSongProfile(String song) {
        int column = getSongIndex(song);
        ArrayList<Double> ratings = new ArrayList<>();

        for (int user = 0; user < users.size(); user++) {
            ratings.add(normalizedRatings.get(user).get(column));
        }

        return new SongProfile(song, ratings);
    }

    /**
     * Creates a UserProfile from a row
     * 
     * @param user User name
     * @return UserProfile
     */
    public UserProfile getUserProfile(String user) {
        int row = getUserIndex(user);
        ArrayList<Double> ratings = new ArrayList<>();

        for (int song = 0; song < songs.size(); song++) {
            ratings.add(normalizedRatings.get(row).get(song));
        }

        return new UserProfile(user, ratings);
    }
    /**
     * Gets a normalized rating
     * 
     * @param userIndex row
     * @param songIndex column
     * @return normalized rating
     */
    public double getRating(int userIndex, int songIndex) {
        return normalizedRatings.get(userIndex).get(songIndex);
    }

    public boolean hasMissingRatings() {
        for (int user = 0; user < getUserCount(); user++) {
            for (int song = 0; song < getSongCount(); song++) {
                if (Double.isNaN(getRating(user, song))) {
                    return true;
                }
            }
        }
        return false;
    }
}
