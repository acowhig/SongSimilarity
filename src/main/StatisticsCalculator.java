public class StatisticsCalculator {
    private double mean = 0.0;
    private int count = 0;
    private double M2 = 0.0;

    /**
     * Adds a variable and calculates mean and M2
     * 
     * @param x The variable to add
     */
    public void addVariable(double x) {
        count ++;
        double oldMean = mean;
        mean += (x - mean)/count;
        M2 += (x - oldMean) * (x - mean);
    }

    /**
     * Removes a variable and calculates mean and M2
     * 
     * @param x The variable to remove
     */
    public void removeVariable(double x) {
        count -= 1;
        double newMean = mean;
        mean -= (x - mean)/count;
        M2 -= (x - newMean) * (x - mean);
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
     * Calculates the variance
     * 
     * @return Variance
     */
    public double getVariance() {
        return M2/count;
    }

    /**
     * Calculates the sample variance
     * 
     * @return Sample variance
     */
    public double getSampleVariance() {
        return M2/(count-1);
    }

    /**
     * Calculates the standard deviation
     * 
     * @return Standard deviation
     */
    public double getStandardDeviation() {
        return Math.sqrt(getVariance());
    }
}
