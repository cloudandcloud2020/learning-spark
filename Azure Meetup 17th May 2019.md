# Commands for 17th May Azure Meetup

## Provision HDInsight cluster

### HDInsight cluster settings

- Cluster name - hd-spark-cluster
- Spark version - Spark 2.3
- Admin user name - admin
- Admin password - AzureMay@2019
- ssh username - sshuser
- resource group name - azure-may-2019
- Storage account name - hdsparkclusterstorage
- Storage container name - hd-spark-cluster-2019

## Local spark setup

### Install Spark 2.3.2 locally to match the HDInsight Spark version

I use homebrew for installing software on Mac. You can follow instructions on [Spark website](https://spark.apache.org) about installing the version suitable for your OS.

```bash

brew tap eddies/spark-tap
brew install apache-spark@2.3.2

```

Set `SPARK_HOME` environemnt variable

```bash

export SPARK_HOME=$(brew --prefix apache-spark@2.3.2)/libexec

```

Login to HDInsight cluster with `sshuser` credentials

```bash

ssh sshuser@hd-spark-cluster-ssh.azurehdinsight.net

```

## Build jar learning-spark-1.0 JAR using Maven

```bash

mvn package

```

## Word count example

Due to an issue with iterm and zsh, the local[4] syntax is not recognized. If the terminal problem is not existing, then the below command can be be changed to `--master local[4] \`. The 4 can be changed to the number of processor cores you want to asign depending on how many cores are available at your disposal.
  
### Local run 

```bash

spark-submit \
    --class "com.nileshgule.WordCount" \
    --master local[4] \
    target/learning-spark-1.0.jar \
    input/README.md
    
```

### Remote run 

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

## Upload jar to default container in storage account

Run the command relative to the `learning-spark-1.0.jar` file. in my case this is `/target` directory under the project root. The blob storage account name is `hdsparkclusterstorage` and the container name is `hd-spark-cluster-2019`.

```bash

az storage blob upload \
--account-name hdsparkclusterstorage \
--account-key <<put your key here>> \
--file target/learning-spark-1.0.jar \
--name learning-spark-1.0.jar \
--container-name hd-spark-cluster-2019

az storage blob upload \
--account-name hdsparkclusterstorage \
--account-key <<Put your key here>> \
--file input\README.md \
--name input\README.md \
--container-name hd-spark-cluster-2019

```

### Upload movie lens dataset to default container

I used [Azure Storage Explorer](https://azure.microsoft.com/en-us/features/storage-explorer/) to upload the dataset to default container

**Note** : while running the spark-submit command, the input path for MovieLens datasets is relative. The files are copied to the `ml-latest` directory inside the default container named `hd-spark-cluster-2019`. While passing the file names for movies.csv, ratings.csv as inputs to the spark-submit command, we can either give the relative path as `ml-latest/movies.csv` or the fully qualified path to the blob as `wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/ml-latest/movies.csv`.

## Execute Spark jobs using `spark-submit`

## Movies CSV reader example

### local mode

```bash

time \
spark-submit \
--class com.nileshgule.movielens.MoviesCsvReader \
--master local \
--deploy-mode client \
--executor-memory 2g \
--name MoviesCsvReader \
--conf "spark.app.id=MoviesCsvReader" \
target/learning-spark-1.0.jar \
ml-latest/movies.csv

```

### Cluster mode

```bash

time \
spark-submit \
--class com.nileshgule.movielens.MoviesCsvReader \
--master yarn \
--deploy-mode cluster \
--executor-memory 12g \
--name MoviesCsvReader \
--conf "spark.app.id=MoviesCsvReader" \
wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/learning-spark-1.0.jar \
/ml-latest/movies.csv

```

### Executor with `1 GB` memory assigned to it

```bash

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

```

### Executor with `3 GB` memory assigned to it

```bash

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

```

## Ratings CSV reader

### local mode

```bash

time \
spark-submit \
--class com.nileshgule.movielens.RatingsCsvReader \
--master local \
--executor-memory 3g \
--name RatingsCsvReader \
--conf "spark.app.id=RatingsCsvReader" \
target/learning-spark-1.0.jar \
ml-latest/ratings.csv

```

### Cluster mode

```bash

time \
spark-submit \
--class com.nileshgule.movielens.RatingsCsvReader \
--master yarn \
--deploy-mode cluster \
--executor-memory 3g \
--name RatingsCsvReader \
--conf "spark.app.id=RatingsCsvReader" \
wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/learning-spark-1.0.jar \
/ml-latest/ratings.csv

```

### Execute with `1 GB executor memory`

```bash

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

```

## Movie Rting Analysis

### Rating analysis local mode

```bash

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

```

### Rating Analysis Cluster mode

Referenceing the rating & movies csv using default container

```bash

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

```

Referenceing the rating & movies csv using WASB fully qualified path

```bash

spark-submit \
--class com.nileshgule.movielens.MovieRatingAnalysis \
--master yarn \
--deploy-mode cluster \
--executor-memory 1g \
--name MoviesCsvReader \
--conf "spark.app.id=MovieRatingAnalysis" \
wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/learning-spark-1.0.jar \
wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/ml-latest/ratings.csv \
wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/ml-latest/movies.csv

```

Referenceing the rating & movies csv using https fully qualified path

```bash

spark-submit \
--class com.nileshgule.movielens.MovieRatingAnalysis \
--master yarn \
--deploy-mode cluster \
--executor-memory 1g \
--name MoviesCsvReader \
--conf "spark.app.id=MovieRatingAnalysis" \
https://hdsparkclusterstorage.blob.core.windows.net/hd-spark-cluster-2019/learning-spark-1.0.jar \
https://hdsparkclusterstorage.blob.core.windows.net/hd-spark-cluster-2019/ml-latest/ratings.csv \
https://hdsparkclusterstorage.blob.core.windows.net/hd-spark-cluster-2019/ml-latest/movies.csv


```

## User Analysis example

### User analysis Cluster mode

```bash

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

```

## Hive integration with Spark examples

## Hive table read write example

```bash

time \
spark-submit \
--class com.nileshgule.HiveTableExample \
--master yarn \
--deploy-mode cluster \
--executor-memory 10g \
--name MovieRatingAnalysis \
--conf "spark.app.id=HiveTableExample" \
wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/learning-spark-1.0.jar

```

## Convert CSV to ORC example

```bash

time \
spark-submit \
--class com.nileshgule.movielens.MoviesCsvToORCConvertor \
--master yarn \
--deploy-mode cluster \
--executor-memory 10g \
--name MoviesCsvToORCConvertor \
--conf "spark.app.id=MoviesCsvToORCConvertor" \
wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/learning-spark-1.0.jar \
/ml-latest/ratings.csv

```

Verify disk usage after conversion

```bash

hdfs dfs -du -s -h wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/ml-latest/

hdfs dfs -du -s -h wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/ml-latest/ratings.csv

hdfs dfs -du -s -h /ml-latest/ratings.csv

hdfs dfs -du -s -h /ml-lates/rating-orc

```

## Movies ORC file reader example

### ORC file reader cluster mode

```bash

time \
spark-submit \
--class com.nileshgule.movielens.MoviesOrcReader \
--master yarn \
--deploy-mode cluster \
--executor-memory 1g \
--name MoviesOrcReader \
--conf "spark.app.id=MoviesOrcReader" \
wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/learning-spark-1.0.jar \
/ml-lates/rating-orc

```

```spark-shell

spark-sql

val df = spark.read.csv("ml-latest/ratings.csv")

val df = spark.read.csv("wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/ml-latest/ratings.csv")

val df = spark.read.csv("/ml-latest/ratings.csv")

df.filter("_c2 >= 3").show()

```