package com.nileshgule;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;


public class WordCount {

    public static void main(String[] args){

        SparkSession spark = SparkSession
                .builder()
                .appName("JavaWordCount")
                .getOrCreate();


        String readmeFilePath = args[0];

//      "input/README.md";
//      "wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/input/README.md";
//      "/input/README.md";



        Dataset<String> logData = spark
                .read()
                .textFile(readmeFilePath)
                .cache();

        long numAs = logData.filter(s -> s.contains("a")).count();
        long numBs = logData.filter(s -> s.contains("b")).count();

        System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);

        spark.stop();

    }
}
