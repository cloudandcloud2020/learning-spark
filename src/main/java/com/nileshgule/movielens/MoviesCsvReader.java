package com.nileshgule.movielens;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;


public class MoviesCsvReader {

    public static void main(String[] args) {

        SparkSession spark = SparkSession
                .builder()
                .appName("Movie CSV Reader")
                .getOrCreate();

        Dataset<Row>  ds = spark
                .read()
                .option("inferSchema", "true")
                .option("header", "true")
                .csv("ml-latest/movies.csv");


        ds.printSchema();

        ds.select("movieId", "title", "genres")
                .show(10, false);

        System.out.println("Total movies in collection = " + ds.count());
    }
}
