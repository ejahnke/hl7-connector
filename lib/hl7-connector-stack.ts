import * as cdk from 'aws-cdk-lib';
import { Construct } from 'constructs';
import * as codecommit from 'aws-cdk-lib/aws-codecommit';
import { CodePipeline, CodePipelineSource, ShellStep } from 'aws-cdk-lib/pipelines';
import { Policy, PolicyStatement, ServicePrincipal, Effect } from 'aws-cdk-lib/aws-iam';
//after initial deployment
import * as ec2 from "aws-cdk-lib/aws-ec2";
import * as ecs from "aws-cdk-lib/aws-ecs";
import * as ecs_patterns from "aws-cdk-lib/aws-ecs-patterns";
import { CfnParameter } from 'aws-cdk-lib';
import { Queue, QueueEncryption } from 'aws-cdk-lib/aws-sqs';
import * as s3 from "aws-cdk-lib/aws-s3";
import { BlockPublicAccess, BucketEncryption } from 'aws-cdk-lib/aws-s3';


export class Hl7ConnectorStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);
    
    const hl7Vpc = new ec2.Vpc(this, "hl7Vpc", {
      cidr: "10.0.0.0/16",
      maxAzs: 2,
      natGateways: 1,
      enableDnsHostnames: true,
      enableDnsSupport: true,
     
      subnetConfiguration: [{
        cidrMask: 24,
        name: 'privateSubnets',
        subnetType: ec2.SubnetType.PRIVATE_WITH_EGRESS,
      }, {
        cidrMask: 24,
        name: 'dmz',
        subnetType: ec2.SubnetType.PUBLIC,
      }],
    });
    
       
    const clusterCustomImage = new ecs.Cluster(this, "MyHl7ClusterCustomImage", {
      vpc: hl7Vpc
    });
    
    const destQueue = new Queue (this, "hl7Queue",{
      encryption: QueueEncryption.KMS_MANAGED,
      queueName: "hl7Queue"
    });
    
    const blogBucket = new s3.Bucket(this, "my-connector-hl7-bucket", {
      bucketName: "my-connector-hl7-bucket",
      blockPublicAccess: BlockPublicAccess.BLOCK_ALL,
      encryption: BucketEncryption.S3_MANAGED,
      enforceSSL: true,
      removalPolicy: cdk.RemovalPolicy.DESTROY,
    });


    const ecsTaskExecutionRole = new PolicyStatement({
      actions: ['logs:CreateLogStream', 'logs:PutLogEvents', 'sqs:SendMessage', 's3:*'],
      resources: [destQueue.queueArn, blogBucket.bucketArn, `${blogBucket.bucketArn}/*`], //restrict this later
    });
    
    const loadBalancedFargateServiceCustomImage = new ecs_patterns.NetworkLoadBalancedFargateService(this, "MyVpcFargateServiceCustomImage", {
      cluster: clusterCustomImage, // Required
      assignPublicIp: false, 
      cpu: 256, // Default is 256
      desiredCount: 2, // Default is 1
      taskImageOptions: { image: ecs.ContainerImage.fromAsset("./hl7connectorImage"),containerPort:parseInt(this.node.tryGetContext('hl7Port')), environment: {HL7_PORT: this.node.tryGetContext('hl7Port'),HL7_QUEUE: destQueue.queueArn, HL7_BUCKET: blogBucket.bucketName }   },
      taskSubnets: {subnetGroupName: "privateSubnets"},
      listenerPort: parseInt(this.node.tryGetContext('hl7Port')),
      memoryLimitMiB: 512, // Default is 512
      publicLoadBalancer: true // Default is true
    });
      
    loadBalancedFargateServiceCustomImage.taskDefinition.taskRole.attachInlinePolicy(
      new Policy(this, 'ecs-policy', {
        statements: [ecsTaskExecutionRole],
      }),
    );
    
    const scalableTargetCustomImage = loadBalancedFargateServiceCustomImage.service.autoScaleTaskCount({
      minCapacity: 1,
      maxCapacity: 20,
    });
    
    scalableTargetCustomImage.scaleOnCpuUtilization('CpuScaling', {
      targetUtilizationPercent: 50,
    });
    
    scalableTargetCustomImage.scaleOnMemoryUtilization('MemoryScaling', {
      targetUtilizationPercent: 50,
    });
    
    
    loadBalancedFargateServiceCustomImage.service.connections.securityGroups[0].addIngressRule(ec2.Peer.ipv4(this.node.tryGetContext('sourceIP')), ec2.Port.tcp(parseInt(this.node.tryGetContext('hl7Port'))), 'HL7 from Home');
    loadBalancedFargateServiceCustomImage.service.connections.securityGroups[0].addIngressRule(ec2.Peer.ipv4("10.0.0.0/16"), ec2.Port.tcp(parseInt(this.node.tryGetContext('hl7Port'))), 'HL7 from VPC');
    
  }
}
