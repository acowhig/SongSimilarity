import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class testClass {
    //StatisticsCalculator
    private StatisticsCalculator statistics;
    @BeforeEach 
    public void resetValues() {
        statistics = new StatisticsCalculator();
    }
    @Test
    public void testMean() {
        statistics.addVariable(3);
        statistics.addVariable(5);
        statistics.addVariable(7);
        assertEquals(5.0, statistics.getMean());
    }
    @Test
    public void testMeanOfOne() {
        statistics.addVariable(5);
        assertEquals(5.0, statistics.getMean());
    }
    @Test
    public void testVariance() {
        statistics.addVariable(3);
        statistics.addVariable(5);
        statistics.addVariable(7);
        assertEquals(2.66667, statistics.getVariance(), 0.001);
    }
    @Test
    public void testSampleVariance() {
        statistics.addVariable(3);
        statistics.addVariable(5);
        statistics.addVariable(7);
        assertEquals(4.0, statistics.getSampleVariance());
    }
    @Test
    public void testStandardDeviation() {
        statistics.addVariable(3);
        statistics.addVariable(5);
        statistics.addVariable(7);
        assertEquals(Math.sqrt(2.66667), statistics.getStandardDeviation(), 0.001);
    }
    @Test
    public void testRemoveVariable() {
        statistics.addVariable(3);
        statistics.addVariable(5);
        statistics.addVariable(7);
        statistics.removeVariable(7);
        assertEquals(4.0, statistics.getMean());
    }
    //Normalizer
    @Test
    public void testPositiveZScore() {
        assertEquals(1.0, Normalizer.zScore(6,4,2), 0.001);
    }
    @Test
    public void testNegativeZScore() {
        assertEquals(-1.0, Normalizer.zScore(2,4,2), 0.001);
    }
    @Test
    public void testExceptionZScore() {
        assertThrows(IllegalArgumentException.class, () -> Normalizer.zScore(5,5,0));
    }
    @Test 
    public void testNormalize() {
        Map<String, Map<String,Integer>> users = new TreeMap<>();
        Map<String, Integer> ratings = new TreeMap<>();

        ratings.put("SongA", 1);
        ratings.put("SongB", 5);
        users.put("Bob", ratings);

        Map<String,Map<String, Double>> normalized = Normalizer.normalize(users);

        assertEquals(-1.0, normalized.get("Bob").get("SongA"), 0.01);
        assertEquals(1.0, normalized.get("Bob").get("SongB"), 0.01);
    }
    //Rating
    @Test
    public void testGetSong() {
        Rating r = new Rating("SongA", "Eddie", 4);
        assertEquals("SongA", r.getSong());
    }
    @Test
    public void testGetUser() {
        Rating r = new Rating("SongA", "Eddie", 4);
        assertEquals("Eddie", r.getUser());
    }
    @Test
    public void testGetValue() {
        Rating r = new Rating("SongA", "Eddie", 4);
        assertEquals(4, r.getValue());
    }
    //SongSummary
    @Test
    public void testGetCount() {
        SongSummary s = new SongSummary(12, 5.7, 1.3);
        assertEquals(12, s.getCount());
    }
    @Test
    public void testGetMean() {
        SongSummary s = new SongSummary(12, 5.7, 1.3);
        assertEquals(5.7, s.getMean());
    }
    @Test
    public void testGetStandardDeviation() {
        SongSummary s = new SongSummary(12, 5.7, 1.3);
        assertEquals(1.3, s.getStandardDeviation());
    }
    //EuclideanSimilarity
    @Test
    public void testDistanceSameValues() {
        SongProfile song1 = new SongProfile("song1", List.of(3.0));
        SongProfile song2 = new SongProfile("song2", List.of(3.0));

        EuclideanSimilarity similarity = new EuclideanSimilarity();

        assertEquals(0.0, similarity.distance(song1, song2));
    }
    @Test
    public void testDistanceDifferentValues() {
        SongProfile song1 = new SongProfile("song1", List.of(3.0));
        SongProfile song2 = new SongProfile("song2", List.of(1.0));

        EuclideanSimilarity similarity = new EuclideanSimilarity();

        assertEquals(2.0, similarity.distance(song1, song2));
    }
    @Test
    public void testDistanceDifferentLengths() {
        SongProfile song1 = new SongProfile("song1", List.of(1.0, 2.0));
        SongProfile song2 = new SongProfile("song2", List.of(1.0));

        EuclideanSimilarity similarity = new EuclideanSimilarity();

        assertThrows(IllegalArgumentException.class, () -> similarity.distance(song1, song2));
    }
    @Test 
    public void testDistanceNoSharedRatings() {
        SongProfile song1 = new SongProfile("song1", List.of(Double.NaN));
        SongProfile song2 = new SongProfile("song2", List.of(Double.NaN));

        EuclideanSimilarity similarity = new EuclideanSimilarity();

        assertTrue(Double.isNaN(similarity.distance(song1, song2)));
    }
    @Test
    public void testDistanceMultipleValues() {
        SongProfile song1 = new SongProfile("song1", List.of(1.0, 3.0));
        SongProfile song2 = new SongProfile("song2", List.of(2.0, 4.0));

        EuclideanSimilarity similarity = new EuclideanSimilarity();

        assertEquals(Math.sqrt(2), similarity.distance(song1, song2), 0.0001);
    }

    //UserFilter
    @Test
    public void testRemoveUncooperativeUser() {
        Map<String, Map<String,Integer>> users = new TreeMap<>();

        Map<String,Integer> ratings = new TreeMap<>();
        ratings.put("SongA", 3);
        ratings.put("SongB", 3);

        users.put("Eddie", ratings);
        UserFilter.removeUncooperativeUsers(users);
        assertEquals(0, users.size());
    }
    @Test
    public void testKeepCooperativeUser() {
        Map<String, Map<String,Integer>> users = new TreeMap<>();

        Map<String,Integer> ratings = new TreeMap<>();
        ratings.put("SongA", 1);
        ratings.put("SongB", 5);

        users.put("Eddie", ratings);
        UserFilter.removeUncooperativeUsers(users);
        assertEquals(1, users.size());
    }
    @Test
    public void testMixedUsers() {
        Map<String, Map<String,Integer>> users = new TreeMap<>();

        Map<String, Integer> user1 = new TreeMap<>();
        user1.put("SongA", 1);
        user1.put("SongB", 1);

        Map<String, Integer> user2 = new TreeMap<>();
        user2.put("SongA", 2);
        user2.put("SongB", 5);

        users.put("Eddie", user1);
        users.put("Fred", user2);

        UserFilter.removeUncooperativeUsers(users);

        assertFalse(users.containsKey("Eddie"));
        assertTrue(users.containsKey("Fred"));
    }
    //RatingMatrix
    @Test
    public void testGetUserCount() {
        Map<String, Map<String, Double>> normalized = new TreeMap<>();

        Map<String, Double> ratings = new TreeMap<>();
        ratings.put("SongA", 1.0);

        normalized.put("Eddie", ratings);
        RatingMatrix matrix = RatingMatrix.fromNormalized(normalized);
        assertEquals(1, matrix.getUserCount());
    }
    @Test
    public void testGetSongIndex() {
        Map<String, Map<String, Double>> normalized = new TreeMap<>();

        Map<String, Double> ratings = new TreeMap<>();
        ratings.put("SongA", 1.0);

        normalized.put("Eddie", ratings);
        RatingMatrix matrix = RatingMatrix.fromNormalized(normalized);
        assertEquals(0, matrix.getSongIndex("SongA"));
    }
    @Test 
    public void testGetSongProfile() {
        Map<String, Map<String, Double>> normalized = new TreeMap<>();
        Map<String, Double> ratings = new TreeMap<>();

        ratings.put("SongA", 1.0);
        normalized.put("Bob", ratings);

        RatingMatrix matrix = RatingMatrix.fromNormalized(normalized);
        SongProfile song = matrix.getSongProfile("SongA");

        assertEquals("SongA", song.getId());
    }
    @Test 
    public void testGetUserProfile() {
        Map<String, Map<String, Double>> normalized = new TreeMap<>();
        Map<String, Double> ratings = new TreeMap<>();

        ratings.put("SongA", 1.0);
        normalized.put("Bob", ratings);

        RatingMatrix matrix = RatingMatrix.fromNormalized(normalized);
        UserProfile user = matrix.getUserProfile("Bob");

        assertEquals("Bob", user.getId());
    }
    @Test 
    public void testGetSongIndexException() {
        Map<String, Map<String, Double>> normalized = new TreeMap<>();

        normalized.put("Bob", new TreeMap<>());

        RatingMatrix matrix = RatingMatrix.fromNormalized(normalized);

        assertThrows(IllegalArgumentException.class, () -> matrix.getSongIndex("NotARealSong"));
    }
    @Test 
    public void testGetUserIndexException() {
        Map<String, Map<String, Double>> normalized = new TreeMap<>();

        normalized.put("Bob", new TreeMap<>());

        RatingMatrix matrix = RatingMatrix.fromNormalized(normalized);

        assertThrows(IllegalArgumentException.class, () -> matrix.getUserIndex("NotARealUser"));
    }
    @Test 
    public void testGetSongCount() {
        Map<String, Map<String, Double>> normalized = new TreeMap<>();
        Map<String, Double> ratings = new TreeMap<>();

        ratings.put("Song1", 1.0);
        ratings.put("Song2", 2.0);

        normalized.put("Bob", ratings);

        RatingMatrix matrix = RatingMatrix.fromNormalized(normalized);

        assertEquals(2, matrix.getSongCount());
    }
    @Test 
    public void testGetUsers() {
        Map<String, Map<String, Double>> normalized = new TreeMap<>();

        normalized.put("Bob", new TreeMap<>());

        RatingMatrix matrix = RatingMatrix.fromNormalized(normalized);

        assertEquals("Bob", matrix.getUsers().get(0));
    }
    @Test 
    public void testGetSongs() {
        Map<String, Map<String, Double>> normalized = new TreeMap<>();
        Map<String, Double> ratings = new TreeMap<>();

        ratings.put("Song1", 1.0);
        normalized.put("Bob", ratings);

        RatingMatrix matrix = RatingMatrix.fromNormalized(normalized);

        assertEquals("Song1", matrix.getSongs().get(0));
    }
    //SongProfile
    @Test
    public void testSongProfileGetId() {
        SongProfile song = new SongProfile("SongA", List.of(1.0, 2.0));

        assertEquals("SongA", song.getId());
    }
    @Test
    public void testSongProfileSize() {
        SongProfile song = new SongProfile("SongA", List.of(1.0, 2.0));

        assertEquals(2, song.size());
    }
    @Test 
    public void testSongProfileGetValue() {
        SongProfile song = new SongProfile("SongA", List.of(1.0, 2.0));

        assertEquals(2.0, song.getValue(1));
    }
    //UserProfile
    @Test
    public void testUserProfileGetId() {
        UserProfile user = new UserProfile("Bob", List.of(1.0, 2.0));

        assertEquals("Bob", user.getId());
    }
    @Test
    public void testUserProfileSize() {
        UserProfile user = new UserProfile("Bob", List.of(1.0, 2.0));

        assertEquals(2, user.size());
    }
    @Test 
    public void testUserProfileGetValue() {
        UserProfile user = new UserProfile("Bob", List.of(1.0, 2.0));

        assertEquals(2.0, user.getValue(1));
    }
    //SimilarityTable
    @Test
    public void testSimilarityTableSize() {
        SongProfile song1 = new SongProfile("Song1", List.of(1.0));
        SongProfile song2 = new SongProfile("Song2", List.of(2.0));
        SimilarityTable<SongProfile> table = new SimilarityTable<>(List.of(song1, song2), new EuclideanSimilarity());

        table.makeSimilarityTable();

        assertEquals(2, table.getItems().size());
    }
    //
    @Test 
    public void hasMissingRatings() {
        Map<String, Map<String, Double>> normalized = new TreeMap<>();
        Map<String, Double> ratings = new TreeMap<>();

        ratings.put("SongA", 1.0);
        normalized.put("Bob", ratings);
        
        RatingMatrix matrix = RatingMatrix.fromNormalized(normalized);

        assertTrue(matrix.hasMissingRatings());
    }
    @Test
    public void noMissingRatings() {
        Map<String, Map<String, Double>> normalized = new TreeMap<>();
        Map<String, Double> bob = new TreeMap<>();
        Map<String, Double> fred = new TreeMap<>();

        bob.put("SongA", 1.0);
        fred.put("SongA", 2.0);

        normalized.put("Bob", bob);
        normalized.put("Fred", fred);

        RatingMatrix matrix = RatingMatrix.fromNormalized(normalized);

        assertFalse(matrix.hasMissingRatings());
    }
}