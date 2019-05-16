package com.nileshgule.movielens;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.desc;

public class UserAnalysis {
    public static void main(String[] args) {

        SparkSession spark = SparkSession
                .builder()
                .appName("Movie Lens User analysis")
                .getOrCreate();

        String ratingsFilePath = args[0];

        Dataset<Row> ratingsDataFrame = CsvUtils.getDataset(spark, ratingsFilePath);

        System.out.println("Total number of ratings = " + ratingsDataFrame.count());

        ratingsDataFrame.cache();

        Dataset<Row> higherRatings = ratingsDataFrame.filter("rating > 3");

        System.out.println("Count of ratings above 3 = " + higherRatings.count());
        higherRatings.cache();

        System.out.println("Users with most ratings > 3");
        higherRatings.groupBy("userId", "rating")
                .count()
                .orderBy(desc("count"), desc("rating"))
                .show();

////        System.out.println("Ratings by user 45811");
////
////        ratingsDataFrame.filter("userId = 45811").groupBy("rating")
////                .count()
////                .show();
        System.out.println("Most commonly used rating");

        ratingsDataFrame.groupBy("rating")
                .count()
                .orderBy(desc("count"))
                .show();

        System.out.println("Top 5 users");
        ratingsDataFrame.groupBy("userId")
                .count()
                .orderBy(desc("count"))
                .show(5);

        spark.stop();
    }
}
