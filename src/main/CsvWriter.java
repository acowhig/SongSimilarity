import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CsvWriter {
    public static void writeSongStatistics(
        String output,
        Map<String, SongSummary> summaries
    ) throws IOException {
        CSVFormat outputFormat = CSVFormat.DEFAULT.builder()
            .setHeader("song","number of ratings","mean","standard deviation")
            .build();

        try (Writer writer = new FileWriter(output);
            CSVPrinter printer = new CSVPrinter(writer, outputFormat)) {
                
            for (Map.Entry<String, SongSummary> entry : summaries.entrySet()) {
                SongSummary s = entry.getValue();
                printer.printRecord(
                    entry.getKey(),
                    s.getCount(),
                    s.getMean(),
                    s.getStandardDeviation());
            }
        } catch (FileNotFoundException e) {
            throw new IOException("Error: File output was not found");
        }
    }

    public static void writeUserRatings(
        String output,
        Map<String, Map<String,Integer>> ratingsByUser,
        Set<String> allSongs)
        throws IOException {
            
            CSVFormat outputFormat = CSVFormat.DEFAULT.builder()
                .setHeader("username", "song", "rating")
                .build();

            try (Writer writer = new FileWriter(output);
                CSVPrinter printer = new CSVPrinter(writer, outputFormat)) {

                    for (Map.Entry<String, Map<String, Integer>> userEntry : ratingsByUser.entrySet()) {
                        for (String song : allSongs) {
                            Integer rating = userEntry.getValue().get(song);
                            printer.printRecord(
                                userEntry.getKey(),
                                song,
                                rating != null ? rating : Double.NaN);
                        }
                    }
                } catch (FileNotFoundException e) {
                    throw new IOException("File output was not found");
                }
        }

    public static void writeSimilarityTable(String output, SimilarityTable<SongProfile> table) throws IOException{
        List<SongProfile> songs = table.getItems();

        CSVFormat outputFormat = CSVFormat.DEFAULT.builder()
            .setHeader("name1","name2","similarity")
            .build();

        try (Writer writer = new FileWriter(output);
            CSVPrinter printer = new CSVPrinter(writer, outputFormat)) {

            for (int i = 0; i < songs.size(); i++) {
                for (int j = i + 1; j < songs.size(); j++) {
                    printer.printRecord(songs.get(i).getId(), songs.get(j).getId(), table.getRow(i).get(j));
                }
            }
        } catch (FileNotFoundException e) {
            throw new IOException("Error: Output file not found");
        }
    }

    public static void writePredictions(String output, RatingPrediction predictor, RatingMatrix matrix) throws IOException {
        CSVFormat outputFormat = CSVFormat.DEFAULT.builder()
            .setHeader("song","user","predicted rating")
            .build();

        try (Writer writer = new FileWriter(output);
            CSVPrinter printer = new CSVPrinter(writer, outputFormat)) {

            for (String song : matrix.getSongs()) {
                int songIndex = matrix.getSongIndex(song);
                for (String user : matrix.getUsers()) {
                    int userIndex = matrix.getUserIndex(user);
                    if (Double.isNaN(matrix.getRating(userIndex, songIndex))) {
                        double predictedRating = predictor.predictRating(user, song);
                        if (Double.isNaN(predictedRating)) {
                            printer.printRecord(song, user, "NaN");
                        } else {
                        printer.printRecord(song, user, (int) predictedRating);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new IOException("Error: Output file not found");
        }
    }
}

