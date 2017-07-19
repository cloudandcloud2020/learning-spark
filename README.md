# learning-spark

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

`
mvn clean
`

### Run Maven compile using command

`
mvn compile
`

### Run Maven package using command

`
mvn package
`


### Run wordcount program

`
spark-submit --class com.nileshgule.WordCount \
--master local \
--deploy-mode client \
--executor-memory 2g \
--name WordCount \
--conf "spark.app.id=WordCount" \
target/learning-spark-1.0.jar
`

### Run MapToDoubleExample program

`
spark-submit --class com.nileshgule.MapToDoubleExample \
--master local \
--deploy-mode client \
--executor-memory 2g \
--name MapToDouble \
--conf "spark.app.id=MapToDoubleExample" \
target/learning-spark-1.0.jar
`

### Run CachingExample program
The last two parameters are for start and end of the range 

`
spark-submit --class com.nileshgule.CachingExample \
--master local \
--deploy-mode client \
--executor-memory 2g \
--name CachingExample \
--conf "spark.app.id=CachingExample" \
target/learning-spark-1.0.jar \
1 100
`