package com.nileshgule.movielens;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class RatingsCsvReaderWithKryo {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName("Movie Lens Movies CSV Reader")
                .getOrCreate();

//        conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");

        String ratingsCsvPath = args[0];

        Dataset<Row> ds = spark
                .read()
                .option("inferSchema", "true")
                .option("header", "true")
                .csv(ratingsCsvPath);


        ds.cache();

        Dataset<Row> ratingsDF = ds.select("userId", "movieId", "rating", "timestamp");

        ratingsDF.show(10, false);

        ratingsDF.cache();

        System.out.println("Total ratings in collection = " + ratingsDF.count());

        System.out.println("Ratings grouped by users = " + ratingsDF.distinct());

        System.out.println("Grouping movies by rating");

        ratingsDF.groupBy("rating").count().show();
    }


}
