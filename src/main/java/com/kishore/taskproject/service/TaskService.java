package com.kishore.taskproject.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.kishore.taskproject.payload.TaskDTO;

@Service
public interface TaskService {

	public TaskDTO saveTask(long userid, TaskDTO taskdto);
	
	public Page<TaskDTO> getAllTasks(long userid,int pageNo,int pageSize,String sortBy,String sortDir);
	
	public TaskDTO getTask(long userid,long taskid);
	
	public void deleteTask(long userid,long taskid);
	
}
