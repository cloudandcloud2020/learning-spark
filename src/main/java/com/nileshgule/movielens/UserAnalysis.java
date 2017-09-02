package com.nileshgule.movielens;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

import static org.apache.spark.sql.functions.desc;

public class UserAnalysis {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();

        JavaSparkContext sparkContext = new JavaSparkContext(conf);
        sparkContext.setLogLevel("ERROR");

        SQLContext sqlContext = new SQLContext(sparkContext);

        String ratingsFilePath = args[0];

        DataFrame ratingsDataFrame = CsvUtils.getDataFrame(sqlContext, ratingsFilePath);

        System.out.println("Total number of ratings = " + ratingsDataFrame.count());

//        ratingsDataFrame.cache();

        DataFrame higherRatings = ratingsDataFrame.filter("rating > 3");

        System.out.println("Count of ratings above 3 = " + higherRatings.count());
        higherRatings.cache();

        System.out.println("Users with most ratings > 3");
        higherRatings.groupBy("userId", "rating")
                .count()
                .orderBy(desc("count"), desc("rating"))
                .show();

//        System.out.println("Ratings by user 45811");
//
//        ratingsDataFrame.filter("userId = 45811").groupBy("rating")
//                .count()
//                .show();
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
    }
}
