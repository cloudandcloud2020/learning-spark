package com.nileshgule.movielens;

//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.sql.DataFrame;
//import org.apache.spark.sql.SQLContext;

public class TagsCsvReaderWithKryo {
    public static void main(String[] args) {
//        SparkConf conf = new SparkConf().setAppName("Movie Lens Tags CSV Reader");
//
//        conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
//
//        JavaSparkContext context = new JavaSparkContext(conf);
//
//        SQLContext sqlContext = new SQLContext(context);
//
//        DataFrame df = sqlContext.read()
//                .format("com.databricks.spark.csv")
//                .option("inferSchema", "true")
//                .option("header", "true")
//                .load("ml-latest/tags.csv");
//
//        DataFrame tagsDF = df.select("userId", "movieId", "tag", "timestamp");
//
//        tagsDF.cache();
//
//        tagsDF.show(10, false);
//
//        System.out.println("Total tags in collection = " + tagsDF.count());
    }
}
