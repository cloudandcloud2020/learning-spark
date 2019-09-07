package com.nileshgule.movielens;


import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

class CsvUtils {

    static Dataset<Row> getDataFrame(SparkSession sparkSession, String inputFilePath) {
        return sparkSession.read()
                .option("inferSchema", "true")
                .option("header", "true")
                .csv(inputFilePath);
    }
}
