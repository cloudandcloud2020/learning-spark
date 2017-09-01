package com.nileshgule.movielens;

import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

public class CsvUtils {
    static DataFrame getDataFrame(SQLContext sqlContext, String inputFilePath) {
        return sqlContext.read()
                .format("com.databricks.spark.csv")
                .option("inferSchema", "true")
                .option("header", "true")
                .load(inputFilePath);
    }
}
