# Azure

Use HDInsight Spark cluster to run Spark jobs. Refer to [introduction to Spark on HD insight](https://docs.microsoft.com/en-us/azure/hdinsight/hdinsight-apache-spark-overview)

Intellij IDE can submmit jobs to HDInsight cluster directly using the [Azure Toolkit for Intellij](https://docs.microsoft.com/en-us/azure/hdinsight/hdinsight-apache-spark-intellij-tool-plugin)

**Most Important:** Don't forget to [delete the cluster](https://docs.microsoft.com/en-us/azure/hdinsight/hdinsight-delete-cluster)


## Azure CLI
To work with Azure resources using command line tools [Azure CLI](https://docs.microsoft.com/en-sg/cli/azure/install-azure-cli) can be installed.

Troubleshooting [ssh connectivity](https://github.com/twright-msft/azure-content/blob/master/articles/hdinsight/hdinsight-hadoop-linux-use-ssh-unix.md) problems to the head node

Reference to the blobstorage [commandline](https://docs.microsoft.com/en-us/cli/azure/storage/blob#uploads)

To fix the ECDSA key fingerprint command run
`ssh-keygen -R ng-spark-ssh.azurehdinsight.net`

### List keys & containers from blob storage

```bash

az storage account keys list --resource-group ngresourcegroup --account-name ngstorageaccount

az storage container list --account-name ngstorageaccount --account-key btQLcwrSfGXolrdtnXt0115rizP24U+JFH7M9uWQxcyQ2gASp3+lxIAe1+44U4JFMvBH8ZDZT30TJh5q4p0lIg==

```

### Create container to store Movielens dataset
```bash

    az storage container create --name movielens-dataset \
    --account-name ngstorageaccount \
    --account-key btQLcwrSfGXolrdtnXt0115rizP24U+JFH7M9uWQxcyQ2gASp3+lxIAe1+44U4JFMvBH8ZDZT30TJh5q4p0lIg==

```

### Copy / move data from existing blob to the new one

```bash

az storage blob copy start-batch \
                                 --account-name ngstorageaccount \
                                 --account-key btQLcwrSfGXolrdtnXt0115rizP24U+JFH7M9uWQxcyQ2gASp3+lxIAe1+44U4JFMvBH8ZDZT30TJh5q4p0lIg== \
                                 --destination-container movielens-dataset \
                                 --pattern "movielense_dataset/*" \
                                 --source-container ng-spark-2017-08-18t14-24-10-259z
                                 
```

### Upload application jar to blobstorage
```bash

az storage blob upload \
--account-name ngstorageaccount \
--account-key btQLcwrSfGXolrdtnXt0115rizP24U+JFH7M9uWQxcyQ2gASp3+lxIAe1+44U4JFMvBH8ZDZT30TJh5q4p0lIg== \
--file learning-spark-1.0.jar \
--name learning-spark-1.0.jar \
--container-name movielens-dataset

```

### upload movielense dataset files to blobstore

```bash

az storage blob upload-batch \
--destination ng-spark-2017-08-18t14-24-10-259z/movielense_dataset \
--source ml-latest \
--account-name ngstorageaccount \
--account-key btQLcwrSfGXolrdtnXt0115rizP24U+JFH7M9uWQxcyQ2gASp3+lxIAe1+44U4JFMvBH8ZDZT30TJh5q4p0lIg==

``` 

### Run Spark Submit
```bash

spark-submit \
--class com.nileshgule.PairRDDExample \
--master yarn \
--deploy-mode cluster \
--executor-memory 2g \
--name PairRDDExample \
--conf "spark.app.id=PairRDDExample" \
wasb://ng-spark-2017-08-18t14-24-10-259z@ngstorageaccount.blob.core.windows.net/learning-spark-1.0.jar


spark-submit \
--packages com.databricks:spark-csv_2.10:1.5.0 \
--class com.nileshgule.movielens.RatingsCsvReader \
--master yarn \
--deploy-mode cluster \
--executor-memory 1g \
--name RatingCSVReader \
--conf "spark.app.id=RatingCsvReader" \
wasb://movielens-dataset@ngstorageaccount.blob.core.windows.net/learning-spark-1.0.jar \
wasb://movielens-dataset@ngstorageaccount.blob.core.windows.net/movielens-dataset/movielense_dataset/ratings.csv

```