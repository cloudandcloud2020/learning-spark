package com.nileshgule.movielens;

//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.sql.DataFrame;
//import org.apache.spark.sql.SQLContext;
//import static org.apache.spark.sql.functions.*;

public class MovieRatingAnalysis {
    public static void main(String[] args) {
//        SparkConf conf = new SparkConf();
//        conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
//
//        JavaSparkContext sparkContext = new JavaSparkContext(conf);
//
//        SQLContext sqlContext = new SQLContext(sparkContext);
//
//        String ratingsFilePath = args[0];
//        String moviesFilePath = args[1];
//
//        DataFrame ratingsDataFrame = CsvUtils.getDataFrame(sqlContext, ratingsFilePath);
//
//        DataFrame ratings = ratingsDataFrame.select("userId", "movieId", "rating")
//                .filter("rating > 3");
//
//        ratings.cache();
//
//        DataFrame moviesDataFrame = CsvUtils.getDataFrame(sqlContext, moviesFilePath)
//                .select("movieId", "title");
//
////        ratings.groupBy("movieId").count().show(10);
//
//        DataFrame moviesAndRatingsDataFrame = ratings.join(moviesDataFrame, "movieId");
//
//        System.out.println("Top 10 rated movies");
//        moviesAndRatingsDataFrame
//                .groupBy("movieId", "title", "rating")
//                .count()
//                .orderBy(desc("count"), desc("rating"))
//                .show(10);
    }

}
