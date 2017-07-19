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
