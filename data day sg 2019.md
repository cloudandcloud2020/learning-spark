# SQL Data Day SG 2019


## Demo 1 Cluster Provisioning

1.1 - Provision HDInsight cluster using Portal

- Hive view
- Ambari view
- YARN View
- Services & configurations
Stacks and Versions
- Hosts

1.2 - Provision HDInsight cluster using Powershell

### create resource group name `datadaysghadooprg`

```Powershell

 New-AzureRmResourceGroup -Name datadaysghadooprg -Location southeastasia

```

### Create the default storage account

```Powershell

New-AzureRmStorageAccount `
    -ResourceGroupName datadaysghadooprg `
    -Name datadayhadoop `
    -Location southeastasia `
    -Type Standard_LRS

```

### Create the default Blob container

```Powershell

$defaultStorageAccountKey = (Get-AzureRmStorageAccountKey `
                                -ResourceGroupName datadaysghadooprg `
                                -Name datadayhadoop)[0].Value

$defaultStorageAccountContext = New-AzureStorageContext `
                                    -StorageAccountName datadayhadoop `
                                    -StorageAccountKey $defaultStorageAccountKey 

New-AzureStorageContainer `
    -Name hdinsighthadoopcontainer `
    -Context $defaultStorageAccountContext

```

### Create the HDInsight Hadoop cluster

```Powershell

$pw = ConvertTo-SecureString -String March@2019 -AsPlainText -Force
$httpCredential = New-Object System.Management.Automation.PSCredential("admin",$pw)
$sshCredential = New-Object System.Management.Automation.PSCredential("sshuser",$pw)

New-AzureRmHDInsightCluster `
    -ResourceGroupName datadaysghadooprg `
    -ClusterName hdinsighthadoop `
    -Location southeastasia `
    -ClusterType Hadoop  `
    -OSType Linux `
    -Version 3.6 `
    -ClusterSizeInNodes 2 `
    -HttpCredential $httpCredential `
    -SshCredential $sshCredential `
    -DefaultStorageAccountName "datadayhadoop.blob.core.windows.net" `
    -DefaultStorageAccountKey $defaultStorageAccountKey `
    -DefaultStorageContainer hdinsighthadoopcontainer  

```

### Validate the cluster

```Powershell

Get-AzureRmHDInsightCluster -ClusterName $hdinsighthadoop

```

## ssh connection

```bash

ssh sshuser@DataDaySG-ssh.azurehdinsight.net

```

---

## Demo 2 Data transfer

- Azure SQL Database
- Sqoop import
- HDFS Data
- Storage Explorer

### Beeline

```bash

beeline -u 'jdbc:hive2://DataDaySG.azurehdinsight.net:443/;ssl=true;transportMode=http;httpPath=/hive2' -n admin -p DataDaySG@2019

```

`List Databases`

```bash

sqoop list-databases \
--connect 'jdbc:sqlserver://sqldatadaysg.database.windows.net:1433;database=AdventureWorksLT;user=sqldatadaysgadmin@sqldatadaysg;password=March@2019;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;'

```

## 2.1 sqoop import

```bash

sqoop import \
--connect 'jdbc:sqlserver://sqldatadaysg.database.windows.net:1433;database=AdventureWorksLT;user=sqldatadaysgadmin@sqldatadaysg;password=March@2019;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;' \
--table Customer \
-- --schema SalesLT \
--target-dir '/user/sshuser/Customer' \
-m 1;

ls -ltr /user/sshuser/Customer/

hdfs dfs -rm -r -f /user/sshuser/Customer/

hdfs dfs -ls -R -h /user/sshuser/Customer


hdfs dfs -text /user/sshuser/Customer/part-m-00000

```

### Hive tables

```bash

hdfs dfs -ls -R -h /user/sshuser/ml-100k/

hdfs dfs -text /user/sshuser/ml-100k/u.item

```
create database adventureworks;

Create `user_data` table

```sql

CREATE TABLE user_data 
(
  userid INT,
  movieid INT,
  rating INT,
  unixtime STRING
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
STORED AS TEXTFILE;

```

Load data into table

```bash

LOAD DATA INPATH '/user/sshuser/ml-100k/u.data'
OVERWRITE INTO TABLE user_data;

```

Create `user_data_orc` table

``` sql
CREATE TABLE user_data_orc 
(
  userid INT,
  movieid INT,
  rating INT,
  unixtime STRING
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
STORED AS ORC;

```

Select into orc table

```sql

INSERT INTO TABLE user_data_orc SELECT * FROM user_data;

```

Verify data size

```bash

hdfs dfs -ls -R -h  /hive/warehouse/adventureworksdb.db/

```

### Query Hive data using Spark from Jupyter notebook

### Query CSV data using Spark from Jupyter notebook

### PowerBI connection to Spark Data using Direct Query

---

## References

[Hortonworks cheatsheet SQL to Hive](http://hortonworks.com/wp-content/uploads/2016/05/Hortonworks.CheatSheet.SQLtoHive.pdf)

[HDInsight introduction](https://docs.microsoft.com/en-us/azure/hdinsight/hadoop/apache-hadoop-introduction)