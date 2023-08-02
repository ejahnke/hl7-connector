# Welcome to your CDK TypeScript project

This is a blank project for CDK development with TypeScript.

The `cdk.json` file tells the CDK Toolkit how to execute your app.

## Useful commands

* `npm run build`   compile typescript to js
* `npm run watch`   watch for changes and compile
* `npm run test`    perform the jest unit tests
* `cdk deploy`      deploy this stack to your default AWS account/region
* `cdk diff`        compare deployed stack with current state
* `cdk synth`       emits the synthesized CloudFormation template

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
4. bootstrap the AWS account for CDK deployments
```
cdk bootstrap
```
5. deploy the CDK stack
```
cdk deploy
```
