# MovieLens
-
### Prerequisite
Download [MovieLens dataset](https://grouplens.org/datasets/movielens/) locally on to the machine where the Spark job would be running

Assumption is that the extracted contents of the zip file are available in folder named `ml-latest` in the root directory of the project

### Run wordcount program

```bash
spark-submit --packages com.databricks:spark-csv_2.10:1.5.0 \
--class com.nileshgule.movielens.MoviesCsvReader \
--master local \
--deploy-mode client \
--executor-memory 2g \
--name WordCount \
--conf "spark.app.id=MoviesCsvReader" \
target/learning-spark-1.0.jar
```