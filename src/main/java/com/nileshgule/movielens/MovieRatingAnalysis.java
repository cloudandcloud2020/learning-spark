package com.nileshgule.movielens;


import static org.apache.spark.sql.functions.*;

import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class MovieRatingAnalysis {
    public static void main(String[] args) {

        SparkSession spark = SparkSession
                .builder()
                .appName("Movie CSV Reader")
                .getOrCreate();

//        spark.sparkContext().setLogLevel("ERROR");

        String ratingsFilePath = args[0];
        String moviesFilePath = args[1];

        DataFrame ratingsDataSet = CsvUtils.getDataFrame(spark, ratingsFilePath);

        System.out.println("Rating dataset schema");
        ratingsDataSet.printSchema();

        DataFrame ratings = ratingsDataSet
                .select("userId", "movieId", "rating")
                .filter("rating > 3");

        System.out.println("Filtered Rating dataset schema");
        ratings.printSchema();

        ratings.cache();

        DataFrame moviesDataFrame = CsvUtils
                .getDataFrame(spark, moviesFilePath)
                .select("movieId", "title");

        System.out.println("Movies dataset schema");
        moviesDataFrame.printSchema();

//        ratings.groupBy("movieId").count().show(10);

        DataFrame moviesAndRatingsDataFrame = ratings.join(moviesDataFrame, "movieId");

        System.out.println("Top 10 rated movies");

        moviesAndRatingsDataFrame
                .groupBy("movieId", "title", "rating")
                .count()
                .orderBy(desc("count"), desc("rating"))
                .show(10);
    }

}
