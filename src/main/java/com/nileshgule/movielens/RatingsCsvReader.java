package com.nileshgule.movielens;

import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SparkSession;

public class RatingsCsvReader {
    public static void main(String[] args) {

        SparkSession spark = SparkSession
                .builder()
                .appName("Movie Lens Movies CSV Reader")
                .getOrCreate();

        String ratingsCsvPath = args[0];

        DataFrame ds = CsvUtils.getDataFrame(spark, ratingsCsvPath);

        ds.cache();

        DataFrame ratingsDF = ds.select("userId", "movieId", "rating", "timestamp");

        ratingsDF.show(10, false);

        ratingsDF.cache();

        System.out.println("Total ratings in collection = " + ratingsDF.count());

        System.out.println("Ratings grouped by users = " + ratingsDF.distinct());

        System.out.println("Grouping movies by rating");

        ratingsDF.groupBy("rating").count().show();
    }


}
