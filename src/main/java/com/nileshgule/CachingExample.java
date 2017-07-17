package com.nileshgule;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CachingExample {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("MapToDouble Example");

        JavaSparkContext context = new JavaSparkContext(conf);

        JavaRDD<Integer> numbers = context.parallelize(IntStream.rangeClosed(1, 100).boxed().collect(Collectors.toList()));

        numbers.cache();

        System.out.println("numbers.count() = " + numbers.count());

        System.out.println("numbers.collect() = " + numbers.collect());
    }

}
