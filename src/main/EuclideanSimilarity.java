public class EuclideanSimilarity {
    /**
     * Calculates the distance between two songs with multiple ratings
     * 
     * @param song1 The first song
     * @param song2 The second song 
     * @return Boolean sharedUser if false or the distance between the two songs
     */
    public double distance(SimilarityComparable a, SimilarityComparable b) {

        if (a.size() != b.size()) {
            throw new IllegalArgumentException("Error: Profiles must be the same length");
        }

        double total = 0.0;
        boolean shared = false;

        for (int i = 0; i < a.size(); i++) {
            double value1 = a.getValue(i);
            double value2 = b.getValue(i);

            if (!Double.isNaN(value1) && !Double.isNaN(value2)) {
                double difference = value1 - value2;
                total += difference * difference;
                shared = true;
            }
        }
        return shared ? Math.sqrt(total) : Double.NaN;
    }
}
