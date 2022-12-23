import * as cdk from 'aws-cdk-lib';
import { Construct } from 'constructs';
import * as codecommit from 'aws-cdk-lib/aws-codecommit';
import { CodePipeline, CodePipelineSource, ShellStep } from 'aws-cdk-lib/pipelines';
import { Policy, PolicyStatement } from 'aws-cdk-lib/aws-iam';
//after initial deployment
import * as ec2 from "aws-cdk-lib/aws-ec2";
import * as ecs from "aws-cdk-lib/aws-ecs";
import * as ecs_patterns from "aws-cdk-lib/aws-ecs-patterns";
import { CfnParameter } from 'aws-cdk-lib';
import { Queue, QueueEncryption } from 'aws-cdk-lib/aws-sqs';


export class Hl7ConnectorStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);
    
    const blogVpc = new ec2.Vpc(this, "blogVPC", {
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
    
       
    const clusterCustomImage = new ecs.Cluster(this, "MyBlogClusterCustomImage", {
      vpc: blogVpc
    });
    
    // const mySG = new ec2.SecurityGroup(this, `ecs-security-group`, {
    // vpc: blogVpc,
    // allowAllOutbound: true,
    // description: 'CDK Security Group'
    // });
    
    // mySG.addIngressRule(ec2.Peer.anyIpv4(), ec2.Port.tcp(17000), 'HL7 from anywhere');
    
    const destQueue = new Queue (this, "hl7Queue",{
      encryption: QueueEncryption.KMS_MANAGED,
      queueName: "hl7Queue"
    });


    const ecsTaskExecutionRole = new PolicyStatement({
      actions: ['logs:CreateLogStream', 'logs:PutLogEvents', 'sqs:SendMessage'],
      resources: [destQueue.queueArn], //restrict this later
    });
    
    const loadBalancedFargateServiceCustomImage = new ecs_patterns.NetworkLoadBalancedFargateService(this, "MyVpcFargateServiceCustomImage", {
      cluster: clusterCustomImage, // Required
      assignPublicIp: false, 
      cpu: 256, // Default is 256
      desiredCount: 2, // Default is 1
      taskImageOptions: { image: ecs.ContainerImage.fromAsset("./hl7connectorImage"),containerPort:parseInt(this.node.tryGetContext('hl7Port')), environment: {HL7_PORT: this.node.tryGetContext('hl7Port'),HL7_QUEUE: destQueue.queueArn}   },
      taskSubnets: {subnetGroupName: "privateSubnets"},
      listenerPort: parseInt(this.node.tryGetContext('hl7Port')),
      memoryLimitMiB: 512, // Default is 512
      publicLoadBalancer: true // Default is true
    });
    
    //clusterCustomImage.connections.addSecurityGroup
    
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
    
    
    loadBalancedFargateServiceCustomImage.service.connections.securityGroups[0].addIngressRule(ec2.Peer.ipv4("174.1.101.0/32"), ec2.Port.tcp(parseInt(this.node.tryGetContext('hl7Port'))), 'HL7 from Home');
    loadBalancedFargateServiceCustomImage.service.connections.securityGroups[0].addIngressRule(ec2.Peer.ipv4("10.0.0.0/16"), ec2.Port.tcp(parseInt(this.node.tryGetContext('hl7Port'))), 'HL7 from VPC');
    
    
    // new cdk.CfnOutput(this, "ecsServiceSecurityGroup", {
    //   value: loadBalancedFargateServiceCustomImage.service.connections.securityGroups[0].securityGroupId,
    //   exportName: "ecsServiceSecurityGroup",
    // });
    
  }
}
