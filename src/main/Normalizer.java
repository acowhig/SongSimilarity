import java.util.Map;
import java.util.TreeMap;

public class Normalizer {

    /**Calculates the zscore based on the rating, mean, and standard deviation of a songs ratings
     * 
     * @param rating The rating of the song
     * @param mean The mean of the song ratings
     * @param standardDeviation The standard deviation of the song ratings
     * @return The z score of the song ratings
     */
    public static double zScore(double value, double mean, double standardDeviation) {
        if (standardDeviation == 0.0) {
            throw new IllegalArgumentException("Error: Standard deviation is zero");
        }
        return (value - mean) / standardDeviation;
    }

    /**
     * Creates a tree map based on the normalized user values
     * 
     * @param usersToRatings A nested map of users and their ratings of different songs
     * @return A tree map of normalized user values
     */
    public static Map<String, Map<String,Double>> normalize(Map<String, Map<String, Integer>> usersToRatings) {
        Map<String, Map<String,Double>> normalized = new TreeMap<>();
        
        for (Map.Entry<String, Map<String, Integer>> userEntry : usersToRatings.entrySet()) {
            Map<String, Integer> ratings = userEntry.getValue();

            StatisticsCalculator statistics = new StatisticsCalculator();
            for (int rating : ratings.values()) {
                statistics.addVariable(rating);
            }
            double mean = statistics.getMean();
            double standardDeviation = statistics.getStandardDeviation();

            Map<String, Double> normalizedUser = new TreeMap<>();
            for (Map.Entry<String, Integer> songEntry : ratings.entrySet()) {
                normalizedUser.put(songEntry.getKey(), zScore(songEntry.getValue(), mean, standardDeviation));
            }
            normalized.put(userEntry.getKey(), normalizedUser);
        }
        return normalized;
    }
}
