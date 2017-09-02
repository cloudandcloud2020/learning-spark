package com.nileshgule.movielens;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;
import static org.apache.spark.sql.functions.*;

public class UserAnalysis {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
//        conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");

        JavaSparkContext sparkContext = new JavaSparkContext(conf);
        sparkContext.setLogLevel("ERROR");

        SQLContext sqlContext = new SQLContext(sparkContext);

        String ratingsFilePath = args[0];
        String moviesFilePath = args[1];

        DataFrame ratingsDataFrame = CsvUtils.getDataFrame(sqlContext, ratingsFilePath)
//                .select("userId", "movieId", "rating")
                .filter("rating > 3");

        System.out.println("Count of ratings above 3 = " + ratingsDataFrame.count());
//
//        ratingsDataFrame.cache();

        DataFrame distinctMovieIds = ratingsDataFrame.distinct();

        System.out.println("Count of distinct movie Ids = " + distinctMovieIds.count());

        DataFrame moviesDataFrame = CsvUtils.getDataFrame(sqlContext, moviesFilePath)
                .select("movieId", "title");
//                .filter("movieId in (" + distinctMovieIds.select().flatMap() + ")");

        Broadcast<DataFrame> moviesBroadcast = sparkContext.broadcast(moviesDataFrame);

//        DataFrame moviesAndRatingsDataFrame = ratingsDataFrame.join(moviesDataFrame, "movieId");

//        DataFrame moviesAndRatingsDataFrame = ratingsDataFrame.join(moviesBroadcast.getValue(), "movieId");

        DataFrame moviesAndRatingsDataFrame = moviesBroadcast.getValue().join(ratingsDataFrame, "movieId");

        moviesAndRatingsDataFrame.groupBy("userId", "rating")
                .count()
                .orderBy(desc("count"), desc("rating"))
//                .orderBy(desc("rating"), desc("count"))
                .show();

    }
}
