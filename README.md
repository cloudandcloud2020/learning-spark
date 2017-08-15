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

### Run PairRDDExample program
The last two parameters are for start and end of the range 

```bash
spark-submit --class com.nileshgule.PairRDDExample \
--master local \
--deploy-mode client \
--executor-memory 2g \
--name PairRDDExample \
--conf "spark.app.id=PairRDDExample" \
target/learning-spark-1.0.jar
```

Refer to the [AWS-EMR](AWS-EMR.md) for details on running the Spark jobs on EMR cluster.

Refer to the [Azure](Azure.md) for details on running the Spark jobs on Microsoft Azure HDInsights cluster.