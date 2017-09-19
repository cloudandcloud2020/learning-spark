package com.nileshgule.RDDSamples;

import com.nileshgule.movielens.functions.CreateRatingFunction;
import com.nileshgule.movielens.models.RatingModel;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.List;

public class RatingsRDDExample {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();

        JavaSparkContext context = new JavaSparkContext(conf);
        context.setLogLevel("ERROR");

        String ratingsFilePath = args[0];

        JavaRDD<String> inputLines = context.textFile(ratingsFilePath);

        String header = inputLines.first();
        System.out.println("headerLine = " + header);
        JavaRDD<String> linesWithoutHeader = inputLines.filter(line -> !line.equalsIgnoreCase(header));

//        System.out.println("linesWithoutHeader.count() = " + linesWithoutHeader.count());
//        System.out.println("linesWithoutHeader.first() = " + linesWithoutHeader.first());

        JavaRDD<RatingModel> userRatings = linesWithoutHeader.map(new CreateRatingFunction());

        JavaPairRDD < String, Double > ratingsGroupedByUser = userRatings.mapToPair((PairFunction<RatingModel, String, Double>) ratingModel -> new Tuple2(ratingModel.getUserId(), ratingModel.getRating()));

//        Tuple2<String, Double> firstMapToPair = ratingsGroupedByUser.first();
//        System.out.println("First MapToPair = " + firstMapToPair._1 + " value = " + firstMapToPair._2 );
//
//        System.out.println("ratingsGroupedByUser.count() = " + ratingsGroupedByUser.count());
//
//        List<Tuple2<String, Double>> filteredRatings = ratingsGroupedByUser.take(100);
//
//        for (Tuple2<String, Double> item : filteredRatings){
//            System.out.printf("UserId = %s Rating = %s%n", item._1, item._2);
//        }

        JavaPairRDD<String, Iterable<Double>> userRatingsByKey = ratingsGroupedByUser.groupByKey();
        System.out.println("userRatings.first() = " + userRatingsByKey.first());

        List<Tuple2<String, Iterable<Double>>> ten = userRatingsByKey.take(10);
        for (Tuple2<String, Iterable<Double>> item : ten){
            System.out.println("item = " + item);

        }


    }
}
