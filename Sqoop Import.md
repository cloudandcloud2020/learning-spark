# Info related to SQL Server import to HDinsight using Sqoop

|Property | Value |
|---|---|
| Server name | azuremay2019sql |
| Server Admin login | sqladmin |
| Password | AzureMay@2019 |
| Location | Southeast Asia |
| Allow Azure services to access server | True |

azuremay2019sql.database.windows.net

sqoop list-databases \
--connect 'jdbc:sqlserver://azuremay2019sql.database.windows.net:1433;database=AdventureWorksLT;user=sqladmin@azuremay2019sql;password=AzureMay@2019;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;'

sqoop import \
--connect 'jdbc:sqlserver://azuremay2019sql.database.windows.net:1433;database=AdventureWorksLT;user=sqladmin@azuremay2019sql;password=AzureMay@2019;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;' \
--table Customer \
-- --schema SalesLT \
--target-dir '/user/sshuser/Customer' \
-m 1;

sqoop import \
--driver com.microsoft.sqlserver.jdbc.SQLServerDriver \
--connect 'jdbc:sqlserver://azuremay2019sql.database.windows.net:1433;database=AdventureWorksLT;user=sqladmin@azuremay2019sql;password=AzureMay@2019;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;' \
--table Customer \
--schema SalesLT \
--hive-import \
--create-hive-table \
--hive-table Customer;


scp \
sqljdbc41.jar \
sshuser@hd-spark-cluster-ssh.azurehdinsight.net:home/sshuser

ls /usr/hdp/current/sqoop-client/lib/

hdfs dfs -ls wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/user/sshuser/Customer