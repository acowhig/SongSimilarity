import java.util.List;

public class UserProfile implements SimilarityComparable {
    private final String user;
    private final List<Double> data;
    
    public UserProfile(String user, List<Double> data) {
        this.user = user;
        this.data = data;
    }

    @Override
    public String getId() {
        return user;
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
