package com.nileshgule.movielens;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

public class MovieRatingAnalysis {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();

        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        SQLContext sqlContext = new SQLContext(sparkContext);

        String ratingsFilePath = args[0];
        String moviesFilePath = args[1];

        DataFrame ratingsDataFrame = CsvUtils.getDataFrame(sqlContext, ratingsFilePath);

        DataFrame ratings = ratingsDataFrame.select("userId", "movieId", "rating");

        DataFrame moviesDataFrame = CsvUtils.getDataFrame(sqlContext, moviesFilePath)
                .select("movieId", "title");

        ratings.groupBy("movieId").count().show(10);

        DataFrame moviesAndRatingsDataFrame = ratings.join(moviesDataFrame, "movieId");

        moviesAndRatingsDataFrame.filter("rating > 3")
                .groupBy("movieId", "title", "rating")
                .count()
                .orderBy("rating")
//                .select("movieId", "title", "rating")
                .show(10);
    }

}
