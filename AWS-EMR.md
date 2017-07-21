## Steps for running Spark using AWS EMR cluster ##

### Check ***aswcli*** is installed ###
```bash
pip show awscli
```

### Setup identity for cluster using AWS console ###
Generate access keys
Create inline policy for following resources with all actions
1. AWS EC2
2. AWS S3
3. AWS IAM
4. AWS EMR
 
### Configure AWS cli and create default roles using commandline
 ```bash
aws configure

aws emr create-default-roles
```

### Generate KeyPair for ssh access
Generate Key Pair using AWS console
Change the ownership of the downloaded key file using command
```bash
chmod 400 ~keyfile path~
```

### Create json configuration for Java8 ###
EMR clusters by default use Java 7. To use Java 8 the configuration needs to be provided at the time of creation of cluster
Refer to [Java8.json](EMR-Conf/Java8.json) for more details

### Create cluster using AWS cli
 ```bash
aws emr create-cluster \
--name SparkCluster \
--release-label emr-4.8.4 \
--instance-type t2.micro \
--instance-count 3 \
--applications Name=Spark \
--use-default-roles \
--ec2-attributes KeyName=SparkCourse \
--configurations https://ng-spark.s3.amazonaws.com/Java8.json
```

*SparkCourse* is the name of the .pem file downloaded in the previous step
*emr-4.8.4* is the AMI with Spark 1.6.3 version

Refer to [AWS Documentation - EMR Configure Java 8](http://docs.aws.amazon.com/emr/latest/ReleaseGuide/emr-configure-apps.html#configuring-java8)

S3 url can be specified in two ways
https://s3-ap-southeast-1.amazonaws.com/ng-spark/Java8.json
https://ng-spark.s3.amazonaws.com/Java8.json

Refer to [AWS Documentation](http://docs.aws.amazon.com/general/latest/gr/rande.html#s3_region) for the endpoint specification.

### ssh into the newly created cluster
```bash
ssh -i SparkCourse.pem hadoop@ec2-52-77-233-71.ap-southeast-1.compute.amazonaws.com -D 8157
```

**-i** is switch for passing identity file
**-D** is switch for dynamic port forwarding

You can refer to [AWS Documentation - Connect to master node using ssh](http://docs.aws.amazon.com/emr/latest/ManagementGuide/emr-connect-master-node-ssh.html) for more details.

*Note*
There was a timeout error while trying to connect using ssh. I had to edit the security group configuration to allow SSh traffic from my own laptop ip.
Refer to [AWS Documentation - Authorizing inbound traffic to Linux instances](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/authorizing-access-to-an-instance.html) for more details.

Run the following command to enable port forwarding
```bash
export SPARK_PUBLIC_DNS=ec2-52-77-233-71.ap-southeast-1.compute.amazonaws.com
```

Copy the jar to S3 bucket

Execute spark submit pointing to the jar from S3 bucket
```bash
spark-submit --master yarn-cluster \
--class com.nileshgule.CachingExample \
s3://ng-spark/learning-spark-1.0.jar \
1 1000
```

Run the MapToDouble example
```bash
spark-submit --class com.nileshgule.MapToDoubleExample \
--master yarn-cluster \
s3://ng-spark/learning-spark-1.0.jar
```

### Access Spark UI using foxyproxy method
Download & install [foxyproxy](http://foxyproxy.mozdev.org/downloads.html) for Chrome or Firefox as browser plugin.
Import the [foxyproxy-settings.xml](EMR-Conf/foxyproxy-settings.xml)

Running application can be viewed using
ec2-52-77-233-71.ap-southeast-1.compute.amazonaws.com:20888/proxy/application_1500608925666_0002/

Spark history server can be exposed using
ec2-52-77-233-71.ap-southeast-1.compute.amazonaws.com:18080

Terminate the cluster after usage
```bash
aws emr terminate-clusters --cluster-ids j-RN5OCV563XXH
```

Replace `j-RN5OCV563XXH` with the actual cluster id