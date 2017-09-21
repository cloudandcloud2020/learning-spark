# learning-spark

[![Build Status](https://travis-ci.org/NileshGule/learning-spark.svg?branch=master)](https://travis-ci.org/NileshGule/learning-spark)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/03a136aed4204f869ed4f70c8b803f63)](https://www.codacy.com/app/vn_nilesh/learning-spark?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=NileshGule/learning-spark&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/NileshGule/learning-spark/branch/master/graph/badge.svg)](https://codecov.io/gh/NileshGule/learning-spark)


## Prerequisites
Ensure that following prerequsites are met for running code which is part of this repository.
1. **Java 8** is installed
* [See](https://www.mkyong.com/java/how-to-set-java_home-environment-variable-on-mac-os-x/) for more details on how to set JAVA_HOME for Mac OSX 
* JAVA_HOME is set correctly
2. **Apache Spark 1.6.3** with Hadoop 2.6 is installed 
* SPARK_HOME environment variable is set correctly
3. **Maven** is installed

## Build & Run code
### Run Maven clean using command

```bash
mvn clean
```

### Run Maven compile using command

```bash
mvn compile
```

### Run Maven package using command

```bash
mvn package
```

*Note:* In order to run the spark-submit command with more than 1 thread, zsh throws error. To overcome the error use the following option
```bash
setopt nonomatch
```

### Run wordcount program

```bash
spark-submit --class com.nileshgule.WordCount \
--master local \
--deploy-mode client \
--executor-memory 2g \
--name WordCount \
--conf "spark.app.id=WordCount" \
target/learning-spark-1.0.jar
```

### Run MapToDoubleExample program

```bash
spark-submit --class com.nileshgule.MapToDoubleExample \
--master local \
--deploy-mode client \
--executor-memory 2g \
--name MapToDouble \
--conf "spark.app.id=MapToDoubleExample" \
target/learning-spark-1.0.jar
```

### Run CachingExample program
The last two parameters are for start and end of the range 

```bash
spark-submit --class com.nileshgule.CachingExample \
--master local \
--deploy-mode client \
--executor-memory 2g \
--name CachingExample \
--conf "spark.app.id=CachingExample" \
target/learning-spark-1.0.jar \
1 100
```
---
### Run PairRDDExample program
The last two parameters are for start and end of the range 

```bash
time sudo spark-submit \
--packages com.groupon.sparklint:sparklint-spark163_2.10:1.0.8 \
--conf spark.logConf=true \
--conf spark.extraListeners=com.groupon.sparklint.SparklintListener \
--class com.nileshgule.PairRDDExample \
--master yarn \
--deploy-mode client \
--executor-memory 2g \
--name PairRDDExample \
--conf "spark.app.id=PairRDDExample" \
https://ngstorageaccount.blob.core.windows.net/ng-spark-2017-08-15t16-28-42-526z/learning-spark-1.0.jar

wasb://ngstorageaccount.blob.core.windows.net/ng-spark-2017-08-18t14-24-10-259z/learning-spark-1.0.jar, expected: 
wasb://ng-spark-2017-08-18t14-24-10-259z@ngstorageaccount.blob.core.windows.net

time sudo spark-submit \
--packages com.groupon.sparklint:sparklint-spark163_2.10:1.0.8 \
--conf spark.logConf=true \
--conf spark.extraListeners=com.groupon.sparklint.SparklintListener \
--class com.nileshgule.PairRDDExample \
--master yarn \
--deploy-mode cluster \
--executor-memory 2g \
--name PairRDDExample \
--conf "spark.app.id=PairRDDExample" \
wasb://ng-spark-2017-08-18t14-24-10-259z@ngstorageaccount.blob.core.windows.net/learning-spark-1.0.jar



time spark-submit --packages com.groupon.sparklint:sparklint-spark163_2.10:1.0.8 \
--conf spark.logConf=true \
--conf spark.extraListeners=com.groupon.sparklint.SparklintListener \
--class com.nileshgule.PairRDDExample \
--master yarn \
--deploy-mode client \
--executor-memory 2g \
--name PairRDDExample \
--conf "spark.app.id=PairRDDExample" \
https://ngstorageaccount.blob.core.windows.net/ng-spark-2017-08-17t14-58-18-512z/SparkSubmission/2017/08/17/92528012-ad26-4f36-9ef2-6174305eae2a/learning-spark-1.0.jar

```
------

### Run BasicRDD program

```bash
time spark-submit --class com.nileshgule.RDDSamples.BasicRDD \
--master local \
--deploy-mode client \
--executor-memory 2g \
--name BasicRDD \
--conf "spark.app.id=BasicRDD" \
target/learning-spark-1.0.jar
```

### Run MoviesRDDExample program

```bash
time spark-submit --class com.nileshgule.RDDSamples.MoviesRDDExample \
--master local \
--deploy-mode client \
--executor-memory 2g \
--name MoviesRDDExample \
--conf "spark.app.id=MoviesRDDExample" \
target/learning-spark-1.0.jar \
ml-latest/movies.csv
```

### Run RDDSetExample program

```bash
time spark-submit --class com.nileshgule.RDDSamples.RDDSetExample \
--master local \
--deploy-mode client \
--executor-memory 2g \
--name RDDSetExample \
--conf "spark.app.id=RDDSetExample" \
target/learning-spark-1.0.jar
```

### Run MoviesRDDExample program

```bash
time spark-submit --class com.nileshgule.RDDSamples.RatingsRDDExample \
--master local \
--deploy-mode client \
--executor-memory 2g \
--name RatingsRDDExample \
--conf "spark.app.id=RatingsRDDExample" \
target/learning-spark-1.0.jar \
ml-latest/ratings.csv
```

Refer to the [AWS-EMR](AWS-EMR.md) for details on running the Spark jobs on EMR cluster.

Refer to the [Azure](Azure.md) for details on running the Spark jobs on Microsoft Azure HDInsights cluster.
