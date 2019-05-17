package com.nileshgule;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.Arrays;


public class WordCount {

    public static void main(String[] args){

        SparkSession spark = SparkSession
                .builder()
                .appName("JavaWordCount")
                .getOrCreate();


        String readmeFilePath = args[0];

        Dataset<String> logData = spark
                .read()
                .textFile(readmeFilePath)
                .cache();

        long numAs = logData.filter(s -> s.contains("a")).count();
        long numBs = logData.filter(s -> s.contains("b")).count();

        JavaRDD<String> textFile = logData.toJavaRDD();

        JavaPairRDD<String, Integer> counts = textFile
                .flatMap(s -> Arrays.asList(s.split(" ")).iterator())
                .mapToPair(word -> new Tuple2<>(word, 1))
                .reduceByKey((a, b) -> a + b);

        System.out.printf("Total number of words in file : %d%n", counts.count());

        System.out.printf("Lines with a: %d, lines with b: %d%n", numAs, numBs);

        spark.stop();

    }
}
