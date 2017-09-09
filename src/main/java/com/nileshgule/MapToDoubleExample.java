package com.nileshgule;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaDoubleRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

public class MapToDoubleExample {
    public static void main(String[] args) {
        SparkConf conf=new SparkConf().setAppName("MapToDouble Example");

        JavaSparkContext context = new JavaSparkContext(conf);
        context.setLogLevel("ERROR");

        JavaRDD<Integer> numbers = context.parallelize(Arrays.asList(1, 2, 3, 4));

        JavaDoubleRDD squaredNumbers = numbers.mapToDouble(x -> (double) x * x);

        System.out.println("Mean value of squared numbers = " + squaredNumbers.mean());

    }
}
