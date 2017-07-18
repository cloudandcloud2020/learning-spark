package com.nileshgule;

import com.google.common.base.Preconditions;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CachingExample {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("MapToDouble Example");

        JavaSparkContext context = new JavaSparkContext(conf);

//        Preconditions.checkArgument(args.length > 1, "Provide start and end range generation");
        Integer rangeStart = Integer.parseInt(args[0]);
        Integer rangeEnd = Integer.parseInt(args[1]);

        JavaRDD<Integer> numbers = context.parallelize(IntStream.rangeClosed(rangeStart, rangeEnd).boxed().collect(Collectors.toList()));

        numbers.cache();

        System.out.println("numbers.count() = " + numbers.count());

        System.out.println("numbers.collect() = " + numbers.collect());
    }

}
