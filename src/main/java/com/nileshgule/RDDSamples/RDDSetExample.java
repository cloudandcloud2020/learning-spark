package com.nileshgule.RDDSamples;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

public class RDDSetExample {
    public static void main(String[] args) {
        SparkConf conf=new SparkConf();

        JavaSparkContext context = new JavaSparkContext(conf);
        context.setLogLevel("ERROR");

        JavaRDD<String> subwayMenu = context.parallelize(Arrays.asList("Sandwitch", "Soup", "Cookies"));
        JavaRDD<String> pastamaniaMenu = context.parallelize(Arrays.asList("Pasta", "Pizza", "Soup", "cold drinks"));

        JavaRDD<String> union = subwayMenu.union(pastamaniaMenu);

        System.out.println("Total items available in menu = " + union.count());

        for (String unionItem: union.collect()){
            System.out.println("item = " + unionItem);
        }

        System.out.println();

        JavaRDD<String> intersection = subwayMenu.intersection(pastamaniaMenu);

        System.out.println("intersection.count() = " + intersection.count());

        for (String intersectionItem: intersection.collect()){
            System.out.println("intersectionItem = " + intersectionItem);
        }

        System.out.println();
        
        JavaRDD<String> subtract = subwayMenu.subtract(pastamaniaMenu);

        System.out.println("subtract.count() = " + subtract.count());

        for (String subtractItem: subtract.collect()){
            System.out.println("subtractItem = " + subtractItem);
        }

    }
}
