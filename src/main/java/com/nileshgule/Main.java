package com.nileshgule;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;


public class Main {

    public static void main(String[] args){
        String master="local[*]";

        SparkConf conf=new SparkConf().
                setAppName("Word Count")
                .setMaster(master);

        JavaSparkContext context = new JavaSparkContext(conf);

        JavaRDD<String> textFile = context.textFile("input/README.md");

        JavaRDD<String> tokenized = textFile.flatMap(line -> Arrays.asList(line.split(" ")));

        JavaPairRDD<String, Integer> wordCountPair = tokenized.mapToPair(word -> new Tuple2<>(word, 1));

        JavaPairRDD<String, Integer> counts = wordCountPair.reduceByKey((accValue, newValue) -> accValue + newValue);

        System.out.println(counts.collect());

//        counts.saveAsTextFile("WordCountOutput/");

    }
}
