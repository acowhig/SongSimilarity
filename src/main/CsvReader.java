import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CsvReader {
    public static List<Rating> readRatings(String input) throws IOException{
        List<Rating> ratings = new ArrayList<>();

        CSVFormat inputFormat = CSVFormat.DEFAULT.builder()
            .setSkipHeaderRecord(true)
            .build();

        try (Reader reader = new FileReader(input);
            CSVParser parser = inputFormat.parse(reader)) {

            parseCSV(ratings, parser);
        } catch (FileNotFoundException e) {
            throw new IOException("File input was not found");
        }

        if (ratings.isEmpty()) {
            throw new IOException("Error: Input file is empty");
        }
        
        return ratings;
    }

    private static void parseCSV(List<Rating> ratings, CSVParser parser) throws IOException {
        for (CSVRecord record : parser) {
            wrongSize(record);

            int rating;
            
            try {
                rating = Integer.parseInt(record.get(2));
            } catch (NumberFormatException e) {
                throw new IOException("Error: Invalid rating");
            }

            if (rating < 1 || rating > 5) {
                throw new IOException("Invalid rating");
            }

            ratings.add(new Rating(
                record.get(0), 
                record.get(1), 
                rating));
        }
    }

    private static void wrongSize(CSVRecord record) throws IOException {
        if (record.size() != 3) {
            throw new IOException("Invalid CSV format");
        }
    }
}