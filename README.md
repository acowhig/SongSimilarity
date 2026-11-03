# SongSimilarity

SongSimilarity is a Java program for analysing a song-rating dataset and generating recommendations from user similarity. It reads a CSV of song ratings, filters uncooperative users, normalises the data, and produces summary reports for songs, user-song matrices, similarity tables, and predicted ratings.

## What it does

The project supports four report modes:

- Default mode: summary statistics per song
- `-a`: cleaned user-by-song rating matrix
- `-u`: pairwise song similarity table
- `-p`: predicted rating values for missing entries

It uses a collaborative filtering approach based on user rating patterns and Euclidean distance in normalised rating space.

## Input format

The expected input file is a CSV containing three columns:

- song
- user
- rating

Example:

```csv
song,user,rating
SongA,Alice,5
SongA,Bob,3
SongB,Alice,4
SongB,Charlie,2
```

Notes:

- The program expects a header row and skips it automatically.
- Ratings must be integers from 1 to 5.
- Each row represents one user rating for one song.

## Usage

Run the application with:

```bash
java -cp out Main <input.csv> <output.csv> [flag]
```

Where `flag` is one of:

- none: compute song-level statistics
- `-a`: export a user-by-song rating matrix
- `-u`: calculate song similarity scores
- `-p`: predict missing ratings

Examples:

```bash
java -cp out Main ratings.csv song_summary.csv
java -cp out Main ratings.csv filtered_ratings.csv -a
java -cp out Main ratings.csv song_similarity.csv -u
java -cp out Main ratings.csv predicted_ratings.csv -p
```

## Compile

This project is plain Java and requires the Apache Commons CSV library on the classpath. A typical compile flow is:

```bash
javac -cp "path/to/commons-csv.jar" -d out $(find src/main -name "*.java")
```

If you are using a local dependency manager or IDE, add the Commons CSV jar to the project classpath before running the code.

## Project structure

```text
src/
  main/
    Main.java
    CsvReader.java
    CsvWriter.java
    Rating.java
    RatingMatrix.java
    UserFilter.java
    Normalizer.java
    EuclideanSimilarity.java
    SimilarityTable.java
    RatingPrediction.java
    SongSummary.java
    SongProfile.java
    UserProfile.java
    StatisticsCalculator.java
  test/
    testClass.java
```

## Output examples

### Song statistics output

```csv
song,number of ratings,mean,standard deviation
SongA,12,3.8,1.1
```

### Similarity table output

```csv
name1,name2,similarity
SongA,SongB,0.84
```

### Predicted ratings output

```csv
song,user,predicted rating
SongC,Bob,4
```

## Notes

- The program removes users whose ratings do not show meaningful variation before similarity analysis.
- Similarity is computed using normalised z-scores and Euclidean distance.
- Prediction outputs are rounded to valid integer ratings between 1 and 5.

This repository is a small recommender-system prototype for exploring collaborative filtering on a song dataset.
