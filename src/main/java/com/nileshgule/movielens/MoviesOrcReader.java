package com.nileshgule.movielens;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;


public class MoviesOrcReader {

    public static void main(String[] args) {

        SparkSession spark = SparkSession
                .builder()
                .appName("Movie CSV Reader")
                .getOrCreate();

        String inputFilePath = args[0];
        //"/ml-lates/rating-orc";

        Dataset<Row> ds = spark
                .read()
                .orc(inputFilePath);


        ds.printSchema();

        ds.select("userId", "movieId", "rating", "timestamp")
                .show(10, false);

        System.out.println("Total movies in collection = " + ds.count());
    }
}
