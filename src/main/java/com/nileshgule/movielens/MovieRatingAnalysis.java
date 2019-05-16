package com.nileshgule.movielens;


import static org.apache.spark.sql.functions.*;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class MovieRatingAnalysis {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName("Movie CSV Reader")
                .getOrCreate();

        String ratingsFilePath = args[0];
        String moviesFilePath = args[1];

        Dataset<Row> ratingsDataSet = CsvUtils.getDataset(spark, ratingsFilePath);

        Dataset<Row> ratings = ratingsDataSet.select("userId", "movieId", "rating")
                .filter("rating > 3");

        ratings.cache();

        Dataset<Row> moviesDataFrame = CsvUtils.getDataset(spark, moviesFilePath)
                .select("movieId", "title");

//        ratings.groupBy("movieId").count().show(10);

        Dataset<Row> moviesAndRatingsDataFrame = ratings.join(moviesDataFrame, "movieId");

        System.out.println("Top 10 rated movies");
        moviesAndRatingsDataFrame
                .groupBy("movieId", "title", "rating")
                .count()
                .orderBy(desc("count"), desc("rating"))
                .show(10);
    }

}
