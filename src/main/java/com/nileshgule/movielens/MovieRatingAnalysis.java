package com.nileshgule.movielens;


import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.desc;

public class MovieRatingAnalysis {
    public static void main(String[] args) {

        SparkSession spark = SparkSession
                .builder()
                .appName("Movie CSV Reader")
                .getOrCreate();

//        spark.sparkContext().setLogLevel("ERROR");

        String ratingsFilePath = args[0];
        String moviesFilePath = args[1];

        Dataset<Row> ratingsDataSet = CsvUtils.getDataFrame(spark, ratingsFilePath);

        System.out.println("Rating dataset schema");
        ratingsDataSet.printSchema();

        Dataset<Row> ratings = ratingsDataSet
                .select("userId", "movieId", "rating")
                .filter("rating > 3");

        System.out.println("Filtered Rating dataset schema");
        ratings.printSchema();

        ratings.cache();

        Dataset<Row> moviesDataFrame = CsvUtils
                .getDataFrame(spark, moviesFilePath)
                .select("movieId", "title");

        System.out.println("Movies dataset schema");
        moviesDataFrame.printSchema();

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
