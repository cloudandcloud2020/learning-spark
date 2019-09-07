package com.nileshgule.movielens;

import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SparkSession;

class CsvUtils {

    static DataFrame getDataFrame(SparkSession sparkSession, String inputFilePath) {
        return sparkSession.read()
                .option("inferSchema", "true")
                .option("header", "true")
                .csv(inputFilePath);
    }
}
