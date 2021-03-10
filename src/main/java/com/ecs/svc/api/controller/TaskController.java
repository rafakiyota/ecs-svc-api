package com.ecs.svc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.ecs.model.DescribeTaskDefinitionResult;
import com.amazonaws.services.ecs.model.RunTaskResult;
import com.ecs.svc.api.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	@GetMapping("/describe")
	public DescribeTaskDefinitionResult describeTask(@RequestParam String task) {
    	return taskService.describeTask(task);
	}
	
	@GetMapping("/run")
	public RunTaskResult runTask(
			@RequestParam String cluster, @RequestParam String task, 
			@RequestParam String subnetIds, @RequestParam String securityGroupIds) {
		
		return taskService.runTask(cluster, task, subnetIds, securityGroupIds);
	}
}
