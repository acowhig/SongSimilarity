import java.util.List;

public class SongProfile implements SimilarityComparable {
    private final String song;
    private final List<Double> data;
    
    public SongProfile(String song, List<Double> data) {
        this.song = song;
        this.data = data;
    }

    @Override
    public String getId() {
        return song;
    };

    @Override 
    public int size() {
        return data.size();
    }
    
    @Override 
    public double getValue(int index){
        return data.get(index);
    }
}
