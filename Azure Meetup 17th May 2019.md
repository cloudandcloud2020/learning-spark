# Commands for 17th March Azure Meetup

## HDInsight cluster settings
Cluster name - hd-spark-cluster
Spark version - Spark 2.3
Admin user name - admin
Admin password - AzureMay@2019
ssh username - sshuser
resource group name - azure-may-2019
Storage account name - hdsparkclusterstorage
Storage container name - hd-spark-cluster-2019

brew tap eddies/spark-tap
brew install apache-spark@2.3.2

export SPARK_HOME=$(brew --prefix apache-spark@2.3.2)/libexec

ssh sshuser@hd-spark-cluster-ssh.azurehdinsight.net

spark-submit \
  --class "com.nileshgule.WordCount" \
  --master local[4] \
  target/learning-spark-1.0.jar
  
## Local run 

```bash

spark-submit \
    --class "com.nileshgule.WordCount" \
    --master local[4] \
    target/learning-spark-1.0.jar \
    input/README.md
    
```

## Remote run 

```bash

spark-submit \
--class com.nileshgule.WordCount \
--master yarn \
--deploy-mode cluster \
--executor-memory 2g \
--name WordCount \
--conf "spark.app.id=WordCount" \
wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/learning-spark-1.0.jar \
/input/README.md
    
```  

## Pair RDD example

```bash

spark-submit \
--class com.nileshgule.PairRDDExample \
--master local[4] \
target/learning-spark-1.0.jar

```
  

az storage blob upload \
--account-name hdsparkclusterstorage \
--account-key 7fWrfquebMPAYZli6LuGe+xFYa2AgqHinZXoGJVmjAByGekTYOpaUgM0g0QZPomprgR0bm9Xzh1Hua6IBvi9XA== \
--file learning-spark-1.0.jar \
--name learning-spark-1.0.jar \
--container-name hd-spark-cluster-2019

az storage blob upload \
--account-name hdsparkclusterstorage \
--account-key 7fWrfquebMPAYZli6LuGe+xFYa2AgqHinZXoGJVmjAByGekTYOpaUgM0g0QZPomprgR0bm9Xzh1Hua6IBvi9XA== \
--file input\README.md \
--name input\README.md \
--container-name hd-spark-cluster-2019

time \
spark-submit \
--class com.nileshgule.movielens.MoviesCsvReader \
--master local \
--deploy-mode client \
--executor-memory 2g \
--name MoviesCsvReader \
--conf "spark.app.id=MoviesCsvReader" \
target/learning-spark-1.0.jar

time \
spark-submit \
--class com.nileshgule.movielens.MoviesCsvReader \
--master yarn \
--deploy-mode cluster \
--executor-memory 2g \
--name MoviesCsvReader \
--conf "spark.app.id=MoviesCsvReader" \
wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/learning-spark-1.0.jar \
/ml-latest/movies.csv

time \
spark-submit \
--class com.nileshgule.movielens.MoviesCsvReader \
--master yarn \
--deploy-mode cluster \
--executor-memory 1g \
--name MoviesCsvReader \
--conf "spark.app.id=MoviesCsvReader" \
wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/learning-spark-1.0.jar \
/ml-latest/movies.csv

time \
spark-submit \
--class com.nileshgule.movielens.MoviesCsvReader \
--master yarn \
--deploy-mode cluster \
--executor-memory 3g \
--name MoviesCsvReader \
--conf "spark.app.id=MoviesCsvReader" \
wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/learning-spark-1.0.jar \
/ml-latest/movies.csv

time \
spark-submit \
--class com.nileshgule.movielens.RatingsCsvReader \
--master yarn \
--deploy-mode cluster \
--executor-memory 3g \
--name MoviesCsvReader \
--conf "spark.app.id=RatingsCsvReader" \
wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/learning-spark-1.0.jar \
/ml-latest/ratings.csv

time \
spark-submit \
--class com.nileshgule.movielens.RatingsCsvReader \
--master yarn \
--deploy-mode cluster \
--executor-memory 1g \
--name MoviesCsvReader \
--conf "spark.app.id=RatingsCsvReader" \
wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/learning-spark-1.0.jar \
/ml-latest/ratings.csv

time \
spark-submit \
--class com.nileshgule.movielens.MovieRatingAnalysis \
--master yarn \
--deploy-mode cluster \
--executor-memory 1g \
--name MoviesCsvReader \
--conf "spark.app.id=MovieRatingAnalysis" \
wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/learning-spark-1.0.jar \
/ml-latest/ratings.csv \
/ml-latest/movies.csv

time \
spark-submit \
--class com.nileshgule.movielens.UserAnalysis \
--master yarn \
--deploy-mode cluster \
--executor-memory 10g \
--name UserAnalysis \
--conf "spark.app.id=UserAnalysis" \
wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/learning-spark-1.0.jar \
/ml-latest/ratings.csv

time \
spark-submit \
--class com.nileshgule.movielens.MovieRatingAnalysis \
--master local \
--executor-memory 4g \
--name MovieRatingAnalysis \
--conf "spark.app.id=MovieRatingAnalysis" \
target/learning-spark-1.0.jar \
ml-latest/ratings.csv \
ml-latest/movies.csv

time \
spark-submit \
--class com.nileshgule.HiveTableExample \
--master yarn \
--deploy-mode cluster \
--executor-memory 10g \
--name MovieRatingAnalysis \
--conf "spark.app.id=HiveTableExample" \
wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/learning-spark-1.0.jar

time \
spark-submit \
--class com.nileshgule.movielens.MoviesCsvToORCConvertor \
--master yarn \
--deploy-mode cluster \
--executor-memory 10g \
--name MoviesCsvToORCConvertor \
--conf "spark.app.id=MoviesCsvToORCConvertor" \
wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/learning-spark-1.0.jar \
/ml-latest/movies.csv


```spark-shell



```