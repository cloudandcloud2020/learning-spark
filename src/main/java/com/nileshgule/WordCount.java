package com.nileshgule;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Map;


public class WordCount {

    public static void main(String[] args){

        SparkConf conf=new SparkConf().setAppName("Word Count");

        JavaSparkContext context = new JavaSparkContext(conf);

        JavaRDD<String> textFile = context.textFile("input/README.md");

        JavaRDD<String> tokenized = textFile.flatMap(line -> Arrays.asList(line.split(" ")));

        Map<String, Long> countByValue = tokenized.countByValue();

//        JavaRDD<String> wordsGreaterThanThreeCharacters = tokenized.filter(x -> x.length() > 3);

//        Map<String, Long> countByValue = wordsGreaterThanThreeCharacters.countByValue();

        System.out.println("Number of words using countByValue = " + countByValue);
        System.out.println("Number of words = " + countByValue.size());

    }
}
