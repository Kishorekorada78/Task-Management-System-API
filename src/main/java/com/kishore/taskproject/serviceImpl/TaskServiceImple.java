package com.kishore.taskproject.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kishore.taskproject.entity.Task;
import com.kishore.taskproject.entity.Users;
import com.kishore.taskproject.exception.ApiException;
import com.kishore.taskproject.exception.TaskNotFound;
import com.kishore.taskproject.exception.UserNotFound;
import com.kishore.taskproject.payload.TaskDTO;
import com.kishore.taskproject.repository.TaskRepositary;
import com.kishore.taskproject.repository.UserRepositary;
import com.kishore.taskproject.service.TaskService;

@Service
public class TaskServiceImple implements TaskService{

	@Autowired
	 private ModelMapper modelMapper;
	
	@Autowired
	private UserRepositary userRepo;
	
	@Autowired
	public TaskRepositary taskRepo;
	
	@Override
	public TaskDTO saveTask(long userid, TaskDTO taskdto) {
		// TODO Auto-generated method stub
    	Users user=	userRepo.findById(userid).orElseThrow(
    			()-> new UserNotFound(String.format("User Id %d not found", userid)));
		Task task=modelMapper.map(taskdto, Task.class);
		task.setUsers(user);
		// After setting the user, we are storing the data in DB
        Task saveTask=taskRepo.save(task);
		return modelMapper.map(saveTask, TaskDTO.class);
	}

	@Override
	public Page<TaskDTO> getAllTasks(
	        long userid,
	        int pageNo,
	        int pageSize,
	        String sortBy,
	        String sortDir) {

	    userRepo.findById(userid).orElseThrow(
	            () -> new UserNotFound(
	                    String.format(
	                            "User Id %d not found",
	                            userid)));
	    Sort sort=sortDir.equalsIgnoreCase("asc")
	    		? Sort.by(sortBy).ascending()
	    		: Sort.by(sortBy).descending();
	    Pageable pageable =
	            PageRequest.of(pageNo, pageSize,sort);

	    Page<Task> tasks =
	            taskRepo.findAllByUsersId(
	                    userid,
	                    pageable);

	    List<TaskDTO> taskDtos =
	            tasks.getContent()
	                 .stream()
	                 .map(task ->
	                      modelMapper.map(
	                              task,
	                              TaskDTO.class))
	                 .toList();

	    return new PageImpl<>(
	            taskDtos,
	            pageable,
	            tasks.getTotalElements());
	}
	@Override
	public TaskDTO getTask(long userid, long taskid) {
		Users user=	userRepo.findById(userid).orElseThrow(
    			()-> new UserNotFound(String.format("User Id %d not found", userid)));
	    Task task=taskRepo.findById(taskid).orElseThrow(
	    		()-> new TaskNotFound(String.format("Task id %d not Found",  taskid)));
	    if(user.getId()!=task.getUsers().getId()) {
	    	throw new ApiException(String.format("Task Id %d is not belongs to User Id %d", taskid,userid));
	    }
	    
		return modelMapper.map(task, TaskDTO.class);
	}

	@Override
	public void deleteTask(long userid, long taskid) {
		Users user=	userRepo.findById(userid).orElseThrow(
    			()-> new UserNotFound(String.format("User Id %d not found", userid)));
	    Task task=taskRepo.findById(taskid).orElseThrow(
	    		()-> new TaskNotFound(String.format("Task id %d not Found",  taskid)));
	    if(user.getId()!=task.getUsers().getId()) {
	    	throw new ApiException(String.format("Task Id %d is not belongs to User Id %d", taskid,userid));
	    }
	    
		taskRepo.deleteById(taskid);
		
	}

}
