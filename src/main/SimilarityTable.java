import java.util.ArrayList;
import java.util.List;

public class SimilarityTable<T extends SimilarityComparable> {
    private final EuclideanSimilarity similarity;
    private final List<T> items;
    private final ArrayList<ArrayList<Double>> table = new ArrayList<>();

    /**
     * Similarity table constructor
     * 
     * @param similarity Song similarity
     * @param items List of songs
     */
    public SimilarityTable(List<T> items, EuclideanSimilarity similarity) {
        this.items = items;
        this.similarity = similarity;
    }

    public void makeSimilarityTable() {
        int n = items.size();

        table.clear();

        for (int i = 0; i < n; i++) {
            ArrayList<Double> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                row.add(Double.NaN);
            }
            table.add(row);
        }

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                double distance = similarity.distance(items.get(i), items.get(j));

                table.get(i).set(j, distance);
                table.get(j).set(i, distance);
            }
        }
    }

    /**
     * Song getter method
     * 
     * @return List of songs
     */
    public List<T> getItems() {
        return items;
    }
    
    /**
     * Row index getter method 
     * 
     * @param index Row index
     * @return Multi song distance values
     */
    public List<Double> getRow(int index) {
        return table.get(index);
    }
}
