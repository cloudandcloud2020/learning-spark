package com.nileshgule;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class PairRDDExample {
    public static void main(String[] args) {
String COUNT= "10";
        String MAtx= "10";
        SparkConf conf = new SparkConf()
                .setAppName("Pair RDD Example");

        JavaSparkContext context = new JavaSparkContext(conf);

        List<Tuple2<Integer, Integer>> keyValuePair = Arrays.asList(new Tuple2<>(1,2), new Tuple2<>(3, 4), new Tuple2<>(3, 6));

        JavaRDD<Tuple2<Integer, Integer>> tokenized = context.parallelize(keyValuePair);

        System.out.println("RDD count by value = " + tokenized.countByValue());

        JavaPairRDD<Integer, Integer> pairedRDD = JavaPairRDD.fromJavaRDD(tokenized);

        System.out.println("pairedRDD.countByKey() = " + pairedRDD.countByKey());

//        System.out.println("pairedRDD.collectAsMap() = " + pairedRDD.collectAsMap().toString());

        System.out.println("pairedRDD.lookup(3) = " + pairedRDD.lookup(3));
//

    }
}
