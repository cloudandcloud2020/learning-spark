package com.nileshgule;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;


public class WordCount {

    public static void main(String[] args){

        SparkSession spark = SparkSession
                .builder()
                .appName("JavaWordCount")
                .getOrCreate();


        String logFile = "input/README.md";

        Dataset<String> logData = spark
                .read()
                .textFile(logFile)
                .cache();

        long numAs = logData.filter(s -> s.contains("a")).count();
        long numBs = logData.filter(s -> s.contains("b")).count();

        System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);

        spark.stop();

    }
}
