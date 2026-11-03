import java.util.HashSet;
import java.util.Map;

/**
 * Filters users that don't have more than one unique rating
 * UserFilter
 */
public class UserFilter {
    /**
     * Removes a user if its ratings values are not unique 
     * 
     * @param ratingsByUser Nested map of users and their ratings
     */
    public static void removeUncooperativeUsers(Map<String, Map<String, Integer>> ratingsByUser) {
        ratingsByUser.values().removeIf(
            ratings -> new HashSet<>(ratings.values()).size() < 2);
    }
}
