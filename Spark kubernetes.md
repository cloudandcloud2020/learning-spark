# Instructions related to runing spark on Kubernetes

cd /usr/local/Cellar/apac….3.2/2.3.2/libexec

docker-image-tool.sh -r nileshgule -t v1.0 build

docker-image-tool.sh -r nileshgule -t v1.0 push

kubectl auth can-i <list|create|edit|delete> pods

kubectl auth can-i list pods
kubectl auth can-i create pods
kubectl auth can-i edit pods
kubectl auth can-i delete pods

bin/spark-submit \
    --master k8s://https://<k8s-apiserver-host>:<k8s-apiserver-port> \
    --deploy-mode cluster \
    --name spark-pi \
    --class org.apache.spark.examples.SparkPi \
    --conf spark.executor.instances=5 \
    --conf spark.kubernetes.container.image=<spark-image> \
    local:///path/to/examples.jar

az aks get-credentials \
--resource-group=techtalksrg \
--name=techtalksCluster

az aks browse \
--resource-group=techtalksrg \
--name=techtalksCluster


spark-submit \
--class com.nileshgule.movielens.MoviesCsvReader \
--master k8s://https://techtalksdns-7974de02.hcp.southeastasia.azmk8s.io:443 \
--deploy-mode cluster \
--executor-memory 2g \
--name MoviesCsvReader \
--conf "spark.app.id=MoviesCsvReader" \
--conf spark.executor.instances=2 \
--conf spark.kubernetes.driver.pod.name=spark-movies-csv-reader \
--conf spark.kubernetes.container.image=nileshgule/spark:v1.0 \
https://sparkkubernetessa.dfs.core.windows.net/may-2019/learning-spark-1.0.jar \
https://sparkkubernetessa.dfs.core.windows.net/may-2019/ml-latest/movies.csv

spark-submit \
--master k8s://https://techtalksdns-7974de02.hcp.southeastasia.azmk8s.io:443 \
--deploy-mode cluster \
--executor-memory 2g \
--name spark-pi \
--class org.apache.spark.examples.SparkPi \
--conf spark.executor.instances=2 \
--conf spark.kubernetes.container.image=nileshgule/spark:v1.0 \
--conf spark.kubernetes.driver.pod.name=spark-pi-driver \
local:///usr/local/Cellar/apac….3.2/2.3.2/libexec/examples/jars/spark-examples_2.11-2.3.0.jar

kubectl logs -f spark-movies-csv-reader

Storage account name - sparkkubernetessa

export AZCOPY_CRED_TYPE=SharedKey;
export ACCOUNT_NAME=sparkkubernetessa;
export ACCOUNT_KEY=OR0oGlNJRakjuyP6s5W3Ejtca249/AcDyMhAEY3NijeWHvmtlqoUSBh0DBNsxknv+8GZSlYFldycL1wRLT62aQ==;
./azcopy copy "/Users/nileshgule/projects/learning-spark/ml-latest/" "https://sparkkubernetessa.dfs.core.windows.net/may-2019/" --overwrite=false --follow-symlinks --recursive --from-to=LocalBlobFS --put-md5;
unset AZCOPY_CRED_TYPE;
unset ACCOUNT_NAME;
unset ACCOUNT_KEY;

kubectl create serviceaccount spark

kubectl create clusterrolebinding spark-role --clusterrole=edit --serviceaccount=default:spark --namespace=default

spark-submit \
--class com.nileshgule.movielens.MoviesCsvReader \
--master k8s://https://techtalksdns-7974de02.hcp.southeastasia.azmk8s.io:443 \
--deploy-mode cluster \
--executor-memory 2g \
--name MoviesCsvReader \
--conf "spark.app.id=MoviesCsvReader" \
--conf spark.executor.instances=2 \
--conf spark.kubernetes.driver.pod.name=spark-movies-csv-reader \
--conf spark.kubernetes.container.image=nileshgule/spark:v1.0 \
--conf spark.kubernetes.authenticate.driver.serviceAccountName=spark \
wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/learning-spark-1.0.jar \
wasb://hd-spark-cluster-2019@hdsparkclusterstorage.blob.core.windows.net/ml-latest/movies.csv

spark-submit \
--class com.nileshgule.movielens.MoviesCsvReader \
--master k8s://https://techtalksdns-7974de02.hcp.southeastasia.azmk8s.io:443 \
--deploy-mode cluster \
--executor-memory 2g \
--name MoviesCsvReader \
--conf "spark.app.id=MoviesCsvReader" \
--conf spark.executor.instances=2 \
--conf spark.kubernetes.driver.pod.name=spark-movies-csv-reader \
--conf spark.kubernetes.container.image=nileshgule/spark:v1.0 \
--conf spark.kubernetes.authenticate.driver.serviceAccountName=spark \
local:///target/learning-spark-1.0.jar \
local:///ml-latest/movies.csv

spark-submit \
--class com.nileshgule.movielens.MoviesCsvReader \
--master local[4] \
--executor-memory 2g \
--name MoviesCsvReader \
--conf "spark.app.id=MoviesCsvReader" \
--conf spark.executor.instances=2 \
https://hdsparkclusterstorage.blob.core.windows.net/hd-spark-cluster-2019/learning-spark-1.0.jar \
https://hdsparkclusterstorage.blob.core.windows.net/hd-spark-cluster-2019/ml-latest/movies.csv

spark-submit \
--class com.nileshgule.movielens.MoviesCsvReader \
--master k8s://https://techtalksdns-7974de02.hcp.southeastasia.azmk8s.io:443 \
--deploy-mode cluster \
--executor-memory 2g \
--name MoviesCsvReader \
--conf "spark.app.id=MoviesCsvReader" \
--conf spark.executor.instances=2 \
--files https://hdsparkclusterstorage.blob.core.windows.net/hd-spark-cluster-2019/ml-latest/movies.csv \
--conf spark.kubernetes.driver.pod.name=spark-movies-csv-reader \
--conf spark.kubernetes.container.image=nileshgule/spark:v1.0 \
https://hdsparkclusterstorage.blob.core.windows.net/hd-spark-cluster-2019/learning-spark-1.0.jar \
movies.csv

time \
spark-submit \
--class com.nileshgule.movielens.MovieRatingAnalysis \
--master k8s://https://techtalksdns-7974de02.hcp.southeastasia.azmk8s.io:443 \
--deploy-mode cluster \
--executor-memory 2g \
--name MovieRatingAnalysis \
--conf "spark.app.id=MovieRatingAnalysis" \
--conf spark.executor.instances=2 \
--files https://hdsparkclusterstorage.blob.core.windows.net/hd-spark-cluster-2019/ml-latest/movies.csv \
--files https://hdsparkclusterstorage.blob.core.windows.net/hd-spark-cluster-2019/ml-latest/ratings.csv \
--conf spark.kubernetes.driver.pod.name=spark-movies-rating-alanysis \
--conf spark.kubernetes.container.image=nileshgule/spark:v1.0 \
--conf spark.kubernetes.authenticate.driver.serviceAccountName=spark \
--conf "spark.app.id=MovieRatingAnalysis" \
https://hdsparkclusterstorage.blob.core.windows.net/hd-spark-cluster-2019/learning-spark-1.0.jar \
ratings.csv \
movies.csv

time \
spark-submit \
--class com.nileshgule.movielens.MovieRatingAnalysis \
--master k8s://https://techtalksdns-7974de02.hcp.southeastasia.azmk8s.io:443 \
--deploy-mode cluster \
--executor-memory 2g \
--name MovieRatingAnalysis \
--conf "spark.app.id=MovieRatingAnalysis" \
--conf spark.executor.instances=2 \
--files https://hdsparkclusterstorage.blob.core.windows.net/hd-spark-cluster-2019/ml-latest/movies.csv,https://hdsparkclusterstorage.blob.core.windows.net/hd-spark-cluster-2019/ml-latest/ratings.csv \
--conf spark.kubernetes.driver.pod.name=spark-movies-rating-alanysis \
--conf spark.kubernetes.container.image=nileshgule/spark:v1.0 \
--conf spark.kubernetes.authenticate.driver.serviceAccountName=spark \
--conf "spark.app.id=MovieRatingAnalysis" \
https://hdsparkclusterstorage.blob.core.windows.net/hd-spark-cluster-2019/learning-spark-1.0.jar \
ratings.csv \
movies.csv

## ORC reader example

az storage blob upload \
--account-name hdsparkclusterstorage \
--file target/learning-spark-1.0.jar \
--name learning-spark-1.0.jar \
--container-name hd-spark-cluster-2019

```bash

time \
spark-submit \
--class com.nileshgule.movielens.MoviesOrcReader \
--name MoviesOrcReader \
--conf "spark.app.id=MoviesOrcReader" \
--master k8s://https://techtalksdns-7974de02.hcp.southeastasia.azmk8s.io:443 \
--deploy-mode cluster \
--executor-memory 2g \
--name MovieRatingAnalysis \
--conf "spark.app.id=MoviesOrcReader" \
--conf spark.executor.instances=2 \
--files https://hdsparkclusterstorage.blob.core.windows.net/hd-spark-cluster-2019/ml-lates/rating-orc/ \
--conf spark.kubernetes.container.image=nileshgule/spark:v1.0 \
--conf spark.kubernetes.authenticate.driver.serviceAccountName=spark \
https://hdsparkclusterstorage.blob.core.windows.net/hd-spark-cluster-2019/learning-spark-1.0.jar \
https://hdsparkclusterstorage.blob.core.windows.net/hd-spark-cluster-2019/ml-lates/rating-orc/

```


export AZURE_STORAGE_CONNECTION_STRING=`az storage account show-connection-string --resource-group azure-may-2019 --name hdsparkclusterstorage -o tsv`

az storage blob url --container-name hd-spark-cluster-2019 --name ml-latest | tr -d '"'

echo "Creating the container..."
az storage container set-permission --name hd-spark-cluster-2019 --public-access blob

echo "Uploading the file..."
az storage blob upload --container-name $CONTAINER_NAME --file $FILE_TO_UPLOAD --name $BLOB_NAME

jarUrl=$(az storage blob url --container-name $CONTAINER_NAME --name $BLOB_NAME | tr -d '"')