package com.nileshgule.movielens;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

import java.io.File;

import static org.apache.spark.sql.functions.desc;

public class UserAnalysis {
    public static void main(String[] args) {

        // warehouseLocation points to the default location for managed databases and tables
        String warehouseLocation = new File("spark-warehouse").getAbsolutePath();

        SparkSession spark = SparkSession
                .builder()
                .appName("Movie Lens User analysis")
                .config("spark.sql.warehouse.dir", warehouseLocation)
                .enableHiveSupport()
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


        ratingsDataFrame
                .write()
                .mode(SaveMode.Overwrite)
                .saveAsTable("topratingusers");

        spark.stop();
    }
}
