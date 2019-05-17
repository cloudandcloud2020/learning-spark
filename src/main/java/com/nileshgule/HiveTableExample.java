package com.nileshgule;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

import java.io.File;

public class HiveTableExample {
    public static void main(String[] args) {

        // warehouseLocation points to the default location for managed databases and tables
        String warehouseLocation = new File("spark-warehouse").getAbsolutePath();

        SparkSession spark = SparkSession
                .builder()
                .appName("Spark Hive Reader")
                .config("spark.sql.warehouse.dir", warehouseLocation)
                .enableHiveSupport()
                .getOrCreate();

        Dataset<Row> sampleTable = spark.sql("select * from hivesampletable");

        sampleTable.show();

        Dataset<Row> coloradoRecords = sampleTable.filter("state = 'Colorado'");

        coloradoRecords.show();

        coloradoRecords
                .write()
                .mode(SaveMode.Overwrite)
                .saveAsTable("ColoradoTable");


    }
}
