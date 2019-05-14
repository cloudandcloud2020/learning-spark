package com.nileshgule.RDDSamples;

import java.util.List;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;



public class BasicRDD {
    public static void main(String[] args) {
       SparkConf conf=new SparkConf().setAppName("MapToDouble Example");

       JavaSparkContext context = new JavaSparkContext(conf);
       context.setLogLevel("ERROR");

       JavaRDD<String> textFile = context.textFile("input/README.md");
       JavaRDD<String> linesContainingSpark = textFile.filter(line -> line.contains("Spark"));

       JavaRDD<String> linesContainingApache = textFile.filter(line -> line.contains("Apache"));

       linesContainingSpark.persist(StorageLevel.MEMORY_ONLY());

       System.out.println("Lines containing Spark = " + linesContainingSpark.count());

       System.out.println("First line containing Spark = " + linesContainingSpark.first());

       System.out.println("Lines containing Apache = " + linesContainingApache.count());

       JavaRDD<String> union = linesContainingApache.union(linesContainingSpark);

       System.out.println("Lines containing Apache & Spark = " + union.count());

       System.out.println("Distinct lines containing Apache & Spark = " + union.distinct().count());

       List<String> fiveLines = union.take(5);
       for (String line : fiveLines) {
           System.out.println("line = " + line);
       }

    }
}
