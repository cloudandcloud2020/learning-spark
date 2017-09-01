package com.nileshgule.movielens;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;
import static org.apache.spark.sql.functions.*;

public class UserAnalysis {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();

        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        SQLContext sqlContext = new SQLContext(sparkContext);

        String ratingsFilePath = args[0];
        String moviesFilePath = args[1];

        DataFrame ratingsDataFrame = CsvUtils.getDataFrame(sqlContext, ratingsFilePath)
                .select("userId", "movieId", "rating")
                .filter("rating > 3");

        ratingsDataFrame.cache();

        DataFrame moviesDataFrame = CsvUtils.getDataFrame(sqlContext, moviesFilePath)
                .select("movieId", "title");


        DataFrame moviesAndRatingsDataFrame = ratingsDataFrame.join(moviesDataFrame, "movieId");

        moviesAndRatingsDataFrame.groupBy("userId", "rating")
                .count()
                .orderBy(desc("count"), desc("rating"))
                .show();

    }
}
