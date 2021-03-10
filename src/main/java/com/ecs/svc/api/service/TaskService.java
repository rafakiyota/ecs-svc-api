package com.ecs.svc.api.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.amazonaws.services.ecs.AmazonECS;
import com.amazonaws.services.ecs.AmazonECSClientBuilder;
import com.amazonaws.services.ecs.model.AssignPublicIp;
import com.amazonaws.services.ecs.model.AwsVpcConfiguration;
import com.amazonaws.services.ecs.model.DescribeTaskDefinitionRequest;
import com.amazonaws.services.ecs.model.DescribeTaskDefinitionResult;
import com.amazonaws.services.ecs.model.NetworkConfiguration;
import com.amazonaws.services.ecs.model.RunTaskRequest;
import com.amazonaws.services.ecs.model.RunTaskResult;

@Service
public class TaskService {

	public DescribeTaskDefinitionResult describeTask(String taskName) {

		DescribeTaskDefinitionRequest requestTask = new DescribeTaskDefinitionRequest().withTaskDefinition(taskName);

		AmazonECS client = AmazonECSClientBuilder.standard().build();
		return client.describeTaskDefinition(requestTask);
	}

	public RunTaskResult runTask(String clusterName, String taskName, String subnetIds, String securityGroupIds) {

		List<String> subnetids = Stream.of(subnetIds.split(",")).map(elem -> new String(elem))
				.collect(Collectors.toList());

		List<String> securitygroupids = Stream.of(securityGroupIds.split(",")).map(elem -> new String(elem))
				.collect(Collectors.toList());

		AwsVpcConfiguration vpc = new AwsVpcConfiguration().withAssignPublicIp(AssignPublicIp.ENABLED)
				.withSubnets(subnetids).withSecurityGroups(securitygroupids);

		NetworkConfiguration network = new NetworkConfiguration().withAwsvpcConfiguration(vpc);

		RunTaskRequest request = new RunTaskRequest().withCluster(clusterName).withTaskDefinition(taskName)
				.withNetworkConfiguration(network).withLaunchType("FARGATE");

		AmazonECS client = AmazonECSClientBuilder.standard().build();
		return client.runTask(request);
	}
}
