package com.nileshgule.movielens;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

public class RatingsCsvReader {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("Movie Lens Movies CSV Reader");

        JavaSparkContext context = new JavaSparkContext(conf);

        SQLContext sqlContext = new SQLContext(context);

        // Todo : Rea csv file as parameter to the program
        DataFrame df = sqlContext.read()
                .format("com.databricks.spark.csv")
                .option("inferSchema", "true")
                .option("header", "true")
                .load("ml-latest/ratings.csv");

        df.cache();

        DataFrame ratingsDF = df.select("userId", "movieId", "rating", "timestamp");

        ratingsDF.show(10, false);

        System.out.println("Total ratings in collection = " + ratingsDF.count());

        System.out.println("Ratings grouped by users = " + ratingsDF.distinct());
    }


}
