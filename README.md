# Welcome to your HL7 Connector CDK TypeScript project

## Solution Overview
![image](https://github.com/ejahnke/hl7-connector/assets/94935251/7710fd06-b72b-409c-acaa-b6f063601353)

This CDK stack deploys the above components into a new VPC in the target account.

1. An internet-facing network load balancer will be instantiated to receive inbound messages on the configured HL7 port
2. An AWS Fargate cluster running Apache Camel will be deployed for message parsing and handling
3. An Amazon S3 bucket is created by the CDK to store message payload
4. An Amazon SQS queue is created to store message and event metadata
5. Once messages are received, they will be forwarded to the AWS Fargate containers running Apache Camel. They will:
      a. parse and acknowledge messages
      b. store the full message payload in Amazon S3
      c. store message and event metadata in AWS SQS
6. Consumers will be able to pop messages from the AWS SQS queue and reference the full message payload in Amazon S3

## Deployment steps

1. Launch an AWS Cloud9 m5.large environment in the ca-central-1 region with default settings
2. Open the IDE environment and clone the provided git repo
```
git clone https://github.com/ejahnke/hl7-connector.git
```
3. navigate into the recently cloned folder workshopVpc and install required libraries
```
cd hl7-connector
```
```
npm install aws-cdk-lib
```
4. If required, modify the HL7 listening port value under hl7Port in cdk.json
5. You MUST configure the IP address of the sending application in the lib/hl7-connector-stack.ts file (line 113). This will allow connections from the source IP into the AWS Fargate containers
   
6. bootstrap the AWS account for CDK deployments
```
cdk bootstrap
```
6. deploy the CDK stack
```
cdk deploy
```
