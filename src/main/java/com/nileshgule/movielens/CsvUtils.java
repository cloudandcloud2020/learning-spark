package com.nileshgule.movielens;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class CsvUtils {

    static Dataset<Row> getDataset(SparkSession sparkSession, String inputFilePath) {
        return sparkSession.read()
                .option("inferSchema", "true")
                .option("header", "true")
                .csv(inputFilePath);
    }
}
