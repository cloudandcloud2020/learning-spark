package com.nileshgule;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;


public class Main {

    public static void main(String[] args){

        SparkConf conf=new SparkConf().setAppName("Word Count");

        JavaSparkContext context = new JavaSparkContext(conf);

        JavaRDD<String> textFile = context.textFile("input/README.md");

        JavaRDD<String> tokenized = textFile.flatMap(line -> Arrays.asList(line.split(" ")));

        JavaPairRDD<String, Integer> wordCountPair = tokenized.mapToPair(word -> new Tuple2<>(word, 1));

        JavaPairRDD<String, Integer> counts = wordCountPair.reduceByKey((accValue, newValue) -> accValue + newValue);

        counts.cache();

        JavaPairRDD<String, Integer> sortedCounts = counts.sortByKey(false);
        System.out.printf("Number of words = %d%n", counts.count());

        System.out.println(sortedCounts.collect());

        JavaPairRDD<String, Integer> filteredCount = counts.filter(c -> c._1.length() > 5);

        System.out.println("Number of words having length greater than 5 ");

        System.out.println("counts = " + filteredCount.count());

    }
}
