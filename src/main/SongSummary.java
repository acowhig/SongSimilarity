public class SongSummary {
    private final int count;
    private final double mean;
    private final double standardDeviation;

    /**
     * Song summary constructor
     * 
     * @param count Number of song rating values
     * @param mean Mean of song ratings
     * @param standardDeviation Standard deviation of song ratings
     */
    public SongSummary(int count, double mean, double standardDeviation) {
        this.count = count;
        this.mean = mean;
        this.standardDeviation = standardDeviation;
    }

    /**
     * Count getter method 
     * 
     * @return Count
     */
    public int getCount() {
        return count;
    }

    /**
     * Mean getter method
     * 
     * @return Mean
     */
    public double getMean() {
        return mean;
    }

    /**
     * Standard deviation getter method
     * 
     * @return Standard deviation
     */
    public double getStandardDeviation() {
        return standardDeviation;
    }
}
