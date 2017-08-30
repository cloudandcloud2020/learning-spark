#Serialization performance

The objective of this test is to do a comparison between Java serialization and Kryo serialization

Running with default serializer

```bash
time spark-submit \
--packages com.databricks:spark-csv_2.10:1.5.0 \
--class com.nileshgule.movielens.TagsCsvReader \
--master local \
--deploy-mode client \
--executor-memory 2g \
--name TagsCsvReader \
--conf "spark.app.id=TagsCsvReader" \
target/learning-spark-1.0.jar
```
Result
18.50s user 0.67s system 180% cpu 10.644 total

Running with defaultKryo serializer

```bash
time spark-submit \
--packages com.databricks:spark-csv_2.10:1.5.0 \
--class com.nileshgule.movielens.TagsCsvReaderWithKryo \
--master local \
--deploy-mode client \
--executor-memory 2g \
--name TagsCsvReaderWithKryo \
--conf "spark.app.id=TagsCsvReaderWithKryo" \
target/learning-spark-1.0.jar
```

Result
18.67s user 0.74s system 176% cpu 10.967 total  

Ratings csv reader 
```bash
time spark-submit \
--packages com.databricks:spark-csv_2.10:1.5.0 \
--class com.nileshgule.movielens.RatingsCsvReader \
--master local \
--deploy-mode client \
--name RatingsCsvReader \
--conf "spark.app.id=RatingsCsvReader" \
target/learning-spark-1.0.jar \
ml-latest/ratings.csv
```

Result
169.62s user 3.88s system 119% cpu 2:25.30 total

Ratings csv reader with Kryo
```bash
time spark-submit \
--packages com.databricks:spark-csv_2.10:1.5.0 \
--class com.nileshgule.movielens.RatingsCsvReaderWithKryo \
--master local \
--deploy-mode client \
--name RatingsCsvReaderWithKryo \
--conf "spark.app.id=RatingsCsvReaderWithKryo" \
target/learning-spark-1.0.jar \
ml-latest/ratings.csv
```
Result
195.75s user 4.49s system 99% cpu 3:20.78 total