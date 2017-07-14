# learning-spark

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
spark-submit --class com.nileshgule.Main \
--master local --deploy-mode client --executor-memory 2g \
--name wordcount --conf "spark.app.id=wordcount" \
target/learning-spark-1.0.jar
`