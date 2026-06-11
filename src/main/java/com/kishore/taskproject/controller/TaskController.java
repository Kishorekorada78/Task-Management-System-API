package com.kishore.taskproject.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kishore.taskproject.payload.TaskDTO;
import com.kishore.taskproject.service.TaskService;

@RestController
@RequestMapping("/api")
public class TaskController {
		
	@Autowired
	private TaskService taskService;
	
	// save the task
	 @PostMapping("/{userid}/tasks")
	 public ResponseEntity<TaskDTO> saveTask(
		 @PathVariable(name="userid") long userid,
		 @RequestBody TaskDTO taskDto
		){
		// taskService.saveTask(userid, taskDto);
		 return new ResponseEntity<>(taskService.saveTask(userid, taskDto),HttpStatus.CREATED);
	 }
	 
	 @PreAuthorize(value="ROLE_USER")
	 
	// get all task
	 @GetMapping("/{userid}/tasks")
	 public ResponseEntity<List<TaskDTO>> getAllTasks(
			 @PathVariable(name="userid") long userid){
		 return new ResponseEntity<>(taskService.getAllTasks(userid),HttpStatus.OK) ;
	 }
	 
	// get indv task
	 @GetMapping("/{userid}/tasks/{taskid}")
	  public ResponseEntity<TaskDTO> getTask(
			  @PathVariable(name="userid") long userid,
			  @PathVariable(name="taskid") long taskid){
		 return new ResponseEntity<>(taskService.getTask(userid, taskid),HttpStatus.OK);
	 }
	 
	 @PreAuthorize(value="ROLE_ADMIN")
	// delete indv task
	 @DeleteMapping("/{userid}/tasks/{taskid}")
	 public ResponseEntity<Map<String, String>> deleteTask(
			  @PathVariable(name="userid") long userid,
			  @PathVariable(name="taskid") long taskid){
		taskService.deleteTask(userid, taskid);
		 //return new ResponseEntity<String>("user deleted successfully",HttpStatus.OK);
		return ResponseEntity.ok(Map.of("message", "task deleted successfully"));
	 }
}
