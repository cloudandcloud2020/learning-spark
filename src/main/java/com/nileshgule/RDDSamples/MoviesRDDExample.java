package com.nileshgule.RDDSamples;

import com.nileshgule.movielens.functions.CreateMovieFunction;
import com.nileshgule.movielens.models.MovieModel;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.List;

public class MoviesRDDExample {
    public static void main(String[] args) {
        SparkConf conf=new SparkConf().setAppName("MapToDouble Example");

        JavaSparkContext context = new JavaSparkContext(conf);
        context.setLogLevel("ERROR");

        String inputFileName = args[0];
        JavaRDD<String> inputFile = context.textFile(inputFileName);
        JavaRDD<MovieModel> movies = inputFile.map(new CreateMovieFunction());

        System.out.println("movies.count() = " + movies.count());

        List<MovieModel> fiveMovies = movies.take(5);
        for (MovieModel movieModel :fiveMovies) {
            System.out.printf("MovieId = %s, Title = %s%n", movieModel.getMovieId(), movieModel.getTitle());
        }

    }

}