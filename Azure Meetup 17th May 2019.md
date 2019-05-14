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

