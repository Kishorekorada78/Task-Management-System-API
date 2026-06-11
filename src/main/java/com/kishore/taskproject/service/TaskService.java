package com.kishore.taskproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kishore.taskproject.payload.TaskDTO;

@Service
public interface TaskService {

	public TaskDTO saveTask(long userid, TaskDTO taskdto);
	
	public List<TaskDTO> getAllTasks(long userid);
	
	public TaskDTO getTask(long userid,long taskid);
	
	public void deleteTask(long userid,long taskid);
	
}
