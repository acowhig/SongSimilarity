import java.util.List;
import java.util.Map;

public class RatingPrediction {
    private final RatingMatrix matrix;
    private final SimilarityTable<UserProfile> similarityTable;
    private final Map<String, Map<String, Integer>> usersToRatings;

    public RatingPrediction(RatingMatrix matrix, SimilarityTable<UserProfile> similarityTable, Map<String, Map<String, Integer>> usersToRatings) {
        this.matrix = matrix;
        this.similarityTable = similarityTable;
        this.usersToRatings = usersToRatings;
    }

    public double predictRating(String targetUser, String song) {
        int targetUserIndex = matrix.getUserIndex(targetUser);
        int songIndex = matrix.getSongIndex(song);
        List<Double> similarities = similarityTable.getRow(targetUserIndex);

        int bestUser = findBestUser(targetUserIndex, songIndex, similarities);

        if (bestUser == -1) {
            return Double.NaN;
        }

        double normalizedRating = matrix.getRating(bestUser, songIndex);

        StatisticsCalculator calculator = new StatisticsCalculator();

        for (int rating : usersToRatings.get(targetUser).values()) {
            calculator.addVariable(rating);
        }

        double mean = calculator.getMean();
        double standardDeviation = calculator.getStandardDeviation();
        double prediction = normalizedRating * standardDeviation + mean;

        prediction = Math.round(prediction);
        if (prediction < 1) {
            prediction = 1;
        }
        if (prediction > 5) {
            prediction = 5;
        }

        return (int) prediction;
    }

    private int findBestUser(int targetUserIndex, int songIndex, List<Double> similarities) {
        int bestUser = -1;
        double bestDistance = Double.MAX_VALUE;

        for (int i = 0; i < similarities.size(); i++) {
            if (i == targetUserIndex) {
                continue;
            }
            
            double distance = similarities.get(i);

            if (Double.isNaN(distance)) {
                continue;
            }

            double rating = matrix.getRating(i, songIndex);
            
            if (Double.isNaN(rating)) {
                continue;
            }

            if (distance < bestDistance) {
                bestDistance = distance;
                bestUser = i;
            }
        }
        return bestUser;
    }
}